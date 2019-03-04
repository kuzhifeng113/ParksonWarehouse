package com.woyun.warehouse;

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
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.PushUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SystemUtil;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 手机登录
 */
public class SMSCodeActivity extends BaseActivity {
    private static final String TAG = "SMSCodeActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.img_go_main)
    ImageView imgGoMain;
    @BindView(R.id.tv_text)
    TextView tvText;
    private ModelLoading modelLoading;
    private TimeCount time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smscode);
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(SMSCodeActivity.this);
        modelLoading=new ModelLoading(SMSCodeActivity.this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.tv_send_code, R.id.img_go_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (editPhone.getText().toString().trim().length() != 11) {
                    ToastUtils.getInstanc(SMSCodeActivity.this).showToast("请输入正确手机号");
                    return;
                }
                requestCode(editPhone.getText().toString().trim());

                break;
            case R.id.img_go_main:
                    goMain();
                break;

        }
    }



    /**
     * 获取验证码
     * @param phone
     */
    private void requestCode(String phone){
            modelLoading.showLoading("",true);
            try {
                JSONObject params = new JSONObject();
                params.put("receiveMobile",phone);
                params.put("userid",phone);
                RequestInterface.requestLogin(this, params, TAG, ReqConstance.I_GET_SMS_CODE, 1, new HSRequestCallBackInterface() {
                    @Override
                    public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                        modelLoading.closeLoading();
                        if(code==0){
                            time.start();
                        }else{
                            ToastUtils.getInstanc(SMSCodeActivity.this).showToast(msg);
                        }
                    }
                    @Override
                    public void requestError(String s, int i) {
                        modelLoading.closeLoading();
                        ToastUtils.getInstanc(SMSCodeActivity.this).showToast("获取验证码失败");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    /**
     * 手机号登陆
     * type==0
     */
    private void loginByPhone(String codee,String phonee ){
        Log.e(TAG, "loginByPhone: "+phonee );
        ModelLoading.getInstance(SMSCodeActivity.this).showLoading("",true);
//        modelLoading.showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("platfrom","AND");
            params.put("loginType", 0);
            params.put("smsCode",codee);
            params.put("sex",0);
            params.put("nickname","");
            params.put("mobile",phonee);
            params.put("fuserid",SPUtils.getInstance(SMSCodeActivity.this).get(Constant.SHARE_KEY,""));
            params.put("avatar","");//头像地址
            params.put("userid",phonee);
            params.put("deviceId", SPUtils.getInstance(SMSCodeActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("deviceAlias",SPUtils.getInstance(SMSCodeActivity.this).get(Constant.USER_DEVICE_ID,""));
            params.put("osVersion", SystemUtil.getSystemVersion());
            RequestInterface.requestLogin(this, params, TAG, ReqConstance.I_USER_LOGIN, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(SMSCodeActivity.this).closeLoading();
                    ToastUtils.getInstanc(SMSCodeActivity.this).showToast(msg);
                    if(code==0){
                        try {
                            Gson gson=new Gson();
                            JSONObject object= (JSONObject) jsonArray.get(0);
                            UserInfoBean userInfoBean= gson.fromJson(object.toString(), UserInfoBean.class);
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_INFO_BEAN,object.toString());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.TOKEN,userInfoBean.getToken());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_IS_REAL,userInfoBean.isIsReal());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_IS_VIP,userInfoBean.isIsVip());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_ID,userInfoBean.getUserid());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_AVATAR,userInfoBean.getAvatar());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_NICK_NAME,userInfoBean.getNickname());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_SEX,userInfoBean.getSex());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_MOBILE,userInfoBean.getMobile());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_IS_AGENT,userInfoBean.isIsAgent());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_TWO_PWD,userInfoBean.getPwd());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_WX,userInfoBean.getWx());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_ZFB,userInfoBean.getZfb());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_ZFB_NAME,userInfoBean.getZfbname());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_INVITE_CODE,userInfoBean.getInviteCode());
                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.USER_DEFAULT_ADDRESS,userInfoBean.isDefaultAddress());
                            PushUtils.setAddlias(userInfoBean.getDeviceAlias());

                            SPUtils.getInstance(SMSCodeActivity.this).put(Constant.IS_LOGIN,true);
                            Intent intent = new Intent(SMSCodeActivity.this, MainActivity.class);
                            startActivity(intent);
                            MyApplication.getInstance().closeActivity();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        ToastUtils.getInstanc(SMSCodeActivity.this).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(SMSCodeActivity.this).closeLoading();
//                    loginByPhone(code,phone);
                    ToastUtils.getInstanc(SMSCodeActivity.this).showToast("登录失败，请重试...");
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

    private void goMain() {
        if (editPhone.getText().toString().trim().length() != 11) {
            ToastUtils.getInstanc(SMSCodeActivity.this).showToast("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(editCode.getText().toString().trim())) {
            ToastUtils.getInstanc(SMSCodeActivity.this).showToast("请输入验证码");
            return;
        }

       loginByPhone(editCode.getText().toString().trim(),editPhone.getText().toString().trim());

    }
}
