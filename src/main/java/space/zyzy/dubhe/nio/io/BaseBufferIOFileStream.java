package space.zyzy.dubhe.nio.io;

import java.io.*;

/**
 * 在进行磁盘或网络IO时，原始的InputStream对数据读取的过程都是一个字节一个字节操作的。
 * 而BufferedInputStream在其内部提供了一个buffer，在读数据时，会一次读取一大块数据到buffer中以提高效率。
 * 注意：
 * Buffer流并不等同于字符流,仅仅是提供了Buffer功能。字符流也可以被包装为Buffer流。
 * 如果没有缓冲，则每次调用 write() 方法会导致将字符转换为字节，然后立即写入到文件，而这是极其低效的。
 * 有了Buffer后只有达到Buffer值或者调用flush方法时才真正送往底层处理。
 */
public class BaseBufferIOFileStream {

    /**
     * 字节流Buffer
     */
    static class BufferedStream {

        public static void main(String[] args) throws Exception {
            File f = new File("E:/dubhe/src/main/resources/data.txt");

            // 4个字节为一个buffer
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f), 4);
            byte[] apple = new byte[]{'B', 'U', 'F', 'F', 'E', 'R', '=', 'B', 'U', 'F', 'F', 'E', 'R'};
            bos.write(apple);

            // 立刻写入文件
            bos.flush();
            bos.close();

            // 1024个字节为一个buffer
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f), 1024);
            byte[] byteArray = new byte[(int) f.length()];
            int size = bis.read(byteArray);
            System.out.println("文件大小:" + size + "\r\n文件内容:" + new String(byteArray));
            bis.close();
        }
    }

    /**
     * 字符流Buffer
     */
    static class Buffered {

        public static void main(String[] args) throws Exception {
            File f = new File("E:/dubhe/src/main/resources/data.txt");

            // buffer为1024字节,当写满buffer后才真正写入文件
            BufferedWriter bw = new BufferedWriter(new FileWriter(f), 1024);
            bw.write("Buffered 读取文件内容");

            // 立刻写入文件
            bw.flush();
            bw.close();

            // 每次读1024个字节到buffer中,操作完缓冲数据之后再进行下一次读取
            BufferedReader br = new BufferedReader(new FileReader(f), 1024);

            // 每次处理4个字符
            StringBuilder content = new StringBuilder();
            char[] c;
            while (br.read(c = new char[4]) != -1) {
                content.append(c);
            }
            System.out.println("文件内容:" + content.toString());
            br.close();
        }
    }
}
