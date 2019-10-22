package com.az.chatdemo.packet;

import com.az.protocoldemo.Packet;

import static com.az.protocoldemo.Command.HEART_BEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEART_BEAT_RESPONSE;
    }
}
