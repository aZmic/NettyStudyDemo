package com.az.chatdemo.server.handler;

import com.az.chatdemo.utils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.NoArgsConstructor;

@ChannelHandler.Sharable
@NoArgsConstructor
public class AuthHandler extends ChannelInboundHandlerAdapter {
    public static final AuthHandler INSTANCE = new AuthHandler();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (!LoginUtil.hasLogin(ctx.channel())) {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        if(LoginUtil.hasLogin(ctx.channel())){
//            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
//        }else {
//            System.out.println("无登陆验证，强制关闭连接!");
//        }
    }
}
