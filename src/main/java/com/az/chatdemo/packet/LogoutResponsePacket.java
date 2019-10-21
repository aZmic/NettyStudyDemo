package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.az.protocoldemo.Command.LOGOUT_RESPONSE;

@Data
@NoArgsConstructor
public class LogoutResponsePacket extends Packet {
    private boolean success;
    private String reason;

    @Override
    public Byte getCommand() {
        return LOGOUT_RESPONSE;
    }
}
