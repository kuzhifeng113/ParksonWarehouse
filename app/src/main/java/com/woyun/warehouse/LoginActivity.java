package com.woyun.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.my.activity.BindPhoneActivity;
import com.woyun.warehouse.utils.GlideBannerImageLoader;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.PushUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SystemUtil;
import com.woyun.warehouse.utils.ToastUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录页面
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.banner)
    Banner mallBanner;
    @BindView(R.id.btn_break)
    Button btnBreak;
    @BindView(R.id.img_wechat)
    ImageView imgWechat;
    @BindView(R.id.img_qq)
    ImageView imgQq;
    @BindView(R.id.ll_di)
    LinearLayout llDi;
    @BindView(R.id.ll_m)
    LinearLayout llM;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private IWXAPI iwxapi;
    public static String uuid = null;
    private boolean mIsExit;
    //qq
    private BaseUiListener mIUiListener;
    private Tencent mTencent;
    private UserInfo userInfo;
    private String nickName,imgUrl,gender,openID;

    List<Integer> imageBanners = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        if (ImmersionBar.hasNavigationBar(this)) {
//            ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
//        } else {
////            Toast.makeText(this, "当前设备没有导航栏或者低于4.4系统", Toast.LENGTH_SHORT).show();
//        }
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(LoginActivity.this);
//        getShareValue();
        //通过WXAPIFactory工厂获取IWXApI的示例
        iwxapi = WXAPIFactory.createWXAPI(this,Constant.WX_APP_ID);
        //将应用的appid注册到微信
        iwxapi.registerApp(Constant.WX_APP_ID);

        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
//        boolean isLogin= (boolean) SPUtils.getInstance(LoginActivity.this).get(Constant.IS_LOGIN,false);
//        if(isLogin){
//            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
        initBannner();
    }

    private void initBannner() {
        imageBanners.clear();
        imageBanners.add(R.mipmap.banner_one);
        imageBanners.add(R.mipmap.banner_two);
        imageBanners.add(R.mipmap.banner_three);
        mallBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        mallBanner.setImageLoader(new GlideBannerImageLoader());
        //设置图片集合
        mallBanner.setImages(imageBanners);
        //设置banner动画效果
        mallBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mallBanner.isAutoPlay(true);
        //设置轮播时间
        mallBanner.setDelayTime(3000);
//        //设置指示器位置（当banner模式中有指示器时）
//        mallBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mallBanner.start();


    }

    /**
     * QQ
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

//    /**
//     * 获取分享的值
//     */
//    private void getShareValue() {
//        OpenInstall.getInstall(new AppInstallAdapter() {
//            @Override
//            public void onInstall(AppData appData) {
//                //获取渠道数据
//                String channelCode = appData.getChannel();
//                //获取自定义数据
//                String bindData = appData.getData();
//                try {
//                    if (!TextUtils.isEmpty(bindData)) {
//                        Log.e("OpenInstall获取数据", "getInstall : installData = " + appData.toString());
//                        JSONObject object = new JSONObject(bindData);
//                        String shareValue = object.getString("sharekey");
//                        SPUtils.getInstance(LoginActivity.this).put(Constant.SHARE_KEY, shareValue);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
////                ToastUtils.getInstanc(SplashActivity.this).showToast(TAG+"========"+bindData);
//            }
//        });
//        //用户注册成功后调用
////        OpenInstall.reportRegister();
//    }

    @OnClick({R.id.btn_break, R.id.img_wechat, R.id.img_qq, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_break:
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.img_wechat:
                wxLogin();
                break;
            case R.id.img_qq:
                mIUiListener=new BaseUiListener();
                mTencent.login(LoginActivity.this,"all", mIUiListener);
                break;
            case R.id.btn_login:
                Intent intentLogin=new Intent(LoginActivity.this,SMSCodeActivity.class);
                startActivity(intentLogin);
                break;
        }
    }

    /** 微信登录 **/
    private void wxLogin() {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(LoginActivity.this, "未安装微信客户端，请先下载",
                    Toast.LENGTH_LONG).show();
            return;
        }
        SPUtils.getInstance(LoginActivity.this).put(Constant.USER_WX_TYPE,0);
        uuid = UUID.randomUUID().toString();
        final SendAuth.Req req = new SendAuth.Req();
        //snsapi_base属于基础接口，若应用已拥有其它scope权限，则默认拥有snsapi_base的权限。
        // 使用snsapi_base可以让移动端网页授权绕过跳转授权登录页请求用户授权的动作，直接跳转第三方网页带上授权临时票据（code），
        // 但会使得用户已授权作用域（scope）仅为snsapi_base，从而导致无法获取到需要用户授权才允许获得的数据和基础功能
        req.scope = "snsapi_userinfo";
        req.state = uuid;
        Log.e(TAG, "wxLogin: " + uuid);
        iwxapi.sendReq(req);
    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response.toString());
            JSONObject obj = (JSONObject) response;
            try {
                openID = obj.getString("openid");
                Log.e(TAG, "onComplete: ===="+openID );
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                userInfo = new UserInfo(getApplicationContext(),qqToken);
                userInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"QQ登录成功"+response.toString());
                        parseQQInfo(response.toString());
                        loginReq(nickName,imgUrl,openID);

                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.e(TAG, "onError: "+uiError.errorMessage );
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }


    private void parseQQInfo(String result){
        try {
            JSONObject object=new JSONObject(result);
            nickName=object.getString("nickname");
            imgUrl=object.getString("figureurl_qq_2");
            gender=object.getString("gender");

            Log.e(TAG, "parseQQInfo: "+nickName +"\n"+imgUrl +"\n"+gender);
//
            SPUtils.getInstance(LoginActivity.this).put(Constant.USER_NICK_NAME,nickName);
            SPUtils.getInstance(LoginActivity.this).put(Constant.USER_AVATAR,imgUrl);
            if(gender.equals("男")){
                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_SEX,1);//1 男 2女 0未知
            }else{
                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_SEX,2);//1 男
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void loginReq(String nickNamee, String avatar, String openIDD){
        ModelLoading.getInstance(LoginActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("platfrom","AND");
            params.put("loginType", 2);
            params.put("smsCode","");
            params.put("sex",SPUtils.getInstance(LoginActivity.this).get("sex",0));
            params.put("nickname",nickNamee);
            params.put("mobile","");
            params.put("fuserid", SPUtils.getInstance(LoginActivity.this).get(Constant.SHARE_KEY, ""));
            params.put("avatar",avatar);//头像地址
            params.put("userid",openIDD.toLowerCase());
            Log.e(TAG, "loginReq: ==="+openIDD.toLowerCase());
            params.put("deviceId", SPUtils.getInstance(LoginActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("deviceAlias",SPUtils.getInstance(LoginActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("osVersion", SystemUtil.getSystemVersion());
            RequestInterface.requestLogin(this, params, TAG, ReqConstance.I_USER_LOGIN, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(LoginActivity.this).closeLoading();
                    if(code==0){
                        if(jsonArray.length()>0){
                            try {
                                Gson gson=new Gson();
                                JSONObject object= (JSONObject) jsonArray.get(0);
                                UserInfoBean userInfoBean= gson.fromJson(object.toString(), UserInfoBean.class);
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_INFO_BEAN,object.toString());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.TOKEN,userInfoBean.getToken());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_IS_REAL,userInfoBean.isIsReal());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_IS_VIP,userInfoBean.isIsVip());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_ID,userInfoBean.getUserid());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_AVATAR,userInfoBean.getAvatar());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_NICK_NAME,userInfoBean.getNickname());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_SEX,userInfoBean.getSex());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_MOBILE,userInfoBean.getMobile());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_IS_AGENT,userInfoBean.isIsAgent());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_TWO_PWD,userInfoBean.getPwd());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_WX,userInfoBean.getWx());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_ZFB,userInfoBean.getZfb());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_ZFB_NAME,userInfoBean.getZfbname());
                                SPUtils.getInstance(LoginActivity.this).put(Constant.USER_INVITE_CODE,userInfoBean.getInviteCode());
                                PushUtils.setAddlias(userInfoBean.getDeviceAlias());

                                if(TextUtils.isEmpty(userInfoBean.getMobile())){
                                    Intent intent=new Intent(LoginActivity.this,BindPhoneActivity.class);
                                    startActivity(intent);
//                                finish();
                                }else{
                                    SPUtils.getInstance(LoginActivity.this).put(Constant.IS_LOGIN,true);
                                    Intent main=new Intent(LoginActivity.this,MainActivity.class);
                                    startActivity(main);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            //第三方登录要去绑定手机号
                            Intent intent=new Intent(LoginActivity.this,BindPhoneActivity.class);
                            intent.putExtra("login_type",2);
                            intent.putExtra("qqopne_id",openIDD);
                            intent.putExtra("nick_name",nickNamee);
                            intent.putExtra("avatar",avatar);
                            startActivity(intent);
//                            finish();
                        }

                    }else{
                        ToastUtils.getInstanc(LoginActivity.this).showToast(msg);
                    }
                }
                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(LoginActivity.this).closeLoading();
//                    loginReq(nickName,imgUrl,openID);
                    ToastUtils.getInstanc(LoginActivity.this).showToast("登录失败请重试...");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                this.finish();

            } else {
                Toast.makeText(LoginActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
                mIsExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mIsExit = false;
                    }
                }, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
