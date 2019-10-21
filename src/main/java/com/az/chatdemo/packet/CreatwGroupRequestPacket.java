package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.az.protocoldemo.Command.CREATE_GROUP_REQUEST;

@Data
@NoArgsConstructor
public class CreatwGroupRequestPacket extends Packet {
    private List<String> userIdList;

    public CreatwGroupRequestPacket(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    @Override
    public Byte getCommand() {
        return CREATE_GROUP_REQUEST;
    }
}
