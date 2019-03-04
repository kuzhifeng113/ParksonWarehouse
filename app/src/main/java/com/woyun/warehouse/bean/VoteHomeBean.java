package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class VoteHomeBean implements Serializable {

    /**
     * image : image
     * goodsList : [{"image":"http://image.bscvip.com/goods_images/1540891360264478.png","goodsId":2,"transport":10,"title":"小标题","content":"<p>\n gg<img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png\"/>\n<\/p>\n<p>\n    2\n    <video class=\"edui-upload-video  vjs-default-skin  video-js\" controls=\"\" preload=\"none\" width=\"420\" height=\"280\" src=\"http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4\" data-setup=\"{}\"\n  poster=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\">\n        <source src=\"http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4\" type=\"video/mp4\"/>\n    <\/video>\n<\/p>","iShelf":true,"totalNum":100,"price":15,"name":"裤子2","vipPrice":10,"wantNum":20,"sortBy":10,"categoryId":2,"isVote":false}]
     * sortBy : 2
     * startTime : 1539100800000
     * endTime : 1539100800000
     * isHistory : false
     * voteId : 2
     * title : 第二期投票
     */

    private String image;
    private int sortBy;
    private long startTime;
    private long endTime;
    private boolean isHistory;
    private int voteId;
    private String title;
    private List<GoodsListBean> goodsList;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public boolean isIsHistory() {
        return isHistory;
    }

    public void setIsHistory(boolean isHistory) {
        this.isHistory = isHistory;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class GoodsListBean implements Serializable {
        /**
         * image : http://image.bscvip.com/goods_images/1540891360264478.png
         * goodsId : 2
         * transport : 10
         * title : 小标题
         * content : <p>
         1<img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png_one.png"/>
         </p>
         <p>
         2
         <video class="edui-upload-video  vjs-default-skin  video-js" controls="" preload="none" width="420" height="280" src="http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4" data-setup="{}"
         poster="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png">
         <source src="http://www.qidianvip.com//Uploads/temporary/20181031170359034343.mp4" type="video/mp4"/>
         </video>
         </p>
         * iShelf : true
         * totalNum : 100
         * price : 15
         * name : 裤子2
         * vipPrice : 10
         * wantNum : 20
         * sortBy : 10
         * categoryId : 2
         * isVote : false
         */

        private String image;
        private int goodsId;
        private int transport;
        private String title;
        private String content;
        private boolean isShelf;
        private int totalNum;//本期上货商品总数
        private String price;
        private String name;
        private String vipPrice;
        private int wantNum;//多少人想要的
        private int sortBy;
        private int categoryId;
        private boolean isVote;

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
            return isShelf;
        }

        public void setIShelf(boolean isShelf) {
            this.isShelf = isShelf;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
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
    }
}
