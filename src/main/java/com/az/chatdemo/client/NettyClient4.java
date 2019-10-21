package com.az.chatdemo.client;

import com.az.chatdemo.client.console.ConsoleCommandManager;
import com.az.chatdemo.client.console.LoginConsoleCommand;
import com.az.chatdemo.client.handler.CreateGroupResponesHandler;
import com.az.chatdemo.client.handler.LoginResponseHandler;
import com.az.chatdemo.client.handler.MessageResponseHandler;
import com.az.chatdemo.codec.PacketDecoder;
import com.az.chatdemo.codec.PacketEcoder;
import com.az.chatdemo.packet.Spliter;
import com.az.chatdemo.utils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class NettyClient4 {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                        nioSocketChannel.pipeline().addLast(new ClientHandler());

                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginResponseHandler());
                        nioSocketChannel.pipeline().addLast(new MessageResponseHandler());
                        nioSocketChannel.pipeline().addLast(new CreateGroupResponesHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEcoder());

                    }
                });
        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + "：连接成功，启动控制台线程...");
                Channel channel = ((ChannelFuture) future).channel();
                //连接成功，以后启动控制台线程
                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                int order = MAX_RETRY - retry + 1;
                int delay = 1 << order;
                System.err.println(new Date() + "：连接失败，第" + retry + "次重连...");
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap, host, port, retry - 1),
                        delay,
                        TimeUnit.SECONDS);

            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        Scanner scanner = new Scanner(System.in);
//        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        ConsoleCommandManager consoleCommandManager=new ConsoleCommandManager();
        LoginConsoleCommand loginConsoleCommand=new LoginConsoleCommand();

        new Thread(() -> {
            while (!Thread.interrupted()) {
//                if (LoginUtil.hasLogin(channel)) {
//                    System.out.println("输入消息发送到服务器：");
//                    Scanner sc = new Scanner(System.in);
//                    String line = sc.nextLine();
//
////                    MessageRequestPacket packet = new MessageRequestPacket();
////                    packet.setMsg(line);
////                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(packet);
////                    channel.writeAndFlush(byteBuf);
//                    channel.writeAndFlush(new MessageRequestPacket(line));
//                }
                if (!SessionUtil.hasLogin(channel)) {
//                    System.out.println("输入用户名：");
//                    String username = scanner.nextLine();
//                    loginRequestPacket.setUserName(username);
//                    //密码使用默认的
//                    loginRequestPacket.setPwd("123456");
//                    //发送登陆数据包
//                    channel.writeAndFlush(loginRequestPacket);
//                    waitForLoginResponse();
                    loginConsoleCommand.exec(scanner,channel);
                } else {
//                    String toUserId = scanner.next();
//                    String msg = scanner.next();
//                    channel.writeAndFlush(new MessageRequestPacket(toUserId, msg));
                    consoleCommandManager.exec(scanner,channel);
                }
            }
        }).start();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
