package com.woyun.warehouse.baseparson.event;

/**
 * event bus
 */
public class OrderIndexEvent {
    private int index;

    public OrderIndexEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
