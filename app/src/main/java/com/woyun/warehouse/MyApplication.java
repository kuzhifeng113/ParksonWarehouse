package com.woyun.warehouse;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.fm.openinstall.OpenInstall;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.woyun.httptools.HSNetToolsApplication;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.receive.MyMessageIntentService;
import com.woyun.warehouse.utils.GlideImageLoader;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;

import java.util.ArrayList;

import cn.udesk.UdeskSDKManager;

public class MyApplication extends HSNetToolsApplication {
    private static final String TAG = "MyApplication";
    public static Context context;
    private ArrayList<Activity> allActivities;
    private static MyApplication instance ;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        initCloudChannel(this);
        initImagePicker();
        WbSdk.install(this, new AuthInfo(this, Constant.WBAPP_KEY, Constant.REDIRECT_URL, Constant.SCOPE));
        if (isMainProcess()) {
            OpenInstall.init(this);
        }
    }

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    public void closeActivity(){
        try {
            for (Activity activity : allActivities) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 初始化云推送通道
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        // 创建notificaiton channel
        this.createNotificationChannel();
        PushServiceFactory.init(applicationContext);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(TAG, "init cloudchannel success");
                Log.e(TAG, "onSuccess:Tuisong "+pushService.getDeviceId());
                SPUtils.getInstance(applicationContext).put(Constant.USER_DEVICE_ID,pushService.getDeviceId());
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.e(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        pushService.setPushIntentService(MyMessageIntentService.class);
        MiPushRegister.register(applicationContext, "2882303761517713329", "5741771342329"); // 初始化小米辅助推送
        HuaWeiRegister.register(applicationContext); // 接入华为辅助推送
    }

    /**
     * 是否是主线程
     * @return
     */
    public boolean isMainProcess() {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return getApplicationInfo().packageName.equals(appProcess.processName);
            }
        }
        return false;
    }

    /**
     * 推动 8.0 以上通知
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }


    /** 图片选择器 **/
    private void initImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(false);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
