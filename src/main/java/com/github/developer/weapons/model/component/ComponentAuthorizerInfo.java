package com.github.developer.weapons.model.component;

import lombok.Data;

@Data
public class ComponentAuthorizerInfo {
    /**
     * 原始ID
     */
    private String userName;
    /**
     * 授权方 appid
     */
    private String authorizerAppid;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String headImg;
    /**
     * 二维码
     */
    private String qrcodeUrl;
    /**
     * 刷新 TOKEN
     */
    private String authorizerRefreshToken;

    /**
     * 公众号认证类型
     * -1 未认证
     * 0 微信认证
     * 1 新浪微博认证
     * 2 腾讯微博认证
     * 3 已资质认证通过但还未通过名称认证
     * 4 已资质认证通过、还未通过名称认证，但通过了新浪微博认证
     * 5 已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    private Integer verifyTypeInfo;

    /**
     * 公众号类型
     * 0 订阅号
     * 1 由历史老帐号升级后的订阅号
     * 2 服务号
     */
    private Integer serviceTypeInfo;
}
