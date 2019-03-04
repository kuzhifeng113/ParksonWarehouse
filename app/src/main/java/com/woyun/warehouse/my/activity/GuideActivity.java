package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.SplashActivity;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 引导页--没用到
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = "GuideActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.btn_break)
    Button btnBreak;
    private Boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        isLogin = (Boolean) SPUtils.getInstance(GuideActivity.this).get(Constant.IS_LOGIN, false);
        SPUtils.getInstance(GuideActivity.this).put(Constant.IS_FIRST_START, true);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    private void initData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.btn_break)
    public void onViewClicked() {
        Intent goIntent=new Intent();
        if (isLogin) {
            goIntent.setClass(GuideActivity.this, MainActivity.class);
        } else {
            goIntent.setClass(GuideActivity.this, LoginActivity.class);
        }
        startActivity(goIntent);
        finish();
    }
}
