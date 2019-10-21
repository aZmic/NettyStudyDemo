package com.az.chatdemo.codec;

import com.az.protocoldemo.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Date;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Packet packet=PacketCodeC.INSTANCE.decode(byteBuf);
        System.out.println(new Date()+"：收到信息"+packet.toString());
        list.add(packet);
    }
}