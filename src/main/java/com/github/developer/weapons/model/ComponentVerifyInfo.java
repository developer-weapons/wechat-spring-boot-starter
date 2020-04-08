package com.github.developer.weapons.model;

import lombok.Data;

@Data
public class ComponentVerifyInfo {
    private String signature;
    private String timestamp;
    private String nonce;
    private String body;
}