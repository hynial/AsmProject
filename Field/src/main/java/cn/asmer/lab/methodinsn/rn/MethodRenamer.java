package cn.asmer.lab.methodinsn.rn;

import cn.asmer.lab.common.CommonUtil;
import org.objectweb.asm.*;

import java.io.IOException;

public class MethodRenamer {
    public static void main(String[] args) throws IOException {
        String className = "cn.jarinst.JarTest"; // Replace with your class name
        String oldMethodName = "oldMethodRenameTo";
        String newMethodName = "anotherName";

        byte[] classBytes = CommonUtil.getBytecode(className);

        ClassReader cr = new ClassReader(classBytes);
        ClassWriter cw = new ClassWriter(cr, 0);

        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals(oldMethodName)) {
//                    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
//                    return new MethodVisitor(Opcodes.ASM6, mv) {
//                        @Override
//                        public void visitEnd() {
//                            cv.visitMethod(access, newMethodName, descriptor, signature, exceptions).visitEnd();
//                        }
//                    };
                    // 以上注释部分会增加一个别名的空方法。

                    return super.visitMethod(access, newMethodName, descriptor, signature, exceptions); // 将原来的方法改名
                }
                return super.visitMethod(access, name, descriptor, signature, exceptions);
            }
        };

        cr.accept(cv, 0);

        byte[] modifiedClassBytes = cw.toByteArray();
        CommonUtil.writeBytesToClassFile(modifiedClassBytes, "JarTest2");
    }
}
