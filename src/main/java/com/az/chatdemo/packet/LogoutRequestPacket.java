package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.az.protocoldemo.Command.LOGOUT_REQUEST;

@Data
@NoArgsConstructor
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return LOGOUT_REQUEST;
    }
}
