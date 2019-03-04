package com.woyun.warehouse.bean;

public class UserInfoBean {
    /**
     * isReal : false
     * qq :
     * wx :
     * deviceAlias : a2818fcc1f23411c9e2d69d2e823fc31
     * sex : 1
     * mobile : 18900791426
     * weight : 65
     * avatar : http://image.bscvip.com/avatar/1540966906070779587.jpg
     * title :
     * birthDate :
     * deviceId : a2818fcc1f23411c9e2d69d2e823fc31
     * userid : bc579548d72786b
     * isVip : true
     * token : d3fccd386885c7292627fd399ae09cb2
     * isAgent : false
     * zfb
     * nickname : 兔兔哈
     * takeGift false
     * bcCoin : 0.0
     * bcMoney : 0.0
     * pwd :
     * vip : {"endDate":1576080000000,"lastPayDate":1576080000000,"name":"","id":1,"isEnable":true}
     * age : 18
     * zfbname :
     * height : 170
     * status : 0
     * inviteCode  邀请码
     * wxname  绑定微信的昵称
     * defaultAddress  是否设置默认地址
     */

    private boolean isReal;
    private String qq;
    private String wx;
    private String deviceAlias;
    private int sex;
    private String mobile;
    private int weight;
    private String avatar;
    private String wb;
    private String title;
    private String birthDate;
    private String deviceId;
    private String userid;
    private boolean isVip;
    private String token;
    private boolean isAgent;
    private boolean takeGift;
    private String nickname;
    private String zfb;
    private double bcCoin;
    private double bcMoney;
    private String pwd;
    private VipBean vip;
    private String zfbname;
    private int age;
    private int height;
    private int status;
    private String inviteCode;
    private String wxname;
    private boolean defaultAddress;

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getWxname() {
        return wxname;
    }

    public void setWxname(String wxname) {
        this.wxname = wxname;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getZfbname() {
        return zfbname;
    }

    public void setZfbname(String zfbname) {
        this.zfbname = zfbname;
    }

    public boolean isIsReal() {
        return isReal;
    }

    public void setIsReal(boolean isReal) {
        this.isReal = isReal;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getDeviceAlias() {
        return deviceAlias;
    }

    public void setDeviceAlias(String deviceAlias) {
        this.deviceAlias = deviceAlias;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWb() {
        return wb;
    }

    public void setWb(String wb) {
        this.wb = wb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTakeGift() {
        return takeGift;
    }

    public void setIsTakeGift(boolean takeGift) {
        this.takeGift = takeGift;
    }

    public String getZfb() {
        return zfb;
    }

    public void setZfb(String zfb) {
        this.zfb = zfb;
    }

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public double getBcCoin() {
        return bcCoin;
    }

    public void setBcCoin(double bcCoin) {
        this.bcCoin = bcCoin;
    }

    public double getBcMoney() {
        return bcMoney;
    }

    public void setBcMoney(double bcMoney) {
        this.bcMoney = bcMoney;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public VipBean getVip() {
        return vip;
    }

    public void setVip(VipBean vip) {
        this.vip = vip;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class VipBean {
        /**
         * endDate : 1576080000000
         * lastPayDate : 1576080000000
         * name :
         * id : 1
         * isEnable : true
         */

        private long endDate;
        private long lastPayDate;
        private String name;
        private int id;
        private boolean isEnable;

        public long getEndDate() {
            return endDate;
        }

        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public long getLastPayDate() {
            return lastPayDate;
        }

        public void setLastPayDate(long lastPayDate) {
            this.lastPayDate = lastPayDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIsEnable() {
            return isEnable;
        }

        public void setIsEnable(boolean isEnable) {
            this.isEnable = isEnable;
        }
    }
}
