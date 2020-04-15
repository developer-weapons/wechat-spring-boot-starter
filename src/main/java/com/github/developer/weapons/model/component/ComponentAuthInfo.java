package com.github.developer.weapons.model.component;

import lombok.Data;

@Data
public class ComponentAuthInfo {
    /**
     * 授权订阅号的 token
     */
    private String token;

    /**
     * 授权订阅号的账号 id
     */
    private String appId;
}
