package com.woyun.warehouse.bean;

public class AgentCenterBean {


    /**
     * totalReward : 180.0
     * currMonthReward : 180.0
     * zeroLevelNum : 1
     * teamReward : 0.0
     * lastMonthReward : 0.0
     * bcMoney : 180.0
     * shoppingReward : 0.0
     * teamNum : 0
     * zeroLevelReward : 180.0
     * agentOut   boolean  是否申请退出代理，=false未申请，=true已申请
     * fee   税率
     * wxh
     * ewm
     */
    private int agentNum;
    private String totalReward;
    private String currMonthReward;
    private int zeroLevelNum;
    private double teamReward;
    private String lastMonthReward;
    private String bcMoney;
    private double shoppingReward;
    private int teamNum;
    private double zeroLevelReward;
    private boolean agentOut;
    private boolean withdrawStatus;//是否有提现：fasle无提现记录，true有提现记录
    private String withdrawMoney;//提现金额
    private int withdrawType;//提现类型：1支付宝，2微信
    private String fee;
    private String wxh;
    private String ewm;

    public String getWxh() {
        return wxh;
    }

    public void setWxh(String wxh) {
        this.wxh = wxh;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(boolean withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getWithdrawMoney() {
        return withdrawMoney;
    }

    public void setWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
    }

    public int getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(int withdrawType) {
        this.withdrawType = withdrawType;
    }

    public boolean isAgentOut() {
        return agentOut;
    }

    public void setAgentOut(boolean agentOut) {
        this.agentOut = agentOut;
    }

    public int getAgentNum() {
        return agentNum;
    }

    public void setAgentNum(int agentNum) {
        this.agentNum = agentNum;
    }

    public String getTotalReward() {
        return totalReward;
    }

    public void setTotalReward(String totalReward) {
        this.totalReward = totalReward;
    }

    public String getCurrMonthReward() {
        return currMonthReward;
    }

    public void setCurrMonthReward(String currMonthReward) {
        this.currMonthReward = currMonthReward;
    }

    public int getZeroLevelNum() {
        return zeroLevelNum;
    }

    public void setZeroLevelNum(int zeroLevelNum) {
        this.zeroLevelNum = zeroLevelNum;
    }

    public double getTeamReward() {
        return teamReward;
    }

    public void setTeamReward(double teamReward) {
        this.teamReward = teamReward;
    }

    public String getLastMonthReward() {
        return lastMonthReward;
    }

    public void setLastMonthReward(String lastMonthReward) {
        this.lastMonthReward = lastMonthReward;
    }

    public String getBcMoney() {
        return bcMoney;
    }

    public void setBcMoney(String bcMoney) {
        this.bcMoney = bcMoney;
    }

    public double getShoppingReward() {
        return shoppingReward;
    }

    public void setShoppingReward(double shoppingReward) {
        this.shoppingReward = shoppingReward;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public double getZeroLevelReward() {
        return zeroLevelReward;
    }

    public void setZeroLevelReward(double zeroLevelReward) {
        this.zeroLevelReward = zeroLevelReward;
    }
}
