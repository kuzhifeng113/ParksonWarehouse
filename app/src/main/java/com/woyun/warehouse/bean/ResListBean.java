package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情录播图 bean
 */
public class ResListBean implements Serializable {
    /**
     "image": "http:\/\/image.bscvip.com\/goods_images\/1548741249810897.png",
     "videoUrl": "",
     "type": 1
     */
    private String image;
    private String videoUrl;
    private int  type;


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
}
