package org.example.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;

public class ByteBufDemo {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);

        print("allocate ByteBuf(9, 100)", buffer);

        // write方法改变写指针，写完之后写指针未到capacity的时候，buffer仍然可写

        buffer.writeBytes(new byte[]{1, 2, 3, 4});

        print("writeBytes(1,2,3,4)", buffer);

        // write方法改变写指针，写完之后写指针未到capacity的时候，buffer仍然可写，写完int类型之后，写指针增加4

        buffer.writeInt(12);

        print("writeInt(12)", buffer);

        // write方法改变写指针，写完之后写指针等于capacity的时候，buffer不可写

        buffer.writeBytes(new byte[]{5});

        print("writeBytes(5)", buffer);

        // write方法改变写指针，写的时候发现buffer不可写则开始扩容，扩容之后capacity随即改变

        buffer.writeBytes(new byte[]{6});

        print("writeBytes(6)", buffer);

        // get方法不改变读写指针

        System.out.println("getByte(3) return： " + buffer.getByte(3));

        System.out.println("getShort(3) return： " + buffer.getShort(3));

        System.out.println("getInt(3) return： " + buffer.getInt(3));

        print("getByte()", buffer);

        ByteBuf slice = buffer.slice();
        ByteBuf duplicate = buffer.duplicate();
        ByteBuf copy = buffer.copy();
        //  从原始ByteBuf中截取一段，这段数据是从readerIndex到writeIndex的
        print("slice",slice);
        // duplicate 整个ByteBuf都截取出来，包括所有的数据、指针信息。
        print("duplicate",duplicate);
        print("copy",copy);
        copy.writeByte(56);
        print("copy-write byte view buffer",buffer);
        print("copy-write byte view copy",copy);

        // set方法不改变读写指针

        buffer.setByte(buffer.readableBytes() + 1, 0);

        print("setByte()", buffer);

        // read方法改变读指针

        byte[] dst = new byte[buffer.readableBytes()];

        buffer.readBytes(dst);

        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String action, ByteBuf buffer) {

        System.out.println("after ===========" + action + "============");

        System.out.println("capacity()： " + buffer.capacity());

        System.out.println("maxCapacity()： " + buffer.maxCapacity());

        System.out.println("readerIndex()： " + buffer.readerIndex());

        System.out.println("readableBytes()： " + buffer.readableBytes());

        System.out.println("isReadable()： " + buffer.isReadable());

        System.out.println("writerIndex()： " + buffer.writerIndex());

        System.out.println("writableBytes()： " + buffer.writableBytes());

        System.out.println("isWritable()： " + buffer.isWritable());

        System.out.println("maxWritableBytes()： " + buffer.maxWritableBytes());

        System.out.println();

        }

}

