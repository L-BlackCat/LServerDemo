package nio.demo;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    public static void main(String[] args) throws IOException {
        String relativelyPath = System.getProperty("user.dir") + "/resources/";
        RandomAccessFile randomAccessFile = new RandomAccessFile(relativelyPath + "textin.txt", "rw");

        //  获取对应通道
        FileChannel channel = randomAccessFile.getChannel();


        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'H');
        mappedByteBuffer.put(3, (byte) '9');
//        mappedByteBuffer.put(5, (byte) 'Y');    //indexOutOfBoundsException

        while (mappedByteBuffer.hasRemaining()) {
            System.out.println((char) mappedByteBuffer.get());
        }

        randomAccessFile.close();

        System.out.println("修改成功~");


    }
}
