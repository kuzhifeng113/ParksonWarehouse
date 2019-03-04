package com.woyun.httptools.net;


import com.woyun.httptools.net.HSNetConstance;
import com.woyun.httptools.tools.HSLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aosen
 */
public class HSRequstBody {
    public final static String TAG = "HSRequstBody";
    /**
     * 功能ID
     */
    private int funcID;
    /**
     * 请求ID
     */
    private int reqID;
    /**
     * 请求令牌
     */
    private String reqToken;

    /**
     * 请求参数
     */
    private String reqPMS;


    private static Map<String, String> map;

    /**
     * 请求体包装静态方法
     * @param params
     * @param funcId
     * @param reqId
     * @param reqToken
     * @return
     */
    public static Map<String, String> getRequstDataforJson(JSONObject params, int funcId, int reqId,String reqToken,String userId) {

        try {
            map = new HashMap<String, String>();
            map.put(HSNetConstance.funcID, "" + funcId);
            map.put(HSNetConstance.reqID, "" + reqId);
            map.put(HSNetConstance.reqToken, reqToken);
            map.put(HSNetConstance.userId, userId);
            map.put(HSNetConstance.reqPMS, params.toString());
            String pms = map.toString();
            HSLog.print(TAG + "请求参数", pms);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
