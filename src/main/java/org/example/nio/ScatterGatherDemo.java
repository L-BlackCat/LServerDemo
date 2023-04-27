package org.example.nio;

import io.netty.buffer.ByteBuf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ScatterGatherDemo {
    //  通道对多个缓冲区

    public static void main(String[] args) {
        String relativelyPath = System.getProperty("user.dir");
        ByteBuffer buffer1 = ByteBuffer.allocate(8);
        ByteBuffer buffer2 = ByteBuffer.allocate(400);

        String  data = "hello world! welcome to China.";
        buffer1.asIntBuffer().put(420);
        buffer2.asCharBuffer().put(data);

        try(FileOutputStream output = new FileOutputStream(relativelyPath + "/resources/textout.txt")){
            GatheringByteChannel channel = output.getChannel();
            channel.write(new ByteBuffer[]{buffer1,buffer2});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        try(FileInputStream output = new FileInputStream(relativelyPath + "/resources/textout.txt")){
            ScatteringByteChannel channel = output.getChannel();
            channel.read(new ByteBuffer[]{buffer1,buffer2});

            buffer1.rewind();
            buffer2.rewind();

            int bufferOne = buffer1.asIntBuffer().get();
            String bufferTwo = buffer2.asCharBuffer().toString().trim();
            System.out.println(bufferOne);
            System.out.println(bufferTwo);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }



}
