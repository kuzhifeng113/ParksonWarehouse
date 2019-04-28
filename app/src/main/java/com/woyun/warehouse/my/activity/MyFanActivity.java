package com.woyun.warehouse.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.adapter.FragmentPageAdapter;
import com.woyun.warehouse.baseparson.event.FansEvent;
import com.woyun.warehouse.baseparson.event.RefreshGrabEvent;
import com.woyun.warehouse.bean.CangCoinTwoBean;
import com.woyun.warehouse.my.fragment.FansFragment;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的粉丝
 */
public class MyFanActivity extends BaseActivity {
    private static final String TAG = "MyFanActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_no_vip)
    TextView tvNoVip;


    private List<Fragment> fragmentsList;//fragment容器
    private ArrayList<String> titles;
    private String[] title = {"VIP会员", "普通会员"};
    private FragmentPageAdapter fragmentPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fan);
        ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        tvVip.setTextColor(Color.parseColor("#FF373A42"));
        tvNoVip.setTextColor(Color.parseColor("#FFAFAFAF"));

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

//        getData(loginUserId,1);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(FansEvent event) {
        if(event.getIsVip()==0){//
            tvNoVip.setText("普通会员 ("+event.getSize()+"人)");
        }else {
            tvVip.setText("VIP会员 ("+event.getSize()+"人)");
        }

    }

    /**
     * 初始化
     */
    private void initData() {
        //tab标题
        titles = new ArrayList<>();
        //页面
        fragmentsList = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            titles.add(title[i]);
            fragmentsList.add(FansFragment.getInstance(1));
            fragmentsList.add(FansFragment.getInstance(0));
        }


        fragmentPageAdapter = new FragmentPageAdapter(getSupportFragmentManager(), fragmentsList, titles);
        viewPager.setOffscreenPageLimit(titles.size());
        viewPager.setAdapter(fragmentPageAdapter);

        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    tvVip.setTextColor(Color.parseColor("#FF373A42"));
                    tvNoVip.setTextColor(Color.parseColor("#FFAFAFAF"));
                }else{
                    tvVip.setTextColor(Color.parseColor("#FFAFAFAF"));
                    tvNoVip.setTextColor(Color.parseColor("#FF373A42"));
                }
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
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @OnClick({R.id.tv_vip, R.id.tv_no_vip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip:
                tvVip.setTextColor(Color.parseColor("#FF373A42"));
                tvNoVip.setTextColor(Color.parseColor("#FFAFAFAF"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_no_vip:
                tvVip.setTextColor(Color.parseColor("#FFAFAFAF"));
                tvNoVip.setTextColor(Color.parseColor("#FF373A42"));
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
