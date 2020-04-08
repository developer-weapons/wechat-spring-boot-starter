package com.github.developer.weapons.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 官方文档
 * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Third_party_platform_appid.html
 */
@Data
@ConfigurationProperties(prefix = "wechat.component")
public class WechatComponentProperties {
    private String appId;
    private String token;
    private String aesKey;
}
