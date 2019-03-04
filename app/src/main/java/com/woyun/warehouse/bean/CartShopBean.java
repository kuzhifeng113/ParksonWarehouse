package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车
 */
public class CartShopBean implements Serializable {

    private List<GoodsListBean> goodsList;
    private List<CartListBean> cartList;

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public List<CartListBean> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartListBean> cartList) {
        this.cartList = cartList;
    }

    public static class GoodsListBean implements  Serializable {
        /**
         * image : http://image.bscvip.com/goods_images/1540891360264478.png
         * goodsId : 2
         * transport : 10
         * content : <p><img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png">
         </p>
         <p><br></p>
         * iShelf : false
         * price : 15
         * name : 裤子2
         * vipPrice : 10
         * wantNum : 3
         * sortBy : 10
         * categoryId : 2
         * isVote : false
         * isFavorite : false
         *
         */
        private double bkCoin;
        private String image;
        private int goodsId;
        private int transport;
        private String content;
        private boolean iShelf;
        private String price;
        private String name;
        private String vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private boolean isVote;
        private boolean isFavorite;

        public double getBkCoin() {
            return bkCoin;
        }

        public void setBkCoin(double bkCoin) {
            this.bkCoin = bkCoin;
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

        public int getTransport() {
            return transport;
        }

        public void setTransport(int transport) {
            this.transport = transport;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public boolean isIShelf() {
            return iShelf;
        }

        public void setIShelf(boolean iShelf) {
            this.iShelf = iShelf;
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

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public boolean isIsVote() {
            return isVote;
        }

        public void setIsVote(boolean isVote) {
            this.isVote = isVote;
        }

        public boolean isIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }
    }

    public static class CartListBean implements Serializable {
        /**
         * skuName : 无敌风火轮蓝绝地的色SS码
         * unitPrice : 3
         * goodsId : 1
         * goodsImage : http://image.bscvip.com/goods_images/1540891360264478.png
         * id : 6
         * goodsName : 裤子1
         * userid : bc579548d72786b
         * skuId : 7
         * skuImage : http://image.bscvip.com/goods_images/1541385780405985.jpg
         * skuNum : 5
         * memeo : SKU 的specValue值拼接，“黑色，L”
         */

        private String skuName;
        private String unitPrice;
        private int goodsId;
        private String goodsImage;
        private int id;
        private String goodsName;
        private String userid;
        private int skuId;
        private String skuImage;
        private int skuNum;
        private String memo;
        private String transport;//邮费
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public String getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(String unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public String getSkuImage() {
            return skuImage;
        }

        public void setSkuImage(String skuImage) {
            this.skuImage = skuImage;
        }

        public int getSkuNum() {
            return skuNum;
        }

        public void setSkuNum(int skuNum) {
            this.skuNum = skuNum;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getTransport() {
            return transport;
        }

        public void setTransport(String transport) {
            this.transport = transport;
        }
    }
}
