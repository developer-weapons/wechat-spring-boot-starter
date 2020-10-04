package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSON;
import com.github.developer.weapons.exception.WechatException;
import com.github.developer.weapons.model.official.OfficialCustomMessage;
import com.github.developer.weapons.model.official.OfficialUserInfo;
import com.github.developer.weapons.model.official.OfficialUserQuery;
import org.apache.commons.lang3.StringUtils;


/**
 * 微信订阅号
 */
public class WechatOfficialService extends WechatBaseService {

    /**
     * 通过 officialUserQuery 获取用户资料
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param officialUserQuery
     * @return
     */
    public OfficialUserInfo getUserInfo(OfficialUserQuery officialUserQuery) {
        if (officialUserQuery.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialUserQuery.getOpenId() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + officialUserQuery.getAccessToken() + "&openid=" + officialUserQuery.getOpenId() + "&lang=zh_CN";
        String body = get(url);
        if (StringUtils.isNotBlank(body)) {
            OfficialUserInfo officialUserInfo = JSON.parseObject(body, OfficialUserInfo.class);
            return officialUserInfo;
        } else {
            throw new WechatException("obtain user info error with " + body);
        }
    }


    /**
     * 发送消息
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     *
     * @param officialCustomMessage
     */
    public void sendMsg(OfficialCustomMessage officialCustomMessage) {
        if (officialCustomMessage.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialCustomMessage.getTouser() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + officialCustomMessage.getAccessToken();
        post(url, JSON.toJSONString(officialCustomMessage));
    }
}
