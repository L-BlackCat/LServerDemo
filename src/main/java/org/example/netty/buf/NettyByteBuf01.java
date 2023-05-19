package org.example.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(15);
        System.out.println(buf);

        for (int i = 0; i < 10; i++) {
            buf.writeByte(i);
        }

        System.out.println("写入完毕");
        System.out.println("buffer capacity:" + buf.capacity());
        System.out.println(buf);

        System.out.println(buf.readableBytes());
        int radLine  = buf.readableBytes();
        for (int i = 0; i < radLine; i++) {
            System.out.println(buf.readByte());
        }

        System.out.println("执行完毕");
        System.out.println(buf);
    }
}
