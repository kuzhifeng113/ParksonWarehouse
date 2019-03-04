package com.woyun.warehouse.bean;

import java.util.List;

public class ZuanQianBean {
    /**
     * vipList : [{"image":"http://image.bscvip.com/app_static/vip/1img.png","unicon":"http://image.bscvip.com/app_static/vip/1u.png","icon":"http://image.bscvip.com/app_static/vip/1.png","name":"超值礼包"},{"image":"http://image.bscvip.com/app_static/vip/2img.png","unicon":"http://image.bscvip.com/app_static/vip/2u.png","icon":"http://image.bscvip.com/app_static/vip/2.png","name":"168全返"},{"image":"http://image.bscvip.com/app_static/vip/3img.png","unicon":"http://image.bscvip.com/app_static/vip/3u.png","icon":"http://image.bscvip.com/app_static/vip/3.png","name":"购物省钱"},{"image":"http://image.bscvip.com/app_static/vip/4img.png","unicon":"http://image.bscvip.com/app_static/vip/4u.png","icon":"http://image.bscvip.com/app_static/vip/4.png","name":"推荐奖励"},{"image":"http://image.bscvip.com/app_static/vip/5img.png","unicon":"http://image.bscvip.com/app_static/vip/5u.png","icon":"http://image.bscvip.com/app_static/vip/5.png","name":"新人奖励"},{"image":"http://image.bscvip.com/app_static/vip/6img.png","unicon":"http://image.bscvip.com/app_static/vip/6u.png","icon":"http://image.bscvip.com/app_static/vip/6.png","name":"购物返利"},{"image":"http://image.bscvip.com/app_static/vip/7img.png","unicon":"http://image.bscvip.com/app_static/vip/7u.png","icon":"http://image.bscvip.com/app_static/vip/7.png","name":"售后无忧"},{"image":"http://image.bscvip.com/app_static/vip/8img.png","unicon":"http://image.bscvip.com/app_static/vip/8u.png","icon":"http://image.bscvip.com/app_static/vip/8.png","name":"底价拼团"}]
     * isAgent : false
     * num : 0
     * vipPrice : 168
     * agentPrice : 6552
     * agentList : [{"image":"http://image.bscvip.com/app_static/vip/9img.png","unicon":"http://image.bscvip.com/app_static/vip/9u.png","icon":"http://image.bscvip.com/app_static/vip/9.png","name":"会员所有权益"},{"image":"http://image.bscvip.com/app_static/vip/10img.png","unicon":"http://image.bscvip.com/app_static/vip/10u.png","icon":"http://image.bscvip.com/app_static/vip/10.png","name":"39个会员名额"},{"image":"http://image.bscvip.com/app_static/vip/11img.png","unicon":"http://image.bscvip.com/app_static/vip/11u.png","icon":"http://image.bscvip.com/app_static/vip/11.png","name":"一级分销奖"},{"image":"http://image.bscvip.com/app_static/vip/12img.png","unicon":"http://image.bscvip.com/app_static/vip/12u.png","icon":"http://image.bscvip.com/app_static/vip/12.png","name":"管理奖"},{"image":"http://image.bscvip.com/app_static/vip/13img.png","unicon":"http://image.bscvip.com/app_static/vip/13u.png","icon":"http://image.bscvip.com/app_static/vip/13.png","name":"会员购物奖励"}]
     * vipNum : 0
     * isVip : false
     */
    private boolean isAgent;
    private int num;//会员直推个数
    private String vipPrice;
    private String agentPrice;
    private String silverPrice;//银牌价格
    private int vipNum;//代理vip剩余可开通个数
    private boolean isVip;
    private List<VipListBean> vipList;
    private List<AgentListBean> agentList;
    private List<SilverListBean> silverList;

    public List<SilverListBean> getSliverList() {
        return silverList;
    }

    public void setSliverList(List<SilverListBean> sliverList) {
        this.silverList = sliverList;
    }


    private String backMoney;//开通代理获得返回的钱

    public String getSilverPrice() {
        return silverPrice;
    }

    public void setSilverPrice(String silverPrice) {
        this.silverPrice = silverPrice;
    }

    public String getBackMoney() {
        return backMoney;
    }

    public void setBackMoney(String backMoney) {
        this.backMoney = backMoney;
    }

    public boolean isIsAgent() {
        return isAgent;
    }

    public void setIsAgent(boolean isAgent) {
        this.isAgent = isAgent;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getAgentPrice() {
        return agentPrice;
    }

    public void setAgentPrice(String agentPrice) {
        this.agentPrice = agentPrice;
    }

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public boolean isIsVip() {
        return isVip;
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    public List<VipListBean> getVipList() {
        return vipList;
    }

    public void setVipList(List<VipListBean> vipList) {
        this.vipList = vipList;
    }

    public List<AgentListBean> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<AgentListBean> agentList) {
        this.agentList = agentList;
    }


    public static class VipListBean {
        /**
         * image : http://image.bscvip.com/app_static/vip/1img.png
         * unicon : http://image.bscvip.com/app_static/vip/1u.png
         * icon : http://image.bscvip.com/app_static/vip/1.png
         * name : 超值礼包
         */

        private String image;
        private String unicon;
        private String icon;
        private String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnicon() {
            return unicon;
        }

        public void setUnicon(String unicon) {
            this.unicon = unicon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class AgentListBean {
        /**
         * image : http://image.bscvip.com/app_static/vip/9img.png
         * unicon : http://image.bscvip.com/app_static/vip/9u.png
         * icon : http://image.bscvip.com/app_static/vip/9.png
         * name : 会员所有权益
         */

        private String image;
        private String unicon;
        private String icon;
        private String name;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnicon() {
            return unicon;
        }

        public void setUnicon(String unicon) {
            this.unicon = unicon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SilverListBean {
        /**
         * image : http://image.bscvip.com/app_static/vip/9img.png
         * unicon : http://image.bscvip.com/app_static/vip/9u.png
         * icon : http://image.bscvip.com/app_static/vip/9.png
         * name : 会员所有权益
         */

        private String image;//大图
        private String unicon;//未选中
        private String icon;//选中
        private String name;//名字

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUnicon() {
            return unicon;
        }

        public void setUnicon(String unicon) {
            this.unicon = unicon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
