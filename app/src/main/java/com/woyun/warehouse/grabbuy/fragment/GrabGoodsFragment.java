package com.woyun.warehouse.grabbuy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.bean.GrabGoodsBean;
import com.woyun.warehouse.bean.OrderListBean;
import com.woyun.warehouse.bean.RushTimeBean;
import com.woyun.warehouse.grabbuy.activity.GrabDetailActivity;
import com.woyun.warehouse.grabbuy.adapter.GrabGoodsAdapter;
import com.woyun.warehouse.utils.ModelLoading;
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
 * 抢购商品
 */
public class GrabGoodsFragment extends BaseFragment {
    private static final String TAG = "GrabGoodsFragment";
    private static final String TIME_ID = "time_id";
    Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int rushId;//限时抢购id

    private GrabGoodsAdapter grabGoodsAdapter;
    private List<GrabGoodsBean> listDatas=new ArrayList<>();
    //回调用来接收参数
    public static GrabGoodsFragment getInstance(int timeId) {
        GrabGoodsFragment myFragment = new GrabGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TIME_ID, timeId);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_grab_goods, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        grabGoodsAdapter=new GrabGoodsAdapter(getActivity(),listDatas);
        recyclerView.setAdapter(grabGoodsAdapter);


        grabGoodsAdapter.setOnItemClickListener(new GrabGoodsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    toDetail(listDatas.get(position).getGoodsId(),
                            listDatas.get(position).getStartTime(),
                            listDatas.get(position).getEndTime());
                }
            });

        grabGoodsAdapter.setOnButtonClickListener(new GrabGoodsAdapter.OnButtonClickListener() {
                @Override
                public void onButtonClick(View view, int positon) {
                    toDetail(listDatas.get(positon).getGoodsId(),
                            listDatas.get(positon).getStartTime(),
                            listDatas.get(positon).getEndTime());
                }
            });

        return rootView;
    }
    private void toDetail(int goodId,long startTime,long endTime){
        Intent intent=new Intent(getActivity(), GrabDetailActivity.class);
        intent.putExtra("goods_id",goodId);
        intent.putExtra("start_time",startTime);
        intent.putExtra("end_time",endTime);
        intent.putExtra("rush_id",rushId);
        startActivity(intent);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        rushId = arguments.getInt(TIME_ID);

    }

    private void initData() {
        //触发自动刷新
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listDatas.clear();
                        getData(rushId);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });

    }

    /**
     * 获取全部订单
     */
    private void getData(int rushBuyId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("rushBuyId", rushBuyId);
            RequestInterface.rushPrefix(getActivity(), params, TAG, ReqConstance.I_RUSH_GOODS_GET_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                        if (code == 0) {
                            String jsonResult = jsonArray.toString();
                            Gson gson = new Gson();
                            List<GrabGoodsBean> grabGoodsBeanList = gson.fromJson(jsonResult, new TypeToken<List<GrabGoodsBean>>() {
                            }.getType());
                            Log.e(TAG, "requestSuccess: "+grabGoodsBeanList.size() );
                            listDatas.addAll(grabGoodsBeanList);
                            grabGoodsAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                        }
                }

                @Override
                public void requestError(String s, int i) {
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
    public void onDestroy() {
        super.onDestroy();
        grabGoodsAdapter.cancelAllTimers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
