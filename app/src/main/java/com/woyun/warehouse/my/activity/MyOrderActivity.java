package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.adapter.FragmentPageAdapter;
import com.woyun.warehouse.my.fragment.AllOrderFragment;
import com.woyun.warehouse.my.fragment.PendingDeliveryFragment;
import com.woyun.warehouse.my.fragment.PendingPayingFragment;
import com.woyun.warehouse.my.fragment.PendingReceiveFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 我的订单
 */
public class MyOrderActivity extends BaseActivity {
    private static final String TAG = "MyOrderActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.host_tab)
    TabLayout tablayout;
    @BindView(R.id.host_viewpager)
    ViewPager viewPager;

    private List<Fragment> fragmentsList;//fragment容器
    private ArrayList<String> titles;
    private String[] title = {"全部", "待付款", "待发货","待收货"};
    private FragmentPageAdapter fragmentPageAdapter;
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        index=getIntent().getIntExtra("index",0);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }


        /**
         * 初始化
         */
    private void initData() {
            //tab标题
            titles = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                titles.add(title[i]);
            }
            //页面
            fragmentsList = new ArrayList<>();
            fragmentsList.add(new AllOrderFragment());
            fragmentsList.add(new PendingPayingFragment());
            fragmentsList.add(new PendingDeliveryFragment());
            fragmentsList.add(new PendingReceiveFragment());

            fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentsList, titles);
            viewPager.setOffscreenPageLimit(4);
            viewPager.setAdapter(fragmentPageAdapter);
            tablayout.setupWithViewPager(viewPager);
            viewPager.setCurrentItem(index);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

}
