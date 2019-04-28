package com.woyun.warehouse.vote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.event.RefreshIndexEvent;
import com.woyun.warehouse.baseparson.event.UnReadMessEvent;
import com.woyun.warehouse.bean.CategoryGoodsBeanTwo;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.adapter.CategoryGoodsAdapterTwo;
import com.woyun.warehouse.utils.CommonUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品分类2.0fragment
 */
public class HostFragmentTwo extends BaseFragment {
    private static final String TAG = "HostFragmentTwo";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.img_back_top)
    ImageView imgBackTop;
    private int categoryId;
    private int pager = 1;//当前页
    private Map<Integer, List<CategoryGoodsBeanTwo.PageBean.ContentBean>> mapData=new HashMap<>();
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> allData = new ArrayList<>();//一页的所有数据14 跳
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> listDataOne = new ArrayList<>();//一行1条数据 type 1
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> listDataTwo = new ArrayList<>();//一行2条数据 type  2
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> finalData = new ArrayList<>();//最终的数据
    private List<CategoryGoodsBeanTwo.GoodsPackListBean> packListBeans = new ArrayList<>();
    private CategoryGoodsAdapterTwo goodsAdapterOne;

    //回调用来接收参数
    public static HostFragmentTwo getInitUrl(int gc_id) {
        LogUtils.e(TAG, "getInitUrl: ==");
        HostFragmentTwo hostFragment = new HostFragmentTwo();
        Bundle bundle = new Bundle();
        bundle.putInt("gc_id", gc_id);
        hostFragment.setArguments(bundle);
        return hostFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_host_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        //获取参数
        categoryId = getArguments().getInt("gc_id");
        LogUtils.e(TAG, "onCreateView: 分类ID" + categoryId);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
//        goodsAdapterOne = new CategoryGoodsAdapterOne(getActivity(), finalData,pager,packListBeans,listDataTwo,mapData);
        goodsAdapterOne = new CategoryGoodsAdapterTwo(getActivity(), allData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setItemViewCacheSize(11);
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 30)));//垂直间距
        }
        recyclerView.setAdapter(goodsAdapterOne);


        goodsAdapterOne.setOnTypeItemClickListener(new CategoryGoodsAdapterTwo.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goodsDetail = new Intent(getActivity(), GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", allData.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });
        imgBackTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.scrollToPosition(0);
                EventBus.getDefault().post(new UnReadMessEvent(true));
            }
        });
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RefreshIndexEvent event) {
        if(event.isFlag()&&isFragmentVisible()){
            LogUtils.e(TAG, "Event:==是否刷新==是否可见= ");
            allData.clear();
            pager = 1;
            getData(pager, "", categoryId);
        }

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();

    }

    private void initData() {
        //触发自动刷新
        mRefreshLayout.autoRefresh();
//      mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        listData.clear();
                        pager = 1;
                        getData(pager, "", categoryId);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pager++;
//                        allData.clear();
//                        listDataOne.clear();
//                        listDataTwo.clear();
                        getData(pager, "", categoryId);
                        mRefreshLayout.finishLoadmore();
                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取分类数据
     */
    private void getData(int page, String name, int categoryId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("page", page);
            params.put("name", name);
            params.put("categoryId", categoryId);

            RequestInterface.goodsPrefix(getActivity(), params, TAG, ReqConstance.I_GOODS_PAGE_BY_CATEGORY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            CategoryGoodsBeanTwo categoryGoodsBeanTwo = gson.fromJson(object.toString(), CategoryGoodsBeanTwo.class);
//                            packListBeans.addAll(categoryGoodsBeanTwo.getGoodsPackList());
                            allData.addAll(categoryGoodsBeanTwo.getPage().getContent());
                            goodsAdapterOne.notifyDataSetChanged();
                            parseData(allData);
                            if (allData.size() == 0 && page == 1) {
                                tvEmptyText.setText("暂无相关商品");
                                ivEmpty.setVisibility(View.VISIBLE);
                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }
//                            goodsAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
//                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.finishRefresh();
            }
        }
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void parseData(List<CategoryGoodsBeanTwo.PageBean.ContentBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (i < 10) {
                CategoryGoodsBeanTwo.PageBean.ContentBean oneBean = data.get(i);
                oneBean.setViewType(1);
                listDataOne.add(oneBean);
            } else {
                CategoryGoodsBeanTwo.PageBean.ContentBean twoBean = data.get(i);
                twoBean.setViewType(2);
                listDataTwo.add(data.get(i));
            }
        }
        finalData.addAll(listDataOne);
//        finalData.addAll(listDataTwo);
        if (data.size() > 10) {
            CategoryGoodsBeanTwo.PageBean.ContentBean newBean = new CategoryGoodsBeanTwo.PageBean.ContentBean();
            newBean.setViewType(2);
            finalData.add(newBean);
        }
        //没用过
       mapData= CommonUtils.group(finalData, new CommonUtils.GroupBy<Integer>() {
            @Override
            public Integer groupby(Object obj) {
                CategoryGoodsBeanTwo.PageBean.ContentBean contentBean= (CategoryGoodsBeanTwo.PageBean.ContentBean)obj;
                return contentBean.getViewType();
            }
        });
    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }
}
