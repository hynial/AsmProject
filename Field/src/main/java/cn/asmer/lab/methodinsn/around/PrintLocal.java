package cn.asmer.lab.methodinsn.around;

public class PrintLocal {
    public static void printLocalString(String l) {
        System.out.println("asdfsad" + l);
    }

    public static void printLocalString(int l) {
        System.out.println("asdfsad11" + l + "true");
    }
}
