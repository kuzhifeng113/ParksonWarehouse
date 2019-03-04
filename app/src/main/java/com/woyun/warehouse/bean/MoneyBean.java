package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 所有图标
 */
public class MoneyBean {
    /**
     * image : http://image.bscvip.com/app_static/vip/1img.png
     * unicon : http://image.bscvip.com/app_static/vip/1u.png
     * icon : http://image.bscvip.com/app_static/vip/1.png
     * name : 超值礼包
     */
    private String image;//大图
    private String unicon;//没选中的
    private String icon;//选中的
    private String name;//名字
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

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
