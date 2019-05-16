package com.woyun.warehouse.grabbuy;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.baseparson.adapter.FragmentPageAdapter;
import com.woyun.warehouse.baseparson.event.RefreshGrabEvent;
import com.woyun.warehouse.bean.RushTimeBean;
import com.woyun.warehouse.grabbuy.adapter.TimeAdapter;
import com.woyun.warehouse.grabbuy.fragment.GrabGoodsFragment;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.AppBarStateChangeListener;
import com.woyun.warehouse.view.HorizontalRecyclerView;

import org.greenrobot.eventbus.EventBus;
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
public class GrabBuyFragmentTwo extends BaseFragmentTwo {
    private static final String TAG = "GrabBuyFragmentTwo";
    Unbinder unbinder;
//    @BindView(R.id.tablayout)
//    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.appbarLayout)
    AppBarLayout appbarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    HorizontalRecyclerView recyclerView;

    private List<Fragment> fragmentsList = new ArrayList<>();//fragment容器
    private ArrayList<String> titles = new ArrayList<>();
    private List<RushTimeBean> listData = new ArrayList<>();
    private FragmentPageAdapter fragmentPageAdapter;
    private TimeAdapter timeAdapter;
    private LinearLayoutManager layoutManager;
    private int index = 0;
    private boolean isRefresh;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_grab_two, container, false);
        unbinder = ButterKnife.bind(this, view);

        timeAdapter=new TimeAdapter(getActivity(),listData);
        layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timeAdapter);
        ImmersionBar.setTitleBar(getActivity(), toolbar);
        timeAdapter.setOnItemClickListener(new TimeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view ,int position) {
                index=position;
                for (int i = 0; i < listData.size(); i++) {
                    if (position == i) {
                        listData.get(i).setSelect(true);
                    } else {
                        listData.get(i).setSelect(false);
                    }
                }
                viewPager.setCurrentItem(position);
                scrollToMiddleW(view, position);
                timeAdapter.notifyDataSetChanged();
                GrabGoodsFragment grabGoodsFragment= (GrabGoodsFragment) fragmentsList.get(index);
                boolean isRequestFail = grabGoodsFragment.isRequestFaile();
                Log.e(TAG, "Grab Request isRequestFail : "+isRequestFail );
                if(isRequestFail){
                    EventBus.getDefault().post(new RefreshGrabEvent(true));
                }


            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        isRefresh=true;
                        getData();
                        EventBus.getDefault().post(new RefreshGrabEvent(true));
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 1000);
            }
        });


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

        return view;
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getData();
//        initView();
    }



//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        ImmersionBar.with(this).titleBar(toolbar)
//                 .statusBarDarkFont(true,0.2f)
//                .init();
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                        listData.addAll(datas);
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
            initViewReycle(datas);
        }
    }


    private void initViewReycle(List<RushTimeBean> datas) {

        titles.clear();
        fragmentsList.clear();
        //tab标题
        for (int i = 0; i < datas.size(); i++) {
            titles.add(datas.get(i).getName());
            fragmentsList.add(GrabGoodsFragment.getInstance(datas.get(i).getRushBuyId()));

        }

        for (int i = 0; i < datas.size(); i++) {
            if(!isRefresh){
                if (datas.get(i).getStatus().equals("正在抢购")) {
                    index = i;
                    break;
                }else{
                    index=datas.size()-1;
                }
            }
        }
        fragmentPageAdapter = new FragmentPageAdapter(getChildFragmentManager(), fragmentsList, titles);
        viewPager.setAdapter(fragmentPageAdapter);
        viewPager.setOffscreenPageLimit(titles.size());
//        viewPager.setCurrentItem(index);

        listData.get(index).setSelect(true);//默认选中
        timeAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(index);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index=position;
                for (int i = 0; i < listData.size(); i++) {
                    if (position == i) {
                        listData.get(i).setSelect(true);
                    } else {
                        listData.get(i).setSelect(false);
                    }
                }

                timeAdapter.notifyDataSetChanged();
                scrollToMiddleW(recyclerView.getChildAt(0), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(index);
    }


    /**
     * Item 横向布局点击居中
     * Item中的点击事件调用此方法则，不在中间位置的View自动滚动到屏幕中间的位置
     */
    private void scrollToMiddleW(View view, int position) {
        int vWidth = view.getWidth();
        Rect rect = new Rect();

        recyclerView.getGlobalVisibleRect(rect);

        int reWidth = rect.right - rect.left - vWidth; //除掉点击View的宽度，剩下整个屏幕的宽度


        final int firstPosition = layoutManager.findFirstVisibleItemPosition();

        int left = recyclerView.getChildAt(position - firstPosition).getLeft();//从左边到点击的Item的位置距离

        int half = reWidth / 2;//半个屏幕的宽度

        int moveDis = left - half;//向中间移动的距离
        recyclerView.smoothScrollBy(moveDis, 0);
    }
}
