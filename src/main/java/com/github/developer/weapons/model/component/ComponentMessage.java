package com.github.developer.weapons.model.component;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by codedrinker on 2019/4/27.
 */
@Data
@Accessors(chain = true)
public class ComponentMessage {
    private String ToUserName;
    private String FromUserName;
    private Long CreateTime;
    private String MsgType;
    private String Content;
    private String MsgId;
    private Integer ArticleCount;
    private JSONObject Articles;

    public ComponentMessage addArticle(ComponentMessageArticle article) {
        JSONObject value = new JSONObject();
        value.put("Title", article.getTitle());
        value.put("Description", article.getDescription());
        value.put("PicUrl", article.getPicUrl());
        value.put("Url", article.getUrl());
        Articles = new JSONObject();
        Articles.put("item", value);
        return this;
    }
}
