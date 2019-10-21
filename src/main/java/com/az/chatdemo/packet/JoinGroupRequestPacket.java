package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.JOIN_GROUP_REQUEST;

@Data
public class JoinGroupRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_REQUEST;
    }
}
