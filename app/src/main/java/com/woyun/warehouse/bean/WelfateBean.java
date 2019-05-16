package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 福利 bean
 */
public class WelfateBean {

    /**
     * totalMoney : 0
     * unPack : 0
     * goodsList : [{"image":"http://image.bscvip.com/goods_images/1552879003325334.jpg","compareUrl":"","bkCoin":0,"goodsId":1,"resList":[{"image":"http://image.bscvip.com/res_images/1552879392162918.jpg","videoUrl":"","type":1}],"transport":0,"title":"代理礼包","cartNum":0,"content":"<img src=\"http://image.bscvip.com/content_images/1552879706474987.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879717241019.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879726454028.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879739740553.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879748941671.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879892783867.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879902996214.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879913054527.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879920316957.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879933851960.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879964397256.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552879984077457.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552880003063199.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552880021254432.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552880032136498.png\" alt=\"\" />\n<img src=\"http://image.bscvip.com/content_images/1552880039786408.png\" alt=\"\" />","favoriteNum":1,"price":3680,"supplier":"","name":"金牌代理礼包","vipPrice":3680,"wantNum":0,"sortBy":0,"stock":10000,"categoryId":-4,"sellNum":1368,"freeShopping":80,"isFavorite":false}]
     * openPack : 0
     * redList : [{"money":1.28,"nickname":"文景","avatar":"http://image.bscvip.com/avatar/1548904828692536506.jpg","title":"拆红包","userid":"bce16533563c4"}]
     * redPackMoney : 0
     */

    private String totalMoney;//红包累计余额
    private int unPack;//剩余可拆红包次数---剩余能量
    private int openPack;//已拆红包数量
    private String redPackMoney;//红包余额
    private List<GoodsListBean> goodsList;
    private List<RedListBean> redList;
    private String fNum;//封死

    public String getfNum() {
        return fNum;
    }

    public void setfNum(String fNum) {
        this.fNum = fNum;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getUnPack() {
        return unPack;
    }

    public void setUnPack(int unPack) {
        this.unPack = unPack;
    }

    public int getOpenPack() {
        return openPack;
    }

    public void setOpenPack(int openPack) {
        this.openPack = openPack;
    }

    public String getRedPackMoney() {
        return redPackMoney;
    }

    public void setRedPackMoney(String redPackMoney) {
        this.redPackMoney = redPackMoney;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public List<RedListBean> getRedList() {
        return redList;
    }

    public void setRedList(List<RedListBean> redList) {
        this.redList = redList;
    }

    public static class GoodsListBean {
        /**
         * image : http://image.bscvip.com/goods_images/1552879003325334.jpg
         * compareUrl :
         * bkCoin : 0
         * goodsId : 1
         * resList : [{"image":"http://image.bscvip.com/res_images/1552879392162918.jpg","videoUrl":"","type":1}]
         * transport : 0
         * title : 代理礼包
         * cartNum : 0
         * content : <img src="http://image.bscvip.com/content_images/1552879706474987.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879717241019.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879726454028.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879739740553.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879748941671.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879892783867.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879902996214.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879913054527.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879920316957.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879933851960.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879964397256.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552879984077457.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552880003063199.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552880021254432.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552880032136498.png" alt="" />
         <img src="http://image.bscvip.com/content_images/1552880039786408.png" alt="" />
         * favoriteNum : 1
         * price : 3680
         * supplier :
         * name : 金牌代理礼包
         * vipPrice : 3680
         * wantNum : 0
         * sortBy : 0
         * stock : 10000
         * categoryId : -4
         * sellNum : 1368
         * freeShopping : 80
         * isFavorite : false
         */
        private boolean isBuy;//是否购买
        private int peopleNum;//需要能量数量
        private String image;
        private String compareUrl;
        private int bkCoin;
        private int goodsId;
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
        private int stock;
        private int categoryId;
        private int sellNum;
        private String freeShopping;
        private boolean isFavorite;
        private List<ResListBean> resList;

        public boolean isBuy() {
            return isBuy;
        }

        public void setBuy(boolean buy) {
            isBuy = buy;
        }

        public int getPeopleNum() {
            return peopleNum;
        }

        public void setPeopleNum(int peopleNum) {
            this.peopleNum = peopleNum;
        }

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

        public int getBkCoin() {
            return bkCoin;
        }

        public void setBkCoin(int bkCoin) {
            this.bkCoin = bkCoin;
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

        public String getFreeShopping() {
            return freeShopping;
        }

        public void setFreeShopping(String freeShopping) {
            this.freeShopping = freeShopping;
        }

        public boolean isIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
        }

        public List<ResListBean> getResList() {
            return resList;
        }

        public void setResList(List<ResListBean> resList) {
            this.resList = resList;
        }

        public static class ResListBean {
            /**
             * image : http://image.bscvip.com/res_images/1552879392162918.jpg
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

    public static class RedListBean {
        /**
         * money : 1.28
         * nickname : 文景
         * avatar : http://image.bscvip.com/avatar/1548904828692536506.jpg
         * title : 拆红包
         * userid : bce16533563c4
         */

        private double money;
        private String nickname;
        private String avatar;
        private String title;
        private String userid;

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
