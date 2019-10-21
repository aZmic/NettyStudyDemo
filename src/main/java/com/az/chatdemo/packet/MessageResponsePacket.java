package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.LOGIN_RESPONSE;
import static com.az.protocoldemo.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String msg;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
