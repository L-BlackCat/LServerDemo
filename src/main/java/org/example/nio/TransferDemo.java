package org.example.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TransferDemo {
    //  通道对通道

    public static void main(String[] args) {
        String relativelyPath = System.getProperty("user.dir");
        String[] inFiles = new String[]{relativelyPath + "/resources/inputFile1.txt",relativelyPath + "/resources/inputFile2.txt",
                relativelyPath + "/resources/inputFile3.txt",relativelyPath + "/resources/inputFile4.txt"};

        String of = relativelyPath + "/resources/mergeFile.txt";

        try{
            FileOutputStream output = new FileOutputStream(of);
            FileChannel targetChannel = output.getChannel();

            for (String inFile : inFiles) {
                FileInputStream input = new FileInputStream(inFile);
                FileChannel inputChannel = input.getChannel();
                inputChannel.transferTo(0,inputChannel.size(),targetChannel);
                inputChannel.close();
                input.close();
            }

            targetChannel.close();
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
