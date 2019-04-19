package com.woyun.warehouse.bean;

public class UserInfoTwoBean {
    /**
     * manageMoney : 0.0
     * userInfo : {"isReal":false,"wx":"","platfrom":"AND","deviceAlias":"a2818fcc1f23411c9e2d69d2e823fc31","mobile":"18900791426","fuserid":"","avatar":"","birthDate":"","deviceId":"a2818fcc1f23411c9e2d69d2e823fc31","userid":"bc37037ad0a54ac0","isVip":false,"token":"d3fccd386885c7292627fd399ae09cb2","wxname":"","isAgent":false,"zfb":"","nickname":"bc3***7ad0a54ac0","certReal":false,"bcCoin":0,"bcMoney":0,"pwd":"","zfbname":"","status":0}
     * wxh : bsc20180208
     * fee : 3%
     * shoppingMoney : 0.0
     * totalMoney : 0.0
     * pushMoney : 0.0
     * vipewm : http://image.bscvip.com/app_static/vip_ewm.jpg
     * personal : 17%   //个税
     * grantMoney : 0.0
     * withdrawStatus : false
     * grantCbCoin : 0.0
     * newMoney : 0.0
     * bcCoin : 0.0
     * bcMoney : 0.0
     * agentMoney : 0.0
     * isTree
     * totalBkMoney
     * agentTotalBkMoney
     */

    private double manageMoney;
    private UserInfoBean userInfo;
    private String wxh;
    private String fee;
    private double shoppingMoney;
    private double totalMoney;
    private double pushMoney;
    private String vipewm;
    private String personal;
    private double grantMoney;
    private boolean withdrawStatus;
    private double grantCbCoin;
    private double newMoney;
    private double bcCoin;
    private double bcMoney;
    private double agentMoney;
    private double todayMoney;//今日奖励
    private double yesterdayMoney;//昨日奖励
    private double beforeMoney;//前日奖励
    private double lastWeekMoney;//上周奖励
    private double weekMoney;//本周奖励
    private double monthMoney;//本月奖励
    private double totalBkMoney;//最终可获得返回收益
    private double agentTotalBkMoney;//最终可获得返回收益

    public double getAgentTotalBkMoney() {
        return agentTotalBkMoney;
    }

    public void setAgentTotalBkMoney(double agentTotalBkMoney) {
        this.agentTotalBkMoney = agentTotalBkMoney;
    }

    public double getTotalBkMoney() {
        return totalBkMoney;
    }

    public void setTotalBkMoney(double totalBkMoney) {
        this.totalBkMoney = totalBkMoney;
    }

    public double getLastWeekMoney() {
        return lastWeekMoney;
    }

    public void setLastWeekMoney(double lastWeekMoney) {
        this.lastWeekMoney = lastWeekMoney;
    }

    public double getWeekMoney() {
        return weekMoney;
    }

    public void setWeekMoney(double weekMoney) {
        this.weekMoney = weekMoney;
    }

    public double getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(double monthMoney) {
        this.monthMoney = monthMoney;
    }

    public double getManageMoney() {
        return manageMoney;
    }

    public void setManageMoney(double manageMoney) {
        this.manageMoney = manageMoney;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public double getShoppingMoney() {
        return shoppingMoney;
    }

    public void setShoppingMoney(double shoppingMoney) {
        this.shoppingMoney = shoppingMoney;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public double getPushMoney() {
        return pushMoney;
    }

    public void setPushMoney(double pushMoney) {
        this.pushMoney = pushMoney;
    }

    public String getVipewm() {
        return vipewm;
    }

    public void setVipewm(String vipewm) {
        this.vipewm = vipewm;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public double getGrantMoney() {
        return grantMoney;
    }

    public void setGrantMoney(double grantMoney) {
        this.grantMoney = grantMoney;
    }

    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(boolean withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public double getGrantCbCoin() {
        return grantCbCoin;
    }

    public void setGrantCbCoin(double grantCbCoin) {
        this.grantCbCoin = grantCbCoin;
    }

    public double getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(double newMoney) {
        this.newMoney = newMoney;
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

    public double getAgentMoney() {
        return agentMoney;
    }

    public void setAgentMoney(double agentMoney) {
        this.agentMoney = agentMoney;
    }

    public double getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(double todayMoney) {
        this.todayMoney = todayMoney;
    }

    public double getYesterdayMoney() {
        return yesterdayMoney;
    }

    public void setYesterdayMoney(double yesterdayMoney) {
        this.yesterdayMoney = yesterdayMoney;
    }

    public double getBeforeMoney() {
        return beforeMoney;
    }

    public void setBeforeMoney(double beforeMoney) {
        this.beforeMoney = beforeMoney;
    }

    public static class UserInfoBean {
        /**
         * isReal : false
         * wx :
         * platfrom : AND
         * deviceAlias : a2818fcc1f23411c9e2d69d2e823fc31
         * mobile : 18900791426
         * fuserid :
         * avatar :
         * birthDate :
         * deviceId : a2818fcc1f23411c9e2d69d2e823fc31
         * userid : bc37037ad0a54ac0
         * isVip : false
         * token : d3fccd386885c7292627fd399ae09cb2
         * wxname :
         * isAgent : false
         * zfb :
         * nickname : bc3***7ad0a54ac0
         * certReal : false
         * bcCoin : 0.0
         * bcMoney : 0.0
         * pwd :
         * zfbname :
         * status : 0
         * isTree
         * defaultAddress
         */

        private boolean isReal;
        private String wx;
        private String platfrom;
        private String deviceAlias;
        private String mobile;
        private String fuserid;
        private String avatar;
        private String birthDate;
        private String deviceId;
        private String userid;
        private boolean isVip;
        private String token;
        private String wxname;
        private boolean isAgent;
        private String zfb;
        private String nickname;
        private boolean certReal;
        private double bcCoin;
        private double bcMoney;
        private String pwd;
        private String zfbname;
        private int status;
        private boolean isTree;
        private boolean defaultAddress;//是否设置默认地址：false没有true已设置

        public boolean isDefaultAddress() {
            return defaultAddress;
        }

        public void setDefaultAddress(boolean defaultAddress) {
            this.defaultAddress = defaultAddress;
        }

        public boolean isTree() {
            return isTree;
        }

        public void setTree(boolean tree) {
            isTree = tree;
        }

        public boolean isIsReal() {
            return isReal;
        }

        public void setIsReal(boolean isReal) {
            this.isReal = isReal;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }

        public String getPlatfrom() {
            return platfrom;
        }

        public void setPlatfrom(String platfrom) {
            this.platfrom = platfrom;
        }

        public String getDeviceAlias() {
            return deviceAlias;
        }

        public void setDeviceAlias(String deviceAlias) {
            this.deviceAlias = deviceAlias;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFuserid() {
            return fuserid;
        }

        public void setFuserid(String fuserid) {
            this.fuserid = fuserid;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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

        public String getWxname() {
            return wxname;
        }

        public void setWxname(String wxname) {
            this.wxname = wxname;
        }

        public boolean isIsAgent() {
            return isAgent;
        }

        public void setIsAgent(boolean isAgent) {
            this.isAgent = isAgent;
        }

        public String getZfb() {
            return zfb;
        }

        public void setZfb(String zfb) {
            this.zfb = zfb;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public boolean isCertReal() {
            return certReal;
        }

        public void setCertReal(boolean certReal) {
            this.certReal = certReal;
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

        public String getZfbname() {
            return zfbname;
        }

        public void setZfbname(String zfbname) {
            this.zfbname = zfbname;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
