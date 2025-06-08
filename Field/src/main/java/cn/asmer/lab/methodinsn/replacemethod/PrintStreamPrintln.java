package cn.asmer.lab.methodinsn.replacemethod;

import cn.asmer.lab.methodinsn.abs.AbstractMethodReplaceVisitor;
import cn.asmer.lab.methodinsn.entity.MethodInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class PrintStreamPrintln extends AbstractMethodReplaceVisitor {
    public PrintStreamPrintln(ClassVisitor cv, MethodInfo methodInfo) {
        super(cv, methodInfo);
    }

    @Override
    public void generateNewBody(List<String> descList, int access, String name, String desc, String signature, String[] exceptions) {
        if (descList.contains(desc)) {
            if ("(Ljava/lang/String;)V".equals(desc)) {
                MethodVisitor methodVisitor = cv.visitMethod(ACC_PUBLIC, "println", "(Ljava/lang/String;)V", null, null);
                methodVisitor.visitCode();
                Label label0 = new Label();
                Label label1 = new Label();
                Label label2 = new Label();
                methodVisitor.visitTryCatchBlock(label0, label1, label2, null);
                Label label3 = new Label();
                methodVisitor.visitTryCatchBlock(label2, label3, label2, null);
                Label label4 = new Label();
                methodVisitor.visitLabel(label4);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitVarInsn(ASTORE, 2);
                methodVisitor.visitInsn(MONITORENTER);
                methodVisitor.visitLabel(label0);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitLdcInsn(this.keyData);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z", false);
                Label label5 = new Label();
                methodVisitor.visitJumpInsn(IFEQ, label5);
                Label label6 = new Label();
                methodVisitor.visitLabel(label6);
                methodVisitor.visitTypeInsn(NEW, "java/lang/Throwable");
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "()V", false);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V", false);
                methodVisitor.visitLabel(label5);
                methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"java/lang/Object"}, 0, null);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
                Label label7 = new Label();
                methodVisitor.visitLabel(label7);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "newLine", "()V", false);
                Label label8 = new Label();
                methodVisitor.visitLabel(label8);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitInsn(MONITOREXIT);
                methodVisitor.visitLabel(label1);
                Label label9 = new Label();
                methodVisitor.visitJumpInsn(GOTO, label9);
                methodVisitor.visitLabel(label2);
                methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitInsn(MONITOREXIT);
                methodVisitor.visitLabel(label3);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitInsn(ATHROW);
                methodVisitor.visitLabel(label9);
                methodVisitor.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
                methodVisitor.visitInsn(RETURN);
                Label label10 = new Label();
                methodVisitor.visitLabel(label10);
                methodVisitor.visitLocalVariable("this", "Ljava/io/PrintStream;", null, label4, label10, 0);
                methodVisitor.visitLocalVariable("result", "Ljava/lang/String;", null, label4, label10, 1);
                methodVisitor.visitMaxs(2, 4);
                methodVisitor.visitEnd();
            }

            if ("(Ljava/lang/Object;)V".equals(desc)) {
                MethodVisitor methodVisitor = cv.visitMethod(ACC_PUBLIC, "println", "(Ljava/lang/Object;)V", null, null);
                methodVisitor.visitCode();
                Label label0 = new Label();
                Label label1 = new Label();
                Label label2 = new Label();
                methodVisitor.visitTryCatchBlock(label0, label1, label2, null);
                Label label3 = new Label();
                methodVisitor.visitTryCatchBlock(label2, label3, label2, null);
                Label label4 = new Label();
                methodVisitor.visitLabel(label4);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/String", "valueOf", "(Ljava/lang/Object;)Ljava/lang/String;", false);
                methodVisitor.visitVarInsn(ASTORE, 2);
                Label label5 = new Label();
                methodVisitor.visitLabel(label5);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitVarInsn(ASTORE, 3);
                methodVisitor.visitInsn(MONITORENTER);
                methodVisitor.visitLabel(label0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitLdcInsn(this.keyData);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z", false);
                Label label6 = new Label();
                methodVisitor.visitJumpInsn(IFEQ, label6);
                Label label7 = new Label();
                methodVisitor.visitLabel(label7);
                methodVisitor.visitTypeInsn(NEW, "java/lang/Throwable");
                methodVisitor.visitInsn(DUP);
                methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "()V", false);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V", false);
                methodVisitor.visitLabel(label6);
                methodVisitor.visitFrame(Opcodes.F_APPEND, 2, new Object[]{"java/lang/String", "java/lang/Object"}, 0, null);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "print", "(Ljava/lang/String;)V", false);
                Label label8 = new Label();
                methodVisitor.visitLabel(label8);
                methodVisitor.visitVarInsn(ALOAD, 0);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "newLine", "()V", false);
                Label label9 = new Label();
                methodVisitor.visitLabel(label9);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitInsn(MONITOREXIT);
                methodVisitor.visitLabel(label1);
                Label label10 = new Label();
                methodVisitor.visitJumpInsn(GOTO, label10);
                methodVisitor.visitLabel(label2);
                methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
                methodVisitor.visitVarInsn(ASTORE, 4);
                methodVisitor.visitVarInsn(ALOAD, 3);
                methodVisitor.visitInsn(MONITOREXIT);
                methodVisitor.visitLabel(label3);
                methodVisitor.visitVarInsn(ALOAD, 4);
                methodVisitor.visitInsn(ATHROW);
                methodVisitor.visitLabel(label10);
                methodVisitor.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
                methodVisitor.visitInsn(RETURN);
                Label label11 = new Label();
                methodVisitor.visitLabel(label11);
                methodVisitor.visitLocalVariable("this", "Ljava/io/PrintStream;", null, label4, label11, 0);
                methodVisitor.visitLocalVariable("var1", "Ljava/lang/Object;", null, label4, label11, 1);
                methodVisitor.visitLocalVariable("var2", "Ljava/lang/String;", null, label5, label11, 2);
                methodVisitor.visitMaxs(2, 5);
                methodVisitor.visitEnd();
            }

        }
    }
}
