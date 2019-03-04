package com.woyun.warehouse.bean;

import java.util.List;

public class MessageBean {

    /**
     * pageCount : 1
     * total : 1
     * size : 1
     * pageSize : 10
     * currentPage : 1
     * content : [{"image":"http://image.bscvip.com/app_static/message/cb.png","tradeNo":"1544080688972416630","createTime":1544080688000,"isRead":false,"id":1,"title":"百盛仓商城订单","type":7,"enableFlag":true,"userid":"bc36db528c5c99c1","content":"订单号：1544080688972416630成功支付，仓币支付：180.0"}]
     */

    private int pageCount;
    private int total;
    private int size;
    private int pageSize;
    private int currentPage;
    private List<ContentBean> content;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * image : http://image.bscvip.com/app_static/message/cb.png
         * tradeNo : 1544080688972416630
         * createTime : 1544080688000
         * isRead : false
         * id : 1
         * title : 百盛仓商城订单
         * type : 7
         * enableFlag : true
         * userid : bc36db528c5c99c1
         * content : 订单号：1544080688972416630成功支付，仓币支付：180.0
         * redirectId  跳转对象ID
         * url   type=9 网页URL
         */

        private String image;
        private String tradeNo;
        private long createTime;
        private boolean isRead;
        private int id;
        private String title;
        private int type;
        private boolean enableFlag;
        private String userid;
        private String content;
        private int redirectId;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public boolean isIsRead() {
            return isRead;
        }

        public void setIsRead(boolean isRead) {
            this.isRead = isRead;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isEnableFlag() {
            return enableFlag;
        }

        public void setEnableFlag(boolean enableFlag) {
            this.enableFlag = enableFlag;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRedirectId() {
            return redirectId;
        }

        public void setRedirectId(int redirectId) {
            this.redirectId = redirectId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
