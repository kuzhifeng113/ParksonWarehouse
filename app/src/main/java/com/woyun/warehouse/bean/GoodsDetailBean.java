package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class GoodsDetailBean implements Serializable {
    /**
     * image : http://image.bscvip.com/goods_images/1540891360264478.png
     * skuList : [{"skuName":"红色L码","image":"sku-image","goodsId":"1","price":2,"num":"10","vipPrice":1,"specMap":[{"specName":"颜色","specValue":"红色"},{"specName":"尺寸","specValue":"L"}],"sortBy":0,"skuId":1,"status":1},{"skuName":"蓝色M码","image":"sku-image","goodsId":"1","price":3,"num":"20","vipPrice":2,"specMap":[{"specName":"颜色","specValue":"蓝色"},{"specName":"尺寸","specValue":"M"}],"sortBy":1,"skuId":2,"status":1}]
     * goodsId : 1
     * transport : 10
     * title : 小标题
     * content : <p>
     1<img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png"/>
     </p>
     <p>
     2
     <video class="edui-upload-video  vjs-default-skin  video-js" controls="" preload="none" width="420" height="280" src="http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4" data-setup="{}"
     poster="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png">
     <source src="http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4" type="video/mp4"/>
     </video>
     </p>
     * price : 18
     * name : 裤子1
     * vipPrice : 10.99
     * wantNum : 5
     * sortBy : 11
     * stock : 30
     * categoryId : 1
     * sellNum : 5
     * isFavorite : false   是否收藏
     * isVote :  是否投票
     * cartNum : 购物车数量
     */

    private String image;
    private String compareUrl;
    private int goodsId;
    private String bkCoin;//返多少
    private String transport;
    private String title;
    private String content;
    private String price;
    private String name;
    private String vipPrice;
    private int wantNum;
    private int sortBy;
    private int stock;
    private int categoryId;
    private int sellNum;
    private boolean isFavorite;
    private boolean isVote;
    private List<SkuListBean> skuList;
    private int cartNum;
    private String supplier;//供应商
    private String freeShopping;//满多少包邮
    private List<ResListBean> resList;
    private List<ContentListBean> contentList;

    public String getBkCoin() {
        return bkCoin;
    }

    public void setBkCoin(String bkCoin) {
        this.bkCoin = bkCoin;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getFreeShopping() {
        return freeShopping;
    }

    public void setFreeShopping(String freeShopping) {
        this.freeShopping = freeShopping;
    }

    public List<ResListBean> getResList() {
        return resList;
    }

    public void setResList(List<ResListBean> resList) {
        this.resList = resList;
    }

    public List<ContentListBean> getContentList() {
        return contentList;
    }

    public void setContentList(List<ContentListBean> contentList) {
        this.contentList = contentList;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }

    public int getCartNum() {
        return cartNum;
    }

    public void setCartNum(int cartNum) {
        this.cartNum = cartNum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public boolean isIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isIsVote() {
        return isVote;
    }

    public void setIsVote(boolean isVote) {
        isVote = isVote;
    }

    public List<SkuListBean> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuListBean> skuList) {
        this.skuList = skuList;
    }

}
