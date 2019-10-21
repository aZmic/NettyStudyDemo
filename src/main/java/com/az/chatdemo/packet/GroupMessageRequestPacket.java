package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.az.protocoldemo.Command.GROUP_MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class GroupMessageRequestPacket extends Packet {

    private String groupId;
    private String msg;

    public GroupMessageRequestPacket(String groupId, String msg) {
        this.groupId = groupId;
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return GROUP_MESSAGE_REQUEST;
    }
}
