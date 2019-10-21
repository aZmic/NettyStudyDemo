package com.az.chatdemo.client.handler;

import com.az.chatdemo.session.Session;
import com.az.chatdemo.utils.LoginUtil;
import com.az.chatdemo.utils.SessionUtil;
import com.az.protocoldemo.LoginRequestPacket;
import com.az.protocoldemo.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //创建登陆对象
//        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
//        loginRequestPacket.setUserId(UUID.randomUUID().toString());
//        loginRequestPacket.setUserName("awsl");
//        loginRequestPacket.setPwd("123");

        //写数据
//        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        String userId=loginResponsePacket.getUserId();
        String userName=loginResponsePacket.getUserName();
        if(loginResponsePacket.isSuccess()){
            System.out.println("["+userName+"]登陆成功，userId："+userId);
            SessionUtil.bindSession(new Session(userId,userName),channelHandlerContext.channel());
//            System.out.println(new Date()+"：客服端登陆成功！");
//            LoginUtil.markAsLogin(channelHandlerContext.channel());
        }else {
            System.out.println("[" + userName + "]登录失败，原因：" + loginResponsePacket.getReason());
//            System.out.println(new Date()+"：客服端登陆失败！");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接被关闭!");
    }
}
