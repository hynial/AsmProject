package cn.asmer.lab.methodinsn.replacemethod;

import cn.asmer.lab.common.CommonUtil;
import cn.asmer.lab.common.JarLoader;
import cn.asmer.lab.methodinsn.entity.MethodInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class StringBuilderToStringMain {

    public static void main(String[] args) {
        try {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.binaryName = "java.lang.StringBuilder";
            methodInfo.methodName = "toString";
            methodInfo.methodDesc = "()Ljava/lang/String;";
            methodInfo.keepOriginalMethodFlag = false;
            methodInfo.originalMethodRenameTo = "oldMethodRenameTo";

            System.out.println(methodInfo);
            StringBuilderToString mr = new StringBuilderToString(cw, methodInfo, "Preparing:");

            String binaryName = methodInfo.binaryName;
            ClassReader cr = new ClassReader(JarLoader.loadClassStreamFromClassPath(binaryName));
            cr.accept(mr, ClassReader.EXPAND_FRAMES);

            byte[] bytes = cw.toByteArray();
            CommonUtil.writeBytesToClassFile(bytes, binaryName);

            System.out.println("Done!");
        } catch (Exception e) {
            System.err.println("Ooops");
            e.printStackTrace();
        }
    }

}
