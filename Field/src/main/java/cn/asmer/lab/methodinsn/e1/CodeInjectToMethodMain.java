package cn.asmer.lab.methodinsn.e1;

import org.objectweb.asm.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 方法执行前注入 方法调用
 * MethodVisitor > visitCode
 */
public class CodeInjectToMethodMain {

    public static void main(String[] args) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        String userDir = System.getProperty("user.dir");
        String toSourcePath = userDir + File.separator + "Field" + File.separator + "bytecodedir";
        String className = "cn.asmer.lab.methodinsn.e1.TargetClass";
        String clazzPath = toSourcePath + File.separator + className.replaceAll("[.]", "/");
        System.out.println(className);
        byte[] classBytes = Files.readAllBytes(Paths.get(clazzPath + ".class"));

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("myMethod")) {
                    return new MethodVisitor(Opcodes.ASM6, mv) {
                        @Override
                        public void visitCode() {
                            // Insert code to print "Hello from injected code!"
                            visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            visitLdcInsn("Hello from injected code!");
                            visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                            super.visitCode();
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, 0);

        byte[] modifiedClassBytes = cw.toByteArray();

        // Load the modified class using a custom class loader
        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) {
                if (name.indexOf("Target")> -1) {
                    System.out.println();
                }
                if (name.equals(className)) {
                    return defineClass(name, modifiedClassBytes, 0, modifiedClassBytes.length);
                }
                return null;
            }
        };

        Class<?> modifiedClass = classLoader.loadClass(className);
        Object instance = modifiedClass.getDeclaredConstructor().newInstance();
        modifiedClass.getMethod("myMethod").invoke(instance);
    }
}