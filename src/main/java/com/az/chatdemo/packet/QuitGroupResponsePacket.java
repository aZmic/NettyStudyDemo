package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.JOIN_GROUP_RESPONSE;
import static com.az.protocoldemo.Command.QUIT_GROUP_RESPONSE;

@Data
public class QuitGroupResponsePacket extends Packet {
    private String groupId;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return QUIT_GROUP_RESPONSE;
    }
}
