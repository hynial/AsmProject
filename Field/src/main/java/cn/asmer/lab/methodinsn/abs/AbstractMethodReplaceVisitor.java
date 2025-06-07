package cn.asmer.lab.methodinsn.abs;

import cn.asmer.lab.methodinsn.entity.MethodInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public abstract class AbstractMethodReplaceVisitor extends ClassVisitor implements Opcodes {
    private String mname;
    private String mdesc;
    private String cname;
    private boolean keepOriginal = false;

    public AbstractMethodReplaceVisitor(ClassVisitor cv, MethodInfo methodInfo) {
        super(Opcodes.ASM6, cv);
        this.mname = methodInfo.methodName;
        this.mdesc = methodInfo.methodDesc;
        this.cname = methodInfo.originalMethodRenameTo;
        this.keepOriginal = methodInfo.keepOriginalMethodFlag;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals(mname) && desc.equals(mdesc)) {
            generateNewBody(access, name, desc, signature, exceptions);

            if (this.keepOriginal) {
                return super.visitMethod(access, this.cname, desc, signature, exceptions); // 将原来的方法改名
            }

            return null; // 将原来的方法丢弃
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    public abstract void generateNewBody(int access, String name, String desc, String signature, String[] exceptions);
}
