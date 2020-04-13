package com.github.developer.weapons.exception;

public class WechatException extends RuntimeException {
    private String message;

    public WechatException(String message) {
        this.message = message;
    }


    public WechatException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
