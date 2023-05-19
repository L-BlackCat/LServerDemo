package nio.demo.zerocopy;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IOClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        String relativelyPath = System.getProperty("user.dir") + "/resources/";
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost",7001));
        String filename = "D:\\env\\java.zip";
//        filename = relativelyPath + "textin.txt";
        File file = new File(filename);
        System.out.println(file.exists());

        long startTime =System.currentTimeMillis();

        FileInputStream fis = new FileInputStream(file);
        OutputStream os = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int transferCount = 0;
        int len = -1;
        while ((len = fis.read(bytes)) != -1) {
            os.write(bytes);
            transferCount += len;
        }

        //  发送的总的字节数 = 220324320 耗时: 3199
        System.out.println("发送的总的字节数 = " + transferCount + " 耗时: " + (System.currentTimeMillis() - startTime));

        fis.close();
        os.close();
    }
}
