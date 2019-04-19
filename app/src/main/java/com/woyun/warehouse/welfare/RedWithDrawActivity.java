package com.woyun.warehouse.welfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.woyun.warehouse.my.activity.BindAccountActivity;
import com.woyun.warehouse.my.activity.WithDrawActivity;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.KeybordS;
import com.woyun.warehouse.utils.MD5Util;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.TwoPassWordDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 红包提现---到余额
 */
public class RedWithDrawActivity extends BaseActivity {
    private static final String TAG = "WithDrawActivity";
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


    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;


    @BindView(R.id.tv_read)
    TextView tvRead;

    private String tiXianMoney;

    private String passWord;//2级密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//设置软键盘弹出遮盖其他布局
        setContentView(R.layout.activity_red_with_draw);
        ButterKnife.bind(this);

        tiXianMoney = getIntent().getStringExtra("tixian_money");

        tvYuerNum.setText(tiXianMoney);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

        editMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.e(TAG, "onTextChanged: " + s.toString());
                if (!TextUtils.isEmpty(s.toString())) {
                    if (Double.valueOf(s.toString()) > Double.valueOf(tiXianMoney)) {//输入金额超过余额
                        tvRead.setVisibility(View.VISIBLE);
                        btnWithdraw.setClickable(false);
                    }else{
                        tvRead.setVisibility(View.GONE);
                        btnWithdraw.setClickable(true);
                    }
                } else {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.btn_all_withdraw, R.id.rl_wei_xin, R.id.btn_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_all_withdraw://全部提现
                editMoney.setText(tiXianMoney);
                break;
            case R.id.rl_wei_xin:
                checkboxWeixin.setChecked(true);
                break;

            case R.id.btn_withdraw://确认提现操作
                if (TextUtils.isEmpty(editMoney.getText().toString().trim())) {
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast("提现金额不能为空...");
                    return;
                }

                if (editMoney.getText().toString().trim().equals("0")) {
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast("提现金额不能为0...");
                    return;
                }

                if (editMoney.getText().toString().trim().startsWith("00")) {
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast("请填写正确的提现金额");
                    return;
                }
                int money = Integer.valueOf(editMoney.getText().toString().trim());
                if(money<20){
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast("提现金额必选大于20");
                    return;
                }
//                if(i % 100 == 0)
//
//                if (money % 10 != 0) {
//                    ToastUtils.getInstanc(WithDrawActivity.this).showToast("提现金额必须为10的整数");
//                    return;
//                }
                if (!checkboxWeixin.isChecked() ) {
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast("请选择到账方式！！！");
                    return;
                }
                doWithDraw(loginUserId,editMoney.getText().toString().trim());

                break;
        }
    }

    /**
     * 提现
     *
     * @param userid
     */
    private void doWithDraw(String userid, String withdraw) {
        ModelLoading.getInstance(RedWithDrawActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userid);
            params.put("money", withdraw);

            RequestInterface.redpackPrefix(RedWithDrawActivity.this, params, TAG, ReqConstance.I_REDPACK_WITHDRAW, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast(msg);
                    ModelLoading.getInstance(RedWithDrawActivity.this).closeLoading();
                    if (code == 0) {
                        setResult(WelfareFragment.RESULT_CODE);
                        finish();
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(RedWithDrawActivity.this).closeLoading();
                    ToastUtils.getInstanc(RedWithDrawActivity.this).showToast(s);
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
        final TwoPassWordDialog twoPassWordDialog = new TwoPassWordDialog(RedWithDrawActivity.this);
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
                passWord = s.toString();
//                doWithDraw(loginUserId, editMoney.getText().toString().trim(), passWord);
                if (KeybordS.isSoftShowing(RedWithDrawActivity.this)) {
                    KeybordS.closeKeybord(editPwd, RedWithDrawActivity.this);
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
