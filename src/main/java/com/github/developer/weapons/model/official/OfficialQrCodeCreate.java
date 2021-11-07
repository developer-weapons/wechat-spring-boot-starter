package com.github.developer.weapons.model.official;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficialQrCodeCreate {
    private String accessToken;
    private String scene;
    private QrCodeActionName actionName;
}
