package com.woyun.warehouse.bean;

import java.util.List;

public class AgentBuyVipBean {

    private List<VipTypeListBean> vipTypeList;
    private List<GoodsListBean> goodsList;

    public List<VipTypeListBean> getVipTypeList() {
        return vipTypeList;
    }

    public void setVipTypeList(List<VipTypeListBean> vipTypeList) {
        this.vipTypeList = vipTypeList;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class VipTypeListBean {
        /**
         * price : 10000.00
         * resList : []
         * name : 会员20个
         * remark : 10
         * id : 3
         */

        private String price;
        private String name;
        private String remark;
        private int id;
        private List<?> resList;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<?> getResList() {
            return resList;
        }

        public void setResList(List<?> resList) {
            this.resList = resList;
        }
    }

    public static class GoodsListBean {
        /**
         * image : http://image.bscvip.com/goods_images/1542332997317124.jpg
         * goodsId : 6
         * transport : 0.0
         * title : 代理开通
         * content : <p><br></p>
         * iShelf : true
         * favoriteNum : 0
         * price : 10000.0
         * name : 代理礼包
         * vipPrice : 10000.0
         * wantNum : 0
         * sortBy : 0
         * categoryId : -2
         * isVote : false
         * sellNum : 0
         * isFavorite : false
         */

        private String image;
        private int goodsId;
        private double transport;
        private String title;
        private String content;
        private boolean iShelf;
        private int favoriteNum;
        private double price;
        private String name;
        private double vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private boolean isVote;
        private int sellNum;
        private boolean isFavorite;
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
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

        public double getTransport() {
            return transport;
        }

        public void setTransport(double transport) {
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

        public boolean isIShelf() {
            return iShelf;
        }

        public void setIShelf(boolean iShelf) {
            this.iShelf = iShelf;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(double vipPrice) {
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
    }
}
