package com.woyun.warehouse.bean;

import com.google.gson.annotations.SerializedName;

public class WxPayBean {

    /**
     * package : Sign=WXPay
     * createTime : 1541831063276
     * appid : wx76f362b1df43cb52
     * sign : BB268A6AA0D7723C68B12307F86B778FEE0D8B19484A8634E02B221735EAE63E
     * partnerid : 1495452012
     * prepayid : wx101424230525000cf909bed82625132351
     * noncestr : v06x9HUya08T06N8
     * timestamp : 1541831063
     */

    @SerializedName("package")
    private String packageX;
    private String tradeNo;//订单号
    private String createTime;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
