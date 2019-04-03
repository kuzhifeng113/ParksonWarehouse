package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jkb.vcedittext.VerificationAction;
import com.jkb.vcedittext.VerificationCodeEditText;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.KeybordS;
import com.woyun.warehouse.utils.MD5Util;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.TwoPassWordDialog;
import com.woyun.warehouse.view.TwoPassWordForgetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 设置2级密码
 */
public class TwoPassWordActivity extends BaseActivity {
    private static final String TAG = "TwoPassWordActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.text_pwd_status)
    TextView textPwdStatus;
    @BindView(R.id.rl_use_two_pwd)
    RelativeLayout rlUseTwoPwd;
    @BindView(R.id.rl_update_two_pwd)
    RelativeLayout rlUpdateTwoPwd;
    @BindView(R.id.rl_forget_two_pwd)
    RelativeLayout rlForgetTwoPwd;

    private String setPwd,confrimPwd,oldPwd,updatePwd,confimUpDatePwd;
    private boolean pay_flag;//是否设置过来的
    String twoPwd;//是否设置2及密码
    private TimeCount time;
    private TextView tvSendCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_pwd);
        ButterKnife.bind(this);
        pay_flag=getIntent().getBooleanExtra("pay_flag",false);
//        Log.e(TAG, "onCreate: "+pay_flag );
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();


    }

    private void initData() {
        twoPwd= (String) SPUtils.getInstance(TwoPassWordActivity.this).get(Constant.USER_TWO_PWD,"");
        if(TextUtils.isEmpty(twoPwd)){
            textPwdStatus.setText("未设置");
            textPwdStatus.setTextColor(getResources().getColor(R.color.text_content));
            rlUpdateTwoPwd.setClickable(false);
            rlForgetTwoPwd.setClickable(false);
            rlUseTwoPwd.setVisibility(View.VISIBLE);
            rlUpdateTwoPwd.setVisibility(View.GONE);
            rlForgetTwoPwd.setVisibility(View.GONE);
        }else{
            textPwdStatus.setText("已设置");
            textPwdStatus.setTextColor(getResources().getColor(R.color.mainColor));
            rlUseTwoPwd.setClickable(false);
            rlUseTwoPwd.setVisibility(View.GONE);
            rlUpdateTwoPwd.setVisibility(View.VISIBLE);
            rlForgetTwoPwd.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    /**
     * 弹窗设置
     * type 1  第一次设置密码   2 确认设置密码
     * 3 输入旧密码  4输入修改密码  5 确认修改密码
     *
     */
    private void showSetPWD(final int type) {
        final TwoPassWordDialog twoPassWordDialog=new TwoPassWordDialog(TwoPassWordActivity.this);
        twoPassWordDialog.show();
        final TextView tvTitle=twoPassWordDialog.findViewById(R.id.tv_title);
        TextView tvCancel=twoPassWordDialog.findViewById(R.id.tv_cancel);
        final VerificationCodeEditText editPwd=twoPassWordDialog.findViewById(R.id.edit_pwd);
        if(type==1){
            tvTitle.setText("请设置二级密码");
        }else if(type==2){
            tvTitle.setText("请确认二级密码");
        }else if(type==3){
            tvTitle.setText("请输入旧二级密码");
        }else if(type==4){
            tvTitle.setText("请输入修改密码");
        }else{
            tvTitle.setText("请确认修改密码");
        }

        //输入框监听事件
        editPwd.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void onInputCompleted(CharSequence s) {
                //输入完成请求接口
             String   code=s.toString();
                Log.e(TAG, "onInputCompleted: "+code );
//                loginByPhone(code,phone);
                Log.e(TAG, "onInputCompleted: " +s);

                if(type==1){
                    setPwd=s.toString();
                    twoPassWordDialog.dismiss();
                    showSetPWD(2);
                }else if(type==2){
                    confrimPwd=s.toString();
                    if(!setPwd.equals(confrimPwd)){
                        ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("两次密码不一致");
                    }else{
                        closeAll(editPwd,twoPassWordDialog);
                        Log.e(TAG, "onInputCompleted: "+MD5Util.getMD5(confrimPwd) );
                        setPwd("", MD5Util.getMD5(confrimPwd));
                        Log.e(TAG, "onInputCompleted: 设置2级密码" );
                    }

//                    twoPassWordDialog.dismiss();
                }else if(type==3){
                    oldPwd=s.toString();
                    twoPassWordDialog.dismiss();
                    showSetPWD(4);
                }else if(type==4){
                    updatePwd=s.toString();
                    twoPassWordDialog.dismiss();
                    showSetPWD(5);
                }else{
                    confimUpDatePwd=s.toString();
                    if(!updatePwd.equals(confimUpDatePwd)){
                        ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("两次密码不一致");
                    }else{
                        closeAll(editPwd,twoPassWordDialog);
                        setPwd(MD5Util.getMD5(oldPwd), MD5Util.getMD5(confimUpDatePwd));
                    }
//                    twoPassWordDialog.dismiss();
                    Log.e(TAG, "onInputCompleted: 修改2级密码" );
                }

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPassWordDialog.dismiss();
            }
        });
    }

    @OnClick({R.id.rl_use_two_pwd, R.id.rl_update_two_pwd,R.id.rl_forget_two_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_use_two_pwd://启用设置2及密码
                showSetPWD(1);
                break;
            case R.id.rl_update_two_pwd://更改2及密码
                showSetPWD(3);
                break;
            case R.id.rl_forget_two_pwd://忘记2级密码
                showForgetPwd();
                break;
        }
    }

    /**
     * 忘记密码弹窗
     */
    private void showForgetPwd() {
        final TwoPassWordForgetDialog forgetDialog=new TwoPassWordForgetDialog(TwoPassWordActivity.this);
        forgetDialog.show();
        time = new TimeCount(60000, 1000);
        final TextView tvPhone=forgetDialog.findViewById(R.id.editPhone);
        TextView tvCancel=forgetDialog.findViewById(R.id.tv_cancel);
         tvSendCode=forgetDialog.findViewById(R.id.tv_get_code);
        EditText edCode=forgetDialog.findViewById(R.id.editCode);
        EditText newPassWord=forgetDialog.findViewById(R.id.newPassWord);
        Button btnForget=forgetDialog.findViewById(R.id.btn_forgetpwd);
        String phone= (String) SPUtils.getInstance(TwoPassWordActivity.this).get(Constant.USER_MOBILE,"");

        tvPhone.setText(phone);
        //获取验证码
        tvSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCode(phone);
            }
        });

        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edCode.getText().toString())){
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("请输入验证码~");
                    return ;
                }
                if(TextUtils.isEmpty(newPassWord.getText().toString())){
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("请设置二级密码~");
                    return ;
                }
                foegetPwd(phone,edCode.getText().toString(), MD5Util.getMD5(newPassWord.getText().toString()));
                forgetDialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgetDialog.dismiss();
            }
        });
    }

    private void closeAll(EditText view, Dialog dialog){
            KeybordS.closeKeybord(view,TwoPassWordActivity.this);
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }

    /**
     * 设置密码
     * @param
     */
    private void setPwd(String oldPwd,String newPwd) {
        ModelLoading.getInstance(TwoPassWordActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);
            params.put("oldPwd", oldPwd);
            params.put("newPwd", newPwd);

            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_SET_USER_SECOND_PWD, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast(responseMessage);
                    if (responseCode == 0) {

                        textPwdStatus.setText("已设置");
                        textPwdStatus.setTextColor(getResources().getColor(R.color.mainColor));
                        rlUseTwoPwd.setClickable(false);
                        rlUpdateTwoPwd.setClickable(true);
                        rlForgetTwoPwd.setClickable(true);
                        if(pay_flag){
                            SPUtils.getInstance(TwoPassWordActivity.this).put(Constant.USER_TWO_PWD,newPwd);
                            finish();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast(responseMessage);
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                }
            });

        } catch (JSONException e) {
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


    /**
     * 获取验证码
     * @param phone
     */
    private void requestCode(String phone){
        ModelLoading.getInstance(TwoPassWordActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("mob",phone);
            params.put("userid",loginUserId);
            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_GET_USER_SECOND_PWD_CODE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                    if(code==0){
                        time.start();
                    }else{
                        ToastUtils.getInstanc(TwoPassWordActivity.this).showToast(msg);
                    }
                }
                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("获取验证码失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 找回2及密码
     * @param phone
     */
    private void foegetPwd(String phone,String code,String pwd){
        ModelLoading.getInstance(TwoPassWordActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid",loginUserId);
            params.put("mob",phone);
            params.put("code",code);
            params.put("pwd",pwd);
            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_RESET_USER_SECOND_PWD, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast(msg);
                    if(code==0){

                    }else{
                        ToastUtils.getInstanc(TwoPassWordActivity.this).showToast(msg);
                    }
                }
                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(TwoPassWordActivity.this).closeLoading();
                    ToastUtils.getInstanc(TwoPassWordActivity.this).showToast("找回二级密码失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
