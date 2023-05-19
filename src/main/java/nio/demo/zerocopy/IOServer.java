package nio.demo.zerocopy;


import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

//服务器
public class IOServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(7001);

        while (true){
            Socket client = serverSocket.accept();
            System.out.println("已连接");
            InputStream inputStream = client.getInputStream();
            byte[] bytes = new byte[1024];
            int len = -1;
            StringBuilder retMsg = new StringBuilder();
            while((len = inputStream.read(bytes)) != -1){
                retMsg.append(new String(bytes));
            }
            System.out.println(retMsg);

        }

    }
}