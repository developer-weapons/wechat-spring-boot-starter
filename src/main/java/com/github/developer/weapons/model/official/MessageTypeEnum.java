package com.github.developer.weapons.model.official;

public enum MessageTypeEnum {
    TEXT("text")
    ,IMAGE("image")
    ;
    private String type;

    public String getType() {
        return type;
    }

    MessageTypeEnum(String type) {
        this.type = type;
    }
}
