package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private ComponentMessageArticleItem Articles;

    public ComponentMessage addArticle(ComponentMessageArticle article) {
        if (Articles == null) {
            Articles = new ComponentMessageArticleItem();
        }
        if (Articles.getItem() == null) {
            Articles.setItem(new ArrayList<>());
        }
        Map<String, String> map = new HashMap<>();
        map.put("Title", article.getTitle());
        map.put("Description", article.getDescription());
        map.put("PicUrl", article.getPicUrl());
        map.put("Url", article.getUrl());
        Articles.getItem().add(map);
        return this;
    }
}
