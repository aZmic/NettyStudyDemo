package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.QuitGroupRequestPacket;
import com.az.chatdemo.packet.QuitGroupResponsePacket;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.NoArgsConstructor;

@ChannelHandler.Sharable
@NoArgsConstructor
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {
    public static final QuitGroupRequestHandler INSTANCE = new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket msg) throws Exception {
        //获取群对应的channelGroup，然后将当前的channel移除
        String grioupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(grioupId);
        channelGroup.remove(ctx.channel());
        //构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(grioupId);
        quitGroupResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
