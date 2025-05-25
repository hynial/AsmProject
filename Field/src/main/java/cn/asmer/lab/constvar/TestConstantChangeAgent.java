package cn.asmer.lab.constvar;

import java.lang.instrument.*;
import java.security.ProtectionDomain;

import cn.asmer.lab.localvar.ExampleAgent;
import org.objectweb.asm.*;

/**
 * 修改静态常量值
 * ClassVisitor > FieldVisitor > visitField
 * MethodVisitor > visitLdcInsn
 * MethodVisitor
 */
public class TestConstantChangeAgent implements ClassFileTransformer {
//    private static final String TRANSFORM_CLASS = "cn.asmer.lab.constvar.TestConstantChange";
    private static final String TRANSFORM_CLASS = "cn/asmer/lab/constvar/TestConstantChange";
    private static final String TRANSFORM_METHOD_NAME = "main";
    private static final String TRANSFORM_METHOD_DESC = "([Ljava/lang/String;)V";

    public static void premain(String arg, Instrumentation instrumentation) {
        instrumentation.addTransformer(new TestConstantChangeAgent());
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> cl, ProtectionDomain pd, byte[] classfileBuffer) {
        if (className.indexOf("TestConstantChange") > -1) {
            System.out.println(className);
        }
        if(!TRANSFORM_CLASS.equals(className)) return null;

        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, 0);
        cr.accept(new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public FieldVisitor visitField(int access, String name, String desc,
                                           String signature, Object cst) {
                if("Hello World".equals(cst)) cst = "Multiply Of x*y is: ";
                return super.visitField(access, name, desc, signature, cst);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(
                        access, name, desc, signature, exceptions);
                if(name.equals(TRANSFORM_METHOD_NAME)
                        && desc.equals(TRANSFORM_METHOD_DESC)) {
                    return new MethodVisitor(Opcodes.ASM5, mv) {
                        @Override
                        public void visitLdcInsn(Object cst) {
                            if("Hello World".equals(cst)) cst = "Multiply Of x*y is: ";
                            super.visitLdcInsn(cst);
                        }
                    };
                }
                return mv;
            }
        }, 0);
        return cw.toByteArray();
    }
}
