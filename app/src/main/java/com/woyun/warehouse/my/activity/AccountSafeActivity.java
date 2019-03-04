package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 账户与安全
 */
public class AccountSafeActivity extends BaseActivity {
    private static final String TAG = "AccountSafeActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rl_set_pwd)
    RelativeLayout rlSetPwd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);

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


    @OnClick(R.id.rl_set_pwd)
    public void onViewClicked() {
        Intent setPwd=new Intent(AccountSafeActivity.this,TwoPassWordActivity.class);
        startActivity(setPwd);
    }


}
