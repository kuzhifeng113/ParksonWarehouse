package com.woyun.warehouse.baseparson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.gyf.barlibrary.ImmersionBar;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

/**
 * activity 公共类
 */
public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private InputMethodManager imm;
    protected ImmersionBar mImmersionBar;
    protected InputMethodManager inputMethodManager;
    public String loginUserId;
    public boolean isVip;//是否会员
    public boolean isAgent;//是否代理
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUserId= (String) SPUtils.getInstance(BaseActivity.this).get(Constant.USER_ID,"");
        isVip= (boolean) SPUtils.getInstance(BaseActivity.this).get(Constant.USER_IS_VIP,false);
        isAgent= (boolean) SPUtils.getInstance(BaseActivity.this).get(Constant.USER_IS_AGENT,false);
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public void finish() {
        super.finish();
        hideSoftKeyBoard();

    }

    public void hideSoftKeyBoard() {
        View localView = getCurrentFocus();
        if (this.imm == null) {
            this.imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
        }
        if ((localView != null) && (this.imm != null)) {
            this.imm.hideSoftInputFromWindow(localView.getWindowToken(), 2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.imm = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //在BaseActivity里销毁
    }

    /**
     * Token 过期处理
     */
    public static void tokenTimeLimit(Activity contenxt, int code){
        if(code==1000||code==1001){//鉴权失败，可能是token已过期，请重新登陆
            ToastUtils.getInstanc(contenxt).showToast("账号已过期,请重新登录");
            Intent intent=new Intent(contenxt, LoginActivity.class);
            contenxt.startActivity(intent);
            contenxt.finish();
            return;
        }
    }
}
