package com.woyun.warehouse.receive;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.sdk.android.push.AliyunMessageIntentService;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MessageListActivity;
import com.woyun.warehouse.my.activity.CangCoinActivity;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.my.activity.YuErActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by liyazhou on 17/8/22.
 * 为避免推送广播被系统拦截的小概率事件,我们推荐用户通过IntentService处理消息互调,接入步骤:
 * 1. 创建IntentService并继承AliyunMessageIntentService
 * 2. 覆写相关方法,并在Manifest的注册该Service
 * 3. 调用接口CloudPushService.setPushIntentService
 * 详细用户可参考:https://help.aliyun.com/document_detail/30066.html#h2-2-messagereceiver-aliyunmessageintentservice
 */

public class MyMessageIntentService extends AliyunMessageIntentService {
    private static final String TAG = "MyIntentService";

    /**
     * 推送通知的回调方法
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
//        ToastUtils.getInstanc(context).showToast("收到一条推送通知"+extraMap.toString());
//        Log.e(TAG,"收到一条推送通知 ： " + title + ", summary:" + summary+"extraMap"+extraMap.toString());
//        MainApplication.setConsoleText("收到一条推送通知 ： " + title + ", summary:" + summary);
    }

    /**
     * 推送消息的回调方法
     * @param context
     * @param cPushMessage
     */
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        Log.e(TAG,"收到一条推送消息 ： " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
//        MainApplication.setConsoleText(cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    /**
     * 从通知栏打开通知的扩展处理
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationOpened(Context context, String title, String summary, String extraMap) {
//        ToastUtils.getInstanc(context).showToast("open"+extraMap.toString());
        Log.e(TAG,"onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
//        MainApplication.setConsoleText("onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
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

            if(type.equals("12")){// type=12  秒杀抢购
                Intent grabIntent=new Intent(context, MainActivity.class);
                grabIntent.putExtra("grab_time",true);
                context.startActivity(grabIntent);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 无动作通知点击回调。当在后台或阿里云控制台指定的通知动作为无逻辑跳转时,通知点击回调为onNotificationClickedWithNoAction而不是onNotificationOpened
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        Log.e(TAG,"onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
//        MainApplication.setConsoleText("onNotificationClickedWithNoAction ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    /**
     * 通知删除回调
     * @param context
     * @param messageId
     */
    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        Log.e(TAG,"onNotificationRemoved ： " + messageId);
//        MainApplication.setConsoleText("onNotificationRemoved ： " + messageId);
    }

    /**
     * 应用处于前台时通知到达回调。注意:该方法仅对自定义样式通知有效,相关详情请参考https://help.aliyun.com/document_detail/30066.html#h3-3-4-basiccustompushnotification-api
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     * @param openType
     * @param openActivity
     * @param openUrl
     */
    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        Log.e(TAG,"onNotificationReceivedInApp ： " + " : " + title + " : " + summary + "  " + extraMap + " : " + openType + " : " + openActivity + " : " + openUrl);
//        MainApplication.setConsoleText("onNotificationReceivedInApp ： " + " : " + title + " : " + summary);
    }
}
