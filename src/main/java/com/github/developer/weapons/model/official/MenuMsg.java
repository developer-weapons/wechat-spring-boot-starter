package com.github.developer.weapons.model.official;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class MenuMsg {
    @JSONField(name = "head_content")
    private String headContent;

    @JSONField(name = "tail_content")
    private String tailContent;

    private List<MenuMsgItem> list;
}
