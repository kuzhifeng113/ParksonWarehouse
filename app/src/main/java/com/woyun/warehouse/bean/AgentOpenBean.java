package com.woyun.warehouse.bean;

import java.util.List;

public class AgentOpenBean {

    /**
     * goodsList : [{"image":"http://image.bscvip.com/goods_images/1542332997317124.jpg","goodsId":6,"transport":0,"title":"代理开通","content":"<p><br><\/p>","iShelf":true,"price":10000,"name":"代理礼包","vipPrice":10000,"wantNum":0,"sortBy":0,"categoryId":-2,"isVote":false,"sellNum":0,"isFavorite":false}]
     * vipType : {"price":"10000.00","name":"开通代理","id":2}
     */
    private String wxh;
    private VipTypeBean vipType;
    private List<GoodsListBean> goodsList;
    private String ewm;

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public VipTypeBean getVipType() {
        return vipType;
    }

    public void setVipType(VipTypeBean vipType) {
        this.vipType = vipType;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class VipTypeBean {
        /**
         * price : 10000.00
         * name : 开通代理
         * id : 2
         * remark
         */

        private String price;
        private String name;
        private int id;
        private String remark;

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class GoodsListBean {
        /**
         * image : http://image.bscvip.com/goods_images/1542332997317124.jpg
         * goodsId : 6
         * transport : 0
         * title : 代理开通
         * content : <p><br></p>
         * iShelf : true
         * price : 10000
         * name : 代理礼包
         * vipPrice : 10000
         * wantNum : 0
         * sortBy : 0
         * categoryId : -2
         * isVote : false
         * sellNum : 0
         * isFavorite : false
         */

        private String image;
        private int goodsId;
        private int transport;
        private String title;
        private String content;
        private boolean iShelf;
        private int price;
        private String name;
        private int vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private boolean isVote;
        private int sellNum;
        private boolean isFavorite;
        private boolean isCheck;

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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVipPrice() {
            return vipPrice;
        }

        public void setVipPrice(int vipPrice) {
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

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
