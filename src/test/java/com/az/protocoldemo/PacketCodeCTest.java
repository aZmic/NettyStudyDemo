package com.az.protocoldemo;

import com.az.protocoldemo.serialize.JSONSerializer;
import com.az.protocoldemo.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

public class PacketCodeCTest {

    @Test
    public void encode() {

        Serializer serializer = new JSONSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion((byte) 1);
        loginRequestPacket.setUserId("123");
        loginRequestPacket.setUserName("aZ三三");
        loginRequestPacket.setPwd("123456");

        PacketCodeC packetCodeC = PacketCodeC.INSTANCE;
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodePacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodePacket));
    }
}