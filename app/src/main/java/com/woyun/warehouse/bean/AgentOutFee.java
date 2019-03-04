package com.woyun.warehouse.bean;

public class AgentOutFee {

    /**
     * fee : 1%
     * actrefund : 14671.8
     * vipNum : 19
     * freeMoney : 148.2
     * refund : 14820.0
     */

    private String fee;
    private double actrefund;
    private int vipNum;
    private double feeMoney;
    private double refund;

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public double getActrefund() {
        return actrefund;
    }

    public void setActrefund(double actrefund) {
        this.actrefund = actrefund;
    }

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public double getFeeMoney() {
        return feeMoney;
    }

    public void setFeeMoney(double feeMoney) {
        this.feeMoney = feeMoney;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }
}
