package cn.asmer.lab.common;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class JarLoader {
    public static void loadJar(List<String> jars) throws IOException {
        URL[] url = new URL[jars.size()];

        URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class urlClassLoaderClass = URLClassLoader.class;
        try {
            for(int i = 0; i < jars.size(); i++) {
                String jarPath = jars.get(i);
                if (jarPath.startsWith("file:")) {
                    url[i] = new URL(jars.get(i));
                } else {
                    url[i] = new File(jarPath).toURI().toURL();
                }
            }

            // 在原有的classloader中加上jar路径
            Method method = urlClassLoaderClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(systemClassLoader, url);

            // 而不是新增classloader
//            URLClassLoader classLoader = new URLClassLoader(url);
        } catch (Throwable ex) {
            System.out.println("URL error");
            throw new IOException("Error, could not add URL to system classloader");
        }

    }

    public static InputStream loadClassStreamFromClassPath(String binaryName) {
        // ClassLoader.getSystemClassLoader().getSystemResourceAsStream(binaryName.replace('.', '/') + ".class");
        return JarLoader.class.getClassLoader().
                getSystemResourceAsStream(binaryName.replace('.', '/') + ".class");
    }


    public static synchronized Class<?> loadClass(byte[] bytecode) throws Exception {
        ClassLoader scl = ClassLoader.getSystemClassLoader();
        Class<?>[] types = new Class<?>[]{ String.class, byte[].class, int.class, int.class };
        Object[] args = new Object[]{null, bytecode, 0, bytecode.length};

        Method defineClassMethod = ClassLoader.class.getDeclaredMethod("defineClass", types);
        defineClassMethod.setAccessible(true);

        Method m = defineClassMethod;
//        Method m = ClassLoader.class.getMethod("defineClass", types); // error ？

        m.setAccessible(true);
        return (Class<?>) m.invoke(scl, args);
    }

    // not working
    public static synchronized Class<?> defineClassAndLoad(byte[] modifiedBytecode, String className) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if (name.indexOf("Integer") > -1) {
                    System.out.println();
                }
                if (name.equals(className)) {
                    return defineClass(name, modifiedBytecode, 0, modifiedBytecode.length);
                }

                return super.findClass(name);
            }
        };

        Class<?> modifiedClass = classLoader.loadClass(className);
        return modifiedClass;
    }
}
