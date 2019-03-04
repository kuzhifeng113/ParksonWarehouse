package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 关于百盛仓
 */
public class AboutMeActivity extends BaseActivity {
    private static final String TAG = "AboutMeActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rl_private)
    RelativeLayout rlPrivate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        initData();

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.rl_private)
    public void onViewClicked() {
        Intent priva=new Intent(AboutMeActivity.this, MyWebViewActivity.class);
        priva.putExtra("web_url", Constant.WEB_PRIVATE);
        startActivity(priva);
    }
}
