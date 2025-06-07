package cn.asmer.lab.localvar;

import java.lang.instrument.*;
import java.security.ProtectionDomain;
import org.objectweb.asm.*;

/**
 * 修改局部变量的值 - 读区入栈操作数
 * MethodVisitor > visitIntInsn
 */
public class ExampleAgent implements ClassFileTransformer {
    private static final String TRANSFORM_CLASS = "cn/asmer/lab/localvar/Multiplier";
    private static final String TRANSFORM_METHOD_NAME = "main";
    private static final String TRANSFORM_METHOD_DESC = "([Ljava/lang/String;)V";

    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("premain------");
        instrumentation.addTransformer(new ExampleAgent());
    }

    public static void agentmain(String args, Instrumentation inst) {
        premain(args,inst) ;
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> cl,
                            ProtectionDomain pd, byte[] classfileBuffer) {
        // System.out.println(className);
        if(!TRANSFORM_CLASS.equals(className)) return null;

        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, 0);
        cr.accept(new ClassVisitor(Opcodes.ASM6, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc,
                                             String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(
                        access, name, desc, signature, exceptions);
                if(name.equals(TRANSFORM_METHOD_NAME)
                        && desc.equals(TRANSFORM_METHOD_DESC)) {
                    return new MethodVisitor(Opcodes.ASM6, mv) {
                        @Override
                        public void visitIntInsn(int opcode, int operand) {
                            if(opcode == Opcodes.BIPUSH && operand == 10) operand = 1;
                            super.visitIntInsn(opcode, operand);
                        }
                    };
                }
                return mv;
            }
        }, 0);
        return cw.toByteArray();
    }
}