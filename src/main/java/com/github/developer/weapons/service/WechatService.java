package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSONObject;
import com.github.developer.weapons.exception.WechatException;
import okhttp3.*;

import java.io.IOException;

public class WechatService {
    private OkHttpClient okHttpClient = new OkHttpClient();

    protected String post(String url, JSONObject body) {
        Request request = new Request.Builder()
                .addHeader("body-type", "application/json")
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), body.toJSONString()))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                assert execute.body() != null;
                return execute.body().string();
            }
            throw new WechatException("post to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            throw new WechatException("post to wechat endpoint " + url + " error ", e);
        }
    }

    protected String get(String url) {
        Request request = new Request.Builder()
                .addHeader("body-type", "application/json")
                .url(url)
                .get()
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                assert execute.body() != null;
                return execute.body().string();
            }
            throw new WechatException("get to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            throw new WechatException("get to wechat endpoint " + url + " error ", e);
        }
    }
}
