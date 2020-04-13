package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.developer.weapons.config.WechatComponentProperties;
import com.github.developer.weapons.exception.WechatException;
import com.github.developer.weapons.model.ComponentVerifyInfo;
import com.github.developer.weapons.util.XmlUtils;
import com.github.developer.weapons.util.aes.WXBizMsgCrypt;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * 微信第三方平台服务
 */
public class WechatComponentService implements InitializingBean {

    @Autowired
    private WechatComponentProperties wechatComponentProperties;

    private OkHttpClient okHttpClient = new OkHttpClient();

    private WXBizMsgCrypt wxBizMsgCrypt;

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


    /**
     * 根据 token 获取 preauthcode 用户授权订阅号
     *
     * @param componentToken
     * @return
     */
    public String getPreAuthCodeByComponentToken(String componentToken) {
        if (componentToken == null) {
            throw new WechatException("componentToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=" + componentToken;
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("component_appid", wechatComponentProperties.getAppId());
        String post = post(url, bodyObject);
        JSONObject jsonObject = JSON.parseObject(post);
        if (StringUtils.isNotBlank(jsonObject.getString("pre_auth_code"))) {
            return jsonObject.getString("pre_auth_code");
        } else {
            throw new WechatException("obtain component_access_token error with " + post);
        }
    }

    /**
     * 根据缓存好的 Ticket 获取应用的 conponent_token
     *
     * @param ticket
     * @return
     */
    public String getComponentTokenByTicket(String ticket) {
        if (wechatComponentProperties.getSecret() == null) {
            throw new WechatException("wechat.component.secret is missing");
        }

        String url = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("component_appid", wechatComponentProperties.getAppId());
        bodyObject.put("component_appsecret", wechatComponentProperties.getSecret());
        bodyObject.put("component_verify_ticket", ticket);
        String post = post(url, bodyObject);
        JSONObject jsonObject = JSON.parseObject(post);
        if (StringUtils.isNotBlank(jsonObject.getString("component_access_token"))) {
            return jsonObject.getString("component_access_token");
        } else {
            throw new WechatException("obtain component_access_token error with " + post);
        }
    }

    private String post(String url, JSONObject body) {
        Request request = new Request.Builder()
                .addHeader("body-type", "application/json")
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), body.toJSONString()))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                assert execute.body() != null;
                return execute.body().string();
            }
            throw new WechatException("post to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            throw new WechatException("post to wechat endpoint " + url + " error ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (wechatComponentProperties.getAesKey() == null) {
            throw new WechatException("wechat.component.aesKey is missing");
        }
        if (wechatComponentProperties.getToken() == null) {
            throw new WechatException("wechat.component.token is missing");
        }
        if (wechatComponentProperties.getAppId() == null) {
            throw new WechatException("wechat.component.appId is missing");
        }
        wxBizMsgCrypt = new WXBizMsgCrypt(wechatComponentProperties.getToken(), wechatComponentProperties.getAesKey(), wechatComponentProperties.getAppId());
    }
}
