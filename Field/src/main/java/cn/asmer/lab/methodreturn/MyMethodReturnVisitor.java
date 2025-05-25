package cn.asmer.lab.methodreturn;

// 高版本的ASM
//import org.objectweb.asm.commons.AdviceAdapter;
//public class MyMethodReturnVisitor extends AdviceAdapter {
//
//    private Type returnType;
//
//    protected MyMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
//        super(Opcodes.ASM7, methodVisitor, access, name, descriptor);
//        this.returnType = Type.getReturnType(descriptor);
//    }
//
//    @Override
//    public void onMethodExit(int opcode) {
//        if (opcode == Opcodes.ARETURN || opcode == Opcodes.IRETURN || opcode == Opcodes.LRETURN || opcode == Opcodes.FRETURN || opcode == Opcodes.DRETURN) {
//            // Duplicate the return value on the stack
//            dup(returnType);
//
//            // Store the return value in a local variable
//            int returnVar = newLocal(returnType);
//            storeLocal(returnVar);
//
//            // Load the return value for further processing (e.g., printing or modifying)
//            loadLocal(returnVar);
//
//            // Example: Print the return value (you can replace this with your logic)
//            if (returnType.equals(Type.INT_TYPE)) {
//                invokeStatic(Type.getType(System.class), new org.objectweb.asm.commons.Method("println", "(I)V"));
//            } else if (returnType.equals(Type.getType(String.class))) {
//                invokeStatic(Type.getType(System.class), new org.objectwebasm.commons.Method("println", "(Ljava/lang/String;)V"));
//            }
//            // Add more conditions for other return types as needed
//
//            // Restore the original return value for the method to return correctly
//            loadLocal(returnVar);
//        }
//    }
//}