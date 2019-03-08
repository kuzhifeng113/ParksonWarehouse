package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 各地好货 实体类
 */
public class GoodCategoryBean implements Serializable{

    /**
     * goodsPackList : []
     * page : {"pageCount":1,"total":3,"size":3,"pageSize":14,"currentPage":1,"content":[{"image":"http://image.bscvip.com/goods_images/1548741249810897.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.5.31dadfefKTYSnu&pvid=008c4298-6049-499f-bb4a-d4a9ffdf62ba&pos=3&acm=03054.1003.1.2768562&id=567126786389&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:3,%22x_pvid%22:%22008c4298-6049-499f-bb4a-d4a9ffdf62ba%22,%22x_object_id%22:567126786389}&skuId=3781878589267","bkCoin":5,"goodsId":11,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741249810897.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"宝贝洗完澡挥手都是香香哒","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546064023031363.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064068770409.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064079598860.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064089695015.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064103383633.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":4,"price":72,"supplier":"德国原装进口","name":"施巴 婴儿洁肤浴露","vipPrice":69.9,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":216,"freeShopping":80,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1548741264954479.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.3.610c399fJhBn9u&pvid=85eb56ed-7f4d-4f76-8bf4-e1c7baf633a5&pos=2&acm=03054.1003.1.2768562&id=567208987954&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:2,%22x_pvid%22:%2285eb56ed-7f4d-4f76-8bf4-e1c7baf633a5%22,%22x_object_id%22:567208987954}&skuId=3781878297681","bkCoin":5,"goodsId":12,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741264954479.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"专为宝宝皮脂PH值定","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546065001360089.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065014471454.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065023460337.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065031388697.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065040049901.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":2,"price":72,"supplier":"德国原装进口","name":"施巴 婴儿泡泡浴露 200ml","vipPrice":69.8,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":223,"freeShopping":80,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1548741226879137.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-18213753237.109.118bd951MIqGFY&id=567215199191&rn=1b22f773448b3f84e655cd893edb9d55&abbucket=1&skuId=3781880529550","bkCoin":7.8,"goodsId":13,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741226879137.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"听说，宝宝更喜欢泡泡浴哦~","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546065270065090.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065278222707.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065288056975.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065295986783.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":0,"price":139,"supplier":"弱酸性的 pH5.5","name":"施巴 婴儿泡泡浴露 500ml","vipPrice":118,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":145,"freeShopping":80,"isFavorite":false}]}
     */

    private PageBean page;
    private List<?> goodsPackList;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<?> getGoodsPackList() {
        return goodsPackList;
    }

    public void setGoodsPackList(List<?> goodsPackList) {
        this.goodsPackList = goodsPackList;
    }

    public static class PageBean implements Serializable {
        /**
         * pageCount : 1
         * total : 3
         * size : 3
         * pageSize : 14
         * currentPage : 1
         * content : [{"image":"http://image.bscvip.com/goods_images/1548741249810897.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.5.31dadfefKTYSnu&pvid=008c4298-6049-499f-bb4a-d4a9ffdf62ba&pos=3&acm=03054.1003.1.2768562&id=567126786389&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:3,%22x_pvid%22:%22008c4298-6049-499f-bb4a-d4a9ffdf62ba%22,%22x_object_id%22:567126786389}&skuId=3781878589267","bkCoin":5,"goodsId":11,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741249810897.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"宝贝洗完澡挥手都是香香哒","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546064023031363.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064068770409.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064079598860.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064089695015.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546064103383633.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":4,"price":72,"supplier":"德国原装进口","name":"施巴 婴儿洁肤浴露","vipPrice":69.9,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":216,"freeShopping":80,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1548741264954479.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.3.610c399fJhBn9u&pvid=85eb56ed-7f4d-4f76-8bf4-e1c7baf633a5&pos=2&acm=03054.1003.1.2768562&id=567208987954&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:2,%22x_pvid%22:%2285eb56ed-7f4d-4f76-8bf4-e1c7baf633a5%22,%22x_object_id%22:567208987954}&skuId=3781878297681","bkCoin":5,"goodsId":12,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741264954479.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"专为宝宝皮脂PH值定","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546065001360089.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065014471454.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065023460337.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065031388697.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065040049901.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":2,"price":72,"supplier":"德国原装进口","name":"施巴 婴儿泡泡浴露 200ml","vipPrice":69.8,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":223,"freeShopping":80,"isFavorite":false},{"image":"http://image.bscvip.com/goods_images/1548741226879137.png","compareUrl":"https://detail.tmall.com/item.htm?spm=a1z10.3-b-s.w4011-18213753237.109.118bd951MIqGFY&id=567215199191&rn=1b22f773448b3f84e655cd893edb9d55&abbucket=1&skuId=3781880529550","bkCoin":7.8,"goodsId":13,"resList":[{"image":"http://image.bscvip.com/goods_images/1548741226879137.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}],"transport":8,"title":"听说，宝宝更喜欢泡泡浴哦~","cartNum":0,"content":"<p>\r\n\t<img src=\"http://image.bscvip.com/goods_images/1546065270065090.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065278222707.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065288056975.png\" style=\"width:100%;\" /> <br />\r\n<img src=\"http://image.bscvip.com/goods_images/1546065295986783.png\" style=\"width:100%;\" /> \r\n<\/p>","favoriteNum":0,"price":139,"supplier":"弱酸性的 pH5.5","name":"施巴 婴儿泡泡浴露 500ml","vipPrice":118,"wantNum":0,"sortBy":0,"categoryId":12,"sellNum":145,"freeShopping":80,"isFavorite":false}]
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

        public static class ContentBean implements Serializable {
            /**
             * image : http://image.bscvip.com/goods_images/1548741249810897.png
             * compareUrl : https://detail.tmall.com/item.htm?spm=a220o.1000855.1998025129.5.31dadfefKTYSnu&pvid=008c4298-6049-499f-bb4a-d4a9ffdf62ba&pos=3&acm=03054.1003.1.2768562&id=567126786389&scm=1007.16862.95220.23864_0&utparam={%22x_hestia_source%22:%2223864%22,%22x_object_type%22:%22item%22,%22x_mt%22:0,%22x_src%22:%2223864%22,%22x_pos%22:3,%22x_pvid%22:%22008c4298-6049-499f-bb4a-d4a9ffdf62ba%22,%22x_object_id%22:567126786389}&skuId=3781878589267
             * bkCoin : 5
             * goodsId : 11
             * resList : [{"image":"http://image.bscvip.com/goods_images/1548741249810897.png","videoUrl":"","type":1},{"image":"http://image.bscvip.com/1.png","videoUrl":"http://image.bscvip.com/1.mp4","type":2}]
             * transport : 8
             * title : 宝贝洗完澡挥手都是香香哒
             * cartNum : 0
             * content : <p>
             <img src="http://image.bscvip.com/goods_images/1546064023031363.png" style="width:100%;" /> <br />
             <img src="http://image.bscvip.com/goods_images/1546064068770409.png" style="width:100%;" /> <br />
             <img src="http://image.bscvip.com/goods_images/1546064079598860.png" style="width:100%;" /> <br />
             <img src="http://image.bscvip.com/goods_images/1546064089695015.png" style="width:100%;" /> <br />
             <img src="http://image.bscvip.com/goods_images/1546064103383633.png" style="width:100%;" />
             </p>
             * favoriteNum : 4
             * price : 72
             * supplier : 德国原装进口
             * name : 施巴 婴儿洁肤浴露
             * vipPrice : 69.9
             * wantNum : 0
             * sortBy : 0
             * categoryId : 12
             * sellNum : 216
             * freeShopping : 80
             * isFavorite : false
             * stock
             */

            private String image;
            private String compareUrl;
            private String bkCoin;
            private int goodsId;
            private int transport;
            private String title;
            private int cartNum;
            private String content;
            private int favoriteNum;
            private int price;
            private String supplier;
            private String name;
            private double vipPrice;
            private int wantNum;
            private int sortBy;
            private int categoryId;
            private int sellNum;
            private int freeShopping;
            private boolean isFavorite;
            private int stock;

            public int getStock() {
                return stock;
            }

            public void setStock(int stock) {
                this.stock = stock;
            }

            private List<ResListBean> resList;

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

            public String getBkCoin() {
                return bkCoin;
            }

            public void setBkCoin(String bkCoin) {
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

            public static class ResListBean implements Serializable {
                /**
                 * image : http://image.bscvip.com/goods_images/1548741249810897.png
                 * videoUrl :
                 * type : 1
                 */

                private String image;
                private String videoUrl;
                private int type;//1 图片 2视频

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
    }
}
