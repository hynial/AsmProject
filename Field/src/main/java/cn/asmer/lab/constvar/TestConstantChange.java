package cn.asmer.lab.constvar;

public class TestConstantChange {
    private static final String a = "Hello World";
    private static final String b = "ASM is awasome";

    public static void main(String[] args) {
        int x = 10;
        int y = 25;
        int z = x * y;
        System.out.println(a);
        System.out.println(z);
        System.out.println(b);
    }
}
