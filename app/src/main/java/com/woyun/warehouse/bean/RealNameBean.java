package com.woyun.warehouse.bean;

import java.io.Serializable;

public class RealNameBean implements Serializable{
//    id	number	无
//    userid	string	我的用户ID
//    status	number	申请状态 0=等待审核，1=审核通过，-1=审核拒绝
//    realName	string	真实姓名
//    cardFurl	string	身份证正面
//    cardBurl	string	身份证反面
//    cardPos	string	手持身份证
//    cardId	string	身份证号
//            result
    private int id;
    private String userid;
    private int status;
    private String realName;
    private String cardFurl;
    private String cardBurl;
    private String cardPos;
    private String cardId;
    private String result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCardFurl() {
        return cardFurl;
    }

    public void setCardFurl(String cardFurl) {
        this.cardFurl = cardFurl;
    }

    public String getCardBurl() {
        return cardBurl;
    }

    public void setCardBurl(String cardBurl) {
        this.cardBurl = cardBurl;
    }

    public String getCardPos() {
        return cardPos;
    }

    public void setCardPos(String cardPos) {
        this.cardPos = cardPos;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
