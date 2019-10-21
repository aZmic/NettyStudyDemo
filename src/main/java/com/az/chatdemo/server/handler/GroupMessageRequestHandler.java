package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.GroupMessageRequestPacket;
import com.az.chatdemo.packet.GroupMessageResponsePacket;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.NoArgsConstructor;

@ChannelHandler.Sharable
@NoArgsConstructor
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket msg) throws Exception {
        //拿到groupId构造群聊消息的响应
        String groupId=msg.getGroupId();
        GroupMessageResponsePacket responsePacket=new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMsg(msg.getMsg());
        responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        //拿到群聊对应的channelGroup，写到每个客户端
        ChannelGroup channelGroup=SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(responsePacket);

    }
}
