package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.vip.BannerAdapter;
import com.woyun.warehouse.vip.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 会员代理 权益 2.1
 */
public class QuanYiTwoActivity extends BaseActivity {
    private static final String TAG = "QuanYiActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.ll_layout)
    LinearLayout linearLayout;

    private BannerAdapter mBannerAdapter;

    List<String> imageBanners = new ArrayList<>();
    private int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_yi_two);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }
    private void initData() {
        String title = getIntent().getStringExtra("z_title");
        tvTitle.setText(title);
        imageBanners = getIntent().getStringArrayListExtra("imagesarray");
        index = getIntent().getIntExtra("index", 0);
        LogUtils.e(TAG, "initData: " + imageBanners.size());
        LogUtils.e(TAG, "index==: " + index);
        initBannner();
        viewPager.setCurrentItem(index);
    }


    /**
     * 轮播图
     */
    private void initBannner() {
        //设置适配器
        viewPager.setAdapter(new BannerAdapter(this, viewPager, imageBanners));
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(imageBanners.size());
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());//3D画廊模式

        //左右都有图
        viewPager.setCurrentItem(1);

        //viewPager左右两边滑动无效的处理
//        linearLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return viewPager.dispatchTouchEvent(motionEvent);
//            }
//        });
    }
}
