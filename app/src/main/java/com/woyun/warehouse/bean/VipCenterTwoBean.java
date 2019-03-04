package com.woyun.warehouse.bean;

import java.util.List;

public class VipCenterTwoBean {

    /**
     * goodsList : [{"image":"http://image.bscvip.com/goods_images/1547532495654651.jpg","compareUrl":"","goodsId":2,"transport":0,"title":"即刻加入会员行列 尊享臻贵会员专享礼遇","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547604570903134.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571061943.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571254765.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571470885.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571647000.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571778068.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571941550.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572128240.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572253193.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572438223.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572683409.png\" alt=\"\" />","isShelf":true,"favoriteNum":0,"price":1009,"supplier":"","name":"玻尿酸清润补水组合","vipPrice":780,"wantNum":0,"sortBy":0,"categoryId":-1,"sellNum":0},{"image":"http://image.bscvip.com/goods_images/1547532465100612.jpg","compareUrl":"","goodsId":3,"transport":0,"title":"vip","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1547604670640391.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604670786082.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604670918930.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671070273.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671219534.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671337347.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671455005.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671573319.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671654864.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604671793155.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604672016688.png\" alt=\"\" />","isShelf":true,"favoriteNum":0,"price":1009,"supplier":"","name":"烟酰胺蛋白亮肌组合","vipPrice":780,"wantNum":0,"sortBy":0,"categoryId":-1,"sellNum":0}]
     * vipType : {"price":"168.00","name":"VIP尊贵会员","remark":"0","id":1}
     */

    private VipTypeBean vipType;
    private List<GoodsListBean> goodsList;

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
         * price : 168.00
         * name : VIP尊贵会员
         * remark : 0
         * id : 1
         */
        private String price;
        private String name;
        private String remark;
        private int id;

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
    }

    public static class GoodsListBean {
        /**
         * image : http://image.bscvip.com/goods_images/1547532495654651.jpg
         * compareUrl :
         * goodsId : 2
         * transport : 0
         * title : 即刻加入会员行列 尊享臻贵会员专享礼遇
         * cartNum : 0
         * content : <img src="http://image.bscvip.com/goods_images/1547604570903134.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571061943.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571254765.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571470885.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571647000.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571778068.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571941550.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572128240.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572253193.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572438223.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572683409.png" alt="" />
         * isShelf : true
         * favoriteNum : 0
         * price : 1009
         * supplier :
         * name : 玻尿酸清润补水组合
         * vipPrice : 780
         * wantNum : 0
         * sortBy : 0
         * categoryId : -1
         * sellNum : 0
         */
        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private String image;
        private String compareUrl;
        private int goodsId;
        private int transport;
        private String title;
        private int cartNum;
        private String content;
        private boolean isShelf;
        private int favoriteNum;
        private int price;
        private String supplier;
        private String name;
        private int vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private int sellNum;

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

        public boolean isIsShelf() {
            return isShelf;
        }

        public void setIsShelf(boolean isShelf) {
            this.isShelf = isShelf;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public int getSellNum() {
            return sellNum;
        }

        public void setSellNum(int sellNum) {
            this.sellNum = sellNum;
        }
    }
}
