package nio.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class NIOFileChannel04 {

    public static void main(String[] args) throws Exception {
        String relativelyPath = System.getProperty("user.dir") + "/resources/";

        //创建相关流
        FileInputStream fileInputStream = new FileInputStream(relativelyPath + "img.png");
        FileOutputStream fileOutputStream = new FileOutputStream(relativelyPath + "img2.png");

        //获取各个流对应的 FileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        //使用 transferForm 完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        //关闭相关通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}