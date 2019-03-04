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
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CangCoinBean;
import com.woyun.warehouse.my.adapter.CangCoinAdapter;
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
 * 仓币明细
 */
public class CangCoinDetailActivity extends BaseActivity {
    private static final String TAG = "CangCoinDetailActivity";
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

    private List<CangCoinBean.ContentBean> listData = new ArrayList<>();
    private CangCoinAdapter cangCoinAdapter;
    private int pager = 1;
    private int type;

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

        type = getIntent().getIntExtra("hb_type", 0);

        if (type == 1) {
            tvTitle.setText("仓币明细");
        } else if (type == 2) {
            tvTitle.setText("余额明细");
        }

        cangCoinAdapter = new CangCoinAdapter(CangCoinDetailActivity.this, listData);
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
     * 获取全部仓币  1仓币，2余额；只有vip才传type=1
     */
    private void getData(String userId, final int page, int type) {
        ModelLoading.getInstance(CangCoinDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("type", type);
            RequestInterface.userPrefix(CangCoinDetailActivity.this, params, TAG, ReqConstance.I_CB_BALANCE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(CangCoinDetailActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<CangCoinBean.ContentBean> beanList = gson.fromJson(contentArray.toString(), new TypeToken<List<CangCoinBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(beanList);
                            if (listData.size() == 0) {
                                ivEmpty.setVisibility(View.VISIBLE);

                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }

                            cangCoinAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(CangCoinDetailActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(CangCoinDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(CangCoinDetailActivity.this).showToast(s);
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
