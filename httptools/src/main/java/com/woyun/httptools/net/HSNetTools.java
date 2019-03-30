package com.woyun.httptools.net;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.woyun.httptools.HSNetToolsApplication;
import com.woyun.httptools.tools.HSLog;


import org.json.JSONObject;

import java.util.Map;

public class HSNetTools {

    /***********************************************************
     ***********************使用该类必须先设置userToken**************
     **********************具体业务模块通讯请继承该类***************
     ***********************************************************/

    public final static String TAG = "HSNetTools";

    //用户token,信任标识
    private static String userToken;
    private static String userId;

    private  static String getUserToken() {
        if (userToken == null){
            String msg = "请设置用户token，NetTools.setUserToken";
            HSLog.print(TAG,msg);
            return msg;
        } else {
            return userToken;
        }
    }

    /**
     * 设置用户token
     *
     * @param token
     */
    public static void setUserToken(String token) {
        HSNetTools.userToken = token;
    }

    public static void setUserId(String userId){
        HSNetTools.userId=userId;
    }

    //服务器地址
    private static String hostAddress;

    public static String getHostAddress() {
        if (hostAddress == null){
            String msg = "请设置用户服务主机地址";
            HSLog.print(TAG,msg);
            return msg;
        } else {
            return hostAddress;
        }
    }

    public static void setHostAddress(String hostAddress) {
        HSNetTools.hostAddress = hostAddress;
    }


    public static void request(final Context context, String funcPrefix, final JSONObject params, final String tag, final int funcID, final int reqID,  final HSRequestCallBackInterface callBackInterface) {

        try {
            stringRequestPost(tag, hostAddress + funcPrefix, HSRequstBody.getRequstDataforJson(params, funcID, reqID, userToken,userId), new HSBackRequestInterface(context, HSBackRequestInterface.mListener, HSBackRequestInterface.mErrorListener) {
                HSResponseBody response;

                @Override
                public void backSuccess(String responseResult) {
                    try {
                        response = HSResponseBody.getResponseForString(responseResult, callBackInterface);
                        if (response != null) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = response;
                            requestHandler.sendMessage(msg);
//                            callBackInterface.requestSuccess(response.funcID, response.dataArray, response.msg, response.resCode, response.reqCode);
                        } else {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = response;
                            requestHandler.sendMessage(msg);
//                            callBackInterface.requestError(response.funcID, response.msg, response.resCode, response.reqCode);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void backError(VolleyError error) {
                    // toastShort(NetConstens.NET_ERROR, true);

                    try {
                        if (error!=null){
                            Message msg = new Message();
                            msg.what = -1;
                            msg.obj = callBackInterface;
//                            msg.arg1 = error.networkResponse.statusCode;
                            requestHandler.sendMessage(msg);
//                    if (context != null)
//                        ToastUtils.showToast(context, NetConstens.NET_ERROR);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "request: "+e.toString() );
        }
    }


    /**
     * 无状态请求
     *
     * @param tag
     * @param url
     * @param params
     * @param resutlInteface
     */
    private static StringRequest stringRequest;

    public static void stringRequestPost(String tag, String url, final Map<String, String> params, HSBackRequestInterface resutlInteface) {
        Log.e(TAG, "request===Url======= "+url);
        HSNetToolsApplication.getHttpRequestQueue().cancelAll(tag);
        stringRequest = new StringRequest(Request.Method.POST, url, resutlInteface.loadingListener(), resutlInteface.backErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        //网络访问一直使用的volley，前几天调试，出现一个问题
        // ，其他接口都可以访问成功，有一两个接口调用，会重复调用两次，访问的方法只有一次，
        // volley在一定时间内访问不到请求，会重新请求，所以，为了防止这样的问题，可以将默认的超时时间延长，
        // 尽量比服务器最大响应时间多一些，防止服务端返回错误信息时已断开连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(40* 1000,//默认超时时间，应设置一个稍微大点儿的，例如本处的40000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setTag(tag);
        HSNetToolsApplication.getHttpRequestQueue().add(stringRequest);
        HSNetToolsApplication.getHttpRequestQueue().start();
    }


    private static Handler requestHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            try {
                if (message.what == 1) {//OK
                    HSResponseBody response = (HSResponseBody) message.obj;
                    response.callBackInterface.requestSuccess(response.funcID, response.reqID, response.reqToken, response.msg, response.code, response.dataArray);
                } else if (message.what == 0) {
                    HSResponseBody response = (HSResponseBody) message.obj;
                    response.callBackInterface.requestSuccess(response.funcID, response.reqID, response.reqToken, response.msg, response.code,null);
                } else if (message.what == -1) {//error
                    HSRequestCallBackInterface callBackInterface = (HSRequestCallBackInterface) message.obj;
                    callBackInterface.requestError( HSNetConstance.NET_ERROR, message.arg1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void cancleReuqest(String tag){
        HSNetToolsApplication.getHttpRequestQueue().cancelAll(tag);
        HSNetToolsApplication.getHttpRequestQueue().stop();
    }
}
