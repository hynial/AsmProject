package cn.jarinst;

public class JarTest {
    public boolean Test(String a, boolean b, String c) {
        System.out.println("GOTit123");
        System.out.println(a + b + c);
        return false;
    }

    public static String testLocals(boolean one) {
        String two = "hello local variables";
        one = true;
        int three = 64;
        System.out.println(three);
        return two + "Three...";
    }
}
