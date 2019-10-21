package com.az.chatdemo.client.console;

import com.az.chatdemo.packet.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息给某个用户：");
        String toUserId = scanner.next();
        String msg = scanner.next();
        channel.writeAndFlush(new MessageRequestPacket(toUserId, msg));
    }
}
