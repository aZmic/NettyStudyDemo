package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.ListGroupMemberRequestPacket;
import com.az.chatdemo.packet.ListGroupMemberResponsePacket;
import com.az.chatdemo.session.Session;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
@NoArgsConstructor
public class ListGroupMemberRequestHandler extends SimpleChannelInboundHandler<ListGroupMemberRequestPacket> {
    public static final ListGroupMemberRequestHandler INSTANCE = new ListGroupMemberRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMemberRequestPacket msg) throws Exception {
        //获取群的channelGroup
        String groupId = msg.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        //遍历群成员的channel，对应的session，构造群成员的信息
        List<Session> sessionList = new ArrayList<>();
        for (Channel channel : channelGroup) {
            Session session = SessionUtil.getSession(channel);
            sessionList.add(session);
        }

        //构造获取群成员列表响应写到客户端
        ListGroupMemberResponsePacket responsePacket = new ListGroupMemberResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
