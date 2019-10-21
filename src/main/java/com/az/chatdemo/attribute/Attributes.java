package com.az.chatdemo.attribute;

import com.az.chatdemo.session.Session;
import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
