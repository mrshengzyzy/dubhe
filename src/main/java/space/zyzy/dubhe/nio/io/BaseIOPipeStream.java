package space.zyzy.dubhe.nio.io;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 管道流
 * 管道主要用来实现同一个虚拟机中的两个线程进行交流。
 * 需要注意的是java中的管道和Unix/Linux中的管道含义并不一样，在Unix/Linux中管道可以作为两个位于不同空间进程通信的媒介，
 * 而在java中，管道只能为同一个JVM进程中的不同线程进行通信。
 */
public class BaseIOPipeStream {

    public static void main(String[] args) throws IOException {

        // 分别定义输入与输出管道
        final PipedOutputStream output = new PipedOutputStream();
        final PipedInputStream input = new PipedInputStream(output);

        // 线程一写入数据
        Thread thread1 = new Thread(() -> {
            try {
                output.write("Hello 管道!".getBytes());
            } catch (IOException e) {
            }
        });

        // 线程二尝试不断读取数据
        Thread thread2 = new Thread(() -> {
            try {
                /*
                 * 控制台打印
                 * Hello ç®¡é!
                 * 因为我们按照字节读取原始内容,但是一个汉字并不是一个字节
                 */
                int data;
                while ((data = input.read()) != -1) {
                    System.out.print((char) data);
                }
                System.out.println();
            } catch (IOException e) {
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
