package cn.asmer.lab.methodinsn.abs;

import cn.asmer.lab.methodinsn.entity.MethodInfo;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public abstract class AbstractMethodReplaceVisitor extends ClassVisitor implements Opcodes {
    protected String keyData;
    private String mname;
    private List<String> descList;
    private String cname;
    private boolean keepOriginal = false;

    public AbstractMethodReplaceVisitor(ClassVisitor cv, MethodInfo methodInfo) {
        super(Opcodes.ASM6, cv);
        this.mname = methodInfo.methodName;
        this.descList = methodInfo.methodDescList;
        this.cname = methodInfo.originalMethodRenameTo;
        this.keepOriginal = methodInfo.keepOriginalMethodFlag;
        this.keyData = methodInfo.keyData;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        if (name.equals(mname)) {
            generateNewBody(this.descList, access, name, desc, signature, exceptions);

            if (this.keepOriginal && this.descList.size() == 1) {
                return super.visitMethod(access, this.cname, desc, signature, exceptions); // 将原来的方法改名
            }

            if (this.descList.contains(desc)) {
                return null; // 将原来的方法丢弃
            }
        }

        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    public abstract void generateNewBody(List<String> descList, int access, String name, String desc, String signature, String[] exceptions);
}
