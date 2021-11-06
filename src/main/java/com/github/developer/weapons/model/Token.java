package com.github.developer.weapons.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Token<T extends Token> {
    @JSONField(name = "access_token")
    private String accessToken;

    public T withToken(String token) {
        this.accessToken = token;
        return (T) this;
    }
}
