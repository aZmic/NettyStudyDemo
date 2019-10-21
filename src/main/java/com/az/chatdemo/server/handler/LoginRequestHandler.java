package com.az.chatdemo.server.handler;

import com.az.chatdemo.session.Session;
import com.az.chatdemo.utils.SessionUtil;
import com.az.protocoldemo.LoginRequestPacket;
import com.az.protocoldemo.LoginResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@ChannelHandler.Sharable
@NoArgsConstructor
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
//        System.out.println(new Date()+"：收到客服端登陆请求。。。");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUserName());
        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            String userId = randomUserId();
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUserName() + "]登陆成功");
            SessionUtil.bindSession(new Session(userId, loginRequestPacket.getUserName()), channelHandlerContext.channel());
//            System.out.println(new Date()+"：登陆成功");
        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + "：登陆失败");
        }
        //登陆响应
        channelHandlerContext.channel().writeAndFlush(loginResponsePacket);
    }

    private String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
