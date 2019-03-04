package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 收货地址
 */
public class ShipAddressBean implements Serializable {
    /**
     * address : 哇哈哈哈路11号
     * province : 北京市
     * city : 北京市
     * phone : 16354575636
     * county : 东城区
     * name : 龙门
     * id : 23
     * userid : bc579548d72786b
     * status : 0
     *transport
     */

    private String address;
    private String province;
    private String city;
    private String phone;
    private String county;
    private String name;
    private int id;
    private String userid;
    private int status;
    private String bcCoin;//仓币
    private String bcMoney;//余额
    private String transport;//满多少包邮

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
    private List<InvoiceListBean> invoiceList;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<InvoiceListBean> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<InvoiceListBean> invoiceList) {
        this.invoiceList = invoiceList;
    }

    public static class InvoiceListBean {
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
