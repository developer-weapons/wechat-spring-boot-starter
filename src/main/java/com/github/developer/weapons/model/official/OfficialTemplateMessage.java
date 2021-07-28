package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 公众号模板消息
 */
public class OfficialTemplateMessage extends Token<OfficialTemplateMessage> {
    /**
     * 接收消息的 OPENID
     */
    @Getter
    private String touser;

    /**
     * 模板id
     */
    @Getter
    private String template_id;

    /**
     * 跳转链接
     */
    @Getter
    private String url;

    /**
     * 小程序
     */
    @Getter
    private Map<String, String> miniprogram;

    /**
     * 内容
     */
    @Getter
    private Map<String, Map<String, String>> data;

    /**
     * 设置小程序
     *
     * @param appid
     * @param pagepath
     * @return
     */
    public OfficialTemplateMessage withMiniProgram(String appid, String pagepath) {
        if (miniprogram == null) {
            miniprogram = new HashMap<>();
        }
        miniprogram.put("appid", appid);
        miniprogram.put("pagepath", pagepath);
        return this;
    }

    /**
     * 设置内容
     *
     * @param k
     * @param v
     * @param color
     * @return
     */
    public OfficialTemplateMessage withData(String k, String v, String color) {
        if (data == null) {
            data = new HashMap<>();
        }
        Map<String, String> value = new HashMap<>();
        value.put("value", v);
        if (StringUtils.isNotBlank(color)) {
            value.put("color", color);
        }
        data.put(k, value);
        return this;
    }

    /**
     * 设置 toUser
     *
     * @param touser
     * @return
     */
    public OfficialTemplateMessage withTouser(String touser) {
        this.touser = touser;
        return this;
    }

    /**
     * 设置 templateId
     *
     * @param templateId
     * @return
     */
    public OfficialTemplateMessage withTemplateId(String templateId) {
        this.template_id = templateId;
        return this;
    }

    /**
     * 设置 url
     *
     * @param url
     * @return
     */
    public OfficialTemplateMessage withUrL(String url) {
        this.url = url;
        return this;
    }

    /**
     * 直接获取实例的静态方法
     *
     * @return
     */
    public static OfficialTemplateMessage build() {
        return new OfficialTemplateMessage();
    }
}
