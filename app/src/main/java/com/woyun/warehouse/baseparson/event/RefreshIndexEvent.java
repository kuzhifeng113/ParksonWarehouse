package com.woyun.warehouse.baseparson.event;

/**
 * event bus 是否刷新首页
 */
public class RefreshIndexEvent {
    private boolean flag;

    public RefreshIndexEvent(boolean flag) {
        this.flag = flag;

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


}
