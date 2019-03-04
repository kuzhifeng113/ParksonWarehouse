package com.woyun.warehouse.utils;

import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

public class PushUtils {
    private static final String TAG = "PushUtils";
    /**
     * 添加别名接口:CloudPushService.addAlias调用示例
     * 1. 调用接口后,可以通过别名进行推送
     */
    public static void setAddlias(String alias){
        CloudPushService cloudPushService = PushServiceFactory.getCloudPushService();
        cloudPushService.addAlias(alias, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtils.e(TAG, "onSuccess: 设置别名成功" );

            }

            @Override
            public void onFailed(String errorCode, String errorMsg) {
                LogUtils.e(TAG, "onSuccess: 设置别名失败" );
            }
        });
    }
}
