package com.github.developer.weapons.model.official;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.developer.weapons.model.Token;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OfficialNews extends Token<OfficialCustomMessage> {

    private String title;

    @JSONField(name = "thumb_media_id")
    private String thumbMediaId;

    private String author;

    private String digest;

    @JSONField(name = "show_cover_pic")
    private Integer showCoverPic = 0;

    private String content;
    @JSONField(name = "content_source_url")

    private String contentSourceUrl;

    @JSONField(name = "need_open_comment")
    private Integer needOpenComment = 0;

    @JSONField(name = "only_fans_can_comment")
    private Integer onlyFansCanComment = 1;
}
