package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.JOIN_GROUP_RESPONSE;

@Data
public class JoinGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return JOIN_GROUP_RESPONSE;
    }
}
