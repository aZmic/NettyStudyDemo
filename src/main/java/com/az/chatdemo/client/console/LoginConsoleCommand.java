package com.az.chatdemo.client.console;

import com.az.protocoldemo.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket=new LoginRequestPacket();
        System.out.println("输入用户名登陆：");
        loginRequestPacket.setUserName(scanner.nextLine());
        loginRequestPacket.setPwd("123456");
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
