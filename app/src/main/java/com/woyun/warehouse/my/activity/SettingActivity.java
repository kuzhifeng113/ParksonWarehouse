package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.callback.OnCancelListener;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.utils.APKVersionCodeUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.BaseDialogg;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {
    private static final String TAG = "SettingActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.rl_account_bind)
    RelativeLayout rlAccountBind;
    @BindView(R.id.rl_real_name)
    RelativeLayout rlRealName;
    @BindView(R.id.rl_account_safe)
    RelativeLayout rlAccountSafe;
    @BindView(R.id.img_update)
    ImageView imgUpdate;
    @BindView(R.id.tv_version_name)
    TextView tvVersionName;
    @BindView(R.id.rl_check_update)
    RelativeLayout rlCheckUpdate;
    @BindView(R.id.rl_pro_advice)
    RelativeLayout rlAdvice;
    @BindView(R.id.rl_about_bsc)
    RelativeLayout rlAboutBsc;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;
    @BindView(R.id.view_red_upate)
    View viewRedUpate;


    private DownloadBuilder builder;//升级下载
    private boolean isUpdate;//是否更新
    private int updateStatus;
    private String downUrl, content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        isUpdate = (boolean) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_FLAG, false);
        updateStatus = (int) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_FLAG_STATUS, 0);
        downUrl = (String) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_DOWN_URL, "");
        content = (String) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_CONTENT, "");
        tvVersionName.setText("V" + APKVersionCodeUtils.getVerName(SettingActivity.this));
        MyApplication.getInstance().addActivity(SettingActivity.this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (isUpdate && updateStatus == 1) {
            viewRedUpate.setVisibility(View.VISIBLE);
        } else {
            viewRedUpate.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    @OnClick({R.id.rl_user_info, R.id.rl_account_bind, R.id.rl_account_safe, R.id.rl_check_update, R.id.rl_pro_advice, R.id.rl_about_bsc, R.id.btn_login_out,R.id.rl_real_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_user_info://个人资料
                Intent userInfo = new Intent(SettingActivity.this, UserInfoActivity.class);
                startActivity(userInfo);
                break;
            case R.id.rl_account_bind://账号绑定
                Intent bind = new Intent(SettingActivity.this, BindAccountActivity.class);
                startActivity(bind);
                break;
            case R.id.rl_account_safe://账户与安全
//                Intent account=new Intent(SettingActivity.this,AccountSafeActivity.class);
                Intent account = new Intent(SettingActivity.this, TwoPassWordActivity.class);
                startActivity(account);
                break;
            case R.id.rl_check_update://检查更新
                checkUpdate();
                break;
            case R.id.rl_pro_advice://问题与反馈
                Intent pro = new Intent(SettingActivity.this, MyWebViewActivity.class);
                pro.putExtra("isPro", true);
                pro.putExtra("web_url", Constant.WEB_PROBLEM);
                startActivity(pro);
                break;
            case R.id.rl_about_bsc://关于我们
                Intent about = new Intent(SettingActivity.this, MyWebViewActivity.class);
                about.putExtra("web_url", Constant.WEB_ABOUT_ME);
                startActivity(about);
//                Intent about = new Intent(SettingActivity.this, AboutMeActivity.class);
//                startActivity(about);
                break;
            case R.id.rl_real_name://实名认证
                Intent realName = new Intent(SettingActivity.this, RealNameActivity.class);
                startActivity(realName);
                break;
            case R.id.btn_login_out://退出登录
                MyApplication.getInstance().closeActivity();
                SPUtils.getInstance(SettingActivity.this).remove(Constant.IS_LOGIN);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_ID);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_INFO_BEAN);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.TOKEN);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_AVATAR);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_IS_VIP);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_IS_AGENT);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_TWO_PWD);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_ZFB_NAME);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_ZFB);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_WX);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_WX_NAME);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.PAY_TYPE);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_DEFAULT_ADDRESS);
                SPUtils.getInstance(SettingActivity.this).remove(Constant.USER_MOBILE);

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);
                MyApplication.getInstance().closeActivity();
                break;
        }
    }


    /**
     * 检查更新
     */
    private void checkUpdate() {
        if (isUpdate && updateStatus == 1) {
            showVersionDialog();
        } else {
            ToastUtils.getInstanc(SettingActivity.this).showToast("当前已是最新版本");
        }
    }

    private void showVersionDialog() {
        builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(crateUIData());
        builder.setCustomVersionDialogListener(createCustomDialogOne());
        builder.setForceRedownload(true);//强制重新下载apk 无论本地是否缓存
        builder.setNotificationBuilder(createCustomNotification());//自定义通知栏
        String address = Environment.getExternalStorageDirectory() + "/DY/VersionPath/";
        builder.setDownloadAPKPath(address);//设置下载地址
        builder.setShowDownloadingDialog(false);//下载框

        builder.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel() {

            }
        });
        builder.executeMission(SettingActivity.this);
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
        String mUrl = (String) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_DOWN_URL, "");
        uiData.setDownloadUrl(mUrl);
        uiData.setContent((String) SPUtils.getInstance(SettingActivity.this).get(Constant.UPDATE_CONTENT, ""));
        return uiData;
    }
}
