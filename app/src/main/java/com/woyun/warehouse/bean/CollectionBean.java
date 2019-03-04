package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 收藏列表
 */
public class CollectionBean {
    /**
     * pageCount : 1
     * total : 2
     * size : 2
     * pageSize : 10
     * currentPage : 0
     * content : [{"image":"http://image.bscvip.com/goods_images/1540891360264478.png","favoriteId":11,"skuList":[],"goodsId":2,"transport":10,"content":"<p><img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png_one.png\">\r\n<\/p>\r\n<p><br><\/p>","iShelf":false,"price":15,"name":"裤子2","vipPrice":10,"wantNum":3,"sortBy":10,"categoryId":2,"isVote":false,"isFavorite":true},{"image":"http://image.bscvip.com/goods_images/1540891360264478.png","favoriteId":10,"skuList":[],"goodsId":1,"transport":10,"content":"<p><img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png_one.png\"><\/p>","iShelf":false,"price":18,"name":"裤子1","vipPrice":10.99,"wantNum":5,"sortBy":11,"categoryId":1,"isVote":false,"isFavorite":true}]
     */
    private int pageCount;
    private int total;
    private int size;
    private int pageSize;
    private int currentPage;
    private List<ContentBean> content;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * image : http://image.bscvip.com/goods_images/1540891360264478.png
         * favoriteId : 11
         * favoriteNum   收藏人数
         * skuList : []
         * goodsId : 2
         * transport : 10.0
         * content : <p><img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png_one.png">
         </p>
         <p><br></p>
         * iShelf : false
         * price : 15.0
         * name : 裤子2
         * vipPrice : 10.0
         * wantNum : 3
         * sortBy : 10
         * categoryId : 2
         * isVote : false
         * isFavorite : true
         * bkCoin
         */
        private double bkCoin;
        private String image;
        private int favoriteId;
        private int favoriteNum;
        private int goodsId;
        private double transport;
        private String content;
        private boolean iShelf;
        private double price;
        private String name;
        private double vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private boolean isVote;
        private boolean isFavorite;
        private boolean isCheck;

        public double getBkCoin() {
            return bkCoin;
        }

        public void setBkCoin(double bkCoin) {
            this.bkCoin = bkCoin;
        }

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getFavoriteId() {
            return favoriteId;
        }

        public void setFavoriteId(int favoriteId) {
            this.favoriteId = favoriteId;
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
