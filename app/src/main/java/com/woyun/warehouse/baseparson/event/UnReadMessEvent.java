package com.woyun.warehouse.baseparson.event;

/**
 * event bus
 */
public class UnReadMessEvent {
    private int num;

    public UnReadMessEvent(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
