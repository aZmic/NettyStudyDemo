package com.az.protocoldemo.serialize;

public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    //序列化算法
    byte getSerializerAlgorithm();

    //java对象转换成二进制
    byte[] serialize(Object o);

    //二进制转换成java对象
    <T> T deserialize(Class<T> tClass, byte[] bytes);
}
