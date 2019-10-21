package com.az.chatdemo.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Session {
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String toString() {
        return "userId：" + userId + "/userName：" + userName;
    }
}
