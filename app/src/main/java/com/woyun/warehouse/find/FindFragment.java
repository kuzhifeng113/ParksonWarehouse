package com.woyun.warehouse.find;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.adapter.FragmentPageAdapter;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.find.fragment.DongTaiFragment;
import com.woyun.warehouse.find.fragment.FuLiFragment;
import com.woyun.warehouse.find.fragment.GuanZhuFragment;
import com.woyun.warehouse.my.fragment.AllOrderFragment;
import com.woyun.warehouse.my.fragment.PendingDeliveryFragment;
import com.woyun.warehouse.my.fragment.PendingPayingFragment;
import com.woyun.warehouse.my.fragment.PendingReceiveFragment;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 发现
 */
public class FindFragment extends BaseFragment {
    private static final String TAG = "FindFragment";
    Unbinder unbinder;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.img_fuli)
    ImageView imgFuli;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<Fragment> fragmentsList;//fragment容器
    private ArrayList<String> titles;
    private String[] title = {"福利", "动态", "关注"};
    private FragmentPageAdapter fragmentPageAdapter;
    private TextView tv_tab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_find, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }
    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initView();
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    private void initView() {
            //tab标题
            titles = new ArrayList<>();
            for (int i = 0; i < title.length; i++) {
                titles.add(title[i]);
            }
            //页面
            fragmentsList = new ArrayList<>();
            fragmentsList.add(FuLiFragment.newInstance(1));
            fragmentsList.add(DongTaiFragment.newInstance(2));
            fragmentsList.add(GuanZhuFragment.newInstance(3));

            fragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager(), fragmentsList, titles);
            viewPager.setAdapter(fragmentPageAdapter);
            tablayout.setupWithViewPager(viewPager);

        for (int i = 0; i < fragmentPageAdapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = tablayout.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            tabAt.setCustomView(R.layout.tablayout_item);
            //默认选中第一个
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tabAt.getCustomView().findViewById(R.id.tv_tab).setSelected(true);//第一个tab被选中
                //设置选中标签的文字大小
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextSize(19);
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextColor(Color.parseColor("#F5336F"));
            }
            //通过tab对象找到自定义视图的ID
            TextView textView = (TextView) tabAt.getCustomView().findViewById(R.id.tv_tab);
            textView.setText(titles.get(i));//设置tab上的文字
        }

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                //定义方法，判断是否选中
                updateTabView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }







    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.img_message, R.id.img_fuli})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_message:
                break;
            case R.id.img_fuli:
                break;
        }
    }

    /**
     *  用来改变tabLayout选中后的字体大小及颜色
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        tv_tab = (TextView) tab.getCustomView().findViewById(R.id.tv_tab);
        if(isSelect) {
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体变大
            tv_tab.setTextSize(19);
            tv_tab .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_tab.setTextColor(Color.parseColor("#F5336F"));

        }else{
            //设置标签取消选中
            tv_tab.setSelected(false);
            //恢复为默认字体大小
            tv_tab.setTextSize(16);
            //设置不为加粗
            tv_tab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_tab.setTextColor(Color.parseColor("#AFAFAF"));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
