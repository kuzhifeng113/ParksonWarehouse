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
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.baseparson.LogisticsActivity;
import com.woyun.warehouse.bean.OrderListBean;
import com.woyun.warehouse.my.adapter.AllOrderAdapter;
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
 * 待收货
 */
public class PendingReceiveFragment extends BaseFragmentTwo {
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
    private  List<OrderListBean.ContentBean> listData = new ArrayList<>();
    private  AllOrderAdapter allOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        allOrderAdapter = new AllOrderAdapter(getActivity(), listData);
        recyclerView.setAdapter(allOrderAdapter);

      allOrderAdapter.setOnButtonClickListener(new AllOrderAdapter.OnButtonClickListener() {
          @Override
          public void onButtonClick(View view, int positon) {
              switch (view.getId()){
                  case R.id.tv_confirm_receipt:
//                      ToastUtils.getInstanc(getActivity()).showToast("已发货  确认收货" + positon);
                      confirmOrder(getActivity(),userId,listData.get(positon).getTradeNo(),positon,listData,allOrderAdapter);
                      break;
                  case R.id.tv_look_logistics:
//                      ToastUtils.getInstanc(getActivity()).showToast("已发货 查看物流" + positon);
                      Intent intent=new Intent(getActivity(), LogisticsActivity.class);
                      intent.putExtra("trade_no",listData.get(positon).getTradeNo());
                      startActivity(intent);
                      break;
              }
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
     * 获取全部订单
     */
    private void getData(String userId, int page) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("billStatus", 1);
            RequestInterface.payPrefix(getActivity(), params, TAG, ReqConstance.I_PAY_BILL_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<OrderListBean.ContentBean> shipAddressBeans = gson.fromJson(contentArray.toString(), new TypeToken<List<OrderListBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(shipAddressBeans);
                            allOrderAdapter.notifyDataSetChanged();
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
                mRefreshLayout.finishRefresh();
            }
        }
    }


    /**
     * 确认收货
     */
    public static void confirmOrder(Activity myActivity, String userId,String tradeNo ,int index,List<OrderListBean.ContentBean> datas,AllOrderAdapter adapter) {
        ModelLoading.getInstance(myActivity).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("tradeNo", tradeNo);

            RequestInterface.payPrefix(myActivity, params, TAG, ReqConstance.I_PAY_BILL_RECEIPT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(myActivity).closeLoading();
                        if (code == 0) {
                            ToastUtils.getInstanc(myActivity).showToast(msg);
                            datas.get(index).setBillStatus(2);
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(myActivity).showToast(msg);
                        }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(myActivity).closeLoading();
                    ToastUtils.getInstanc(myActivity).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
