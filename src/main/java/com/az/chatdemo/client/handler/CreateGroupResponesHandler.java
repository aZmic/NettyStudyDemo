package com.az.chatdemo.client.handler;

import com.az.chatdemo.packet.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CreateGroupResponesHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        System.out.print("群创建成功，id 为[" + msg.getGroupId() + "], ");
        System.out.println("群里面有：" + msg.getUserNameList());
    }
}
