package space.zyzy.dubhe.nio.io;

import java.io.*;

public class BaseIOFileStream {

    /**
     * 文件字节流
     */
    static class ByteFileStream {
        public static void main(String[] args) {
            try {

                // 因为是往文件中写,所以使用文件输出流
                File f = new File("E:/dubhe/src/main/resources/data.txt");
                OutputStream os = new FileOutputStream(f);

                // 写入多个字节
                byte[] apple = new byte[]{'A', 'P', 'P', 'L', 'E'};
                os.write(apple);

                // 写入回车与换行
                // 区别请参考：http://www.ruanyifeng.com/blog/2006/04/post_213.html
                os.write(13); // 回车
                os.write(10); // 换行

                // 再写入一个字母B
                os.write('B');
                os.close();

                System.out.println("===============================================");

                // 使用文件输入流从文件中一个字节一个字节读内容
                InputStream inputStream = new FileInputStream(f);
                int len = inputStream.available();
                for (int i = 0; i < len; i++) {
                    System.out.print((char) inputStream.read());
                }

                System.out.println();
                System.out.println("===============================================");

                // 使用文件输出流多字节读多字节读取内容
                File aFile = new File("E:/dubhe/src/main/resources/data.txt");
                InputStream is = new FileInputStream(aFile);
                StringBuilder sb = new StringBuilder();
                byte[] bytes = new byte[4];
                while (is.read(bytes) != -1) {
                    for (int i = 0; i < bytes.length; i++) {
                        // 不是-1表示是有效数据
                        if (bytes[i] != -1) {
                            sb.append((char) bytes[i]);
                        }
                        // "置空"bytes
                        bytes[i] = -1;
                    }
                }
                System.out.println(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件字符流
     */
    static class CharFileStream {
        public static void main(String[] args) {
            try {
                // 定义一个字符输出流
                Writer fileWriter = new FileWriter("E:/dubhe/src/main/resources/data.txt");

                // 直接写入字符串
                fileWriter.write("HELLO,世界");

                // 写入回车换行
                fileWriter.write("\r\n");

                // 再写入一个Char字符
                fileWriter.append('A');

                fileWriter.close();

                // 下面按照字符读取文件内容
                Reader fileReader = new FileReader("E:/dubhe/src/main/resources/data.txt");

                // 一个字符一个字符的读取
                // read放回的是字符代表的ASCII码,范围是0-65535(0x00-0xffff)
                int i;
                while ((i = fileReader.read()) != -1) {
                    System.out.print((char) i);
                }
                fileReader.close();

                System.out.println();
                System.out.println("===============================================");

                // 一个流只能读取一次
                Reader fileReader1 = new FileReader("E:/dubhe/src/main/resources/data.txt");

                // 使用char数组读取时要注意,避免上一次读取内容污染下一次读取的情况,一般发生再最后一次读取时长度不够一次读取的情况
                // 那么数组后面几位就是上一次读取的内容
                char[] chars;
                while (fileReader1.read(chars = new char[4]) != -1) {
                    System.out.println(new String(chars));
                }
                fileReader1.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
