package com.az.bufferdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class BufferDemo {

    public static void main(String[] args) {
        //writerIndex-readerIndex-capacity
        //ByteBuf bytebuf
        //markReaderIndex()-resetReaderIndex() markWriterIndex()-resetWriterIndex() 标记位置 重置位置
        //getBytes()-readBytes() setBytes()-writeBytes() get和set不会影响index位置，read和write会改变index
        //Netty的ByteBuf是通过引用计数的方式管理的 release()-retain() 标示bytebuf的引用次数 relese-1 retain+1
        //slice()-duplicate()-copy()
        //slice对原有的ByteBuf进行切片，将readableBytes作为maxCapacity
        //duplicate复制一份，完全一样，但是底层数据没有做copy，写入数据会影响原有的ByteBuf
        //copy复制一份，连同数据

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocator ByteBuf(9,100)", buffer);

        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1,2,3,4)", buffer);

        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        //超过capacity将要扩容
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        //get方法不改变读写指针
        System.out.println("getByte(3) return: " + buffer.getByte(3));
        System.out.println("getShort(3) return: " + buffer.getShort(3));
        System.out.println("getInt(3) return: " + buffer.getInt(3));
        System.out.println();
        print("getByte()", buffer);

        //set()不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 2);
        print("setByte()", buffer);

        //read()改变读写指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);

    }

    private static void print(String s, ByteBuf buffer) {
        System.out.println("after=========" + s + "=========");
        System.out.println("capacity():" + buffer.capacity());
        System.out.println("maxCapacity():" + buffer.maxCapacity());
        System.out.println("isReadable():" + buffer.isReadable());
        System.out.println("readerIndex():" + buffer.readerIndex());
        System.out.println("readableBytes():" + buffer.readableBytes());
        System.out.println("isWritable():" + buffer.isWritable());
        System.out.println("writerIndex():" + buffer.writerIndex());
        System.out.println("writableBytes():" + buffer.writableBytes());
        System.out.println("markWriterIndex():" + buffer.markWriterIndex());
    }

}


