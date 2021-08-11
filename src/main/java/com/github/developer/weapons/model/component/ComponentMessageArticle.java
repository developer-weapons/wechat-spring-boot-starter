package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ComponentMessageArticle {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
