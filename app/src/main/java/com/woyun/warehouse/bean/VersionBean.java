package com.woyun.warehouse.bean;

public class VersionBean {

    /**
     * code : 5
     * isNew : false
     * version : 2.0
     * content : 强烈推荐您立即更新。本次增加了更多撩妹神器
     * isVip : true
     * platform : AND
     * url : https://dymeet.oss-cn-shenzhen.aliyuncs.com/app_download/dy.apk
     * shareContent : 这里有很多奇葩的妹子和榴莲在等你
     * isAgent : true
     * shareIcon : https://dymeet.oss-cn-shenzhen.aliyuncs.com/comm/logo-100.jpg
     * shareUrl : http://comm.dymeet.com/download.html
     * id : 1
     * status : 0
     */

    private String code;
    private boolean isNew;
    private String version;
    private String content;
    private boolean isVip;
    private String platform;
    private String url;
    private String shareContent;
    private boolean isAgent;
    private String shareIcon;
    private String shareUrl;
    private String shareTitle;
    private int unreadNum;//未读消息
    private int id;
    private int status;

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public String getShareIcon() {
        return shareIcon;
    }

    public void setShareIcon(String shareIcon) {
        this.shareIcon = shareIcon;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
