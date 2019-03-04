package com.woyun.warehouse.bean;

/**
 * 微信access_token
 */

public class WxAccessResult {


    /**
     * access_token : 15_DpJanAnJ7NRW9cjSAIDPdO6OQ2sjL_9u6ibGbnHtlq5DY2n3om-uXj2zFMIqXcIlYPDhJZ1DVBAk2k40h6iAIU1ZMFfjJgqGgzM2Aos_8c8
     * expires_in : 7200
     * refresh_token : 15_NZM_muOyYDA1CALOjsOWKQSjBFISSpDcgHqfALll2gTjznfps10GJOOUbPK9587Yjtt75h_zbAryZLC5BHFUyGJZxvAQ7lFUVeuEZjFRydk
     * openid : oFY4z0pZKvQWK4vnuEryjd-F3Q8I
     * scope : snsapi_userinfo
     * unionid : oqLF-xJLOJsxNnsuk0CKIrO_X5RE
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
