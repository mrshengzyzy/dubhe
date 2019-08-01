package space.zyzy.dubhe.javastructure;

public class ThreadLocalDemo {

    private static ThreadLocal<String> test = new ThreadLocal<>();

    public static void main(String[] args) {

        test.set("testThreadLocal");
        System.out.println(test.get());
    }
}
