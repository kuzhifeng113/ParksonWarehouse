package com.woyun.warehouse.bean;

import java.util.List;

public class GrabGoodsBean {

    /**
     * image : http://image.bscvip.com/goods_images/1548738828473313.png
     * compareUrl :
     * goodsId : 144
     * resList : [{"image":"http://image.bscvip.com/res_images/1553068365309766.jpg","videoUrl":"","type":1}]
     * rushBuyId : 1
     * transport : 8
     * title : 何必为难耳朵 这款耳机戴上就不想摘下
     * cartNum : 0
     * content : <img src='http://image.bscvip.com/goods_images/1546182298550074.png' /><img src='http://image.bscvip.com/goods_images/1546182303803917.png' /><img src='http://image.bscvip.com/goods_images/1546182310440095.png' /><img src='http://image.bscvip.com/goods_images/1546182316406651.png' /><img src='http://image.bscvip.com/goods_images/1546182321852208.png' /><img src='http://image.bscvip.com/goods_images/1546182327753630.png' /><img src='http://image.bscvip.com/goods_images/1546182333036064.png' /><img src='http://image.bscvip.com/goods_images/1546182339719027.png' />
     * favoriteNum : 1
     * price : 18
     * supplier : 乔威工厂供应商
     * name :  乔威 音浪线控耳机 自带高清麦克风 一键高清通话
     * vipPrice : 16.9
     * wantNum : 0
     * sortBy : 1
     * endTime : 1554775200000
     * stock : 64
     * categoryId : 38
     * sellNum : 645
     * freeShopping : 80
     */

    private String image;
    private String compareUrl;
    private int goodsId;
    private int rushBuyId;
    private int transport;
    private String title;
    private int cartNum;
    private String content;
    private int favoriteNum;
    private String price;
    private String supplier;
    private String name;
    private String vipPrice;
    private int wantNum;
    private int sortBy;
    private long endTime;
    private int stock;
    private int categoryId;
    private int sellNum;
    private int freeShopping;
    private List<ResListBean> resList;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getRushBuyId() {
        return rushBuyId;
    }

    public void setRushBuyId(int rushBuyId) {
        this.rushBuyId = rushBuyId;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public void setFavoriteNum(int favoriteNum) {
        this.favoriteNum = favoriteNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getWantNum() {
        return wantNum;
    }

    public void setWantNum(int wantNum) {
        this.wantNum = wantNum;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSellNum() {
        return sellNum;
    }

    public void setSellNum(int sellNum) {
        this.sellNum = sellNum;
    }

    public int getFreeShopping() {
        return freeShopping;
    }

    public void setFreeShopping(int freeShopping) {
        this.freeShopping = freeShopping;
    }

    public List<ResListBean> getResList() {
        return resList;
    }

    public void setResList(List<ResListBean> resList) {
        this.resList = resList;
    }

    public static class ResListBean {
        /**
         * image : http://image.bscvip.com/res_images/1553068365309766.jpg
         * videoUrl :
         * type : 1
         */

        private String image;
        private String videoUrl;
        private int type;

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
}
