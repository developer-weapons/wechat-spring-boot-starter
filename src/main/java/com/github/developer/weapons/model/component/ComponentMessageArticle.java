package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ComponentMessageArticle {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
    private List<ComponentMessageArticle> item;
}
