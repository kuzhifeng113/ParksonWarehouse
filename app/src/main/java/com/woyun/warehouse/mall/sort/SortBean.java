package com.woyun.warehouse.mall.sort;

import java.io.Serializable;
import java.util.List;

/**
 * 分类
 */
public class SortBean implements Serializable {
    /**
     * categoryList : [{"image":"http://image.bscvip.com/app_static/message/3-8-11.png","name":"宝宝专用","categoryId":22}]
     * name : 热门
     * categoryId : 1
     */

    private String name;
    private int categoryId;
    private List<CategoryListBean> categoryList;

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

    public List<CategoryListBean> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListBean> categoryList) {
        this.categoryList = categoryList;
    }

    public static class CategoryListBean implements Serializable{
        /**
         * image : http://image.bscvip.com/app_static/message/3-8-11.png
         * name : 宝宝专用
         * categoryId : 22
         */

        private String image;
        private String name;
        private int categoryId;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

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
    }
}
