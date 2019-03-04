package com.woyun.warehouse.baseparson.event;

/**
 * event bus
 */
public class WxUserInfoEvent {
    private String name;//微信昵称
    private String openId;//微信open id

    public WxUserInfoEvent(String name, String openId) {
        this.name = name;
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
