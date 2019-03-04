package com.woyun.warehouse.baseparson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.woyun.warehouse.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo
 */
public class DemoActivity extends BaseActivity  {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    private RecyclerView recyclerView;
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
//        recyclerView.canScrollHorizontally(1); //的值表示是否能向上滚动  false 表示已经滚动到底部
//        recyclerView.canScrollHorizontally(-1); //的值表示是否能向下滚动  false 表示已经滚动到顶部
    }

    private void initData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

}
