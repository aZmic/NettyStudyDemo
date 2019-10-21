package com.az.chatdemo.client.console;

import com.az.chatdemo.packet.CreatwGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.Arrays;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";

    @Override
    public void exec(Scanner scanner, Channel channel) {
        CreatwGroupRequestPacket creatwGroupRequestPacket = new CreatwGroupRequestPacket();
        System.out.println("【拉人群聊】输入userId列表，userId之间用逗号隔开：");
        String userIds = scanner.next();
        creatwGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));
        channel.writeAndFlush(creatwGroupRequestPacket);
    }
}
