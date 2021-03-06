package com.woyun.warehouse.grabbuy;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.adapter.FragmentPageAdapter;
import com.woyun.warehouse.bean.RushTimeBean;
import com.woyun.warehouse.grabbuy.fragment.GrabGoodsFragment;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.AppBarStateChangeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 抢购
 */
public class GrabBuyFragment extends BaseFragment {
    private static final String TAG = "GrabBuyFragment";
    Unbinder unbinder;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private List<Fragment> fragmentsList;//fragment容器
    private ArrayList<String> titles;
    private FragmentPageAdapter fragmentPageAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_grab, container, false);
        unbinder = ButterKnife.bind(this, view);

        appbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    tvTitle.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    tvTitle.setVisibility(View.VISIBLE);
                    //折叠状态
                } else {
                    //中间状态
                    tvTitle.setVisibility(View.GONE);
                }
            }

        });

//        appbarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                int scrollRangle = appBarLayout.getTotalScrollRange();
//                //初始verticalOffset为0，不能参与计算。
//                if (verticalOffset == 0) {
//                    tvTitle.setAlpha(0.0f);
//                } else {
//                    //保留一位小数
//                    float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
//                    tvTitle.setAlpha(alpha);
//                }
//            }
//        });


        return view;
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getData();
//        initView();
    }


    //获取数据
    private void initData() {
        //触发自动刷新
//        mRefreshLayout.autoRefresh();
////        mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
//        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                refreshLayout.getLayout().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getData();
//                        mRefreshLayout.finishRefresh();
//                        mRefreshLayout.resetNoMoreData();
//                    }
//                }, 800);
//            }
//        });

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            getData();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        ImmersionBar.with(this)
//                .statusBarDarkFont(true).init();
//    }

    private void initView(List<RushTimeBean> datas) {
        int positon = 0;
        //tab标题
        titles = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            titles.add(datas.get(i).getName());
            if (datas.get(i).getStatus().equals("正在抢购")) {
                positon = i;
            }
        }
        //页面
        fragmentsList = new ArrayList<>();

        for (int j = 0; j < titles.size(); j++) {
            fragmentsList.add(GrabGoodsFragment.getInstance(datas.get(j).getRushBuyId()));
        }

        fragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager(), fragmentsList, titles);
        viewPager.setAdapter(fragmentPageAdapter);
        tablayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(titles.size());
        ////动态设置tab
        for (int i = 0; i < fragmentPageAdapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = tablayout.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            View view = getLayoutInflater().inflate(R.layout.tab_grab_item, null, false);
            tabAt.setCustomView(view);

            TextView tvTime = view.findViewById(R.id.tv_time);
            TextView tvStaus = view.findViewById(R.id.tv_status);

            tvTime.setText(titles.get(i));
            tvStaus.setText(datas.get(i).getStatus());

            if (i == positon) {
                updateTabView(tabAt, true);
            }
        }
        viewPager.setCurrentItem(positon);

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


    /**
     * 用来改变tabLayout选中后的字体大小及颜色
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        TextView tvtime = (TextView) tab.getCustomView().findViewById(R.id.tv_time);
        TextView tvstatus = (TextView) tab.getCustomView().findViewById(R.id.tv_status);
        if (isSelect) {
            //设置标签选中
            tvtime.setSelected(true);
            //选中后字体变大
            tvtime.setTextSize(19);
            tvtime.setTextColor(Color.parseColor("#ffffff"));

            tvstatus.setSelected(true);
            //选中后字体变大
            tvstatus.setTextSize(12);
            tvstatus.setTextColor(Color.parseColor("#ffffff"));

        } else {
            //设置标签取消选中
            tvtime.setSelected(false);
            //恢复为默认字体大小
            tvtime.setTextSize(17);
            tvtime.setTextColor(Color.parseColor("#FFA8B8"));

            //设置标签取消选中
            tvstatus.setSelected(false);
            //恢复为默认字体大小
            tvstatus.setTextSize(12);
            tvstatus.setTextColor(Color.parseColor("#FFA8B8"));

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取数据 tablaout 数据
     */
    private void getData() {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.rushPrefix(getActivity(), params, TAG, ReqConstance.I_RUSH_GET_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Gson gson = new Gson();
                        List<RushTimeBean> datas = gson.fromJson(jsonResult, new TypeToken<List<RushTimeBean>>() {
                        }.getType());
                        pasterData(datas);
                        LogUtils.e(TAG, "requestSuccess: " + datas.size());

                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    LogUtils.e(TAG, "requestError: getData");
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void pasterData(List<RushTimeBean> datas) {
        if (datas != null && datas.size() > 0) {
            initView(datas);
        }
    }
}
