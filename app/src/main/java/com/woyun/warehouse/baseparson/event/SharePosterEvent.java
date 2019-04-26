package com.woyun.warehouse.baseparson.event;

public class SharePosterEvent {

    private boolean isShare;

    public SharePosterEvent(boolean isShare) {
        this.isShare = isShare;
    }

    public boolean isShare() {
        return isShare;
    }

    public void setShare(boolean share) {
        isShare = share;
    }
}
