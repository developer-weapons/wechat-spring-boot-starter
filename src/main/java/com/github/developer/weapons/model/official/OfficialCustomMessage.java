package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class OfficialCustomMessage extends Token<OfficialCustomMessage> {
    /**
     * 接收消息的 OPENID
     */
    @Getter
    private String touser;

    /**
     * 消息类型
     */
    @Getter
    private MessageTypeEnum msgtype;

    /**
     * 消息类型
     */
    @Getter
    private Map<String, String> text;

    /**
     * 当内容是消息类型的时候设置发送内容
     *
     * @param content
     * @return
     */
    public OfficialCustomMessage withContent(String content) {
        if (text == null) {
            text = new HashMap<>();
        }
        text.put("content", content);
        return this;
    }


    /**
     * 设置 toUser
     *
     * @param touser
     * @return
     */
    public OfficialCustomMessage withTouser(String touser) {
        this.touser = touser;
        return this;
    }


    /**
     * 设置类型
     *
     * @param msgtype
     * @return
     */
    public OfficialCustomMessage withMsgtype(MessageTypeEnum msgtype) {
        this.msgtype = msgtype;
        return this;
    }

    /**
     * 直接获取实例的静态方法
     *
     * @return
     */
    public static OfficialCustomMessage build() {
        return new OfficialCustomMessage();
    }
}
