package com.woyun.warehouse.bean;

import java.util.List;

public class ZuanQianBean {

    /**
     * agentGift : [{"image":"http://image.bscvip.com/goods_images/1550732422135012.jpg","compareUrl":"","goodsId":4,"transport":0,"title":"代理礼包","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1551083474467051.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083474990941.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083475131472.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083475276072.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083475578505.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083475729781.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083475867463.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083476004355.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083476130611.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1551083476293008.png\" alt=\"\" />","isShelf":true,"favoriteNum":0,"price":6552,"supplier":"","name":"植蔻小配方三件套","vipPrice":6552,"wantNum":0,"sortBy":0,"categoryId":-2,"sellNum":0,"freeShopping":80}]
     * isAgent : false
     * vipGift : [{"image":"http://image.bscvip.com/goods_images/1548758049146799.jpg","compareUrl":"","goodsId":2,"transport":0,"title":"即刻加入会员行列 尊享臻贵会员专享礼遇","cartNum":0,"content":"<img src=\"http://image.bscvip.com/goods_images/1548757869554451.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757869685105.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757869754579.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757869888961.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870036433.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870157302.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870283196.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870420025.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870481371.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1548757870585557.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571061943.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571254765.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571470885.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571647000.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571778068.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604571941550.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572128240.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572253193.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572438223.png\" alt=\"\" /><img src=\"http://image.bscvip.com/goods_images/1547604572683409.png\" alt=\"\" />","isShelf":true,"favoriteNum":1,"price":427,"supplier":"","name":"植蔻小配方三件套","vipPrice":168,"wantNum":0,"sortBy":0,"categoryId":-1,"sellNum":0,"freeShopping":80}]
     * profitList : [{"image":"http://image.bscvip.com/app_static/vip/vipimg.png","unicon":"http://image.bscvip.com/app_static/vip/vipu.png","icon":"http://image.bscvip.com/app_static/vip/vip.png","name":"VIP会员权益"},{"image":"http://image.bscvip.com/app_static/vip/agentimg.png","unicon":"http://image.bscvip.com/app_static/vip/vipu.png","icon":"http://image.bscvip.com/app_static/vip/agent.png","name":"金牌代理权益"}]
     * isVip : false
     */

    private boolean isAgent;
    private boolean isVip;
    private List<AgentGiftBean> agentGift;
    private List<VipGiftBean> vipGift;
    private List<ProfitListBean> profitList;

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public List<AgentGiftBean> getAgentGift() {
        return agentGift;
    }

    public void setAgentGift(List<AgentGiftBean> agentGift) {
        this.agentGift = agentGift;
    }

    public List<VipGiftBean> getVipGift() {
        return vipGift;
    }

    public void setVipGift(List<VipGiftBean> vipGift) {
        this.vipGift = vipGift;
    }

    public List<ProfitListBean> getProfitList() {
        return profitList;
    }

    public void setProfitList(List<ProfitListBean> profitList) {
        this.profitList = profitList;
    }

    public static class AgentGiftBean {
        /**
         * image : http://image.bscvip.com/goods_images/1550732422135012.jpg
         * compareUrl :
         * goodsId : 4
         * transport : 0
         * title : 代理礼包
         * cartNum : 0
         * content : <img src="http://image.bscvip.com/goods_images/1551083474467051.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083474990941.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083475131472.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083475276072.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083475578505.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083475729781.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083475867463.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083476004355.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083476130611.png" alt="" /><img src="http://image.bscvip.com/goods_images/1551083476293008.png" alt="" />
         * isShelf : true
         * favoriteNum : 0
         * price : 6552
         * supplier :
         * name : 植蔻小配方三件套
         * vipPrice : 6552
         * wantNum : 0
         * sortBy : 0
         * categoryId : -2
         * sellNum : 0
         * freeShopping : 80
         */

        private String image;
        private String compareUrl;
        private int goodsId;
        private int transport;
        private String title;
        private int cartNum;
        private String content;
        private boolean isShelf;
        private int favoriteNum;
        private String price;
        private String supplier;
        private String name;
        private String vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private int sellNum;
        private int freeShopping;

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
    }

    public static class VipGiftBean {
        /**
         * image : http://image.bscvip.com/goods_images/1548758049146799.jpg
         * compareUrl :
         * goodsId : 2
         * transport : 0
         * title : 即刻加入会员行列 尊享臻贵会员专享礼遇
         * cartNum : 0
         * content : <img src="http://image.bscvip.com/goods_images/1548757869554451.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757869685105.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757869754579.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757869888961.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870036433.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870157302.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870283196.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870420025.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870481371.png" alt="" /><img src="http://image.bscvip.com/goods_images/1548757870585557.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571061943.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571254765.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571470885.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571647000.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571778068.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604571941550.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572128240.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572253193.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572438223.png" alt="" /><img src="http://image.bscvip.com/goods_images/1547604572683409.png" alt="" />
         * isShelf : true
         * favoriteNum : 1
         * price : 427
         * supplier :
         * name : 植蔻小配方三件套
         * vipPrice : 168
         * wantNum : 0
         * sortBy : 0
         * categoryId : -1
         * sellNum : 0
         * freeShopping : 80
         */

        private String image;
        private String compareUrl;
        private int goodsId;
        private int transport;
        private String title;
        private int cartNum;
        private String content;
        private boolean isShelf;
        private int favoriteNum;
        private String price;
        private String supplier;
        private String name;
        private String vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private int sellNum;
        private int freeShopping;

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
    }

    public static class ProfitListBean {
        /**
         * image : http://image.bscvip.com/app_static/vip/vipimg.png
         * unicon : http://image.bscvip.com/app_static/vip/vipu.png
         * icon : http://image.bscvip.com/app_static/vip/vip.png
         * name : VIP会员权益
         */

        private String image;
        private String unicon;
        private String icon;
        private String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnicon() {
            return unicon;
        }

        public void setUnicon(String unicon) {
            this.unicon = unicon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
