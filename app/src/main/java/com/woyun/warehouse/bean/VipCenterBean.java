package com.woyun.warehouse.bean;

import java.util.List;

public class VipCenterBean {


    /**
     * takeBcCoin : 0.0
     * goodsList : [{"image":"http://image.bscvip.com/goods_images/1541408769775055.jpg","goodsId":3,"transport":10,"title":"小标题","content":"<p><img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png\">&nbsp;<\/p>","iShelf":true,"price":15,"name":"会员礼包","vipPrice":11,"wantNum":0,"sortBy":0,"categoryId":-1,"isVote":false,"sellNum":20,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1542188707406395.jpg","goodsId":4,"transport":0,"title":"无敌礼包","content":"<p><br><\/p>","iShelf":true,"price":680,"name":"VIP超级礼包","vipPrice":680,"wantNum":0,"sortBy":0,"categoryId":-1,"isVote":false,"sellNum":0,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1542189707171234.jpg","goodsId":5,"transport":0,"title":"vip","content":"<p><br><\/p>","iShelf":true,"price":680,"name":"VIP礼包","vipPrice":680,"wantNum":0,"sortBy":0,"categoryId":-1,"isVote":false,"sellNum":0,"isFavorite":false}]
     * grantCbCoin : 3000.0
     * profitImage : ["http://image.bscvip.com/app_static/vip_1.png","http://image.bscvip.com/app_static/vip_2.png","http://image.bscvip.com/app_static/vip_3.png","http://image.bscvip.com/app_static/vip_4.png"]
     * vipType : {"price":"798.00","name":"VIP尊贵会员","id":1}
     * vipewm : http://image.bscvip.com/app_static/vip_ewm.jpg
     * takeGift : false
     * bcCoin : 1.11
     * vip : {"lastPayDate":1576080000000,"name":"","id":1,"isEnable":true}
     * isVip : true
     * wxh
     */
    private boolean sellVip;//是否购买过VIP，=false未购买，=true已购买
    private double takeBcCoin;
    private double grantCbCoin;
    private VipTypeBean vipType;
    private String vipewm;
    private String wxh;
    private boolean takeGift;//是否领取礼包 =false未领取，=true，已领取
    private double bcCoin;
    private VipBean vip;
    private boolean isVip;
    private List<GoodsListBean> goodsList;
    private List<String> profitImage;

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public boolean isSellVip() {
        return sellVip;
    }

    public void setSellVip(boolean sellVip) {
        this.sellVip = sellVip;
    }

    public double getTakeBcCoin() {
        return takeBcCoin;
    }

    public void setTakeBcCoin(double takeBcCoin) {
        this.takeBcCoin = takeBcCoin;
    }

    public double getGrantCbCoin() {
        return grantCbCoin;
    }

    public void setGrantCbCoin(double grantCbCoin) {
        this.grantCbCoin = grantCbCoin;
    }

    public VipTypeBean getVipType() {
        return vipType;
    }

    public void setVipType(VipTypeBean vipType) {
        this.vipType = vipType;
    }

    public String getVipewm() {
        return vipewm;
    }

    public void setVipewm(String vipewm) {
        this.vipewm = vipewm;
    }

    public boolean isTakeGift() {
        return takeGift;
    }

    public void setTakeGift(boolean takeGift) {
        this.takeGift = takeGift;
    }

    public double getBcCoin() {
        return bcCoin;
    }

    public void setBcCoin(double bcCoin) {
        this.bcCoin = bcCoin;
    }

    public VipBean getVip() {
        return vip;
    }

    public void setVip(VipBean vip) {
        this.vip = vip;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public List<String> getProfitImage() {
        return profitImage;
    }

    public void setProfitImage(List<String> profitImage) {
        this.profitImage = profitImage;
    }

    public static class VipTypeBean {
        /**
         * price : 798.00
         * name : VIP尊贵会员
         * id : 1
         */

        private String price;
        private String name;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class VipBean {
        /**
         * endDate
         * lastPayDate : 1576080000000
         * name :
         * id : 1
         * isEnable : true
         */

        private long lastPayDate;
        private long endDate;
        private String name;
        private int id;
        private boolean isEnable;

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public long getLastPayDate() {
            return lastPayDate;
        }

        public void setLastPayDate(long lastPayDate) {
            this.lastPayDate = lastPayDate;
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

        public boolean isIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
        }
    }

    public static class GoodsListBean {
        /**
         * image : http://image.bscvip.com/goods_images/1541408769775055.jpg
         * goodsId : 3
         * transport : 10.0
         * title : 小标题
         * content : <p><img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png_one.png">&nbsp;</p>
         * iShelf : true
         * price : 15.0
         * name : 会员礼包
         * vipPrice : 11.0
         * wantNum : 0
         * sortBy : 0
         * categoryId : -1
         * isVote : false
         * sellNum : 20
         * isFavorite : false
         */

        private String image;
        private int goodsId;
        private double transport;
        private String title;
        private String content;
        private boolean iShelf;
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

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
