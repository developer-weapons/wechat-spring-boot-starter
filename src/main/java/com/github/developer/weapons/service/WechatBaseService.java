package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSONObject;
import com.github.developer.weapons.exception.WechatException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;

@Slf4j
public class WechatBaseService {
    protected OkHttpClient okHttpClient = new OkHttpClient();

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
                String result = execute.body().string();
                log.debug("WechatBaseService post, url : {}, body: {}, result : {}", url, body, result);
                return result;
            }
            log.error("WechatBaseService post error, url : {}, body: {}, message : {}", url, body, execute.message());
            throw new WechatException("post to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            throw new WechatException("post to wechat endpoint " + url + " error ", e);
        }
    }

    protected String post(String url, String body) {
        Request request = new Request.Builder()
                .addHeader("body-type", "application/json")
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), body))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                assert execute.body() != null;
                String result = execute.body().string();
                log.debug("WechatBaseService post, url : {}, body: {}, result : {}", url, body, result);
                return result;
            }
            log.error("WechatBaseService post error, url : {}, body: {}, message : {}", url, body, execute.message());
            throw new WechatException("post to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            log.error("WechatBaseService post error, url : {}, body: {}", url, body, e);
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
                String result = execute.body().string();
                log.debug("WechatBaseService get, url : {}, result : {}", url, result);
                return result;
            }
            log.error("WechatBaseService get error, url : {}, message : {}", url, execute.message());
            throw new WechatException("get to wechat endpoint " + url + " error " + execute.message());
        } catch (IOException e) {
            log.error("WechatBaseService get error, url : {}", url, e);
            throw new WechatException("get to wechat endpoint " + url + " error ", e);
        }
    }
}
