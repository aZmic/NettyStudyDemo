package com.az.protocoldemo.serialize;

import com.alibaba.fastjson.JSON;

public class JSONSerializer  implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(Class<T> tClass, byte[] bytes) {
        return JSON.parseObject(bytes,tClass);
    }
}
