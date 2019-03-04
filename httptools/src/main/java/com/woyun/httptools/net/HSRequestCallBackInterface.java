package com.woyun.httptools.net;

import org.json.JSONArray;

/**
 * Created by Aosen
 */
public interface HSRequestCallBackInterface {

    /**
     * 请求成功数据返回
     * @param funcID
     * @param reqID
     * @param reqToken
     * @param responseMessage
     * @param responseCode
     * @param responseData
     */
    public void requestSuccess(int funcID,int reqID,String reqToken, String responseMessage, int responseCode, JSONArray responseData);

    /**
     * 请求异常-报错
     * @param responseMessage
     * @param responseCode
     */
    public void requestError(String responseMessage, int responseCode);
}
