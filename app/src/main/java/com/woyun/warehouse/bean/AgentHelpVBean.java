package com.woyun.warehouse.bean;

import java.util.List;

public class AgentHelpVBean {


    /**
     * vipOpenNum : -10
     * vipHistoryNum : 0
     * pageBean : {"pageCount":1,"total":4,"size":4,"pageSize":10,"currentPage":1,"content":[{"vipPhone":"VIP_phone1","vipName":"VIP3","vipUserid":"bc36bbc7af11a25c"},{"vipPhone":"VIP_phone1","vipName":"VIP4","vipUserid":"bc36bbc7af11a25c"},{"vipPhone":"18122198010","vipName":"文景","vipUserid":"bc579306f340121","createTime":1542622037000},{"vipPhone":"18122198010","vipName":"文景","vipUserid":"bc579306f340121","createTime":1542622137000}]}
     * vipNum : 10
     */

    private int vipOpenNum;
    private int vipHistoryNum;
    private PageBeanBean pageBean;
    private int vipNum;

    public int getVipOpenNum() {
        return vipOpenNum;
    }

    public void setVipOpenNum(int vipOpenNum) {
        this.vipOpenNum = vipOpenNum;
    }

    public int getVipHistoryNum() {
        return vipHistoryNum;
    }

    public void setVipHistoryNum(int vipHistoryNum) {
        this.vipHistoryNum = vipHistoryNum;
    }

    public PageBeanBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBeanBean pageBean) {
        this.pageBean = pageBean;
    }

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public static class PageBeanBean {
        /**
         * pageCount : 1
         * total : 4
         * size : 4
         * pageSize : 10
         * currentPage : 1
         * content : [{"vipPhone":"VIP_phone1","vipName":"VIP3","vipUserid":"bc36bbc7af11a25c"},{"vipPhone":"VIP_phone1","vipName":"VIP4","vipUserid":"bc36bbc7af11a25c"},{"vipPhone":"18122198010","vipName":"文景","vipUserid":"bc579306f340121","createTime":1542622037000},{"vipPhone":"18122198010","vipName":"文景","vipUserid":"bc579306f340121","createTime":1542622137000}]
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
             * vipPhone : VIP_phone1
             * vipName : VIP3
             * vipUserid : bc36bbc7af11a25c
             * createTime : 1542622037000
             */

            private String vipPhone;
            private String vipName;
            private String vipUserid;
            private long createTime;

            public String getVipPhone() {
                return vipPhone;
            }

            public void setVipPhone(String vipPhone) {
                this.vipPhone = vipPhone;
            }

            public String getVipName() {
                return vipName;
            }

            public void setVipName(String vipName) {
                this.vipName = vipName;
            }

            public String getVipUserid() {
                return vipUserid;
            }

            public void setVipUserid(String vipUserid) {
                this.vipUserid = vipUserid;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }
        }
    }
}
