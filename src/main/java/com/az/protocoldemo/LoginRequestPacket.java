package com.az.protocoldemo;

import lombok.Data;

import static com.az.protocoldemo.Command.LOGIN_REQEUST;

@Data
public class LoginRequestPacket extends Packet {
    private String userId;
    private String userName;
    private String pwd;

    @Override
    public Byte getCommand() {
        return LOGIN_REQEUST;
    }
}
