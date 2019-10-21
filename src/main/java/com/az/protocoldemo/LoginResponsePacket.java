package com.az.protocoldemo;

import lombok.Data;

import static com.az.protocoldemo.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
