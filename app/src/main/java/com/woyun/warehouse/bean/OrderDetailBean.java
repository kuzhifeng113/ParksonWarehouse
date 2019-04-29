package com.woyun.warehouse.bean;

import java.util.List;

/**
 * 订单明细 实体类
 */
public class OrderDetailBean {
    /**
     * address : 河北省石家庄市长安区无聊恶魔22号
     * tradeNo : 1542012209154175807
     * payConfrimTime : 1542012209000
     * ip : 192.168.1.2
     * totalMoney : 4.88
     * memo :
     * transport : 0
     * title : 商城订单
     * billDetailList : [{"skuName":"黑棕色S码","unitPrice":1.22,"tradeNo":"1542012209154175807","goodsId":1,"totalMoney":4.88,"goodsImage":"http://image.bscvip.com/goods_images/1540891360264478.png","goodsName":"裤子1","userid":"bc579548d72786b","skuId":6,"skuImage":"http://image.bscvip.com/goods_images/1541385786871126.jpg","skuNum":4}]
     * userid : bc579548d72786b
     * payType : 1
     * receiveName : 哈哈哈2
     * createTime : 1542012209000
     * phone : 13696354236
     * totalFee : 4.88
     * billStatus : 0
     * bcCoin : 0
     * bcMoney : 0
     * tradeType : 3
     * status : false
     */

    private String address;
    private String tradeNo;
    private long payConfrimTime;
    private String ip;
    private double totalMoney;
    private String memo;
    private double transport;
    private String title;
    private String userid;
    private int payType;
    private String receiveName;
    private long createTime;
    private String phone;
    private double totalFee;
    private int billStatus;
    private String bcCoin;
    private String bcMoney;
    private String bcHb;
    private int tradeType;
    private boolean status;
    private List<BillDetailListBean> billDetailList;
    private String shareMoney;

    public String getBcHb() {
        return bcHb;
    }

    public void setBcHb(String bcHb) {
        this.bcHb = bcHb;
    }

    public String getShareMoney() {
        return shareMoney;
    }

    public void setShareMoney(String shareMoney) {
        this.shareMoney = shareMoney;
    }

    private UserInvoiceBean userInvoice;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public long getPayConfrimTime() {
        return payConfrimTime;
    }

    public void setPayConfrimTime(long payConfrimTime) {
        this.payConfrimTime = payConfrimTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getTransport() {
        return transport;
    }

    public void setTransport(double transport) {
        this.transport = transport;
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

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(int billStatus) {
        this.billStatus = billStatus;
    }

    public String getBcCoin() {
        return bcCoin;
    }

    public void setBcCoin(String bcCoin) {
        this.bcCoin = bcCoin;
    }

    public String getBcMoney() {
        return bcMoney;
    }

    public void setBcMoney(String bcMoney) {
        this.bcMoney = bcMoney;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<BillDetailListBean> getBillDetailList() {
        return billDetailList;
    }

    public void setBillDetailList(List<BillDetailListBean> billDetailList) {
        this.billDetailList = billDetailList;
    }

    public UserInvoiceBean getUserInvoice() {
        return userInvoice;
    }

    public void setUserInvoice(UserInvoiceBean userInvoice) {
        this.userInvoice = userInvoice;
    }

    public static class BillDetailListBean {
        /**
         * skuName : 黑棕色S码
         * unitPrice : 1.22
         * tradeNo : 1542012209154175807
         * goodsId : 1
         * totalMoney : 4.88
         * goodsImage : http://image.bscvip.com/goods_images/1540891360264478.png
         * goodsName : 裤子1
         * userid : bc579548d72786b
         * skuId : 6
         * skuImage : http://image.bscvip.com/goods_images/1541385786871126.jpg
         * skuNum : 4
         * memo
         */

        private String skuName;
        private double unitPrice;
        private String tradeNo;
        private int goodsId;
        private double totalMoney;
        private String memo;
        private String goodsImage;
        private String goodsName;
        private String userid;
        private int skuId;
        private String skuImage;
        private int skuNum;

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public String getTradeNo() {
            return tradeNo;
        }

        public void setTradeNo(String tradeNo) {
            this.tradeNo = tradeNo;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public double getTotalMoney() {
            return totalMoney;
        }

        public void setTotalMoney(double totalMoney) {
            this.totalMoney = totalMoney;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public int getSkuId() {
            return skuId;
        }

        public void setSkuId(int skuId) {
            this.skuId = skuId;
        }

        public String getSkuImage() {
            return skuImage;
        }

        public void setSkuImage(String skuImage) {
            this.skuImage = skuImage;
        }

        public int getSkuNum() {
            return skuNum;
        }

        public void setSkuNum(int skuNum) {
            this.skuNum = skuNum;
        }
    }
    public static class UserInvoiceBean {
        /**
         * name : 哈哈哈
         * taxNumber :
         * invoiceId : 6
         * type : 0
         * userid : bc36e97c40869b76
         * email : 3625459@qq.com
         */

        private String name;
        private String taxNumber;
        private int invoiceId;
        private int type;
        private String userid;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(String taxNumber) {
            this.taxNumber = taxNumber;
        }

        public int getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(int invoiceId) {
            this.invoiceId = invoiceId;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
