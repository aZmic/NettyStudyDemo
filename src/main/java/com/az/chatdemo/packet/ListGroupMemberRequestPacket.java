package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;

import static com.az.protocoldemo.Command.JOIN_GROUP_REQUEST;
import static com.az.protocoldemo.Command.LIST_GROUP_MEMBERS_REQUEST;

@Data
public class ListGroupMemberRequestPacket extends Packet {

    private String groupId;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_REQUEST;
    }
}
