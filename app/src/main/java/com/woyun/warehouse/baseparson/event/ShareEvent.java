package com.woyun.warehouse.baseparson.event;

public class ShareEvent {

    private boolean isShare;

    public ShareEvent(boolean isShare) {
        this.isShare = isShare;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }
}
