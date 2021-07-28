package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSON;
import com.github.developer.weapons.exception.WechatException;
import com.github.developer.weapons.model.official.*;
import com.github.developer.weapons.util.XmlUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信订阅号
 */
public class WechatOfficialService extends WechatBaseService {

    /**
     * 验证请求是否正确
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public boolean isValid(String signature, String timestamp, String nonce, String token) {
        String[] values = {token, timestamp, nonce};
        Arrays.sort(values);
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);
        return StringUtils.equals(signature, sign);
    }

    /**
     * 非加密的信息流转换为map
     *
     * @param is
     * @return
     */
    public Map<String, String> toMap(InputStream is) {
        Map<String, String> map = new HashMap<>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            is.close();
            return map;
        } catch (Exception e) {
            throw new WechatException("toMap error with" + e.getMessage());
        }
    }

    /**
     * 转换构建好的类型为 String
     *
     * @param officialAutoReplyMessage
     * @return
     */
    public String toMsg(OfficialAutoReplyMessage officialAutoReplyMessage) {
        return XmlUtils.objectToXml(officialAutoReplyMessage);
    }

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
     * https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     *
     * @param accessToken
     * @param nextOpenId
     * @return
     */
    public Fan getFans(String accessToken, String nextOpenId) {
        if (accessToken == null) {
            throw new WechatException("accessToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        if (StringUtils.isNotBlank(nextOpenId)) {
            url += "&next_openid=" + nextOpenId;
        }
        String body = get(url);
        if (StringUtils.isNotBlank(body)) {
            Fan fan = JSON.parseObject(body, Fan.class);
            return fan;
        } else {
            throw new WechatException("obtain fan info error with " + body);
        }
    }


    /**
     * 发送消息
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     *
     * @param officialCustomMessage
     * @return
     */
    public String sendMsg(OfficialCustomMessage officialCustomMessage) {
        if (officialCustomMessage.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialCustomMessage.getTouser() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + officialCustomMessage.getAccessToken();
        return post(url, JSON.toJSONString(officialCustomMessage));
    }

    /**
     * 发送模板消息
     * POST https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
     *
     * @param officialTemplateMessage
     * @return
     */
    public String sendTemplateMsg(OfficialTemplateMessage officialTemplateMessage) {
        if (officialTemplateMessage.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialTemplateMessage.getTouser() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + officialTemplateMessage.getAccessToken();
        return post(url, JSON.toJSONString(officialTemplateMessage));
    }
}
