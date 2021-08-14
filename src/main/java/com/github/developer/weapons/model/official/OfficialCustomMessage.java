package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 公众号自定义消息
 */
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
     * 文本消息内容
     */
    @Getter
    private Map<String, String> text;


    /**
     * 图片消息内容
     */
    @Getter
    private Map<String, String> image;

    /**
     * 列表
     */
    @Getter
    private MenuMsg msgmenu;

    /**
     * 设置头文字
     *
     * @param content
     * @return
     */
    public OfficialCustomMessage withHeadContent(String content) {
        if (msgmenu == null) {
            msgmenu = new MenuMsg();
        }
        msgmenu.setHeadContent(content);
        return this;
    }

    /**
     * 设置尾文字
     *
     * @param content
     * @return
     */
    public OfficialCustomMessage withTailContent(String content) {
        if (msgmenu == null) {
            msgmenu = new MenuMsg();
        }
        msgmenu.setTailContent(content);
        return this;
    }

    /**
     * 设置按钮
     *
     * @return
     */
    public OfficialCustomMessage withMenu(String id, String content) {
        if (msgmenu == null) {
            msgmenu = new MenuMsg();
        }
        if (msgmenu.getList() == null) {
            msgmenu.setList(new ArrayList<>());
        }
        MenuMsgItem menuMsgItem = new MenuMsgItem();
        menuMsgItem.setId(id);
        menuMsgItem.setContent(content);
        msgmenu.getList().add(menuMsgItem);
        return this;
    }

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
     * 图片消息的时候，进行图片设置
     *
     * @param mediaId
     * @return
     */
    public OfficialCustomMessage withMediaId(String mediaId) {
        if (image == null) {
            image = new HashMap<>();
        }
        image.put("media_id", mediaId);
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
