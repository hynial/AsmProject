package cn.asmer.lab.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class CommonUtil {
    public static byte[] getBytecode(String className) throws IOException {
        String userDir = System.getProperty("user.dir");
        String toSourcePath = userDir + File.separator + "Field" + File.separator + "bytecodedir";

        String clazzPath = toSourcePath + File.separator + className.replaceAll("[.]", "/");
        return Files.readAllBytes(Paths.get(clazzPath + ".class"));
    }

    public static void writeBytesToClassFile(byte[] bytes, String fileName) throws IOException {
        fileName = fileName.replace('.', '/');
        if (fileName.endsWith("/class")) {
            fileName = fileName.replace("/class", ".class");
        } else {
            fileName += ".class";
        }

        File file = new File(fileName);

        if (!fileName.startsWith("/")) {
            file = new File(System.getProperty("user.dir") + File.separator + "Field" + File.separator + "bytecodedir" + File.separator + fileName);
        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(bytes);
        }
    }

    public static byte[] convertToBytes(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }

    public static void loadClassFileOfJarToDirectory(String jarPath, String className, String targetSourceDir) throws IOException {
        JarLoader.loadJar(Arrays.asList(jarPath));
        InputStream inputStream = JarLoader.loadClassStreamFromClassPath(className);

        File file = new File(targetSourceDir + File.separator + className.replace('.', '/') + ".class");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String targetSourceDir = System.getProperty("user.dir") + File.separator + "Field" + File.separator + "bytecodedir";
//        String jarPath = "file:///Users/hynial/IdeaProjects/ByteInstrument/AsmProject/0output/JarInst-1.0-SNAPSHOT.jar";
        String jarPath = "/Users/hynial/IdeaProjects/ByteInstrument/AsmProject/JarInst/target/JarInst-1.0-SNAPSHOT.jar";
        loadClassFileOfJarToDirectory(jarPath, "cn.jarinst.JarTest", targetSourceDir);
        loadClassFileOfJarToDirectory(jarPath, "cn.jarinst.Main", targetSourceDir);
    }
}
