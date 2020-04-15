package com.github.developer.weapons.model;

import lombok.Data;

@Data
public class ComponentAuthorizerInfo {
    private String userName;
    private String authorizerAppid;
    private String nickName;
    private String headImg;
    private String qrcodeUrl;
    private String authorizerRefreshToken;

    // 认证状态
    private Integer verifyTypeInfo;

    
}
