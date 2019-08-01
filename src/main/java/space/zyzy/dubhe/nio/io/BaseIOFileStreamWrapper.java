package space.zyzy.dubhe.nio.io;

import java.io.*;

/**
 * 包装流
 * 将字节流包装成字符流
 */
public class BaseIOFileStreamWrapper {

    public static void main(String[] args) throws IOException {

        // 待操作的文件
        File file = new File("E:/dubhe/src/main/resources/data.txt");

        // 字节流
        InputStream is = new FileInputStream(file);

        // 将字节流包装为字符流
        Reader reader = new InputStreamReader(is);

        // 然后按照字符流方式读取
        char[] byteArray = new char[(int) file.length()];
        int size = reader.read(byteArray);

        System.out.println("文件大小:" + size);
        System.out.println("文件内容:" + new String(byteArray));

        // 关闭所有流
        reader.close();
        is.close();

        // 同样的我们也可以把输出字节流包装为输入字符流
        OutputStream os = new FileOutputStream(file);
        Writer wr = new OutputStreamWriter(os);
        wr.write("追加在文件后面");
        wr.close();
        os.close();
    }

}
