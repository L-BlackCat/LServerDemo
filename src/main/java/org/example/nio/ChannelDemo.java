package org.example.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelDemo {
    public static void main(String[] args) throws FileNotFoundException {
        String relativelyPath = System.getProperty("user.dir");

        try{
            FileInputStream input = new FileInputStream(relativelyPath + "/resources/textin.txt");

            FileOutputStream output = new FileOutputStream(relativelyPath + "/resources/textout.txt");

            ReadableByteChannel readChannel = input.getChannel();
            WritableByteChannel writeChannel = output.getChannel();


            ByteBuffer buffer = ByteBuffer.allocate(58);
            while(readChannel.read(buffer) != -1){
                buffer.flip();
                while(buffer.hasRemaining()){
                    int len = writeChannel.write(buffer);
                    System.out.println(len);

                }
                buffer.clear();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
