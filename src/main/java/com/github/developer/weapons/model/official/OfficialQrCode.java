package com.github.developer.weapons.model.official;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class OfficialQrCode {
    private String ticket;
    @JSONField(name = "expire_seconds")
    private int expireSeconds;
    private String url;
}
