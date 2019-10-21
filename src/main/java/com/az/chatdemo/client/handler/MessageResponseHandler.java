package com.az.chatdemo.client.handler;

import com.az.chatdemo.packet.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();
        System.out.println(fromUserId + ": " + fromUserName + ": " + messageResponsePacket.getMsg());
//        System.out.println(new Date()+"：收到服务端消息："+messageResponsePacket.getMsg());
    }
}
