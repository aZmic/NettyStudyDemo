package com.az.hello;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstClientHandler extends ChannelInboundHandlerAdapter {

    //连接成功后调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + "：客户端写出数据");
        //获取数据
        ByteBuf buf = getByteBuf(ctx);
        //写数据
        ctx.channel().writeAndFlush(buf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        //获取二进制抽象ByteBuf
        ByteBuf byteBuf = ctx.alloc().buffer();
        //准备数据，指定字符串的字符集胃utf-8
        byte[] bytes = "你好，aZ呀～".getBytes(StandardCharsets.UTF_8);
        //填充数据到ByteBuf
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf =(ByteBuf)msg;
        System.out.println(new Date() + "：客户端读到数据 ->"+buf.toString(StandardCharsets.UTF_8));
    }
}
