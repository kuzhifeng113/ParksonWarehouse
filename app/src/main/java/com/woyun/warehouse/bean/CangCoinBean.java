package com.woyun.warehouse.bean;

import java.util.List;

public class CangCoinBean {

    /**
     * pageCount : 1
     * total : 4
     * size : 4
     * pageSize : 10
     * currentPage : 1
     * content : [{"userAcountLogId":23,"act":7,"money":10,"createTime":1542007615000,"source":"系统发放","type":1,"userid":"bc579548d72786b"},{"userAcountLogId":3,"act":1,"money":-0.6,"tradeNo":"1541670412663257882","createTime":1541670412000,"source":"订单支付","type":1,"userid":"bc579548d72786b"},{"userAcountLogId":2,"act":1,"money":-1.1,"tradeNo":"154167039777695626","createTime":1541670397000,"source":"订单支付","type":1,"userid":"bc579548d72786b"},{"userAcountLogId":1,"act":3,"money":82.1,"tradeNo":"1541670197355947629","createTime":1541670197000,"source":"订单支付","type":1,"userid":"bc579548d72786b"}]
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
         * userAcountLogId : 23
         * act : 7
         * money : 10
         * createTime : 1542007615000
         * source : 系统发放
         * type : 1
         * userid : bc579548d72786b
         * tradeNo : 1541670412663257882
         */

        private int userAcountLogId;
        private int act;
        private String money;
        private long createTime;
        private String source;
        private int type;
        private String userid;
        private String tradeNo;

        public int getUserAcountLogId() {
            return userAcountLogId;
        }

        public void setUserAcountLogId(int userAcountLogId) {
            this.userAcountLogId = userAcountLogId;
        }

        public int getAct() {
            return act;
        }

        public void setAct(int act) {
            this.act = act;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }
    }
}
