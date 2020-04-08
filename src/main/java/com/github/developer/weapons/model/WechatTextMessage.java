package com.github.developer.weapons.model;

import lombok.Data;

/**
 * Created by codedrinker on 2019/4/27.
 */
@Data
public class WechatTextMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
}
