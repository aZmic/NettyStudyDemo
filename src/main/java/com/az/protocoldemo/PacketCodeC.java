package com.az.protocoldemo;

import com.az.chatdemo.packet.MessageRequestPacket;
import com.az.chatdemo.packet.MessageResponsePacket;
import com.az.protocoldemo.serialize.JSONSerializer;
import com.az.protocoldemo.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.az.protocoldemo.Command.*;

public class PacketCodeC {
    private static final int MAGIC_NUM = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQEUST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);

    }

    public ByteBuf encode(Packet packet) {
        //创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        //实际编码过程
        byteBuf.writeInt(MAGIC_NUM);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        //跳过magic num
        byteBuf.skipBytes(4);
        //跳过版本号
        byteBuf.skipBytes(1);
        //序列化算法标示
        byte serializerAlgoritnm = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgoritnm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializerAlgoritnm) {
        return serializerMap.get(serializerAlgoritnm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
