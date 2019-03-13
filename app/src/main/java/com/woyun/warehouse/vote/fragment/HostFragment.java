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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.bean.CategoryGoodsBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.adapter.CategoryGoodsAdapter;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 商品分类fragment
 */
public class HostFragment extends BaseFragment {
    private static final String TAG = "HostFragment";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    private int categoryId;
    private int pager = 1;//当前页
    private List<CategoryGoodsBean.ContentBean> listData = new ArrayList<>();
    private CategoryGoodsAdapter goodsAdapter;
    private boolean isVip;

    //回调用来接收参数
    public static HostFragment getInitUrl(int gc_id) {
        Log.e(TAG, "getInitUrl: ==");
        HostFragment hostFragment = new HostFragment();
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
        isVip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, false);
        //获取参数
        categoryId = getArguments().getInt("gc_id");
        Log.e(TAG, "onCreateView: 分类ID" + categoryId);

        goodsAdapter = new CategoryGoodsAdapter(getActivity(), listData, isVip);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(goodsAdapter);


        goodsAdapter.setOnTypeItemClickListener(new CategoryGoodsAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent goodsDetail = new Intent(getActivity(), GoodsDetailActivity.class);
                Intent goodsDetail = new Intent(getActivity(), GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", listData.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });
        return view;
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
                        listData.clear();
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
                        getData(pager, "", categoryId);
                        mRefreshLayout.finishLoadmore();

//                        } else {
//                            Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        }
                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取分类数据
     */
    private void getData(int page, String name, int categoryId) {
//        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("page", page);
            params.put("name", name);
            params.put("categoryId", categoryId);

            RequestInterface.goodsPrefix(getActivity(), params, TAG, ReqConstance.I_GOODS_PAGE_BY_CATEGORY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
//                    ModelLoading.getInstance(getActivity()).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<CategoryGoodsBean.ContentBean> beanList = gson.fromJson(contentArray.toString(), new TypeToken<List<CategoryGoodsBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(beanList);
                            if (listData.size() == 0) {
                                tvEmptyText.setText("暂无相关商品");
                                ivEmpty.setVisibility(View.VISIBLE);
                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }
                            goodsAdapter.notifyDataSetChanged();
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
//                Log.e(TAG, "initData: finish");
                mRefreshLayout.finishRefresh();
            }
        }
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
}
