package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.SMSCodeActivity;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.PushUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SystemUtil;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.wxapi.WXEntryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 绑定手机号
 */
public class BindPhoneActivity extends BaseActivity {
    private static final String TAG = "BindPhoneActivity";
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.img_go_main)
    ImageView imgGoMain;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_text)
    TextView tvText;

    private TimeCount time;
    private int loginType;
    private String wxopneId,qqOpenid,nickName,avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(BindPhoneActivity.this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loginType=getIntent().getIntExtra("login_type",0);
        wxopneId=getIntent().getStringExtra("wxopne_id");
        qqOpenid=getIntent().getStringExtra("qqopne_id");
        nickName=getIntent().getStringExtra("nick_name");
        avatar=getIntent().getStringExtra("avatar");
//        Log.e(TAG, "onCreate: "+loginType );
//        Log.e(TAG, "onCreate: "+wxopneId );
//        Log.e(TAG, "onCreate: "+nickName );
//        Log.e(TAG, "onCreate: "+avatar );
//        Log.e(TAG, "onCreate: "+ qqOpenid);
        initData();
    }

    private void initData() {
        time = new TimeCount(60000, 1000);
        editCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    goMain();
                }
                return false;
            }
        });
    }

    private void goMain() {
        if (editPhone.getText().toString().trim().length() != 11) {
            ToastUtils.getInstanc(BindPhoneActivity.this).showToast("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
            ToastUtils.getInstanc(BindPhoneActivity.this).showToast("请输入验证码");
            return;
        }
        bindPhone(editPhone.getText().toString().trim(), editCode.getText().toString().trim());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    /**
     * 绑定手机号
     *
     * @param
     */
    private void bindPhone(String mob, String code) {
        time.start();
        ModelLoading.getInstance(BindPhoneActivity.this).showLoading("", true);
//        modelLoading.showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid",wxopneId);
            params.put("loginType",loginType);
            params.put("mob", mob);
            params.put("code", code);
            params.put("wxlogin", wxopneId);
            params.put("qqlogin", qqOpenid);
            params.put("platfrom", "AND");
            params.put("sex",SPUtils.getInstance(BindPhoneActivity.this).get(Constant.USER_SEX,0));
            params.put("nickname",nickName);
            params.put("fuserid",SPUtils.getInstance(BindPhoneActivity.this).get(Constant.SHARE_KEY,""));
            params.put("avatar",avatar);
            params.put("deviceId", SPUtils.getInstance(BindPhoneActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("deviceAlias",SPUtils.getInstance(BindPhoneActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("osVersion", SystemUtil.getSystemVersion());
            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_CHANAGE_MOB, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(BindPhoneActivity.this).closeLoading();
                    ToastUtils.getInstanc(BindPhoneActivity.this).showToast(msg);
                    if (code == 0) {
                        try {
                            Gson gson=new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            UserInfoBean userInfoBean= gson.fromJson(object.toString(), UserInfoBean.class);
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_INFO_BEAN,object.toString());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.TOKEN,userInfoBean.getToken());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_IS_REAL,userInfoBean.isIsReal());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_IS_VIP,userInfoBean.isIsVip());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_ID,userInfoBean.getUserid());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_AVATAR,userInfoBean.getAvatar());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_NICK_NAME,userInfoBean.getNickname());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_SEX,userInfoBean.getSex());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_MOBILE,userInfoBean.getMobile());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_IS_AGENT,userInfoBean.isIsAgent());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_TWO_PWD,userInfoBean.getPwd());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_WX,userInfoBean.getWx());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_ZFB,userInfoBean.getZfb());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_ZFB_NAME,userInfoBean.getZfbname());
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.USER_INVITE_CODE,userInfoBean.getInviteCode());
                            PushUtils.setAddlias(userInfoBean.getDeviceAlias());
                            //绑定手机才算登录
                            SPUtils.getInstance(BindPhoneActivity.this).put(Constant.IS_LOGIN,true);
                            Intent intent = new Intent(BindPhoneActivity.this, MainActivity.class);
                            startActivity(intent);
                            MyApplication.getInstance().closeActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(BindPhoneActivity.this).closeLoading();
                    time.cancel();
//                    time.onFinish();
//                    loginReq(nickName,imgUrl,openID);
                    ToastUtils.getInstanc(BindPhoneActivity.this).showToast("获取验证码失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取绑定手机验证码
     *
     * @param phone
     */
    private void requestCode(String phone) {
        time.start();
        ModelLoading.getInstance(BindPhoneActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("mob", phone);
            if(!TextUtils.isEmpty(wxopneId)){
                params.put("userid",wxopneId);
            }
            if(!TextUtils.isEmpty(qqOpenid)){
                params.put("userid",qqOpenid);
            }

            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_GET_CHANAGE_MOB_VALIDATE_CODE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(BindPhoneActivity.this).closeLoading();
                    if (code == 0) {

                    } else {
                        ToastUtils.getInstanc(BindPhoneActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(BindPhoneActivity.this).closeLoading();
                    ToastUtils.getInstanc(BindPhoneActivity.this).showToast("获取验证码失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tvSendCode.setClickable(false);
            tvSendCode.setText("" + millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            tvSendCode.setText("重新发送");
            tvSendCode.setClickable(true);
        }
    }

    @OnClick({R.id.tv_send_code, R.id.img_go_main,R.id.tv_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                requestCode(editPhone.getText().toString().trim());
                break;
            case R.id.img_go_main:
                goMain();
                break;
            case R.id.tv_text:
                LogUtils.e(TAG, "onViewClicked: ====" );
                ModelLoading.getInstance(BindPhoneActivity.this).showLoading("",true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ModelLoading.getInstance(BindPhoneActivity.this).closeLoading();
                    }
                },2000);
                break;
        }
    }
}
