package com.woyun.warehouse.utils;

import com.woyun.warehouse.bean.GoodsDetailBean;

import java.util.ArrayList;
import java.util.List;

import cn.udesk.PreferenceHelper;
import cn.udesk.UdeskSDKManager;
import cn.udesk.model.NavigationMode;
import cn.udesk.model.UdeskCommodityItem;
import udesk.core.model.Product;

/**
 * Udesk 帮助类
 */
public class UdeskHelp {
    private static UdeskHelp udeskHelp;

    private UdeskHelp(){}

    public static synchronized  UdeskHelp getInstance(){
        if(udeskHelp==null){
            udeskHelp=new UdeskHelp();
        }
        return  udeskHelp;
    }

    /**
     *  配置商品信息
     * @return
     */
    public UdeskCommodityItem createCommodity(GoodsDetailBean goodsDetailBean,String goodsWebUrl) {
        UdeskCommodityItem item = new UdeskCommodityItem();
        item.setTitle(goodsDetailBean.getName());// 商品主标题
        item.setSubTitle("￥"+goodsDetailBean.getVipPrice());//商品副标题-价格
        item.setThumbHttpUrl(goodsDetailBean.getImage());// 左侧图片
        item.setCommodityUrl(goodsWebUrl);// 商品网络链接

        return item;
    }

    /**
     * 发送商品信息
     * @param goodsDetailBean  商品信息
     * @param  url  链接
     */
    public Product createProduct(GoodsDetailBean goodsDetailBean,String url) {
        Product product = new Product();
        product.setImgUrl(goodsDetailBean.getImage());
        product.setName(goodsDetailBean.getName());
        product.setUrl(url);

        List<Product.ParamsBean> paramsBeans = new ArrayList<>();

        Product.ParamsBean paramsBean0 = new Product.ParamsBean();
        paramsBean0.setText("￥");
        paramsBean0.setColor("#ffffff");
        paramsBean0.setFold(false);
        paramsBean0.setBreakX(false);
        paramsBean0.setSize(14);

        Product.ParamsBean paramsBean1 = new Product.ParamsBean();
        paramsBean1.setText(goodsDetailBean.getVipPrice());
        paramsBean1.setColor("#ffffff");
        paramsBean1.setFold(true);
        paramsBean1.setBreakX(true);
        paramsBean1.setSize(16);

        Product.ParamsBean paramsBean2 = new Product.ParamsBean();
        paramsBean2.setText("原 价:");
        paramsBean2.setColor("#ffffff");
        paramsBean2.setFold(false);
        paramsBean2.setBreakX(false);
        paramsBean2.setSize(14);

        Product.ParamsBean paramsBean3 = new Product.ParamsBean();
        paramsBean3.setText(goodsDetailBean.getPrice());
        paramsBean3.setColor("#ffffff");
        paramsBean3.setFold(true);
        paramsBean3.setBreakX(false);
        paramsBean3.setSize(14);
        paramsBeans.add(paramsBean0);
        paramsBeans.add(paramsBean1);
        paramsBeans.add(paramsBean2);
        paramsBeans.add(paramsBean3);

        product.setParams(paramsBeans);

        return product;
    }

    /**
     * 导航ui
     * @return
     */
    public List<NavigationMode> getNavigations() {
        List<NavigationMode> modes = new ArrayList<>();
        NavigationMode navigationMode1 = new NavigationMode("发送订单号", 1);
        modes.add(navigationMode1);
        return modes;
    }

}
