package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.MessageRequestPacket;
import com.az.chatdemo.packet.MessageResponsePacket;
import com.az.chatdemo.session.Session;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.NoArgsConstructor;

@ChannelHandler.Sharable
@NoArgsConstructor
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        //拿到消息发送方的会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());
        //通过消息发送方的会话信息构造要发送的消息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMsg(messageRequestPacket.getMsg());
        //拿到消息接收方的channel
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        //发送消息给消息接收方
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
        } else {
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }

//        MessageResponsePacket messageResponsePacket=new MessageResponsePacket();
//        System.out.println(new Date()+"：收到客户端消息："+messageRequestPacket.getMsg());
//        messageResponsePacket.setMsg("服务端回复【"+messageRequestPacket.getMsg()+"】");
//        channelHandlerContext.channel().writeAndFlush(messageResponsePacket);

    }
}
