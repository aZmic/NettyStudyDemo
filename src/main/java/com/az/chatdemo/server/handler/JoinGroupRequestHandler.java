package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.JoinGroupRequestPacket;
import com.az.chatdemo.packet.JoinGroupResponsePacket;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.NoArgsConstructor;

@ChannelHandler.Sharable
@NoArgsConstructor
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket msg) throws Exception {
        //获取群对应的channelGroup，然后将当前用户的channel添加进去
        String groupId=msg.getGroupId();
        ChannelGroup channelGroup= SessionUtil.getChannelGroup(groupId);
        channelGroup.add(ctx.channel());
        //构造加群响应发送给客户端
        JoinGroupResponsePacket joinGroupResponsePacket=new JoinGroupResponsePacket();
        joinGroupResponsePacket.setSuccess(true);
        joinGroupResponsePacket.setGroupId(groupId);
        ctx.channel().writeAndFlush(joinGroupResponsePacket);

    }
}
