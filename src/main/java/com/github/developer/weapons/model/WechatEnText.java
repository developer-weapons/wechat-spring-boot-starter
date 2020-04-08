package com.github.developer.weapons.model;

import lombok.Data;

@Data
public class WechatEnText {
    private String Encrypt;
    private String MsgSignature;
    private String TimeStamp;
    private String Nonce;
}
