package cn.asmer.lab.fields;

import cn.asmer.lab.common.CommonUtil;
import cn.asmer.lab.common.JarLoader;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * 给Integer增加字段 - 字节码中有字段没有默认值（NotWorking）
 */
public class TestFieldAddMain {
    public static void main(String[] args) {
        try {
            CustomClassWriter customClassWriter = new CustomClassWriter("aNewField", 10);
            byte[] bytes = customClassWriter.addField();

            // to file
            CommonUtil.writeBytesToClassFile(bytes, "/Users/hynial/IdeaProjects/ByteInstrument/AsmProject/Field/bytecodedir/tmp/Integer");
            Class<?> clazz = JarLoader.defineClassAndLoad(bytes, "java.lang.Integer");
//            Class<?> clazz = JarLoader.loadClass(bytes);
            Object instance = clazz.getDeclaredConstructor(int.class).newInstance(1);
            Field field = clazz.getDeclaredField("aNewField");
            Object v = field.get(instance);
            System.out.println(v);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
