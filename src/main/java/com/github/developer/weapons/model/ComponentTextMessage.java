package com.github.developer.weapons.model;

import lombok.Data;

/**
 * Created by codedrinker on 2019/4/27.
 */
@Data
public class ComponentTextMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;

    public static ComponentTextMessage build() {
        return new ComponentTextMessage();
    }

    public ComponentTextMessage withToUserName(String userName) {
        this.ToUserName = userName;
        return this;
    }

    public ComponentTextMessage withFromUserName(String fromUserName) {
        this.FromUserName = fromUserName;
        return this;
    }

    public ComponentTextMessage withCreateTime(Long createTime) {
        this.CreateTime = createTime;
        return this;
    }

    public ComponentTextMessage withMsgType(String msgType) {
        this.MsgType = msgType;
        return this;
    }

    public ComponentTextMessage withContent(String content) {
        this.Content = content;
        return this;
    }

    public ComponentTextMessage withMsgId(String msgId) {
        this.MsgId = msgId;
        return this;
    }
}
