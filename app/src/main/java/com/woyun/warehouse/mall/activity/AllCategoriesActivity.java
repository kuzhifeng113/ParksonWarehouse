package com.woyun.warehouse.mall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CategoryTitleBean;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.vote.fragment.HostFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 全部分类
 */
public class AllCategoriesActivity extends BaseActivity {
    private static final String TAG = "AllCategoriesActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.host_tab)
    TabLayout hostTab;
    @BindView(R.id.host_viewpager)
    ViewPager hostViewpager;

    private List<Fragment> fragments;
    private List<CategoryTitleBean> listTiles=new ArrayList<>();
    private Fragment[] arrays;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getCategoryData();
//        initData();
    }

    private void initData() {
        fragments=new ArrayList<>();
        fragments.add(new HostFragment());
//        titles.add("标题1");
//        titles.add("标题2");
//        titles.add("标题3");
//        titles.add("标题4");
//        titles.add("标题5");
//        titles.add("标题6");
//        titles.add("标题7");
//        titles.add("标题8");

        hostTab.setupWithViewPager(hostViewpager);

        Myadapter adapter=new Myadapter(getSupportFragmentManager());
        //联动
        hostViewpager.setAdapter(adapter);
        hostViewpager.setOffscreenPageLimit(listTiles.size());
    }

    /**
     * 获取分类数据
     */
    private void getCategoryData(){
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.goodsPrefix(AllCategoriesActivity.this, params, TAG, ReqConstance.I_GOODS_CATEGORY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        Gson gson = new Gson();
                        List<CategoryTitleBean> categoryTitleBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<CategoryTitleBean>>() {
                        }.getType());
                        listTiles.addAll(categoryTitleBeans);
                        initData();
                    } else {
                        ToastUtils.getInstanc(AllCategoriesActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(AllCategoriesActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    //适配器
    class Myadapter extends FragmentPagerAdapter {

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getfragment(position);
        }

        @Override
        public int getCount() {
            return listTiles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTiles.get(position).getName();
        }
    }

    //动态创建Fragment的方法
    public Fragment  getfragment(int position){
        arrays=new Fragment[listTiles.size()];
        Fragment fg = arrays[position];
        if (fg == null) {
            fg = HostFragment.getInitUrl(listTiles.get(position).getCategoryId());
            arrays[position] = fg;
        }
        return fg;
    }


}
