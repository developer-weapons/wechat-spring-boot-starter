package com.github.developer.weapons.model.component;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ComponentMessageArticleItem {
    private List<Map<String, String>> item;
}
