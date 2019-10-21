package com.az.chatdemo.server;

import com.az.chatdemo.codec.PacketCodecHandler;
import com.az.chatdemo.codec.PacketDecoder;
import com.az.chatdemo.codec.PacketEcoder;
import com.az.chatdemo.packet.Spliter;
import com.az.chatdemo.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

public class NettyServer {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        final ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new ServerHandler());
                        //inBound 处理读数据读逻辑链
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
//                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());
                        //outBound 处理写数据读逻辑连
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
//                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());

                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(new LifecycleTestHandler());
//                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(AuthHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(MessageRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(CreateGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(LogoutRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(JoinGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(QuitGroupRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(ListGroupMemberRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(GroupMessageRequestHandler.INSTANCE);
//                        nioSocketChannel.pipeline().addLast(new PacketEcoder());
                        nioSocketChannel.pipeline().addLast(IMHandler.INSTANCE);

                    }
                });
        bind(serverBootstrap, PORT);

    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + "：端口[" + port + "]绑定成功！");
            } else {
                System.out.println("端口[" + port + "]绑定失败！");
            }
        });
    }
}
