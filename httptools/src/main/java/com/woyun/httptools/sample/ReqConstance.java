package com.woyun.httptools.sample;

/**
 *  1.0 API
 *
 */
public class ReqConstance {

    //当前API版本
    public static final String VER="/v1";

    //服务器地址
    public static final String HOST_ADDR="http://192.168.1.8:8080"+VER;


    //////////////////用户管理模块/////////////////////////////
    /**
     * 用户模块名称
     */
    public static final String USER_PREFIX = "/users";

    //查找所有用户
    public static final int I_USER_FIND_ALL = 100;
    //新增一个用户
    public static final int I_USER_ADD = 101;
    //删除一个用户
    public static final int I_USER_DEL = 102;


    /////////////////首页布局控制模块/////////////////
    /**
     * 首页布局模块名称
     */
    public static final String HOME_LAYOUT_PREFIX = "/layout_home";

    //获取首页布局
    public static final int I_HOME_FRAME = 400;
    //获取轮播集合
    public static final int I_HOME_ADV_1 = 401;

    //更多写这里，保持同步更新

}
