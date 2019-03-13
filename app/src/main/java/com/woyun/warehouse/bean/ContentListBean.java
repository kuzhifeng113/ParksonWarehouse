package com.woyun.warehouse.bean;

import java.io.Serializable;

/**
 * 商品详情内容 bean
 */
public class ContentListBean implements Serializable {
    /**
     "image": "http:\/\/image.bscvip.com\/goods_images\/1548741249810897.png",
     "videoUrl": "",
     "type": 1  图片  2  视频  3 富文本
     */
    private String image;
    private String videoUrl;
    private int  type;
    private String content;//富文本

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
