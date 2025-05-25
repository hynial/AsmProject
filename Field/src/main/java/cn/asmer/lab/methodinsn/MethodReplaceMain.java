package cn.asmer.lab.methodinsn;

import cn.asmer.lab.common.CommonUtil;
import cn.asmer.lab.common.JarLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

// https://stackoverflow.com/questions/11770353/java-asm-bytecode-modification-changing-method-bodies

/**
 * 加载jar包并读取Jar包中的class文件，做方法体替换，并把旧的方法取别名
 */
public class MethodReplaceMain {
    public static void main(String[] args) {
        try {
            String oldMethodRenameTo = "oldMethodRenameTo";
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            MethodReplacer mr = new MethodReplacer(cw, "Test", "(Ljava/lang/String;ZLjava/lang/String;)Z", oldMethodRenameTo);

            String binaryName = "cn.jarinst.JarTest";
            List<String> jars = Arrays.asList("file:///Users/hynial/IdeaProjects/ByteInstrument/AsmProject/0output/JarInst-1.0-SNAPSHOT.jar");
            JarLoader.loadJar(jars);

            ClassReader cr = new ClassReader(JarLoader.loadClassStreamFromClassPath(binaryName));
            cr.accept(mr, ClassReader.EXPAND_FRAMES);

            byte[] bytes = cw.toByteArray();
            CommonUtil.writeBytesToClassFile(bytes, binaryName);

            Class clazz = JarLoader.loadClass(bytes); // replace JarTest's class in the jar... from memory.

            Method oldMethod = clazz.getMethod(oldMethodRenameTo, String.class, boolean.class, String.class);
            oldMethod.invoke(clazz.newInstance(), "a", true, "c");

            Method newBodyMethod = clazz.getMethod("Test", String.class, boolean.class, String.class);
            newBodyMethod.invoke(clazz.newInstance(), "a", true, "c");

            System.out.println("----------------FromJarMain-----------");
            // Main method call JarTest's Test method which have been replaced.
            Class.forName("cn.jarinst.Main").getMethod("main", new Class<?>[]{args.getClass()})
                    .invoke(null, new Object[]{args});

            System.out.println("Done!");
        } catch (Exception e) {
            System.err.println("Ooops");
            e.printStackTrace();
        }
    }
}
