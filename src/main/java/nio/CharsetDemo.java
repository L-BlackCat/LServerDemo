package nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetDemo {
    public static void main(String[] args) throws CharacterCodingException {
        Charset cs = Charset.forName("UTF-8");
        CharsetDecoder csdecoder = cs.newDecoder();
        CharsetEncoder csencoder = cs.newEncoder();
        String st = "Example of Encode and Decode in Java NIO.";
        ByteBuffer bb = ByteBuffer.wrap(st.getBytes());
        //  将数组或字节序列解码为unicode字符
        CharBuffer cb = csdecoder.decode(bb);
        //  将unicode字符编码为字节序列
        ByteBuffer newbb = csencoder.encode(cb);
        while (newbb.hasRemaining()) {
            char ca = (char) newbb.get();
            System.out.print(ca);
        }
        newbb.clear();
    }


}
