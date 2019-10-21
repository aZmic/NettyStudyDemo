package com.az.chatdemo.client.handler;

import com.az.chatdemo.packet.ListGroupMemberResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ListGroupMemberResponseHandler extends SimpleChannelInboundHandler<ListGroupMemberResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberResponsePacket msg) throws Exception {
        System.out.println("群[" + msg.getGroupId() + "]中的人包括：" + msg.getSessionList());
    }
}
