package space.zyzy.dubhe.test;

public class OutOfMemoryMain {

    static final int SIZE = 2 * 1024 * 1024;

    public static void main(String[] a) {
        try {
            int[] i = new int[SIZE];
        } catch (Throwable e) {
            System.out.println("=======================");
        }
    }

}
