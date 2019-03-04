package com.woyun.warehouse;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppInstallAdapter;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.woyun.httptools.net.HSNetTools;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.bean.VersionBean;
import com.woyun.warehouse.my.activity.GuideActivity;
import com.woyun.warehouse.utils.APKVersionCodeUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean isLogin;
    private boolean isFirstStart;
    private boolean isFirstOpenInstall;
    private RxPermissions rxPermissions;
    private boolean flag1, flag2, flag3, flag4;//允许全部权限

    private int error = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!isTaskRoot()) {
//            finish();
//            return;
//        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .titleBar(toolbar, false)
                .transparentBar()
                .init();
//        if (ImmersionBar.hasNavigationBar(this)) {
//            ImmersionBar.with(this).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
//        } else {
////            Toast.makeText(this, "当前设备没有导航栏或者低于4.4系统", Toast.LENGTH_SHORT).show();
//        }

        //获取唤醒参数
        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
        isLogin = (Boolean) SPUtils.getInstance(SplashActivity.this).get(Constant.IS_LOGIN, false);
        isFirstStart = (boolean) SPUtils.getInstance(SplashActivity.this).get(Constant.IS_FIRST_START, false);
        isFirstOpenInstall = (boolean) SPUtils.getInstance(SplashActivity.this).get(Constant.IS_FIRST_OPENINSTALL, true);
        Log.e(TAG, "isLogin==: " + isLogin);
        //获取OpenInstall安装数据
        getShareValue();
        getPersmission();


        //用户注册成功后调用
//        OpenInstall.reportRegister();
    }

    private void getPersmission() {
        rxPermissions = new RxPermissions(SplashActivity.this);
        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.READ_PHONE_STATE)
//                , Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                flag1 = true;
                            } else {
                                flag1 = false;
                            }
                        }

                        if (permission.name.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            if (permission.granted) {
                                flag2 = true;
                            } else {
                                flag2 = false;
                            }
                        }

                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE)) {
                            if (permission.granted) {
                                flag3 = true;
                            } else {
                                flag3 = false;
                            }
                        }
//                        if (permission.name.equals(Manifest.permission.CAMERA)) {
//                            if (permission.granted) {
//                                flag4 = true;
//                            } else {
//                                flag4 = false;
//                            }
//                        }
//                        Log.e(TAG, "accept: " + permission.name);
//                        Log.e(TAG, "accept: " + permission.granted);
//                        Log.e(TAG, "flag1: " + flag1);
//                        Log.e(TAG, "flag2: " + flag2);
//                        Log.e(TAG, "flag3: " + flag3);
//                        Log.e(TAG, "flag4: " + flag4);
                        if (flag1 && flag2 && flag3 ) {
                            checkVersionUpdate((String) SPUtils.getInstance(SplashActivity.this).get(Constant.USER_ID, ""));
                        }

//                        if (permission.granted) {
//
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            isGrantedAll = false;
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                            Log.e(TAG, permission.name + " is denied. More info should be provided.");
//                        } else {
//                            isGrantedAll = false;
//                            // 用户拒绝了该权限，并且选中『不再询问』
//                            Log.e(TAG, permission.name + " is denied.");
//                        }
                    }

                });


    }

    /**
     * 获取分享的值
     */
    private void getShareValue() {
        if (isFirstOpenInstall) {
            OpenInstall.getInstall(new AppInstallAdapter() {
                @Override
                public void onInstall(AppData appData) {
                    //获取渠道数据
                    String channelCode = appData.getChannel();
                    //获取自定义数据
                    String bindData = appData.getData();
                    try {
                        if (!TextUtils.isEmpty(bindData)) {
                            Log.e("分享链接数据", "" + appData.toString());
                            JSONObject object = new JSONObject(bindData);
                            if(!object.isNull("share")){
                                String shareValue = object.getString("share");
                                Log.e(TAG, "onInstall:分享 "+shareValue );
                                SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_KEY, shareValue);
                            }
                            if(!object.isNull("goodsId")){
                                String goodsId=object.getString("goodsId");
                                Log.e(TAG, "onInstall:分享 "+goodsId );
                                SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_GOODS_ID, goodsId);
                            }
                            //使用数据后，不想再调用，将isFirst设置为false
                            SPUtils.getInstance(SplashActivity.this).put(Constant.IS_FIRST_OPENINSTALL,false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                ToastUtils.getInstanc(SplashActivity.this).showToast(TAG+"========"+bindData);
                }
            });
        }
        //用户注册成功后调用
//        OpenInstall.reportRegister();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        Log.e(TAG, "onNewIntent: ");
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
            Log.e("OpenInstall 唤醒数据", "getWakeUp : wakeupData = " + appData.toString());
            try {
                if (!TextUtils.isEmpty(bindData)) {
                    Log.e("OpenInstall唤醒数据", "getInstall : installData = " + appData.toString());
                    JSONObject object = new JSONObject(bindData);
                    if(!object.isNull("share")){
                        String shareValue = object.getString("share");
                        Log.e(TAG, "onInstall:唤醒 "+shareValue );
                        SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_KEY, shareValue);
                    }
                    if(!object.isNull("goodsId")){
                        String goodsId=object.getString("goodsId");
                        Log.e(TAG, "onInstall:唤醒 "+goodsId );
                        SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_GOODS_ID, goodsId);
                    }
//                    String shareValue = object.getString("share");
//                    String goodsId=object.getString("goodsId");
//                    Log.e(TAG, "onInstall:唤醒 "+goodsId );
//                    SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_KEY, shareValue);
//                    SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_GOODS_ID, goodsId);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 检查更新
     */
    private void checkVersionUpdate(String userId) {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("verCode", APKVersionCodeUtils.getVersionCode(SplashActivity.this));
            params.put("platform", "AND");
            RequestInterface.sysPrefix(SplashActivity.this, params, TAG, ReqConstance.I_SYS_VERSION, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    if (responseCode == 0 && responseData.length() > 0) {
                        Log.e(TAG, "requestSuccess: checkUpdate");
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = (JSONObject) responseData.get(0);
                            VersionBean bean = gson.fromJson(jsonObject.toString(), VersionBean.class);
                            SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_URL, bean.getShareUrl());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_TITLE, bean.getShareTitle());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_CONTENT, bean.getShareContent());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.SHARE_ICON, bean.getShareIcon());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.UPDATE_FLAG, bean.isIsNew());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.UPDATE_FLAG_STATUS, bean.getStatus());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.UPDATE_DOWN_URL, bean.getUrl());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.UPDATE_CONTENT, bean.getContent());
                            SPUtils.getInstance(SplashActivity.this).put(Constant.UNREAD_NUM, bean.getUnreadNum());
                            final Intent goIntent = new Intent();
//                            if (!isFirstStart) {//是否第一次打开
//                                goIntent.setClass(SplashActivity.this, GuideActivity.class);
//                                goIntent.putExtra("isOpenAdv", bean.isAdvOpen());
//                            } else {
//                                if (bean.isAdvOpen()) {//是否有广告
//                                    String endDate = DateUtils.getCurentTime();//当前时间
//                                    String startDate = (String) SPUtils.getInstance(SplashActivity.this).get(Constant.ADV_TIME, "");
//                                    if (TextUtils.isEmpty(startDate)) {
//                                        SPUtils.getInstance(SplashActivity.this).put(Constant.ADV_TIME, DateUtils.getCurentTime());
//                                        goIntent.setClass(SplashActivity.this, AdvActivity.class);
//                                    }else{//判断是否>24小时
//                                        String diffValueHour = DateUtils.getTimeDifference(startDate, endDate, 2);
//                                        Log.e(TAG, "requestSuccess:diffValueHour== "+diffValueHour );
//                                        //24小时广告一次
//                                        if (Integer.valueOf(diffValueHour) > 24) {
//                                            SPUtils.getInstance(SplashActivity.this).put(Constant.ADV_TIME, DateUtils.getCurentTime());
//                                            goIntent.setClass(SplashActivity.this, AdvActivity.class);
//                                        }else{
//                                            goIntent.setClass(SplashActivity.this, MainActivity.class);
//                                        }
//                                    }
//                                } else {
//                                    goIntent.setClass(SplashActivity.this, MainActivity.class);
//                                }
//                            }
                            //是否登录---进首页
                            goIntent.setClass(SplashActivity.this, MainActivity.class);
//                            if (isLogin) {
//                                goIntent.setClass(SplashActivity.this, MainActivity.class);
//                            } else {
//                                goIntent.setClass(SplashActivity.this, LoginActivity.class);
//                            }
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(goIntent);
                                    finish();
                                }
                            }, 800);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    error++;
                    if (error < 4) {
                        checkVersionUpdate((String) SPUtils.getInstance(SplashActivity.this).get(Constant.USER_ID, ""));
                    } else {
                        ToastUtils.getInstanc(SplashActivity.this).showToast(responseMessage);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HSNetTools.cancleReuqest(TAG);
        ImmersionBar.with(this).destroy();
    }
}
