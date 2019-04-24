package com.woyun.warehouse.my.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.woyun.warehouse.baseparson.LogisticsActivity;
import com.woyun.warehouse.bean.CangCoinTwoBean;
import com.woyun.warehouse.bean.OrderListBean;
import com.woyun.warehouse.my.adapter.AllOrderAdapter;
import com.woyun.warehouse.my.adapter.YuErAdapter;
import com.woyun.warehouse.utils.ModelLoading;
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
 * 余额明细
 */
public class YuErDetailFragment extends BaseFragment {
    private static final String TAG = "PendingReceiveFragment";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View emptyView;

    private String userId;
    private int pager = 1;
    private List<CangCoinTwoBean.PageBean.ContentBean> listData = new ArrayList<>();
    private YuErAdapter cangCoinAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cangCoinAdapter = new YuErAdapter(getActivity(), listData);
        recyclerView.setAdapter(cangCoinAdapter);

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
                        pager=1;
                        getData(userId, pager);
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
                        getData(userId, pager);
                        mRefreshLayout.finishLoadmore();

                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取全部订单
     * userid	string	我的用户ID
     page	number	页码 page=1
     type	number	类型：1仓币，2余额；只有vip才传type=1
     status	number	发放状态：0未发，1已发
     */
    private void getData(String userId, int page) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("type", 2);
            params.put("status", 1);
            RequestInterface.userPrefix(getActivity(), params, TAG, ReqConstance.I_CB_BALANCE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            CangCoinTwoBean cangCoinTwoBean = gson.fromJson(object.toString(), CangCoinTwoBean.class);

                            listData.addAll(cangCoinTwoBean.getPage().getContent());
                            cangCoinAdapter.notifyDataSetChanged();
                            if (listData.size() == 0) {
                                emptyView.setVisibility(View.VISIBLE);
                            } else {
                                emptyView.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mRefreshLayout.isRefreshing()) {
                Log.e(TAG, "initData: finish");
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
