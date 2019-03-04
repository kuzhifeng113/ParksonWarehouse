package com.woyun.warehouse.bean;

import java.util.List;

public class CangCoinTwoBean {
    /**
     * fee : 3%
     * grantCbCoin : 10.0
     * personal : 17%
     * page : {"pageCount":1,"total":2,"size":2,"pageSize":10,"currentPage":1,"content":[{"userAcountLogId":2,"act":3,"money":20,"tradeNo":"1548588828651476699","createTime":1548588828000,"source":"购物支出","title":"购物支出","type":1,"userid":"bc37037ad0a54ac0"},{"userAcountLogId":1,"act":2,"money":28,"tradeNo":"1548588825603966039","createTime":1548588825000,"source":"购物支出","title":"购物支出","type":1,"userid":"bc37037ad0a54ac0"}]}
     * bcCoin : 10.0
     * bcMoney : 10.0
     * grantMoney : 10.0
     *
     */

    private String fee;
    private double grantCbCoin;
    private String personal;
    private PageBean page;
    private double bcCoin;
    private double bcMoney;
    private double grantMoney;
    private boolean withdrawStatus;//是否有提现：fasle无提现记录，true有提现记录
    private String withdrawMoney;//提现金额
    private int withdrawType;//提现类型：1支付宝，2微信

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

    public boolean isWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(boolean withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public double getGrantCbCoin() {
        return grantCbCoin;
    }

    public void setGrantCbCoin(double grantCbCoin) {
        this.grantCbCoin = grantCbCoin;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
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

    public double getGrantMoney() {
        return grantMoney;
    }

    public void setGrantMoney(double grantMoney) {
        this.grantMoney = grantMoney;
    }

    public static class PageBean {
        /**
         * pageCount : 1
         * total : 2
         * size : 2
         * pageSize : 10
         * currentPage : 1
         * content : [{"userAcountLogId":2,"act":3,"money":20,"tradeNo":"1548588828651476699","createTime":1548588828000,"source":"购物支出","title":"购物支出","type":1,"userid":"bc37037ad0a54ac0"},{"userAcountLogId":1,"act":2,"money":28,"tradeNo":"1548588825603966039","createTime":1548588825000,"source":"购物支出","title":"购物支出","type":1,"userid":"bc37037ad0a54ac0"}]
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
             * userAcountLogId : 2
             * act : 3
             * money : 20.0
             * tradeNo : 1548588828651476699
             * createTime : 1548588828000
             * source : 购物支出
             * title : 购物支出
             * type : 1
             * userid : bc37037ad0a54ac0
             */

            private int userAcountLogId;
            private int act;
            private String money;
            private String tradeNo;
            private long createTime;
            private String source;
            private String title;
            private int type;
            private String userid;

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

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
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

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }
        }
    }
}
