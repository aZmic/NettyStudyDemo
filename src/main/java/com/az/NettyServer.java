package com.az;

import com.az.hello.FirstServerHandler;
import com.az.pipelinedemo.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyServer {
    private static final int BEGIN_PORT = 8000;

    public static void main(String[] args) {
        //负责接受连接线程，创建新的连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //负责读取数据的线程，读取数据以及处理业务逻辑
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //绑定线程模型，io模型，连接读写处理逻辑之外，还可以设置一些连接属性
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        AttributeKey<Object> clientKey = AttributeKey.newInstance("clientKey");

        serverBootstrap
                //指定线程模型
                .group(bossGroup, workerGroup)
                //指定io类型
                .channel(NioServerSocketChannel.class)
                //服务端启动过程中的一些逻辑
                .handler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel nioServerSocketChannel) throws Exception {
                        System.out.println("服务端启动中...");
                    }
                })
                //给服务端的channel指定自定义属性
                .attr(AttributeKey.newInstance("serverName"), "nettyServer")
                //给每条channel连接设置属性
                .childAttr(clientKey, "clientValue")
                //设置服务端的channel，临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                //开启TCP底层心跳机制，true表示开启
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                //开启Nagle算法，true表示关闭（要求高实时性，有数据立马发送，则关闭；如果要减少网络交互，则开启）
                .childOption(ChannelOption.TCP_NODELAY, true)
                //指定处理新连接的数据读写及业务处理逻辑
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new FirstServerHandler());
                        //inBound 处理读数据读逻辑链
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new InBoundHandlerC());
//                        outBound 处理写数据读逻辑连
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerA());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerB());
                        nioSocketChannel.pipeline().addLast(new OutBoundHandlerC());

                    }
                })
        ;
        //建立连接
        bind(serverBootstrap, BEGIN_PORT);

    }

    private static void bind(final ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.out.println("端口[" + port + "]绑定失败!");
                //重连
                bind(serverBootstrap, port + 1);
            }
        });
    }

}
