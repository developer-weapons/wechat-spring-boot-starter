package com.github.developer.weapons.service;

import com.github.developer.weapons.config.WechatComponentProperties;
import com.github.developer.weapons.model.ComponentVerifyInfo;
import com.github.developer.weapons.util.XmlUtils;
import com.github.developer.weapons.util.aes.WXBizMsgCrypt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 微信第三方平台服务
 */
public class WechatComponentService implements InitializingBean {

    private WXBizMsgCrypt wxBizMsgCrypt;

    @Autowired
    private WechatComponentProperties wechatComponentProperties;


    /**
     * 通过 verifyInfo 获取授权的订阅号信息
     *
     * @param verifyInfo
     * @return
     */
    public Map<String, String> getVerifiedInfo(ComponentVerifyInfo verifyInfo) {
        String decodeMsg = null;
        try {
            decodeMsg = wxBizMsgCrypt.decryptMsg(verifyInfo.getSignature(), verifyInfo.getTimestamp(), verifyInfo.getNonce(), verifyInfo.getBody());
            return XmlUtils.xmlToMap(decodeMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (wechatComponentProperties.getAesKey() == null) {
            throw new RuntimeException("wechat.component.aesKey is missing");
        }
        if (wechatComponentProperties.getToken() == null) {
            throw new RuntimeException("wechat.component.token is missing");
        }
        if (wechatComponentProperties.getAppId() == null) {
            throw new RuntimeException("wechat.component.appId is missing");
        }
        wxBizMsgCrypt = new WXBizMsgCrypt(wechatComponentProperties.getToken(), wechatComponentProperties.getAesKey(), wechatComponentProperties.getAppId());
    }
}
