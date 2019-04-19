package com.woyun.warehouse.welfare;

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
import com.woyun.warehouse.bean.RedPackDetailBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.welfare.adapter.RedDetailAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 红包明细
 */
public class RedYuErActivity extends BaseActivity {
    private static final String TAG = "RedYuErActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    RelativeLayout ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;


    private List<RedPackDetailBean.ContentBean> listData = new ArrayList<>();
    private RedDetailAdapter cangCoinAdapter;
    private int pager = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_yuer_detail);
        ButterKnife.bind(this);
        tvEmptyText.setText("暂无记录呦！");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cangCoinAdapter = new RedDetailAdapter(RedYuErActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cangCoinAdapter);
        getData(loginUserId, pager);

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();

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
                        getData(loginUserId, pager);
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
                        getData(loginUserId, pager);
                        mRefreshLayout.finishLoadmore();

                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取全部
     */
    private void getData(String userId, final int page) {
        ModelLoading.getInstance(RedYuErActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            RequestInterface.redpackPrefix(RedYuErActivity.this, params, TAG, ReqConstance.I_REDPACK_PAGEQUERY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(RedYuErActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            RedPackDetailBean cangCoinTwoBean = gson.fromJson(object.toString(), RedPackDetailBean.class);
                            listData.addAll(cangCoinTwoBean.getContent());

                            if (listData.size() == 0) {
                                ivEmpty.setVisibility(View.VISIBLE);

                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }

                            cangCoinAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(RedYuErActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(RedYuErActivity.this).closeLoading();
                    ToastUtils.getInstanc(RedYuErActivity.this).showToast(s);
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

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


}
