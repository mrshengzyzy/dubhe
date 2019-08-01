package space.zyzy.dubhe.aop.aspectjdemo;

/**
 * AspectJ 是一个java实现的AOP框架,它能够对java代码进行AOP编译
 * AspectJ 的核心就是acj编译器
 * 这篇文章比较好：https://juejin.im/entry/5a40abb16fb9a0451e400886
 */
public class RunThis {

    void sayHello() {
        System.out.println("hello world !");
    }

    public static void main(String args[]) {
        RunThis helloWord = new RunThis();
        helloWord.sayHello();
    }
}