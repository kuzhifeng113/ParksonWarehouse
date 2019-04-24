package com.woyun.warehouse.baseparson.event;

/**
 * event bus 是否刷新抢购
 */
public class RefreshGrabEvent {
    private boolean flag;

    public RefreshGrabEvent(boolean flag) {
        this.flag = flag;

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
