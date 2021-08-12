package com.github.developer.weapons.model.official;

import com.github.developer.weapons.model.Token;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficialMaterial extends Token<OfficialCustomMessage> {
    private String url;
    private String mediaId;
}
