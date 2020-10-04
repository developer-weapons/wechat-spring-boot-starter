package com.github.developer.weapons.model;

import lombok.Data;

@Data
public class Token<T extends Token> {
    private String accessToken;

    public T withToken(String token) {
        this.accessToken = token;
        return (T) this;
    }
}
