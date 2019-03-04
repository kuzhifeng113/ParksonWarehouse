package com.woyun.httptools.net;



import com.woyun.httptools.tools.HSLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HSResponseBody {
    public final static String TAG = "HSResponseBody";

        /**
         * 功能ID
         */
        public int funcID;
        /**
         * 请求ID
         */
        public int reqID;
        /**
         * 请求令牌
         */
        public String reqToken;


        /**
         * 结果消息
         */
        public String msg;

        /**
         * 请求返回状态码
         */
        public int code;

        /**
         * 返回结果集
         */
        public JSONArray dataArray;

        public HSRequestCallBackInterface callBackInterface;


        public HSResponseBody(int funcID, int reqID, String reqToken,int code, String msg, JSONArray dataArray, HSRequestCallBackInterface callBackInterface) {
            this.funcID = funcID;
            this.reqID = reqID;
            this.reqToken = reqToken;
            this.code = code;
            this.msg = msg;
            this.dataArray = dataArray;
            this.callBackInterface = callBackInterface;
        }


    public static HSResponseBody getResponseForString(String responseReuslt, HSRequestCallBackInterface hsRequestCallBackInterface) {
        try {
            HSLog.print(TAG + "响应：", responseReuslt);
            JSONObject object = new JSONObject(responseReuslt);
            JSONArray data = object.getJSONArray(HSNetConstance.responseData);
            int funcID = object.getInt(HSNetConstance.funcID);
            int reqID = object.getInt(HSNetConstance.reqID);
            String reqToken = object.getString(HSNetConstance.reqToken);
            int code = object.getInt(HSNetConstance.code);
            String msg = object.getString(HSNetConstance.msg);
            HSResponseBody response = new HSResponseBody(funcID,reqID,reqToken,code,msg, data, hsRequestCallBackInterface);
            HSLog.print(TAG, "以上响应正常");
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
            HSLog.print(TAG + "响应：", responseReuslt);
            HSLog.print(TAG, "非结果集响应");
            try {
                JSONObject object = new JSONObject(responseReuslt);
                int funcID = object.getInt(HSNetConstance.funcID);
                int reqID = object.getInt(HSNetConstance.reqID);
                String reqToken = object.getString(HSNetConstance.reqToken);
                int code = object.getInt(HSNetConstance.code);
                String msg = object.getString(HSNetConstance.msg);
                HSResponseBody response = new HSResponseBody(funcID,reqID,reqToken,code,msg, null, hsRequestCallBackInterface);
                return response;
            } catch (JSONException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }
}
