package com.az.chatdemo.server.handler;

import com.az.chatdemo.packet.MessageRequestPacket;
import com.az.chatdemo.packet.MessageResponsePacket;
import com.az.protocoldemo.LoginRequestPacket;
import com.az.protocoldemo.LoginResponsePacket;
import com.az.protocoldemo.Packet;
import com.az.protocoldemo.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date()+"：客户端开始登陆...");

        ByteBuf byteBuf=(ByteBuf)msg;
        Packet packet= PacketCodeC.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginRequestPacket){
            //登陆流程
            LoginRequestPacket loginRequestPacket= (LoginRequestPacket)packet;
            LoginResponsePacket loginResponsePacket= new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());
            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date()+"：登陆成功！");
            } else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("密码错误");
                System.out.println(new Date()+"：登陆失败！");

            }
            ByteBuf responseByteBuf= PacketCodeC.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        } else if(packet instanceof MessageRequestPacket){
            //处理消息
            MessageRequestPacket messageRequestPacket=(MessageRequestPacket)packet;
            System.out.println(new Date()+"：收到客户端消息："+messageRequestPacket.getMsg());

            MessageResponsePacket messageResponsePacket= new MessageResponsePacket();
            messageResponsePacket.setMsg("服务器回复【"+messageRequestPacket.getMsg()+"】");
            ByteBuf responseByteBuf1=PacketCodeC.INSTANCE.encode(messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf1);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        //验证账号密码
        return true;
    }
}
