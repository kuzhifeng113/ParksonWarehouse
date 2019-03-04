package com.woyun.httptools.sample;

import android.content.Context;

import com.woyun.httptools.net.HSNetTools;
import com.woyun.httptools.net.HSRequestCallBackInterface;

import org.json.JSONObject;

public class SampleRequestInterface extends HSNetTools {

    /**
     * 每个继承HSNetTools的类必须获取并设置用户TOKEN
     * @return
     */
    private static String getUserToken(){
        String token =  "这里调用获取用户token";
        return token;
    }

    /**
     * 用户模块请求
     * @param context
     * @param params
     * @param tag
     * @param funcID
     * @param reqID
     * @param callBackInterface
     */
    public static void userRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());
        request(context,ReqConstance.USER_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }

    /**
     * 产品模块请求
     * @param context
     * @param params
     * @param tag
     * @param funcID
     * @param reqID
     * @param callBackInterface
     */
    public static void productRequest(final Context context, final JSONObject params, final String tag, final int funcID, final int reqID, final HSRequestCallBackInterface callBackInterface){
        //调用request之前必须设置服务器地址
        setHostAddress(ReqConstance.HOST_ADDR);
        //调用request之前请必须设置Token
        setUserToken(getUserToken());

        request(context,ReqConstance.HOME_LAYOUT_PREFIX,params,tag,funcID,reqID,callBackInterface);
    }


}
