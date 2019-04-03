package com.woyun.warehouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.fm.openinstall.OpenInstall;
import com.fm.openinstall.listener.AppWakeUpAdapter;
import com.fm.openinstall.model.AppData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.adapter.ViewPagerAdapter;
import com.woyun.warehouse.baseparson.event.UnReadMessEvent;
import com.woyun.warehouse.bean.UnReadNumBean;
import com.woyun.warehouse.cart.CartFragment;
import com.woyun.warehouse.mall.MallFragmentTwo;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.my.MyFragment;
import com.woyun.warehouse.utils.BottomNavigationViewHelper;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.view.BaseDialogg;
import com.woyun.warehouse.view.ViewPagerSlide;
import com.woyun.warehouse.vip.VipFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.udesk.UdeskSDKManager;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.vp)
    ViewPagerSlide viewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private ViewPagerAdapter viewPagerAdapter;
    private boolean mIsExit;

    private DownloadBuilder builder;//升级下载
    private boolean isLogin;
    private String shareGoodsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        checkUpdate();
        //初始化udesk
        UdeskSDKManager.getInstance().initApiKey(getApplicationContext(), Constant.UDESK_DOMAN,
                Constant.UDESK_KEY, Constant.UDESK_APPID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLogin = (boolean) SPUtils.getInstance(MainActivity.this).get(Constant.IS_LOGIN, false);
        if (viewPager.getCurrentItem() == 0) {//通知MallFragment 更新消息状态
            Log.e(TAG, "onResume:Mall============== ");
            getNoReadNum(loginUserId);
        }
        shareGoodsId = (String) SPUtils.getInstance(MainActivity.this).get(Constant.SHARE_GOODS_ID, "");
        Log.e(TAG, "onCreate: " + shareGoodsId);
        if (!TextUtils.isEmpty(shareGoodsId)) {
//            Intent intent=new Intent(MainActivity.this, GoodsDetailActivity.class);
            Intent intent = new Intent(MainActivity.this, GoodsDetailNativeActivity.class);
            intent.putExtra("goods_id", Integer.valueOf(shareGoodsId));
            intent.putExtra("from_id", 2);
            startActivity(intent);
            SPUtils.getInstance(MainActivity.this).remove(Constant.SHARE_GOODS_ID);
        }
//        String share= (String) SPUtils.getInstance(MainActivity.this).get(Constant.SHARE_KEY,"");
//        ToastUtils.getInstanc(MainActivity.this).showToast(share);
    }

    private void getNoReadNum(String userId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.sysPrefix(MainActivity.this, params, TAG, ReqConstance.I_UNREAD_MESSAGE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0 && jsonArray.length() > 0) {
                        Gson gson = new Gson();
                        List<UnReadNumBean> unReadNumBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<UnReadNumBean>>() {
                        }.getType());

                        int billUnread = unReadNumBeans.get(0).getBillUnreadNum();
                        int sysUnread = unReadNumBeans.get(0).getSysUnreadNum();
                        int kefuUnRead = 0;
                        if (isLogin) {
                            kefuUnRead = UdeskSDKManager.getInstance().getCurrentConnectUnReadMsgCount(getApplicationContext(),
                                    loginUserId);
                        }

                        Log.e(TAG, "requestSuccess:消息 " + billUnread + sysUnread + kefuUnRead);
                        EventBus.getDefault().post(new UnReadMessEvent(billUnread + sysUnread+kefuUnRead));
                    }
                }

                @Override
                public void requestError(String s, int i) {
//                    ToastUtils.getInstanc(MainActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode2(bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);//iconTint为null
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setSlide(false);
        List<Fragment> list = new ArrayList<>();
//        list.add(new VoteFragment());
        list.add(new MallFragmentTwo());
        list.add(new VipFragment());
        list.add(new CartFragment());
        list.add(new MyFragment());
        viewPagerAdapter.setList(list);

        viewPager.setCurrentItem(0);

    }

    private void initData() {
        //获取唤醒参数
        OpenInstall.getWakeUp(getIntent(), wakeUpAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 此处要调用，否则App在后台运行时，会无法截获
        OpenInstall.getWakeUp(intent, wakeUpAdapter);
        Log.e(TAG, "onNewIntent: ");
        if (intent != null) {
            boolean toCart = intent.getBooleanExtra("go_cart", false);
            boolean toMoney = intent.getBooleanExtra("go_makemoney", false);
            if (toCart) {
                viewPager.setCurrentItem(2);
            }
            if (toMoney) {
                viewPager.setCurrentItem(1);
            }

        }
    }

    AppWakeUpAdapter wakeUpAdapter = new AppWakeUpAdapter() {
        @Override
        public void onWakeUp(AppData appData) {
            //获取渠道数据
            String channelCode = appData.getChannel();
            //获取绑定数据
            String bindData = appData.getData();
            LogUtils.e(TAG, "getWakeUp : wakeupData = " + appData.toString());
        }
    };


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            menuItem = item;
            switch (item.getItemId()) {
                case R.id.navigation_mall:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_vip:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_cart:
                    if (!isLogin) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return false;
                    } else {
                        viewPager.setCurrentItem(2);
                        return true;
                    }

                case R.id.navigation_my:
                    if (!isLogin) {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return false;
                    } else {
                        viewPager.setCurrentItem(3);
                        return true;
                    }

            }
            return false;
        }
    };


    /**
     * xml 写属性改变图片文字颜色没效果  不写的默认找 colorPrimary 颜色值
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        wakeUpAdapter = null;
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
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
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

    /**
     * 检查更新
     */
    private void checkUpdate() {
        boolean isUpdate = (boolean) SPUtils.getInstance(MainActivity.this).get(Constant.UPDATE_FLAG, false);
        int status = (int) SPUtils.getInstance(MainActivity.this).get(Constant.UPDATE_FLAG_STATUS, 0);
        if (isUpdate && status == 1) {//有更新
            String endDate = DateUtils.getCurentTime();//当前时间
            String startDate = (String) SPUtils.getInstance(MainActivity.this).get(Constant.UPDATE_TIME, "");
            if (TextUtils.isEmpty(startDate)) {
                showVersionDialog();
                return;
            }
            Log.e(TAG, "endDate: " + endDate);
            Log.e(TAG, "startDate: " + startDate);
            String diffValueHour = DateUtils.getTimeDifference(startDate, endDate, 2);
            Log.e(TAG, "requestSuccess: diffValueHour" + diffValueHour);
            if (Integer.valueOf(diffValueHour) > 24) {//24小时弹窗一次
                showVersionDialog();
            }
        }

    }

    private void showVersionDialog() {
        SPUtils.getInstance(MainActivity.this).put(Constant.UPDATE_TIME, DateUtils.getCurentTime());
        builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(crateUIData());
        builder.setCustomVersionDialogListener(createCustomDialogOne());
        builder.setForceRedownload(true);//强制重新下载apk 无论本地是否缓存
        builder.setNotificationBuilder(createCustomNotification());//自定义通知栏
        String address = Environment.getExternalStorageDirectory() + "/BSC/VersionPath/";
        builder.setDownloadAPKPath(address);//设置下载地址
        builder.setShowDownloadingDialog(false);//下载框

        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        builder.executeMission(MainActivity.this);
    }

    /**
     * 1.8 jdk
     * 自定义的dialog UI参数展示，使用versionBundle
     *
     * @return
     */
    private CustomVersionDialogListener createCustomDialogOne() {
        return (context, versionBundle) -> {
            BaseDialogg baseDialogg = new BaseDialogg(context, R.style.BaseDialog, R.layout.popup_version_update);
            TextView textView = baseDialogg.findViewById(R.id.tv_content);
            textView.setText(versionBundle.getContent());
            return baseDialogg;
        };
    }

    private NotificationBuilder createCustomNotification() {
        return NotificationBuilder.create()
                .setRingtone(true)
                .setIcon(R.mipmap.ic_app_logo)
                .setTicker("百盛仓")
                .setContentTitle("百盛仓")
                .setContentText(getString(R.string.custom_content_text));
    }

    //标题 downUrl  content
    private UIData crateUIData() {
        UIData uiData = UIData.create();
        uiData.setTitle(getString(R.string.update_title));
        String mUrl = (String) SPUtils.getInstance(MainActivity.this).get(Constant.UPDATE_DOWN_URL, "");
        uiData.setDownloadUrl(mUrl);
        uiData.setContent((String) SPUtils.getInstance(MainActivity.this).get(Constant.UPDATE_CONTENT, ""));
        return uiData;
    }


}
