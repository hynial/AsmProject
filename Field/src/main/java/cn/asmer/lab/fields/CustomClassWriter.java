package cn.asmer.lab.fields;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class CustomClassWriter {
    static String className = "java.lang.Integer";
    static String cloneableInterface = "java/lang/Cloneable";
    private String fieldName = "aNewField";
    private Integer defaultValue = 1;

    AddFieldAdapter addFieldAdapter;

    ClassReader reader;
    ClassWriter writer;

    public CustomClassWriter(String newFieldName, Integer defaultValue) throws IOException {
        reader = new ClassReader(className);
        writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS); // ClassWriter.COMPUTE_MAXS)
        this.fieldName = newFieldName;
        this.defaultValue = defaultValue;
    }

    public byte[] addField() {
        addFieldAdapter = new AddFieldAdapter(this.fieldName, Opcodes.ACC_PUBLIC, writer, this.defaultValue);
        reader.accept(addFieldAdapter, 0);
        return writer.toByteArray();
    }
}
