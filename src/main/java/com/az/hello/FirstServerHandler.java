package com.az.hello;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    //在收到客户端发来的数据后回调
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + "：服务器读到数据 ->" + byteBuf.toString(StandardCharsets.UTF_8));

        ByteBuf out = getByteBuf(ctx);
        ctx.channel().writeAndFlush(out);
        System.out.println(new Date() + "：服务器写出数据");
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "娉娉袅袅十三余，豆蔻年华二月初".getBytes(StandardCharsets.UTF_8);
        ByteBuf buf = ctx.alloc().buffer();
        buf.writeBytes(bytes);
        return buf;
    }


}
