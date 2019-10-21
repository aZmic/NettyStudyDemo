package com.az.chatdemo.client.console;

import com.az.chatdemo.packet.ListGroupMemberRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class ListGroupMemberConsoleCommad implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        ListGroupMemberRequestPacket listGroupMemberRequestPacket=new ListGroupMemberRequestPacket();

        System.out.println("输入groupId，获取群成员列表：");
        String groupId=scanner.next();

        listGroupMemberRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(listGroupMemberRequestPacket);
    }
}
