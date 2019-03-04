package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CangCoinTwoBean;
import com.woyun.warehouse.my.adapter.CangCoinTwoAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 仓币2.0
 */
public class CangCoinActivity extends BaseActivity {
    private static final String TAG = "CangCoinActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    RelativeLayout ivEmpty;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_wei_ffang)
    TextView tvWeiFfang;

    private List<CangCoinTwoBean.PageBean.ContentBean> listData = new ArrayList<>();
    private CangCoinTwoAdapter cangCoinAdapter;
    private int pager = 1;
    private int type = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcoin_detail);
        ButterKnife.bind(this);
        tvEmptyText.setText("暂无相关数据呦！");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cangCoinAdapter = new CangCoinTwoAdapter(CangCoinActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cangCoinAdapter);
        getData(loginUserId, pager, type);

        initData();
    }

    private void initData() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        pager = 1;
                        getData(loginUserId, pager, type);
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
                        getData(loginUserId, pager, type);
                        mRefreshLayout.finishLoadmore();

                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取全部仓币  1仓币，2余额；只有vip才传type=1
     */
    private void getData(String userId, final int page, int type) {
        ModelLoading.getInstance(CangCoinActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("type", type);
            RequestInterface.userPrefix(CangCoinActivity.this, params, TAG, ReqConstance.I_CB_BALANCE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(CangCoinActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            CangCoinTwoBean cangCoinTwoBean = gson.fromJson(object.toString(), CangCoinTwoBean.class);
                            tvMoney.setText(String.valueOf(cangCoinTwoBean.getBcCoin()));
                            tvWeiFfang.setText("未发放："+String.valueOf(cangCoinTwoBean.getGrantCbCoin()));
                            listData.addAll(cangCoinTwoBean.getPage().getContent());
                            if (listData.size() == 0) {
                                ivEmpty.setVisibility(View.VISIBLE);

                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }

                            cangCoinAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(CangCoinActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(CangCoinActivity.this).closeLoading();
                    ToastUtils.getInstanc(CangCoinActivity.this).showToast(s);
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

}
