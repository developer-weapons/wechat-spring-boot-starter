package com.github.developer.weapons.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.developer.weapons.exception.WechatException;
import com.github.developer.weapons.model.official.*;
import com.github.developer.weapons.util.FileUtils;
import com.github.developer.weapons.util.XmlUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信订阅号
 */
@Slf4j
public class WechatOfficialService extends WechatBaseService {
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

    /**
     * 验证请求是否正确
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public boolean isValid(String signature, String timestamp, String nonce, String token) {
        String[] values = {token, timestamp, nonce};
        Arrays.sort(values);
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);
        return StringUtils.equals(signature, sign);
    }

    /**
     * 非加密的信息流转换为map
     *
     * @param is
     * @return
     */
    public Map<String, String> toMap(InputStream is) {
        Map<String, String> map = new HashMap<>();
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);
            Element root = doc.getRootElement();
            List<Element> list = root.elements();
            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }
            is.close();
            return map;
        } catch (Exception e) {
            throw new WechatException("toMap error with" + e.getMessage());
        }
    }

    /**
     * 转换构建好的类型为 String
     *
     * @param officialAutoReplyMessage
     * @return
     */
    public String toMsg(OfficialAutoReplyMessage officialAutoReplyMessage) {
        return XmlUtils.objectToXml(officialAutoReplyMessage);
    }

    /**
     * 通过 officialUserQuery 获取用户资料
     * https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param officialUserQuery
     * @return
     */
    public OfficialUserInfo getUserInfo(OfficialUserQuery officialUserQuery) {
        if (officialUserQuery.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialUserQuery.getOpenId() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + officialUserQuery.getAccessToken() + "&openid=" + officialUserQuery.getOpenId() + "&lang=zh_CN";
        String body = get(url);
        if (StringUtils.isNotBlank(body)) {
            OfficialUserInfo officialUserInfo = JSON.parseObject(body, OfficialUserInfo.class);
            return officialUserInfo;
        } else {
            throw new WechatException("obtain user info error with " + body);
        }
    }

    /**
     * https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     *
     * @param accessToken
     * @param nextOpenId
     * @return
     */
    public Fan getFans(String accessToken, String nextOpenId) {
        if (accessToken == null) {
            throw new WechatException("accessToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        if (StringUtils.isNotBlank(nextOpenId)) {
            url += "&next_openid=" + nextOpenId;
        }
        String body = get(url);
        if (StringUtils.isNotBlank(body)) {
            Fan fan = JSON.parseObject(body, Fan.class);
            return fan;
        } else {
            throw new WechatException("obtain fan info error with " + body);
        }
    }


    /**
     * 发送消息
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     *
     * @param officialCustomMessage
     * @return
     */
    public String sendMsg(OfficialCustomMessage officialCustomMessage) {
        if (officialCustomMessage.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialCustomMessage.getTouser() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + officialCustomMessage.getAccessToken();
        return post(url, JSON.toJSONString(officialCustomMessage));
    }

    /**
     * 发送模板消息
     * POST https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
     *
     * @param officialTemplateMessage
     * @return
     */
    public String sendTemplateMsg(OfficialTemplateMessage officialTemplateMessage) {
        if (officialTemplateMessage.getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        if (officialTemplateMessage.getTouser() == null) {
            throw new WechatException("openId is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + officialTemplateMessage.getAccessToken();
        String body = JSON.toJSONString(officialTemplateMessage);
        return post(url, body);
    }

    /**
     * 创建菜单
     * POST https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN
     *
     * @param menus
     * @return
     */
    public String createMenu(List<OfficialMenu> menus) {
        if (menus == null || menus.size() == 0) {
            throw new WechatException("menus is empty");
        }
        if (menus.get(0).getAccessToken() == null) {
            throw new WechatException("accessToken is missing");
        }
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + menus.get(0).getAccessToken();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("button", menus);
        String jsonString = jsonObject.toJSONString();
        log.info("createMenu : {}", jsonString);
        return post(url, jsonString);
    }

    /**
     * 添加图片素材
     *
     * @param officialMaterial
     * @return
     */
    public String addMaterial(OfficialMaterial officialMaterial) {
        File file = null;
        try {
            file = FileUtils.newFile(officialMaterial.getUrl());
            String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=%s&type=image";
            URL urlGet = new URL(String.format(url, officialMaterial.getAccessToken()));
            HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", DEFAULT_USER_AGENT);
            conn.setRequestProperty("Charsert", "UTF-8");
            // 定义数据分隔线
            String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL";
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 定义最后数据分隔线
            StringBuilder mediaData = new StringBuilder();
            mediaData.append("--").append(BOUNDARY).append("\r\n");
            mediaData.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
            mediaData.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] mediaDatas = mediaData.toString().getBytes();
            out.write(mediaDatas);
            DataInputStream fs = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = fs.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            IOUtils.closeQuietly(fs);
            // 多个文件时，二个文件之间加入这个
            out.write("\r\n".getBytes());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(end_data);
            out.flush();
            IOUtils.closeQuietly(out);

            // 定义BufferedReader输入流来读取URL的响应
            InputStream in = conn.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in, Charsets.UTF_8));
            String valueString = null;
            StringBuffer bufferRes = null;
            bufferRes = new StringBuffer();
            while ((valueString = read.readLine()) != null) {
                bufferRes.append(valueString);
            }
            IOUtils.closeQuietly(in);
            // 关闭连接
            if (conn != null) {
                conn.disconnect();
            }
            String s = bufferRes.toString();
            JSONObject jsonObject = JSON.parseObject(s);
            return jsonObject.getString("media_id");
        } catch (Exception e) {
            log.error("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR", e);
            throw new WechatException("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR" + e.getMessage());
        } finally {
            FileUtils.deleteFile(file);
        }
    }

    /**
     * 添加文章
     *
     * @param news
     * @return
     */
    public String addNews(List<OfficialNews> news) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=%s";
        JSONObject req = new JSONObject();
        req.put("articles", news);
        String content = JSON.toJSONString(req);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, news.get(0).getAccessToken()))
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
                log.info("WECHAT_OFFICIAL_ADD_MATERIAL : {}", string);
                JSONObject jsonObject = JSON.parseObject(string);
                return jsonObject.getString("media_id");
            }
            log.error("WECHAT_OFFICIAL_ADD_NEWS_ERROR, message : {}, response : {}", content, execute);
            throw new WechatException("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR" + execute);

        } catch (Exception e) {
            log.error("WECHAT_OFFICIAL_ADD_NEWS_ERROR, message : {}", content, e);
            throw new WechatException("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR" + e.getMessage());
        }
    }

    /**
     * 更新文章
     *
     * @param news
     * @return
     */
    public String updateNews(OfficialNews news) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=%s";
        JSONObject req = new JSONObject();
        req.put("articles", news);
        req.put("index", 0);
        req.put("media_id", news.getMediaId());
        String content = JSON.toJSONString(req);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, news.getAccessToken()))
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                assert execute.body() != null;
                String string = execute.body().string();
                log.info("WECHAT_OFFICIAL_UPDATE_MATERIAL : {}", string);
                return string;
            }
            log.error("WECHAT_OFFICIAL_ADD_NEWS_ERROR, message : {}, response : {}", content, execute);
            throw new WechatException("WECHAT_OFFICIAL_UPDATE_MATERIAL_ERROR" + execute);

        } catch (Exception e) {
            log.error("WECHAT_OFFICIAL_ADD_NEWS_ERROR, message : {}", content, e);
            throw new WechatException("WECHAT_OFFICIAL_UPDATE_MATERIAL_ERROR" + e.getMessage());
        }
    }

    /**
     * 获取永久链接
     *
     * @param officialMaterial
     * @return
     */
    public String getMaterialUrl(OfficialMaterial officialMaterial) {
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=%s";
        JSONObject req = new JSONObject();
        req.put("media_id", officialMaterial.getMediaId());
        String content = JSON.toJSONString(req);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, officialMaterial.getAccessToken()))
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                String string = execute.body().string();
                JSONObject jsonObject = JSON.parseObject(string);
                return jsonObject.getJSONArray("news_item").getJSONObject(0).getString("url");
            }
            log.error("WECHAT_OFFICIAL_GET_MATERIAL_URL_ERROR, message : {}, response : {}", content, execute);
            throw new WechatException("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR" + execute);

        } catch (Exception e) {
            log.error("WECHAT_OFFICIAL_GET_MATERIAL_URL_ERROR, message : {}", content, e);
            throw new WechatException("WECHAT_OFFICIAL_ADD_MATERIAL_ERROR" + e.getMessage());
        }
    }
}
