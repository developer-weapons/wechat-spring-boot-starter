package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import lombok.Data;

/**
 * 公众号模板消息
 */
@Data
public class OfficialMenu {
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;

    /**
     * jian
     */
    private String key;
}
