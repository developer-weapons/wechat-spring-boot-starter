package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

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
    private List<item> Articles;

    public ComponentMessage addArticle(item article) {
        if (Articles == null) {
            Articles = new ArrayList<>();
        }
        Articles.add(article);
        return this;
    }
}
