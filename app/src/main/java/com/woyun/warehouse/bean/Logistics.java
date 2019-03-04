package com.woyun.warehouse.bean;

import java.io.Serializable;
import java.util.List;

public class Logistics implements Serializable {
    /**
     * traces : [{"AcceptStation":"【佛山市】  【南海盐步】（0757-85703485、0757-85703483） 的 河西8904 （18688290036） 已揽收","AcceptTime":"2018-11-23 22:27:45"},{"AcceptStation":"【佛山市】  快件离开 【南海盐步】 发往 【广州中心】","AcceptTime":"2018-11-23 23:08:56"},{"AcceptStation":"【佛山市】  快件到达 【佛山中心】","AcceptTime":"2018-11-24 02:16:12"},{"AcceptStation":"【佛山市】  快件离开 【佛山中心】 发往 【广州中心】","AcceptTime":"2018-11-24 02:20:01"},{"AcceptStation":"【广州市】  快件到达 【广州中心】","AcceptTime":"2018-11-24 04:44:23"},{"AcceptStation":"【广州市】  快件离开 【广州中心】 发往 【广州广园】","AcceptTime":"2018-11-24 05:21:53"},{"AcceptStation":"【广州市】  快件到达 【广州广园】","AcceptTime":"2018-11-24 07:11:17"},{"AcceptStation":"【广州市】  【广州广园】 的HS陈龙（18688202010） 正在第1次派件, 请保持电话畅通,并耐心等待","AcceptTime":"2018-11-24 11:26:19"},{"AcceptStation":"【广州市】  快件已在 【广州广园】 签收, 签收人: 已签收, 如有疑问请电联:18688202010 / 020-37414075, 您的快递已经妥投, 如果您对我们的服务感到满意, 请给个五星好评, 鼓励一下我们【请在评价快递员处帮忙点亮五颗星星哦~】","AcceptTime":"2018-11-24 14:34:06"}]
     * shipperName : 中通
     * logisticCode : 75111429006773
     */

    private String shipperName;
    private String logisticCode;
    private List<TracesBean> traces;

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getLogisticCode() {
        return logisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode;
    }

    public List<TracesBean> getTraces() {
        return traces;
    }

    public void setTraces(List<TracesBean> traces) {
        this.traces = traces;
    }

    public static class TracesBean {
        /**
         * AcceptStation : 【佛山市】  【南海盐步】（0757-85703485、0757-85703483） 的 河西8904 （18688290036） 已揽收
         * AcceptTime : 2018-11-23 22:27:45
         */

        private String AcceptStation;
        private String AcceptTime;

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }
    }
}
