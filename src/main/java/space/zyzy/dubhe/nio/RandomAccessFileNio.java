package space.zyzy.dubhe.nio;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * 基于 RandomAccessFile 的NIO
 */
public class RandomAccessFileNio {
    public static void main(String[] args) throws Exception {

        File file = new File("E:/dubhe/src/main/resources/data.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");

        // 实际上是每次返回两个字节
        System.out.println(randomAccessFile.readLine());


    }
}
