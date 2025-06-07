package cn.asmer.lab.methodinsn.common;

import cn.asmer.lab.common.CommonUtil;
import cn.asmer.lab.common.JarLoader;
import org.objectweb.asm.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

/**
 * Caused by: java.lang.SecurityException: Prohibited package name: java.io
 * 上述问题可以通过arthas的redefine对类进行重新定义
 */
public class PrintlnMain {
//    private static String methodName = "println";
//    private static String descriptorOfMethod = "(Ljava/lang/String;)V";

    private static String methodName = "toString";
    private static String descriptorOfMethod = "()Ljava/lang/String;";


    public static void main(String[] args) throws Exception {
//        String binaryName = "cn.asmer.lab.buildcreate.TestGenerateClassMain";
//        String binaryName = "java.io.PrintStream";
        String binaryName = "java.lang.StringBuilder";
        InputStream stream = JarLoader.loadClassStreamFromClassPath(binaryName);
        byte[] bytes = CommonUtil.convertToBytes(stream);

        ClassReader cr = new ClassReader(bytes);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals(methodName) && descriptor.equals(descriptorOfMethod)) {
//                if (name.equals(methodName)) {
                    return new MethodVisitor(Opcodes.ASM6, mv) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
//                            visitVarInsn(Opcodes.ALOAD, 1);
                            visitMethodInsn(Opcodes.INVOKESTATIC, "FriendHelper", "printStack", "(Ljava/lang/String;)V", false);
                            //visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(Ljava/lang/String;)V", false);
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, 0);
        byte[] modifiedBytecode =  cw.toByteArray();
        CommonUtil.writeBytesToClassFile(modifiedBytecode, binaryName);

        Class modifiedClass = JarLoader.loadClass(modifiedBytecode);
        Object instance = modifiedClass.newInstance();
        System.out.println(String.valueOf(instance));

//        java.lang.reflect.Method method = modifiedClass.getDeclaredMethod(methodName, String.class);
//        method.invoke(instance, "true3333333");
    }
}
