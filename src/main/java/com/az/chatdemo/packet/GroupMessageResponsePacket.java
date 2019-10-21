package com.az.chatdemo.packet;

import com.az.chatdemo.session.Session;
import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.GROUP_MESSAGE_RESPONSE;

@Data
public class GroupMessageResponsePacket extends Packet {

    private String fromGroupId;
    private Session fromUser;
    private String msg;

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_RESPONSE;
    }
}
