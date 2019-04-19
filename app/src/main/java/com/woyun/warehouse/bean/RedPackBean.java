package com.woyun.warehouse.bean;

/**
 * 拆红包返回
 */
public class RedPackBean {

    /**
     * money : 1.17
     * totalMoney : 45.19
     * unPack : 9959
     * openPack : 30
     * redPackMoney : 45.19
     */

    private String money;
    private String totalMoney;//已拆金额
    private int unPack;
    private int openPack;
    private String redPackMoney;//红包余额

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getUnPack() {
        return unPack;
    }

    public void setUnPack(int unPack) {
        this.unPack = unPack;
    }

    public int getOpenPack() {
        return openPack;
    }

    public void setOpenPack(int openPack) {
        this.openPack = openPack;
    }

    public String getRedPackMoney() {
        return redPackMoney;
    }

    public void setRedPackMoney(String redPackMoney) {
        this.redPackMoney = redPackMoney;
    }
}
