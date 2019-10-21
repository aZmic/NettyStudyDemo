package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.JOIN_GROUP_REQUEST;
import static com.az.protocoldemo.Command.QUIT_GROUP_REQUEST;

@Data
public class QuitGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_REQUEST;
    }
}
