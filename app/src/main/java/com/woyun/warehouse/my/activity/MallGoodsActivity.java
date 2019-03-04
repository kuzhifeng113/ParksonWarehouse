package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 首页  各地好货  必选名品
 */
public class MallGoodsActivity extends BaseActivity {
    private static final String TAG = "MallGoodsActivity";

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_bg)
    ImageView imgBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_goods);
        ButterKnife.bind(this);

        int goodsmall_type = getIntent().getIntExtra("goodsmall_type", 0);
        if (goodsmall_type == 0) {
            tvTitle.setText("各地好货");
            imgBg.setImageResource(R.mipmap.img_gdhh);
        } else {
            tvTitle.setText("必选名品");
            imgBg.setImageResource(R.mipmap.img_bxyp);
        }

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


}
