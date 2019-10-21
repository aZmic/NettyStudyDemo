package com.az.chatdemo.packet;

import com.az.chatdemo.session.Session;
import com.az.protocoldemo.Packet;
import lombok.Data;

import java.util.List;

import static com.az.protocoldemo.Command.LIST_GROUP_MEMBERS_RESPONSE;

@Data
public class ListGroupMemberResponsePacket extends Packet {

    private String groupId;
    private List<Session> sessionList;

    @Override
    public Byte getCommand() {
        return LIST_GROUP_MEMBERS_RESPONSE;
    }
}
