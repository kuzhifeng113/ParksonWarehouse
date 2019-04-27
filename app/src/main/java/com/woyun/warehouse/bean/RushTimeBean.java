package com.woyun.warehouse.bean;

public class RushTimeBean {
    /**
     * rushBuyId : 1
     * startTime : 1554858000000   开始时间
     * endTime : 1554775200000
     * status
     */
    private String name;
    private int rushBuyId;
    private long startTime;
    private long endTime;
    private String status;
    private boolean isSelect;
    private long currTime;

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRushBuyId() {
        return rushBuyId;
    }

    public void setRushBuyId(int rushBuyId) {
        this.rushBuyId = rushBuyId;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getCurrTime() {
        return currTime;
    }

    public void setCurrTime(long currTime) {
        this.currTime = currTime;
    }
}
