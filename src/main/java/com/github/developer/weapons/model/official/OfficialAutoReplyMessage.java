package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import com.github.developer.weapons.util.XmlUtils;
import lombok.Getter;

import java.util.Date;

/**
 * 公众号被动回复消息
 */
public class OfficialAutoReplyMessage extends Token<OfficialAutoReplyMessage> {
    /**
     * 发送用户
     */
    @Getter
    private String ToUserName;
    /**
     * 发送的订阅号
     */
    @Getter
    private String FromUserName;

    /**
     * 创建时间
     */
    @Getter
    private Long CreateTime;

    /**
     * 消息类型
     */
    @Getter
    private MessageTypeEnum MsgType;

    /**
     * 内容
     */
    @Getter
    private String Content;

    /**
     * 当内容是消息类型的时候设置发送内容
     *
     * @param content
     * @return
     */
    public OfficialAutoReplyMessage withContent(String content) {
        this.Content = content;
        return this;
    }


    /**
     * 设置 ToUserName
     *
     * @param toUserName
     * @return
     */
    public OfficialAutoReplyMessage withToUserName(String toUserName) {
        this.ToUserName = toUserName;
        return this;
    }

    /**
     * 设置 FromUserName
     *
     * @param fromUserName
     * @return
     */
    public OfficialAutoReplyMessage withFromUserName(String fromUserName) {
        this.FromUserName = fromUserName;
        return this;
    }

    /**
     * 设置 CreateTime
     *
     * @param createTime
     * @return
     */
    public OfficialAutoReplyMessage withCreateTime(Long createTime) {
        this.CreateTime = createTime;
        return this;
    }


    /**
     * 设置类型
     *
     * @param msgtype
     * @return
     */
    public OfficialAutoReplyMessage withMsgtype(MessageTypeEnum msgtype) {
        this.MsgType = msgtype;
        return this;
    }

    /**
     * 直接获取实例的静态方法
     *
     * @return
     */
    public static OfficialAutoReplyMessage build() {
        OfficialAutoReplyMessage officialAutoReplyMessage = new OfficialAutoReplyMessage();
        officialAutoReplyMessage.withCreateTime(new Date().getTime());
        return officialAutoReplyMessage;
    }

    /**
     * 转化 xml 格式
     *
     * @return
     */
    public String toXml() {
        return XmlUtils.objectToXml(this);
    }
}
