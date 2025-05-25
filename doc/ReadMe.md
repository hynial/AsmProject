# The ClassVisitor methods in the event-based API are called in the following order:

visit
visitSource?
visitOuterClass?
( visitAnnotation | visitAttribute )*
( visitInnerClass | visitField | visitMethod )*
visitEnd

# https://www.alibabacloud.com/blog/how-to-use-java-agents-with-asm-bytecode-framework_596199

# Try Spring

# Call the method:
Use the appropriate INVOKEx instruction to call the method.
INVOKEVIRTUAL: For instance methods.
INVOKESPECIAL: For constructor and private methods.
INVOKESTATIC: For static methods.
INVOKEINTERFACE: For interface methods.

// Add the new field
FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, fieldName, fieldDescriptor, null, null);
fieldVisitor.visitEnd();

// Default constructor
MethodVisitor constructorVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
constructorVisitor.visitCode();
constructorVisitor.visitVarInsn(Opcodes.ALOAD, 0);
constructorVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
constructorVisitor.visitInsn(Opcodes.RETURN);
constructorVisitor.visitMaxs(1, 1);
constructorVisitor.visitEnd();