package com.az;

import com.az.hello.FirstClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        //处理数据读写
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //客户端辅助类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //指定线程模型
                .group(workerGroup)
                //指定io类型
                .channel(NioSocketChannel.class)
                //设置channel属性
                .attr(AttributeKey.newInstance("clientName"), "nettyClient")
                //连接超时
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //是否开启Nagle算法
                .option(ChannelOption.TCP_NODELAY, true)
                //启动底层心跳机制
                .option(ChannelOption.SO_KEEPALIVE, true)
                //io处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //指定连接数据读写逻辑
                        socketChannel.pipeline().addLast(new FirstClientHandler());
                    }
                });
        //建立连接
        connect(bootstrap, "127.0.0.1", 8001, MAX_RETRY);

    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
            } else if (retry == 0) {
                System.out.println("连接失败");
            } else {
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                //本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + "：连接失败，第" + order + "次重连...");
                bootstrap.config().group().schedule(() ->
                                connect(bootstrap, host, port, MAX_RETRY),
                        delay,
                        TimeUnit.SECONDS
                );
            }
        });
    }
}
