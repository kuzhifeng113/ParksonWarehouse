package com.woyun.warehouse.bean;

/**
 * 投票
 */
public class VoteBean {
    private String name;
    private String price;
    private String y_price;
    private String howBuy;


    public VoteBean(String name, String price, String y_price, String howBuy) {
        this.name = name;
        this.price = price;
        this.y_price = y_price;
        this.howBuy = howBuy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getY_price() {
        return y_price;
    }

    public void setY_price(String y_price) {
        this.y_price = y_price;
    }

    public String getHowBuy() {
        return howBuy;
    }

    public void setHowBuy(String howBuy) {
        this.howBuy = howBuy;
    }
}
