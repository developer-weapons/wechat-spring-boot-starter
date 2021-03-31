package com.github.developer.weapons.model.official;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Fan {
    private Long total;
    private Long count;
    @JSONField(name = "next_openid")
    private String nextOpenId;
    private FanData data;
}
