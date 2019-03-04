package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class MallHomeBean implements Serializable{
    /**
     * packList : [{"image":"http://image.bscvip.com/app_static/yztj.png","showNum":1,"goodsList":[{"image":"http://image.bscvip.com/goods_images/1544499864119614.jpg","goodsId":5,"transport":8,"title":"时尚百搭","content":"<p><img src=\"http://image.bscvip.com/goods_images/1543996666407703.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":195,"name":"韩版时尚鞋子","vipPrice":185,"wantNum":200,"sortBy":10,"stock":188,"categoryId":2,"isVote":false,"sellNum":200,"isFavorite":false}],"name":"优质吃货","goodsPackId":1,"sortBy":100,"title":"网红产品品聚集地","type":0},{"image":"http://image.bscvip.com/app_static/yztj.png","showNum":1,"goodsList":[{"image":"http://image.bscvip.com/goods_images/1544499864119614.jpg","goodsId":5,"transport":8,"title":"时尚百搭","content":"<p><img src=\"http://image.bscvip.com/goods_images/1543996666407703.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":195,"name":"韩版时尚鞋子","vipPrice":185,"wantNum":200,"sortBy":10,"stock":188,"categoryId":2,"isVote":false,"sellNum":200,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499837992243.jpg","goodsId":6,"transport":0,"title":"德国原装进口 温和不涩眼","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544162517617897.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":119,"name":"施巴 婴儿洁肤浴露","vipPrice":95,"wantNum":0,"sortBy":0,"stock":11,"categoryId":4,"isVote":false,"sellNum":0,"isFavorite":false}],"name":"明星单明","goodsPackId":4,"sortBy":99,"title":"明星单明","type":1},{"image":"http://image.bscvip.com/app_static/yztj.png","showNum":1,"goodsList":[{"image":"http://image.bscvip.com/goods_images/1544499684083444.jpg","goodsId":9,"transport":8,"title":"宝宝洗发专用，呵护婴儿娇嫩头皮","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163747054845.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":95,"name":"施巴儿童洗发水婴儿洗发露宝宝洗发液","vipPrice":85,"wantNum":300,"sortBy":70,"stock":0,"categoryId":4,"isVote":false,"sellNum":318,"isFavorite":false}],"name":"24H热卖","goodsPackId":3,"sortBy":98,"title":"24H热卖","type":1},{"image":"http://image.bscvip.com/app_static/yztj.png","showNum":4,"goodsList":[{"image":"http://image.bscvip.com/goods_images/1544499837992243.jpg","goodsId":6,"transport":0,"title":"德国原装进口 温和不涩眼","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544162517617897.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":119,"name":"施巴 婴儿洁肤浴露","vipPrice":95,"wantNum":0,"sortBy":0,"stock":11,"categoryId":4,"isVote":false,"sellNum":0,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499827384700.jpg","goodsId":7,"transport":0,"title":"宝宝专用免冲洗，温和配方加倍滋润","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163154394031.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":109,"name":"施巴 婴儿泡泡浴露","vipPrice":89,"wantNum":100,"sortBy":50,"stock":19,"categoryId":4,"isVote":false,"sellNum":0,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499798046187.jpg","goodsId":8,"transport":0,"title":"宝宝专用免冲洗，温和配方加倍滋润","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163474811150.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":169,"name":"施巴婴儿泡浴露","vipPrice":155,"wantNum":200,"sortBy":60,"stock":21,"categoryId":4,"isVote":false,"sellNum":215,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499864119614.jpg","goodsId":5,"transport":8,"title":"时尚百搭","content":"<p><img src=\"http://image.bscvip.com/goods_images/1543996666407703.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":195,"name":"韩版时尚鞋子","vipPrice":185,"wantNum":200,"sortBy":10,"stock":188,"categoryId":2,"isVote":false,"sellNum":200,"isFavorite":false}],"name":"新味速递","goodsPackId":2,"sortBy":97,"title":"新味速递","type":2},{"image":"http://image.bscvip.com/app_static/yztj.png","showNum":2,"goodsList":[{"image":"http://image.bscvip.com/goods_images/1544499684083444.jpg","goodsId":9,"transport":8,"title":"宝宝洗发专用，呵护婴儿娇嫩头皮","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163747054845.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":95,"name":"施巴儿童洗发水婴儿洗发露宝宝洗发液","vipPrice":85,"wantNum":300,"sortBy":70,"stock":0,"categoryId":4,"isVote":false,"sellNum":318,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499798046187.jpg","goodsId":8,"transport":0,"title":"宝宝专用免冲洗，温和配方加倍滋润","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163474811150.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":169,"name":"施巴婴儿泡浴露","vipPrice":155,"wantNum":200,"sortBy":60,"stock":21,"categoryId":4,"isVote":false,"sellNum":215,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499837992243.jpg","goodsId":6,"transport":0,"title":"德国原装进口 温和不涩眼","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544162517617897.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":119,"name":"施巴 婴儿洁肤浴露","vipPrice":95,"wantNum":0,"sortBy":0,"stock":11,"categoryId":4,"isVote":false,"sellNum":0,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1544499827384700.jpg","goodsId":7,"transport":0,"title":"宝宝专用免冲洗，温和配方加倍滋润","content":"<p><img src=\"http://image.bscvip.com/goods_images/1544163154394031.jpg\" style=\"width: 100%;\"><br><\/p>","isShelf":true,"price":109,"name":"施巴 婴儿泡泡浴露","vipPrice":89,"wantNum":100,"sortBy":50,"stock":19,"categoryId":4,"isVote":false,"sellNum":0,"isFavorite":false}],"name":"进口严选","goodsPackId":6,"sortBy":1,"title":"进口严选 品质保真","type":2}]
     * advBannerList : [{"sec":5,"appVer":"","goodsId":5,"place":2,"tagUrl":"","type":3,"url":"http://image.bscvip.com/banner/1.png"},{"sec":5,"appVer":"","goodsId":6,"place":2,"tagUrl":"","type":1,"url":"http://image.bscvip.com/banner/2.png"},{"sec":3,"appVer":"1.0.1","place":2,"tagUrl":"","type":1,"url":"http://image.bscvip.com/banner/3.png"}]
     * unreadNum : 10
     */
    private int unreadNum;
    private List<PackListBean> packList;
    private List<AdvBannerListBean> advBannerList;

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }
    public List<PackListBean> getPackList() {
        return packList;
    }

    public void setPackList(List<PackListBean> packList) {
        this.packList = packList;
    }

    public List<AdvBannerListBean> getAdvBannerList() {
        return advBannerList;
    }

    public void setAdvBannerList(List<AdvBannerListBean> advBannerList) {
        this.advBannerList = advBannerList;
    }

    public static class PackListBean implements Serializable {
        /**
         * image : image
         * showNum : 1
         * goodsList : [{"image":"http://image.bscvip.com/goods_images/1540891360264478.png","goodsId":1,"transport":10,"title":"小标题","content":"<p><img src=\"http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png\" title=\"20181031165750001741.png\" alt=\"1.png_one.png\"><\/p>","iShelf":true,"price":18,"name":"裤子1","vipPrice":10.99,"wantNum":5,"sortBy":11,"stock":30,"categoryId":1,"sellNum":5}]
         * name : 袜子
         * goodsPackId : 4
         * sortBy : 99
         * title : 优选袜子
         * type : 0
         */

        private String image;
        private int showNum;
        private String name;
        private int goodsPackId;
        private int sortBy;
        private String title;
        private int type;
        private List<GoodsListBean> goodsList;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getShowNum() {
            return showNum;
        }

        public void setShowNum(int showNum) {
            this.showNum = showNum;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGoodsPackId() {
            return goodsPackId;
        }

        public void setGoodsPackId(int goodsPackId) {
            this.goodsPackId = goodsPackId;
        }

        public int getSortBy() {
            return sortBy;
        }

        public void setSortBy(int sortBy) {
            this.sortBy = sortBy;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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
             * goodsId : 1
             * transport : 10
             * title : 小标题
             * content : <p><img src="http://www.qidianvip.com//Uploads/temporary/20181031165750001741.png" title="20181031165750001741.png" alt="1.png"></p>
             * iShelf : true
             * price : 18
             * name : 裤子1
             * vipPrice : 10.99
             * wantNum : 5
             * sortBy : 11
             * stock : 30
             * categoryId : 1
             * sellNum : 5
             */

            private String image;
            private int goodsId;
            private int transport;
            private String title;
            private String content;
            private boolean iShelf;
            private String price;
            private String name;
            private String vipPrice;
            private int wantNum;
            private int sortBy;
            private int stock;
            private int categoryId;
            private int sellNum;

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
        }
    }

    public static class AdvBannerListBean {
        /**
         * sec : 5
         * goodsId
         * appVer :
         * place : 2
         * tagUrl : http://www.baidu.com
         * type : 1
         * url : http://image.bscvip.com/goods_images/1541408738415764.jpg
         */

        private int sec;
        private int goodsId;
        private String appVer;
        private int place;
        private String tagUrl;
        private int type;
        private String url;

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getSec() {
            return sec;
        }

        public void setSec(int sec) {
            this.sec = sec;
        }

        public String getAppVer() {
            return appVer;
        }

        public void setAppVer(String appVer) {
            this.appVer = appVer;
        }

        public int getPlace() {
            return place;
        }

        public void setPlace(int place) {
            this.place = place;
        }

        public String getTagUrl() {
            return tagUrl;
        }

        public void setTagUrl(String tagUrl) {
            this.tagUrl = tagUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
