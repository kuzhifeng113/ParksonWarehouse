package com.woyun.warehouse.bean;

import java.util.List;

public class RedPackDetailBean {

    /**
     * pageCount : 4
     * total : 48
     * size : 15
     * pageSize : 15
     * currentPage : 1
     * content : [{"act":0,"money":3.28,"createTime":1555494094000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":3.78,"createTime":1555495686000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.54,"createTime":1555495849000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.22,"createTime":1555495856000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.07,"createTime":1555496062000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":0.99,"createTime":1555496066000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.03,"createTime":1555496068000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.05,"createTime":1555496069000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.16,"createTime":1555496071000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.06,"createTime":1555496073000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.15,"createTime":1555496291000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":3.8,"createTime":1555496293000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.31,"createTime":1555496295000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1.53,"createTime":1555496296000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"},{"act":0,"money":1,"createTime":1555496297000,"nickname":"bc3***38db71d1e7","title":"拆红包","userid":"bc370738db71d1e7"}]
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
         * act : 0
         * money : 3.28
         * createTime : 1555494094000
         * nickname : bc3***38db71d1e7
         * title : 拆红包
         * userid : bc370738db71d1e7
         */

        private int act;
        private String money;
        private long createTime;
        private String nickname;
        private String title;
        private String userid;

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

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }
    }
}
