package nio.demo.zerocopy;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NewIOClient {

    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        socketChannel.configureBlocking(false);
        String filename = "D:\\env\\protoc-22.3-win64.zip";
        filename = "D:\\env\\Java.zip";
        //得到一个文件channel
        FileChannel fileChannel = FileChannel.open(Paths.get(filename), StandardOpenOption.CREATE_NEW,StandardOpenOption.READ);
        //准备发送
        long startTime = System.currentTimeMillis();
        //在 linux 下一个 transferTo 方法就可以完成传输
        //在 windows 下一次调用 transferTo 只能发送 8m, 就需要分段传输文件,而且要主要
        //传输时的位置=》课后思考...
        //transferTo 底层使用到零拷贝
        long totalCount = fileChannel.size();
        long sendCount = 0;
        while (sendCount < totalCount){
            long transferCount = fileChannel.transferTo(sendCount, fileChannel.size(), socketChannel);
            sendCount = sendCount + transferCount;
        }

        // 发送的总的字节数 = 220324320 耗时: 548
        System.out.println("发送的总的字节数 = " + totalCount + " 耗时: " + (System.currentTimeMillis() - startTime));

        Thread.sleep(20000);
        //关闭
        fileChannel.close();
    }
}