package com.az.chatdemo.client.console;

import com.az.chatdemo.packet.JoinGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class JoinGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        JoinGroupRequestPacket joinGroupRequestPacket= new JoinGroupRequestPacket();
        System.out.println("输入groupId，加入群聊：");
        String groupId=scanner.next();
        joinGroupRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(joinGroupRequestPacket);
    }
}
