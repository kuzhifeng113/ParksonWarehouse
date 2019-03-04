package com.woyun.warehouse.api;

import android.content.Context;
import com.woyun.httptools.net.HSNetTools;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.utils.SPUtils;

import org.json.JSONObject;

public class RequestInterface extends HSNetTools {
    /**
     * 每个继承HSNetTools的类必须获取并设置用户TOKEN
     * @return
     */
    private static String getUserToken(){
        String token = (String) SPUtils.getInstance(MyApplication.context).get(Constant.TOKEN,"token");
        return token;
    }

    private static String getUserId(){
        String userid= (String) SPUtils.getInstance(MyApplication.context).get(Constant.USER_ID,"");
        return  userid;
    }

    /**
     * 登录模块
     * @param context  上下文
     * @param params 请求参数
     * @param tag   tag标识
     * @param funcID 功能ID
     * @param reqID  请求id
     * @param callBackInterface
     */
    public static void requestLogin(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.LOGIN_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /** 用户模块
     * @param context
     * @param params
     * @param tag
     * @param funcID
     * @param reqID
     * @param callBackInterface
     */
    public static void userPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.USER_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    public static void userPrefixVersiontTwo(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR_TWO);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.USER_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /** 系统相关 **/
    public static void sysPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.SYS_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }



    /** 投票模块 **/
    public static void voteRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.VOTE_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }


    /** 商城模块 **/
    public static void goodsPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.GOODS_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }


    /** 购物车模块 **/
    public static void cartPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.CART_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /** 支付模块 **/
    public static void payPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.PAY_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /** 代理模块 **/
    public static void agentPrefix(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        //调用request 之前必须设置userid
        setUserId(getUserId());
        request(context,ReqConstance.AGENT_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /** 获取上传头像图片OSS 参数 **/
    public static void uploadImg(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        request(context,ReqConstance.USER_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

//    /** 获取上传动态图片OSS 参数 **/
//    public static void uploadCircleImg(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.DYC_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }

    /**EaseMob 模块 **/
    /** 获取通讯录 **/
    /** 叮一下 加好友 **/
//   1.发送加好友请求  IM_CMD_FIREND_REQ_ADD = 101;
//   2.同意加好友请求  IM_CMD_FIREND_REQ_AOL = 102;
//   3.拒绝加好友请求 IM_CMD_FIREND_REQ_DE = 103;
//   4.添加好友到黑名单 IM_CMD_FIREND_JOIN_BLACK = 104;
//   5.移除黑名单中的好友  IM_CMD_FIREND_REMOVE_BLACK = 105;
//   6.解除好友关系 IM_CMD_FIREND_DEL = 106;
//    public static void easeMobRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.EASEMOB_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }


//    /** 标签模块 **/
//    public static void labelRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.Meet_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }

//    /** 动态模块 **/
//    public static void circleRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.DYC_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }

//    /** 系统模块 **/
//    public static void sysRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.SYS_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }
//    /** 获取上传 建议举报/实名认证图片OSS 参数 **/
//    public static void uploadRealName(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.SYS_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }


    /** 支付 vip**/
    public static void vipAndVipRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        request(context,ReqConstance.PAY_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

//    /** 首页 **/
//    public static void indexRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
//        //调用request之前必须设置服务器地址
//        setHostAddress(ReqConstance.HOST_ADDR);
//        //调用request之前请必须设置Token
//        setUserToken(getUserToken());
//        request(context,ReqConstance.Meet_PREFIX,params,tag,funcID,reqID,callBackInterface);
//    }

    /** 广告**/
    public static void advRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        request(context,ReqConstance.ADV_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

}
