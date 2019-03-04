package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo
 */
public class DemoActivity extends BaseActivity  {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
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

}
