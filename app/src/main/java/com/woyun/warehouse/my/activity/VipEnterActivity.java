package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;


import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.vip.VipFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * VIP 入口
 */
public class VipEnterActivity extends BaseActivity {
    private static final String TAG = "AboutMeActivity";
    @BindView(R.id.fragment_container)
    FrameLayout frameLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_enter);
        ButterKnife.bind(this);
       FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        VipFragment vipFragment = new VipFragment();
        transaction.add(R.id.fragment_container,vipFragment);
        transaction.commit();

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

}
