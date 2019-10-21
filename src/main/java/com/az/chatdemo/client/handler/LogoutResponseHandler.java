package com.az.chatdemo.client.handler;

import com.az.chatdemo.packet.LogoutResponsePacket;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
