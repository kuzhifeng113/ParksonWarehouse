package com.woyun.warehouse.baseparson.event;

/**
 * fans
 */
public class FansEvent {
    private int isVip;
    private int Size;

    public FansEvent(int isVip, int size) {
        this.isVip = isVip;
        Size = size;
    }

    public int getIsVip() {
        return isVip;
    }

    public void setIsVip(int isVip) {
        this.isVip = isVip;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }
}
