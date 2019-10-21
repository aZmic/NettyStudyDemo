package com.az.chatdemo.client.handler;

import com.az.chatdemo.packet.MessageResponsePacket;
import com.az.chatdemo.utils.LoginUtil;
import com.az.protocoldemo.LoginRequestPacket;
import com.az.protocoldemo.LoginResponsePacket;
import com.az.protocoldemo.Packet;
import com.az.protocoldemo.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date()+"；客户端开始登陆");

        //创建登陆对象
        LoginRequestPacket loginRequestPacket= new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUserName("az三三");
        loginRequestPacket.setPwd("123456");

        //编码
        ByteBuf byteBuf= PacketCodeC.INSTANCE.encode(loginRequestPacket);

        //写数据
        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf=(ByteBuf)msg;
        Packet packet=PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket=(LoginResponsePacket)packet;
            if(loginResponsePacket.isSuccess()){
                //标示已登陆
                System.out.println(new Date()+"：客户端登陆成功！");
                LoginUtil.markAsLogin(ctx.channel());
            } else {
                System.out.println(new Date()+"：客户端登陆失败！");
            }
        } else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket=(MessageResponsePacket)packet;
            System.out.println(new Date()+"：客户端收到服务器回复的消息："+messageResponsePacket.getMsg());

        }
    }
}
