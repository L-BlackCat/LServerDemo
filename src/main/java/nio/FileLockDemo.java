package nio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockDemo {
    public static void main(String[] args) throws IOException {
        String input = "* end of the file.";
        System.out.println("Input string to the test file is: " + input);
        String relativelyPath = System.getProperty("user.dir");
        String fp = relativelyPath + "/resources/testout-file.txt";

        Path path = Paths.get(fp);

        ByteBuffer buffer = ByteBuffer.wrap(input.getBytes());

        FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);

        System.out.println("File channel is open for write and Acquiring lock...");

        channel.position(channel.size() - 1);

        FileLock lock = channel.lock();


        System.out.println("The Lock is shared: " + lock.isShared());

        channel.write(buffer);

        channel.close();    //释放锁

        System.out.println("Content Writing is complete. Therefore close the channel and release the lock.");


        print(fp);
    }


    public static void print(String path) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String msg;
        while((msg = bufferedReader.readLine()) != null){
            System.out.println(msg);
        }

        fileReader.close();
        bufferedReader.close();
    }
}
