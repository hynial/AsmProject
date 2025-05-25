package cn.asmer.lab.methodinsn;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodReplacer extends ClassVisitor implements Opcodes {
    private String mname;
    private String mdesc;
    private String cname;

    public MethodReplacer(ClassVisitor cv, String mname, String mdesc, String cname) {
        super(Opcodes.ASM6, cv);
        this.mname = mname;
        this.mdesc = mdesc;
        this.cname = cname;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals(mname) && desc.equals(mdesc)) {
            generateNewBody(access, desc, signature, exceptions, name);

            return super.visitMethod(access, this.cname, desc, signature, exceptions); // 将原来的方法改名
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    private void generateNewBody(int access, String desc, String signature, String[] exceptions, String name) {
        // 替换原来的body
        MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, name, desc, signature, exceptions);
        mv.visitCode();
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("GOTit4567890");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitInsn(ICONST_0);
        mv.visitInsn(IRETURN);
        Label l3 = new Label();
        mv.visitLabel(l3);
        mv.visitLocalVariable("this", "LcheckASM/MethodCall;", null, l1, l3, 0);
        mv.visitLocalVariable("a", "Ljava/lang/String;", null, l1, l3, 1);
        mv.visitLocalVariable("b", "Z", null, l1, l3, 2);
        mv.visitLocalVariable("c", "Ljava/lang/String;", null, l1, l3, 3);
        mv.visitMaxs(4, 4);
        mv.visitEnd();
    }
}