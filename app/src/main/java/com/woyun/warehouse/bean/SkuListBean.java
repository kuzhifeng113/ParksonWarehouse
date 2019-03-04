package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class SkuListBean implements Serializable {
    /**
     * skuName : 红色L码
     * image : sku-image
     * goodsId : 1
     * price : 2
     * num : 10
     * vipPrice : 1
     * specMap : [{"specName":"颜色","specValue":"红色"},{"specName":"尺寸","specValue":"L"}]
     * sortBy : 0
     * skuId : 1
     * status : 1
     */
    private String skuName;
    private String image;
    private String goodsId;
    private String price;
    private int num;
    private String vipPrice;
    private int sortBy;
    private int skuId;
    private int status;

    private List<SkuAttribute> specMap;

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public List<SkuAttribute> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(List<SkuAttribute> specMap) {
        this.specMap = specMap;
    }
}
