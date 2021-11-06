package com.github.developer.weapons.model.official;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficalAccessTokenQuery {
    private String appid;
    private String secret;
}
