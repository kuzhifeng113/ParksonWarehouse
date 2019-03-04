package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 微信个人信息
 */
public class WxUserInfoResult {

    /**
     * openid : oO3S80_VrrVZS0fCiwyTR2Hy3Sr8
     * nickname : 我也不想取这么长的名字呀
     * sex : 1
     * language : zh_CN
     * city : Chenzhou
     * province : Hunan
     * country : China
     * headimgurl : http://wx.qlogo.cn/mmopen/vi_32/zdQ0VyOfFiaTM1UzQ8ic5YibKAmKu71zoSo01zzYxPtvicqWg4iawV6ljuwVueFFlCZ0ibalORoDO7aQjgyZyWsKUAbQ/0
     * privilege : []
     * unionid : oLe871rZG-Jkwe-8g7lhfgd5PoGo
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
    private List<?> privilege;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<?> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(List<?> privilege) {
        this.privilege = privilege;
    }
}
