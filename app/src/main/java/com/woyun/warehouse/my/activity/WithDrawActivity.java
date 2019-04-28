package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jkb.vcedittext.VerificationAction;
import com.jkb.vcedittext.VerificationCodeEditText;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.KeybordS;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.MD5Util;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.TwoPassWordDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 提现
 */
public class WithDrawActivity extends BaseActivity {
    private static final String TAG = "WithDrawActivity";
    private static final int BIND_ACCOUT = 102;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_money)
    EditText editMoney;
    @BindView(R.id.tv_yuer_num)
    TextView tvYuerNum;
    @BindView(R.id.btn_all_withdraw)
    TextView btnAllWithdraw;
    @BindView(R.id.checkbox_weixin)
    CheckBox checkboxWeixin;
    @BindView(R.id.rl_wei_xin)
    RelativeLayout rlWeiXin;
    @BindView(R.id.checkbox_zfb)
    CheckBox checkboxZfb;
    @BindView(R.id.rl_zfb)
    RelativeLayout rlZfb;
    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;
    @BindView(R.id.tv_fee_money)
    TextView tvFeeMoney;
    @BindView(R.id.tv_person_money)
    TextView tvPersonMoney;

    @BindView(R.id.ll_fee)
    LinearLayout llFee;
    @BindView(R.id.tv_read)
    TextView tvRead;

    private String tiXianMoney;
    private String fee;
    private String personal;//个税
    private String passWord;//2级密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_draw);
        ButterKnife.bind(this);

        tiXianMoney = getIntent().getStringExtra("tixian_money");
        fee = getIntent().getStringExtra("fee");
        personal = getIntent().getStringExtra("personal");
        tvYuerNum.setText(tiXianMoney);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
        if (!TextUtils.isEmpty(fee)&&!TextUtils.isEmpty(personal)) {
            double converFee = Double.valueOf(fee.substring(0, fee.length() - 1)) / 100;
            double personFee = Double.valueOf(personal.substring(0, personal.length() - 1)) / 100;
            LogUtils.e(TAG, "onCreate: ==" + converFee);
            editMoney.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e(TAG, "onTextChanged: " + s.toString());
                    if (!TextUtils.isEmpty(s.toString())) {
                        if (Double.valueOf(s.toString()) > Double.valueOf(tiXianMoney)) {//输入金额超过余额
                            llFee.setVisibility(View.GONE);
                            tvRead.setVisibility(View.VISIBLE);
                        } else {
                            llFee.setVisibility(View.VISIBLE);
                            tvRead.setVisibility(View.GONE);
                            double fee= BigDecimalUtil.getMultiply(converFee,Double.valueOf(s.toString())) ;
                            double person=BigDecimalUtil.getMultiply(personFee,Double.valueOf(s.toString())) ;
                            tvFeeMoney.setText(String.valueOf(fee)+"元");
                            tvPersonMoney.setText("￥"+String.valueOf(person)+"元");
                        }
                    } else {
                        tvFeeMoney.setText("￥" + 0);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    private void initData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.btn_all_withdraw, R.id.rl_wei_xin, R.id.rl_zfb, R.id.btn_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_all_withdraw://全部提现
                editMoney.setText(tiXianMoney);
                break;
            case R.id.rl_wei_xin://微信
                checkboxWeixin.setChecked(true);
                if (checkboxZfb.isChecked()) {
                    checkboxZfb.setChecked(false);
                }
                break;
            case R.id.rl_zfb://支付宝
                checkboxZfb.setChecked(true);
                if (checkboxWeixin.isChecked()) {
                    checkboxWeixin.setChecked(false);
                }
                break;
            case R.id.btn_withdraw://确认提现操作
                if (TextUtils.isEmpty(editMoney.getText().toString().trim())) {
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("提现金额不能为空...");
                    return;
                }

                if (editMoney.getText().toString().trim().equals("0")) {
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("提现金额不能为0...");
                    return;
                }

                if (editMoney.getText().toString().trim().startsWith("00")) {
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("请填写正确的提现金额");
                    return;
                }
//                if(i % 100 == 0)
//                int money = Integer.valueOf(editMoney.getText().toString().trim());
//                if (money % 10 != 0) {
//                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("提现金额必须为10的整数");
//                    return;
//                }

                if (!checkboxWeixin.isChecked() && !checkboxZfb.isChecked()) {
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("至少选择一种到账方式！！！");
                    return;
                }
                showTwoPassWord();

                break;
        }
    }

    /**
     * 提现
     *
     * @param userid
     */
    private void doWithDraw(String userid, String withdraw,String pwd) {
        ModelLoading.getInstance(WithDrawActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("withdraw", withdraw);
            params.put("userid", userid);
            if (checkboxWeixin.isChecked()) {
                params.put("type", "wx");
            } else {
                params.put("type", "zfb");
            }
            params.put("pwd", MD5Util.getMD5(pwd));

            RequestInterface.userPrefixVersiontTwo(WithDrawActivity.this, params, TAG, ReqConstance.I_USER_WITHDRAW, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast(msg);
                    ModelLoading.getInstance(WithDrawActivity.this).closeLoading();
                    if (code == 0) {
                        finish();
                    } else if (code == 1 || code == 2) {//1绑定微信 2//绑定支付宝
                        Intent bind = new Intent(WithDrawActivity.this, BindAccountActivity.class);
                        bind.putExtra("from_type", 1);
                        startActivityForResult(bind, BIND_ACCOUT);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(WithDrawActivity.this).closeLoading();
                    ToastUtils.getInstanc(WithDrawActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 2级密码弹窗
     */
    public void showTwoPassWord() {
        final TwoPassWordDialog twoPassWordDialog = new TwoPassWordDialog(WithDrawActivity.this);
        twoPassWordDialog.show();
        final TextView tvTitle = twoPassWordDialog.findViewById(R.id.tv_title);
        TextView tvCancel = twoPassWordDialog.findViewById(R.id.tv_cancel);
        final VerificationCodeEditText editPwd = twoPassWordDialog.findViewById(R.id.edit_pwd);
//            editPwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        //输入框监听事件
        editPwd.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void onInputCompleted(CharSequence s) {
                //输入完成请求接口
                String code = s.toString();
                passWord=s.toString();

                doWithDraw(loginUserId, editMoney.getText().toString().trim(),passWord);
                if (KeybordS.isSoftShowing(WithDrawActivity.this)) {
                    KeybordS.closeKeybord(editPwd, WithDrawActivity.this);
                } else {

                }
                twoPassWordDialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPassWordDialog.dismiss();
            }
        });
    }
}
