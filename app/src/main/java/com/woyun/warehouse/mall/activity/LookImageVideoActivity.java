package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.bean.ResListBean;
import com.woyun.warehouse.mall.adapter.LookViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


/**
 * 看图片跟视频
 */
public class LookImageVideoActivity extends BaseActivity {
    private static final String TAG = "LookImageVideoActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<ResListBean> listData = new ArrayList<>();
    private LookViewPageAdapter lookViewPageAdapter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_image);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(260));
        getWindow().setExitTransition(new Fade().setDuration(260));
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }

            }
        });

        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private void initData() {
        listData = (List<ResListBean>) getIntent().getSerializableExtra("reslist");
        int index = getIntent().getIntExtra("index", 0);
        lookViewPageAdapter=new LookViewPageAdapter(LookImageVideoActivity.this,listData);

        viewPager.setAdapter(lookViewPageAdapter);
        viewPager.setCurrentItem(index);
        lookViewPageAdapter.setItemClickListener(new LookViewPageAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int index) {
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Jzvd.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
