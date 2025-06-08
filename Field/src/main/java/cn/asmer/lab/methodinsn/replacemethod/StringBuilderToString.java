package cn.asmer.lab.methodinsn.replacemethod;

import cn.asmer.lab.methodinsn.abs.AbstractMethodReplaceVisitor;
import cn.asmer.lab.methodinsn.entity.MethodInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

/**
 * 可以先用插件Asm Byte Code Viewer生成ASM代码
 * public String toString() {
 * 	    	String result = new String(value, 0, count);
 * 	    	if(result.contains("Preparing:")) {
 * 	    		System.err.println(result);
 * 	    		new Throwable().printStackTrace();
 * 	    	            }
 *         return result;
 *
 *     }
 */
public class StringBuilderToString extends AbstractMethodReplaceVisitor {
    private String key = "key";
    public StringBuilderToString(ClassVisitor cv, MethodInfo methodInfo, String key) {
        super(cv, methodInfo);
        this.key = key;
    }

    @Override
    public void generateNewBody(List<String> descList, int access, String name, String desc, String signature, String[] exceptions) {
        // 替换原来的body
        MethodVisitor methodVisitor = cv.visitMethod(ACC_PUBLIC, "toString", "()Ljava/lang/String;", null, null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        methodVisitor.visitLabel(label0);
        methodVisitor.visitTypeInsn(NEW, "java/lang/String");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, "java/lang/StringBuilder", "value", "[C");
        methodVisitor.visitInsn(ICONST_0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, "java/lang/StringBuilder", "count", "I");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/String", "<init>", "([CII)V", false);
        methodVisitor.visitVarInsn(ASTORE, 1);
        Label label1 = new Label();
        methodVisitor.visitLabel(label1);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitLdcInsn(this.key);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "contains", "(Ljava/lang/CharSequence;)Z", false);
        Label label2 = new Label();
        methodVisitor.visitJumpInsn(IFEQ, label2);
        Label label3 = new Label();
        methodVisitor.visitLabel(label3);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/lang/System", "err", "Ljava/io/PrintStream;");
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        Label label4 = new Label();
        methodVisitor.visitLabel(label4);
        methodVisitor.visitTypeInsn(NEW, "java/lang/Throwable");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Throwable", "<init>", "()V", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "printStackTrace", "()V", false);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitFrame(Opcodes.F_APPEND, 1, new Object[]{"java/lang/String"}, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitInsn(ARETURN);
        Label label5 = new Label();
        methodVisitor.visitLabel(label5);
        methodVisitor.visitLocalVariable("this", "Ljava/lang/StringBuilder;", null, label0, label5, 0);
        methodVisitor.visitLocalVariable("result", "Ljava/lang/String;", null, label1, label5, 1);
        methodVisitor.visitMaxs(5, 2);
        methodVisitor.visitEnd();
    }

}
