package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.CreateGroupResponsePacket;
import com.az.chatdemo.packet.CreatwGroupRequestPacket;
import com.az.chatdemo.session.Session;
import com.az.chatdemo.utils.IDUtil;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ChannelHandler.Sharable
@NoArgsConstructor
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreatwGroupRequestPacket> {
    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreatwGroupRequestPacket msg) throws Exception {
        List<String> userIdList = msg.getUserIdList();
        List<String> userNameList = new ArrayList<>();

        //创建一个channel分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        //筛选出待加入群聊的用户的channel和userName
        for (String userId : userIdList) {
            Channel channel = SessionUtil.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        //创建群聊创建结果响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userIdList);

        //给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        System.out.print("群聊创建成功，id为【" + createGroupResponsePacket.getGroupId() + "]，");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());

        //保存群组相关信息
        SessionUtil.bindChannelGroup(groupId,channelGroup);
    }
}
