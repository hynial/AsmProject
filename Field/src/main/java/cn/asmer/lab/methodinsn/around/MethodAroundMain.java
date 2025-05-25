package cn.asmer.lab.methodinsn.around;

import cn.asmer.lab.common.CommonUtil;
import org.objectweb.asm.*;

public class MethodAroundMain {

    public static void main(String[] args) throws Exception {

        String someClass = "cn.jarinst.JarTest";

        byte[] bytecode = CommonUtil.getBytecode(someClass);
        byte[] modifiedBytecode = modifyMethod(bytecode);

        // Load the modified class
        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return defineClass(name, modifiedBytecode, 0, modifiedBytecode.length);
            }
        };

        Class<?> modifiedClass = classLoader.loadClass(someClass);
//        Object instance = modifiedClass.getDeclaredConstructor().newInstance();

        // Invoke the method which has been modified
        java.lang.reflect.Method method = modifiedClass.getDeclaredMethod("testLocals", boolean.class);
        method.invoke(null, true);
    }

    public static byte[] modifyMethod(byte[] bytecode) {
        ClassReader cr = new ClassReader(bytecode);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM6, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("testLocals")) {
                    if ((access & Opcodes.ACC_STATIC) != 0) {
                        // This is a static method
                        System.out.println("Static method: " + name);
                    }
                    return new MethodVisitor(Opcodes.ASM6, mv) {
                        @Override
                        public void visitCode() {
                            super.visitCode();
                            // 方法前调用
                            visitVarInsn(Opcodes.ILOAD, 0); // integer boolean
                            visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(I)V", false);
//                            visitInsn(Opcodes.RETURN); // 是否结束方法返回
                        }

                        @Override
                        public void visitMaxs(int maxStack, int maxLocals) {
                            super.visitMaxs(Math.max(maxStack, 1), maxLocals); // Adjust maxStack
                        }

                        @Override
                        public void visitInsn(int opcode) {
                            //whenever we find a RETURN, we instert the code, here only crazy example code
                            switch (opcode) {
                                case Opcodes.IRETURN:
                                case Opcodes.FRETURN:
                                case Opcodes.ARETURN:
                                case Opcodes.LRETURN:
                                case Opcodes.DRETURN:
//                                    方法结束后调用
//                                    mv.visitVarInsn(Opcodes.ALOAD, 1);
//                                    visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(Ljava/lang/String;)V", false);
//                                    mv.visitVarInsn(Opcodes.ILOAD, 2);
//                                    visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(I)V", false);

                                    // 取得返回值 - 注意没有返回值的情况 排开 Opcodes.RETURN
                                    mv.visitInsn(Opcodes.DUP);
                                    visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(Ljava/lang/String;)V", false);
                                    break;
                                case Opcodes.RETURN:
                                    visitLdcInsn("------NoReturnValue------");
                                    visitMethodInsn(Opcodes.INVOKESTATIC, "cn.asmer.lab.methodinsn.around.PrintLocal".replaceAll("[.]", "/"), "printLocalString", "(Ljava/lang/String;)V", false);
                                    break;
                                default: // do nothing
                            }
                            super.visitInsn(opcode);
                        }
                    };
                }
                return mv;
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

}