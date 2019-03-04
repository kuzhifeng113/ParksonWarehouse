package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class MallHomeTwoBean implements Serializable{
    /**
     * categoryList : [{"goodsPackList":[{"name":"网红产品品聚集地","goodsPackId":1,"title":"优质推荐"}],"name":"热门","categoryId":1},{"goodsPackList":[{"name":"充满幸福感的明星商品","goodsPackId":3,"title":"明星种草"}],"name":"个护美妆","categoryId":3},{"goodsPackList":[{"name":"每一件都是公认的热卖好货","goodsPackId":4,"title":"24H热卖"}],"name":"3C数码","categoryId":4},{"goodsPackList":[{"name":"家里缺的、想不到的，都在这里","goodsPackId":5,"title":"当季必备"}],"name":"服装配饰","categoryId":5},{"goodsPackList":[{"name":"闭着眼睛买，老会员最爱买的口碑商品","goodsPackId":2,"title":"名优严选"}],"name":"母婴用品","categoryId":2},{"goodsPackList":[{"name":"新年限量系列传递心意","goodsPackId":8,"title":"年货年味"}],"name":"休闲食品","categoryId":7}]
     * advBannerList : [{"sec":3,"appVer":"1.0.1","goodsId":0,"place":2,"tagUrl":"https://comm.bscvip.com/about.html","type":1,"url":"http://image.bscvip.com/banner/3.png"},{"sec":5,"appVer":"","goodsId":12,"place":2,"tagUrl":"","type":5,"url":"http://image.bscvip.com/banner/2.png"},{"sec":5,"appVer":"","goodsId":11,"place":2,"tagUrl":"","type":6,"url":"http://image.bscvip.com/banner/1.png"}]
     * unreadNum : 0   //未读消息数
     */
    private int unreadNum;
    private boolean defaultAddress;
    private List<CategoryListBean> categoryList;
    private List<AdvBannerListBean> advBannerList;

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public List<AdvBannerListBean> getAdvBannerList() {
        return advBannerList;
    }

    public void setAdvBannerList(List<AdvBannerListBean> advBannerList) {
        this.advBannerList = advBannerList;
    }

    public static class CategoryListBean {
        /**
         * goodsPackList : [{"name":"网红产品品聚集地","goodsPackId":1,"title":"优质推荐"}]
         * name : 热门
         * categoryId : 1
         */

        private String name;
        private int categoryId;
        private List<GoodsPackListBean> goodsPackList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public List<GoodsPackListBean> getGoodsPackList() {
            return goodsPackList;
        }

        public void setGoodsPackList(List<GoodsPackListBean> goodsPackList) {
            this.goodsPackList = goodsPackList;
        }

        public static class GoodsPackListBean {
            /**
             * name : 网红产品品聚集地
             * goodsPackId : 1
             * title : 优质推荐
             */

            private String name;
            private int goodsPackId;
            private String title;

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }

    public static class AdvBannerListBean {
        /**
         * sec : 3
         * appVer : 1.0.1
         * goodsId : 0
         * place : 2
         * tagUrl : https://comm.bscvip.com/about.html
         * type : 1
         * url : http://image.bscvip.com/banner/3.png
         */

        private int sec;
        private String appVer;
        private int goodsId;
        private int place;
        private String tagUrl;
        private int type;
        private String url;

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

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
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
