package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.developer.weapons.config.WechatComponentProperties;
import com.github.developer.weapons.exception.WechatException;
import com.github.developer.weapons.model.ComponentAuthInfo;
import com.github.developer.weapons.model.ComponentAuthorizerInfo;
import com.github.developer.weapons.model.ComponentTextMessage;
import com.github.developer.weapons.model.ComponentVerifyInfo;
import com.github.developer.weapons.util.XmlUtils;
import com.github.developer.weapons.util.aes.AesException;
import com.github.developer.weapons.util.aes.WXBizMsgCrypt;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

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
            Map<String, String> maps = XmlUtils.xmlToMap(decodeMsg);
            String appId = maps.get("AppId");
            String infoType = maps.get("InfoType");
            if ("component_verify_ticket".equals(infoType)) {
                if (!StringUtils.equals(wechatComponentProperties.getAppId(), appId)) {
                    throw new WechatException("component_verify_ticket does not match appId : " + wechatComponentProperties.getAppId());
                }
            }
            return maps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据授权账号的信息，获取授权账号的详细内容
     *
     * @param componentToken
     * @param authorizerAppId
     * @return
     */
    public ComponentAuthorizerInfo getAuthorizerInfo(String componentToken, String authorizerAppId) {
        if (componentToken == null) {
            throw new WechatException("componentToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token=" + componentToken;
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("component_appid", wechatComponentProperties.getAppId());
        bodyObject.put("authorizer_appid", authorizerAppId);
        String post = post(url, bodyObject);
        JSONObject jsonObject = JSON.parseObject(post);
        ComponentAuthorizerInfo authorizerInfo = new ComponentAuthorizerInfo();
        JSONObject authorizer_info = jsonObject.getJSONObject("authorizer_info");
        JSONObject authorization_info = jsonObject.getJSONObject("authorization_info");
        authorizerInfo.setQrcodeUrl(authorizer_info.getString("qrcode_url"));
        authorizerInfo.setUserName(authorizer_info.getString("user_name"));
        authorizerInfo.setNickName(authorizer_info.getString("nick_name"));
        authorizerInfo.setHeadImg(authorizer_info.getString("head_img"));
        authorizerInfo.setAuthorizerAppid(authorization_info.getString("authorizer_appid"));
        authorizerInfo.setAuthorizerRefreshToken(authorization_info.getString("authorizer_refresh_token"));
        return authorizerInfo;
    }

    /**
     * 根据token 和授权的 auth code 获取授权账号的信息
     *
     * @param componentToken
     * @param authCode
     * @return
     */
    public ComponentAuthInfo getAuth(String componentToken, String authCode) {
        if (componentToken == null) {
            throw new WechatException("componentToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token=" + componentToken;
        JSONObject bodyObject = new JSONObject();
        bodyObject.put("component_appid", wechatComponentProperties.getAppId());
        bodyObject.put("authorization_code", authCode);
        String post = post(url, bodyObject);
        JSONObject jsonObject = JSON.parseObject(post);
        JSONObject authorizationInfo = jsonObject.getJSONObject("authorization_info");
        if (authorizationInfo != null && StringUtils.isNotBlank(authorizationInfo.getString("authorizer_access_token"))) {
            ComponentAuthInfo authorizerToken = new ComponentAuthInfo();
            authorizerToken.setToken(authorizationInfo.getString("authorizer_access_token"));
            authorizerToken.setAppId(authorizationInfo.getString("authorizer_appid"));
            return authorizerToken;
        } else {
            throw new WechatException("getAuth error with " + post);
        }
    }

    /**
     * 根据 token 获取 preauthcode 用户授权订阅号
     *
     * @param componentToken
     * @return
     */
    public String getPreAuthCode(String componentToken) {
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
    public String getComponentToken(String ticket) {
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


    /**
     * 根据回复消息进行自动加密
     *
     * @param componentTextMessage
     * @return
     */
    public String encryptMsg(ComponentTextMessage componentTextMessage) {
        String str = XmlUtils.objectToXml(componentTextMessage);
        try {
            return wxBizMsgCrypt.encryptMsg(str, String.valueOf(System.currentTimeMillis()), UUID.randomUUID().toString().replace("-", ""));
        } catch (AesException e) {
            throw new WechatException("encryptMsg error", e);
        }
    }

    /**
     * 根据 componentToken 生成登录地址
     *
     * @param componentToken
     * @param redirectUri
     * @return
     */
    public String generateLoginUrl(String componentToken, String redirectUri) {
        String url = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=1";
        String authCode = getPreAuthCode(componentToken);
        return String.format(url, wechatComponentProperties.getAppId(), authCode, redirectUri);
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
