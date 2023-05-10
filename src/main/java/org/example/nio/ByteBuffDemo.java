package org.example.nio;


import java.nio.ByteBuffer;

public class ByteBuffDemo {
    public static void main(String[] args) {
        //  刚刚创建默认为写模式，limit和cap相同，position为写到的位置
        ByteBuffer buffer = ByteBuffer.allocate(256);
        System.out.println(buffer);
        buffer.put("hello world!".getBytes());

        System.out.println(buffer);

        System.out.println("limit - pos:" + buffer.remaining());

        //  切换成读模式
        buffer.flip();

        //  读模式下，limit为最后写入的位置，position为开始读的位置
        System.out.println(buffer);

        System.out.println("limit - pos:" + buffer.remaining());

        //  position设置为0，mark丢弃，变成-1
        System.out.println(buffer.rewind());
        System.out.println("=======================");

        byte[] bytes = new byte[buffer.remaining()];

        buffer.get(bytes);

        System.out.println(buffer);
        System.out.println("hasRemaining:" + buffer.hasRemaining());
        System.out.println(new String(bytes));
        System.out.println(buffer);
        System.out.println("hasRemaining:" + buffer.hasRemaining());

    }
}
