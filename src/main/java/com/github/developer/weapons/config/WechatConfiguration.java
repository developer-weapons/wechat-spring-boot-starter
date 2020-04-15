package com.github.developer.weapons.config;

import com.github.developer.weapons.service.WechatComponentService;
import com.github.developer.weapons.service.WechatOfficialService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({WechatComponentProperties.class})
public class WechatConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public WechatComponentService wechatComponentService() {
        return new WechatComponentService();
    }

    @Bean
    @ConditionalOnMissingBean
    public WechatOfficialService wechatOfficialService() {
        return new WechatOfficialService();
    }
}
