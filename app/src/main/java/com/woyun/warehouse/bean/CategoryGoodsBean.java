package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 全部分类商品
 */
public class CategoryGoodsBean {

    /**
     * pageCount : 1
     * total : 1
     * size : 1
     * pageSize : 10
     * currentPage : 1
     * content : [{"image":"http://image.bscvip.com/\r\ngoods_images/1541408769775055.jpg","skuList":[],"goodsId":1,"transport":10,"content":"<p><img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png_one.png\"><\/p>","iShelf":false,"favoriteNum":3,"videoUrl":"video_url","price":18,"name":"裤子1","videoImage":"video_image","vipPrice":10.99,"wantNum":5,"sortBy":11,"categoryId":1,"isVote":false,"isFavorite":false}]
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
         * image : http://image.bscvip.com/
         goods_images/1541408769775055.jpg
         * skuList : []
         * goodsId : 1
         * transport : 10
         * content : <p><img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1ogin_one.png"></p>
         * iShelf : false
         * favoriteNum : 3
         * videoUrl : video_url
         * price : 18
         * name : 裤子1
         * videoImage : video_image
         * vipPrice : 10.99
         * wantNum : 5
         * sortBy : 11
         * categoryId : 1
         * isVote : false
         * isFavorite : false
         * title
         */

        private String image;
        private int goodsId;
        private int transport;
        private String content;
        private boolean iShelf;
        private int favoriteNum;
        private String videoUrl;
        private String price;
        private String name;
        private String videoImage;
        private String vipPrice;
        private int wantNum;
        private int sortBy;
        private int categoryId;
        private boolean isVote;
        private boolean isFavorite;
        private List<?> skuList;
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public int getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(int favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
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

        public String getVideoImage() {
            return videoImage;
        }

        public void setVideoImage(String videoImage) {
            this.videoImage = videoImage;
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

        public List<?> getSkuList() {
            return skuList;
        }

        public void setSkuList(List<?> skuList) {
            this.skuList = skuList;
        }
    }
}
