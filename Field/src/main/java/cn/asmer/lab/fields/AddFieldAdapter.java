package cn.asmer.lab.fields;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import static org.objectweb.asm.Opcodes.ASM6;

public class AddFieldAdapter extends ClassVisitor {
    private String fieldName;
    private Integer fieldDefault = 100;
    private int access = org.objectweb.asm.Opcodes.ACC_PUBLIC;
    private boolean isFieldPresent;

    public AddFieldAdapter(String fieldName, int fieldAccess, ClassVisitor cv, Integer fieldDefault) {
        super(ASM6, cv);
        this.cv = cv;
        this.fieldName = fieldName;
        this.access = fieldAccess;
        this.fieldDefault = fieldDefault;
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (name.equals(fieldName)) {
            isFieldPresent = true;
        }
        return cv.visitField(access, name, desc, signature, value);
    }

    @Override
    public void visitEnd() {
        if (!isFieldPresent) {
            String fieldType = "I";
            FieldVisitor fv = cv.visitField(access, fieldName, fieldType, null, this.fieldDefault);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }
}