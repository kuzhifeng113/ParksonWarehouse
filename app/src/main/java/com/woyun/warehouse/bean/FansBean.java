package com.woyun.warehouse.bean;

public class FansBean {

    /**
     * isAgent : false
     * mobile : 17650313201
     * nickname :
     * avatar :
     * isVip : false
     * registerDate : 1556329976000
     */

    private boolean isAgent;
    private String mobile;
    private String nickname;
    private String avatar;
    private boolean isVip;
    private long registerDate;

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(long registerDate) {
        this.registerDate = registerDate;
    }
}
