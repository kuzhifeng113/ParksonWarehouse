package com.woyun.warehouse.bean;

/**
 * 全部分类
 */
public class CategoryTitleBean {
    /**
     * name : 裤子
     * sortBy : 0
     * isLeaf : 0
     * categoryId : 1
     * parentId : 0
     */
    private String name;
    private int sortBy;
    private int isLeaf;
    private int categoryId;//分类id
    private int parentId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortBy() {
        return sortBy;
    }

    public void setSortBy(int sortBy) {
        this.sortBy = sortBy;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
