package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.ZuanQianBean;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.vip.VipFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 会员代理 权益 2.0
 */
public class QuanYiActivity extends BaseActivity {
    private static final String TAG = "QuanYiActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    List<String> imageBanners = new ArrayList<>();
    List<ImageView> mListView = new ArrayList<>();
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_yi);
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
        String title = getIntent().getStringExtra("z_title");
        tvTitle.setText(title);
        imageBanners=getIntent().getStringArrayListExtra("imagesarray");
       index= getIntent().getIntExtra("index",0);
        LogUtils.e(TAG, "initData: "+imageBanners.size() );
        LogUtils.e(TAG, "index==: "+index );
        initBannner();
        viewPager.setCurrentItem(index);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }
    /**
     * 轮播图
     */
    private void initBannner() {
//        imageBanners.clear();
        mListView = new ArrayList<>();
        mListView.clear();
        for (int k = 0; k < imageBanners.size(); k++) {
            ImageView imageView = new ImageView(QuanYiActivity.this);
            Glide.with(QuanYiActivity.this).load(imageBanners.get(k)).asBitmap().into(imageView);
            mListView.add(imageView);
        }

        MyImageAdapter imageAdapter = new MyImageAdapter();
        viewPager.setAdapter(imageAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                LogUtils.e(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mListView.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //产生一个新的视图

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mListView.get(position));
            return mListView.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mListView.get(position));
        }
    }
}
