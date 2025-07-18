package cn.asmer.lab.buildcreate;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

/**
 * 生成一个class文件
 */
public class TestGenerateClassMain {
    public void main2(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "GeneratedClass", null, "java/lang/Object", null);

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello world!");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        cw.visitEnd();

        // Write the bytes as a class file
        byte[] bytes = cw.toByteArray();
        try (FileOutputStream stream = new FileOutputStream("GeneratedClass.class")) {
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // java GeneratedClass
    }
}
