package com.woyun.warehouse.receive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MessageListActivity;
import com.woyun.warehouse.my.activity.CangCoinActivity;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.my.activity.YuErActivity;
import com.woyun.warehouse.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * 阿里推送---看MyMessageIntentService 类
 */
public class MyMessageReceiver extends MessageReceiver {
    private static final String TAG = "MyMessageReceiver";

    /**
     * 消息接收回调
     * @param context
     * @param cPushMessage  类型，可以获取消息Id、消息标题和内容
     */
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        LogUtils.e(TAG, "onMessage: "+cPushMessage.getContent() );
        LogUtils.e(TAG, "onMessage: "+cPushMessage.getAppId() );
    }

    /**
     * 通知接收回调11111
     * @param context
     * @param title   通知标题
     * @param summary  通知内容
     * @param extraMap  通知额外参数，包括部分系统自带参数：
    _ALIYUN_NOTIFICATION_ID_(V2.3.5及以上):创建通知对应id
    _ALIYUN_NOTIFICATION_PRIORITY_(V2.3.5及以上):创建通知对应id。默认不带，需要通过OpenApi设置
     */
    @Override
    protected void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        super.onNotification(context, title, summary, extraMap);
        LogUtils.e(TAG, "onNotification:通知接收回调 ===="+extraMap.toString() );
    }

    /**
     * 打开通知回调
     * @param context
     * @param title  标题
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        super.onNotificationOpened(context, title, summary, extraMap);
        LogUtils.e(TAG, "onNotificationOpened: "+extraMap.toString() );

        String redirect="";
        try {
            JSONObject object=new JSONObject(extraMap);
            if(!object.isNull("redirect")){
                redirect=object.getString("redirect");
            }
            String type=object.getString("type");
            Log.e(TAG, "onNotificationOpened:type "+type );
            if(type.equals("1")){//订单发货---跳订单详情
                Intent order=new Intent(context, OrderDetailActivity.class);
                order.putExtra("tradeNo",redirect);
                context.startActivity(order);
            }
            if(type.equals("5")){//投票的人有通知---跳转商品详情
//                Intent goods=new Intent(context, GoodsDetailActivity.class);
                Intent goods=new Intent(context, GoodsDetailNativeActivity.class);
                goods.putExtra("goods_id",Integer.valueOf(redirect));
                goods.putExtra("from_id",2);
                context.startActivity(goods);
            }

            //2  3  4 6 9---消息列表
            if(type.equals("2")||type.equals("3")||type.equals("4")||type.equals("6")||type.equals("9")||type.equals("10")){
                Intent goIntent=new Intent(context, MessageListActivity.class);
                goIntent.putExtra("messType",0);//系统消息
                context.startActivity(goIntent);
            }
            if(type.equals("7")){//仓币明细
//                Intent coinDetail=new Intent(context, CangCoinDetailActivity.class);
                Intent coinDetail=new Intent(context, CangCoinActivity.class);
                coinDetail.putExtra("hb_type",1);
                context.startActivity(coinDetail);
            }

            if(type.equals("8")){//余额明细
//                Intent coinDetail=new Intent(context, CangCoinDetailActivity.class);
                Intent coinDetail=new Intent(context, YuErActivity.class);
                coinDetail.putExtra("hb_type",2);
                context.startActivity(coinDetail);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无跳转逻辑通知打开回调
     * @param context
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String s, String s1, String s2) {
        super.onNotificationClickedWithNoAction(context, s, s1, s2);
        LogUtils.e(TAG, "onNotificationClickedWithNoAction: " );
    }

    /**
     * 通知删除
     * @param context
     * @param s  删除通知的Id
     */
    @Override
    protected void onNotificationRemoved(Context context, String s) {
        super.onNotificationRemoved(context, s);
        LogUtils.e(TAG, "onNotificationRemoved: ");
    }

    /**
     * 通知在应用内到达回调
     * @param context
     * @param title 通知标题
     * @param summary 通知内容
     * @param extraMap 通知额外参数
     * @param openType 原本通知打开方式，1：打开APP；2：打开activity；3：打开URL；4：无跳转逻辑
     * @param openActivity 所要打开的activity的名称，仅当openType=2时有效，其余情况为null
     * @param openUrl    所要打开的URL，仅当openType=3时有效，其余情况为null
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title , String summary , Map<String, String> extraMap , int openType , String openActivity , String openUrl ) {
        super.onNotificationReceivedInApp(context, title , summary , extraMap , openType , openActivity , openUrl );
        LogUtils.e(TAG, "onNotificationReceivedInApp: ");
    }
}
