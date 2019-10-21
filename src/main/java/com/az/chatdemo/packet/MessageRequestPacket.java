package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.az.protocoldemo.Command.MESSAGE_REQUEST;

@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {
    private String toUserId;
    private String msg;

    public MessageRequestPacket( String toUserId,String msg) {
        this.toUserId = toUserId;
        this.msg = msg;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
