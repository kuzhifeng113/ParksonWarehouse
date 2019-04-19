package com.woyun.warehouse.baseparson;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.woyun.warehouse.R;
import com.woyun.warehouse.view.HorizontalProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * demo
 */
public class DemoActivity extends BaseActivity {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.progress_bar)
    HorizontalProgressBar progressBar;

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
        progressBar.setTextString("1200");
        progressBar.setProgressWithAnimation(60).setProgressListener(new HorizontalProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
            }
        });
        progressBar.startProgressAnimation();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

}
