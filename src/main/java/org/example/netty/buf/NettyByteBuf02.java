package org.example.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello, world!", StandardCharsets.UTF_8);

        if(byteBuf.hasArray()){
            byte[] context = byteBuf.array();

            //  将content转成字符串
            System.out.println(new String(context,StandardCharsets.UTF_8).trim());

            //  (ridx: 0, widx: 13, cap: 64)
            System.out.println("byteBuf：" + byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0)); //104   不会移动readIndex坐标
//            System.out.println(byteBuf.readByte());   //会移动readIndex坐标
            int len = byteBuf.readableBytes();
            System.out.println("len = " + len);


            for (int i = 0; i < len; i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            //  按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0,4, StandardCharsets.UTF_8));
            System.out.println(byteBuf.getCharSequence(4,6, StandardCharsets.UTF_8));


        }
    }
}
