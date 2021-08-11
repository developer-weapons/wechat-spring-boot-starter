package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;

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
        Articles.getItem().add(article);
        return this;
    }
}
