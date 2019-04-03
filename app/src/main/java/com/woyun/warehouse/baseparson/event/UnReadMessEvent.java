package com.woyun.warehouse.baseparson.event;

/**
 * event bus
 */
public class UnReadMessEvent {
    private int num;
    private boolean isZhanKai;//首页appBar是否展开

    public UnReadMessEvent(int num) {
        this.num = num;
    }

    public UnReadMessEvent(boolean isZhanKai) {
        this.isZhanKai = isZhanKai;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isZhanKai() {
        return isZhanKai;
    }

    public void setZhanKai(boolean zhanKai) {
        isZhanKai = zhanKai;
    }
}
