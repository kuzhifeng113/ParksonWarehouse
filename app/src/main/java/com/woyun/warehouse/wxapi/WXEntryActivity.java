package com.woyun.warehouse.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import com.google.gson.Gson;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.SMSCodeActivity;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.event.ShareEvent;
import com.woyun.warehouse.baseparson.event.WxUserInfoEvent;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.bean.WxAccessResult;
import com.woyun.warehouse.bean.WxUserInfoResult;
import com.woyun.warehouse.grabbuy.activity.GrabDetailActivity;
import com.woyun.warehouse.my.activity.BindAccountActivity;
import com.woyun.warehouse.my.activity.BindPhoneActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.PushUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SystemUtil;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import udesk.org.jivesoftware.smackx.xevent.packet.MessageEvent;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "WxEntryActivity";
    /**
     * 微信登录相关
     */
    private IWXAPI iwxapi;
    SendAuth.Resp response;
    Gson gson = new Gson();
    private CloudPushService mPushService;
    private ModelLoading modelLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(WXEntryActivity.this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPushService = PushServiceFactory.getCloudPushService();
        modelLoading=new ModelLoading(WXEntryActivity.this);

        //通过WXAPIFactory工厂获取IWXApI的示例
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID,true);
        //将应用的appid注册到微信
        iwxapi.registerApp(Constant.WX_APP_ID);
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
//        try {
//            boolean result =  api.handleIntent(getIntent(), this);
//            if(!result){
//                Log.e(TAG,"参数不合法，未被SDK处理，退出");
//                finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        iwxapi.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {
        Log.e(TAG, "onReq: "+baseReq.toString() );
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.e(TAG, "onResp微信:=========== " +resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {//分享
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    if(resp.transaction.contains("timewebpage")){//限时抢购商品分享
                        Log.e(TAG, "onResp:限时抢购商品分享 ");
                        EventBus.getDefault().post(new ShareEvent(true));
                    }
                    finish();

                    break;
            }
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                response = (SendAuth.Resp) resp;
                String code = response.code;
                Log.e(TAG,"微信==CODE==="+code);
                requestByAccessToken(code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                ToastUtils.getInstanc(WXEntryActivity.this).showToast("取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                ToastUtils.getInstanc(WXEntryActivity.this).showToast("拒绝");
                finish();
                break;
            case BaseResp.ErrCode.ERR_BAN:
                ToastUtils.getInstanc(WXEntryActivity.this).showToast("签名不正确");
                finish();
            default:
                break;
        }
    }

    /**
     * 第一步：请求CODE
     * 第二步：通过code获取access_token
     */
    private void requestByAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("appid", Constant.WX_APP_ID)
                .add("secret",Constant.WX_APP_SECRET)
                .add("code",code)
                .add("grant_type","authorization_code")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "okhttp onFailure: token " + e.getMessage());
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, response.protocol() + " " +response.code() + " " + response.message());
                if(response.code()==200){
//                    Log.e(TAG, "okhttp  onResponse: " + response.body().string());
                    final String result = response.body().string();
                            WxAccessResult access = gson.fromJson(result, WxAccessResult.class);
                            String access_token = access.getAccess_token();
                            String openid = access.getOpenid();
                            getUserInfoByToken(access_token, openid);

                }

            }
        });


//        final RequestParams params = new RequestParams(url);
//        Log.e("TAG", code);
//        params.addBodyParameter("appid", Constant.WX_APP_ID);
//        params.addBodyParameter("secret", Constant.WX_APP_SECRET);
//        params.addBodyParameter("code", code);
//        params.addBodyParameter("grant_type", "authorization_code");
//        x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.e(TAG, "onSuccess Token: "+result );
//                WxAccessResult access = gson.fromJson(result, WxAccessResult.class);
//                String access_token = access.getAccess_token();
//                String openid = access.getOpenid();
//                getUserInfoByToken(access_token, openid);
//            }
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//            }
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//            @Override
//            public void onFinished() {
//            }
//        });
    }


    /**
     * 通过access_token open_id 拿到个人信息
     * @param access_token
     * @param openid
     */
    private void getUserInfoByToken(String access_token, final String openid) {
        final String url = "https://api.weixin.qq.com/sns/userinfo";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("access_token", access_token)
                .add("openid",openid)
                .add("lang","zh_CN")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "okhttp onFailure:getUserInfo" + e.getMessage());
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e(TAG, "okhttp  onResponse: " + response.body().string());
                final WxUserInfoResult user = gson.fromJson(response.body().string(), WxUserInfoResult.class);
                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_NICK_NAME,user.getNickname());
                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_AVATAR,user.getHeadimgurl());
                if(user.getSex()==1){
                    SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_SEX,user.getSex());//1 男 0女   -----> 1男 2女 0 未知
                }else if(user.getSex()==0){
                    SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_SEX,2);
                }else{
                    SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_SEX,0);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int wxType= (int) SPUtils.getInstance(WXEntryActivity.this).get(Constant.USER_WX_TYPE,0);
                        Log.e(TAG, "run: wxType==="+wxType );
                        if(wxType==0){
                            loginReq(user.getNickname(),user.getHeadimgurl(),openid);
                        }else{
                            EventBus.getDefault().post(new WxUserInfoEvent(user.getNickname(),openid));
                            finish();
                        }
                    }
                });
            }
        });
    }

    /**
     * 登陆
     * @param nickName
     * @param avatar
     * @param openID
     */
    private void loginReq(String nickName, String avatar, String openID){
        ModelLoading.getInstance(WXEntryActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("platfrom","AND");
            params.put("loginType", 1);
            params.put("smsCode","");
            params.put("sex",SPUtils.getInstance(WXEntryActivity.this).get(Constant.USER_SEX,0));
            params.put("nickname",nickName);
            params.put("mobile","");
            params.put("fuserid",SPUtils.getInstance(WXEntryActivity.this).get(Constant.SHARE_KEY,""));
            params.put("avatar",avatar);
            params.put("userid",openID);
            params.put("deviceId", SPUtils.getInstance(WXEntryActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("deviceAlias",SPUtils.getInstance(WXEntryActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("osVersion", SystemUtil.getSystemVersion());
            RequestInterface.requestLogin(this, params, TAG, ReqConstance.I_USER_LOGIN, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(WXEntryActivity.this).closeLoading();
                    if(code==0 ){
                        if(jsonArray.length()>0){
                            try {
                                Gson gson=new Gson();
                                JSONObject object= (JSONObject) jsonArray.get(0);
                                UserInfoBean userInfoBean= gson.fromJson(object.toString(), UserInfoBean.class);
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_INFO_BEAN,object.toString());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.TOKEN,userInfoBean.getToken());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_IS_REAL,userInfoBean.isIsReal());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_IS_VIP,userInfoBean.isIsVip());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_ID,userInfoBean.getUserid());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_AVATAR,userInfoBean.getAvatar());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_NICK_NAME,userInfoBean.getNickname());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_SEX,userInfoBean.getSex());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_MOBILE,userInfoBean.getMobile());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_IS_AGENT,userInfoBean.isIsAgent());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_TWO_PWD,userInfoBean.getPwd());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_WX,userInfoBean.getWx());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_ZFB,userInfoBean.getZfb());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_ZFB_NAME,userInfoBean.getZfbname());
                                SPUtils.getInstance(WXEntryActivity.this).put(Constant.USER_INVITE_CODE,userInfoBean.getInviteCode());

                                PushUtils.setAddlias(userInfoBean.getDeviceAlias());
                                if(TextUtils.isEmpty(userInfoBean.getMobile())){
                                    Intent intent=new Intent(WXEntryActivity.this,BindPhoneActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    SPUtils.getInstance(WXEntryActivity.this).put(Constant.IS_LOGIN,true);
                                    Intent main=new Intent(WXEntryActivity.this,MainActivity.class);
                                    startActivity(main);
                                    MyApplication.getInstance().closeActivity();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            //第三方登录要去绑定手机号
                            Intent intent=new Intent(WXEntryActivity.this,BindPhoneActivity.class);
                            intent.putExtra("login_type",1);
                            intent.putExtra("wxopne_id",openID);
                            intent.putExtra("nick_name",nickName);
                            intent.putExtra("avatar",avatar);
                            startActivity(intent);
                            finish();
                        }

                    }else{
                        ToastUtils.getInstanc(WXEntryActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(WXEntryActivity.this).closeLoading();
                    ToastUtils.getInstanc(WXEntryActivity.this).showToast("登录失败请重试~~~");
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
