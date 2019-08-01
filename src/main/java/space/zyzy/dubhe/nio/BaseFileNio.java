package space.zyzy.dubhe.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用NIO拷贝文件
 */
public class BaseFileNio {

    public static void main(String[] args) throws Exception {

        // 声明源文件与目标文件
        File file = new File("E:/dubhe/src/main/resources/data.txt");
        File copy = new File("E:/dubhe/src/main/resources/copy.txt");

        // 从源文件读取内容,向目标文件写入内容
        FileInputStream fi = new FileInputStream(file);
        FileOutputStream fo = new FileOutputStream(copy);

        // 获得传输通道channel
        FileChannel inChannel = fi.getChannel();
        FileChannel outChannel = fo.getChannel();

        // 获得容器buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {

            //判断是否读完文件
            int eof = inChannel.read(buffer);
            if (eof == -1) {
                break;
            }

            // 写模式转换为读模式 limit=position,position=0
            buffer.flip();

            // 开始写
            outChannel.write(buffer);

            // 写完要重置buffer，重设position=0,limit=capacity
            buffer.clear();
        }

        // 关闭所有资源
        inChannel.close();
        outChannel.close();
        fi.close();
        fo.close();
    }
}
