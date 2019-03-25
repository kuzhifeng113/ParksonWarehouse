package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.imagepicker.util.Utils;
import com.lzy.imagepicker.view.GridSpacingItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.SearchBean;
import com.woyun.warehouse.mall.adapter.HistorySearchAdapter;
import com.woyun.warehouse.mall.adapter.HotSearchAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 热门 搜索activity
 */
public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_search)
    EditText editText;
    @BindView(R.id.recycler_view_hot)
    RecyclerView recyclerViewHot;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.recycler_view_history)
    RecyclerView recyclerViewHistory;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.empty_view)
    View empty_view;
    @BindView(R.id.tv_clear_data)
    TextView tvClearData;

    private HotSearchAdapter hotSearchAdapter;
    private List<SearchBean> hotList = new ArrayList<>();

    private HistorySearchAdapter historySearchAdapter;
    private List<SearchBean> historyList = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(200));
        getWindow().setExitTransition(new Fade().setDuration(200));
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                finish();
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });


        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editText.getText().toString().trim())) {
                    //如果actionId是搜索的id，则进行下一步的操作
                    Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    intent.putExtra("search_name", editText.getText().toString().trim());
                    startActivity(intent);
                    //保存为历史搜索
                    SearchBean bean = new SearchBean();
                    bean.setName(editText.getText().toString().trim());
                    historyList.add(bean);
                    Gson gson = new Gson();
                    String history = gson.toJson(historyList);
                    SPUtils.getInstance(SearchActivity.this).put(Constant.USER_SEARCH_HISTORY, history);
                }
                return false;
            }
        });

        hotSearchAdapter = new HotSearchAdapter(SearchActivity.this, hotList);
        recyclerViewHot.setLayoutManager(new GridLayoutManager(SearchActivity.this, 3));
        recyclerViewHot.addItemDecoration(new GridSpacingItemDecoration(3, Utils.dp2px(this, 2), false));
        recyclerViewHot.setAdapter(hotSearchAdapter);
        initData();

        hotSearchAdapter.setOnTypeItemClickListener(new HotSearchAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search_name", hotList.get(position).getName());
                startActivity(intent);
            }
        });

        historySearchAdapter = new HistorySearchAdapter(SearchActivity.this, historyList);
        recyclerViewHistory.setNestedScrollingEnabled(false);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(historySearchAdapter);


        historySearchAdapter.setOnTypeItemClickListener(new HistorySearchAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search_name", historyList.get(position).getName());
                startActivity(intent);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hotList.clear();
                        initData();
                        getHistoryData();
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistoryData();
    }

    private void getHistoryData() {
        historyList.clear();
        Gson gson = new Gson();
        //取历史数据
        String historyLog = (String) SPUtils.getInstance(SearchActivity.this).get(Constant.USER_SEARCH_HISTORY, "");
        if (!TextUtils.isEmpty(historyLog)) {
            tvClearData.setVisibility(View.VISIBLE);
            empty_view.setVisibility(View.GONE);
            List<SearchBean> hisLog = gson.fromJson(historyLog, new TypeToken<List<SearchBean>>() {
            }.getType());
            historyList.addAll(hisLog);
            historySearchAdapter.notifyDataSetChanged();
        } else {
            tvClearData.setVisibility(View.GONE);
            empty_view.setVisibility(View.VISIBLE);
            tvEmptyText.setText("暂无历史搜索记录");
        }
    }

    /**
     * 获取数据
     */
    private void initData() {
        ModelLoading.getInstance(SearchActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.goodsPrefix(SearchActivity.this, params, TAG, ReqConstance.I_GOODS_SEARCH_WORD, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(SearchActivity.this).closeLoading();
                    if (code == 0) {
                        Gson gson = new Gson();
                        List<SearchBean> data = gson.fromJson(jsonArray.toString(), new TypeToken<List<SearchBean>>() {
                        }.getType());
                        hotList.addAll(data);
                        hotSearchAdapter.notifyDataSetChanged();
                        Log.e(TAG, "requestSuccess: " + hotList.size());
                    } else {

                        ToastUtils.getInstanc(SearchActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(SearchActivity.this).closeLoading();
                    ToastUtils.getInstanc(SearchActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(0,0);
//    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    //删除记录
    @OnClick(R.id.tv_clear_data)
    public void onViewClicked() {
        SPUtils.getInstance(SearchActivity.this).remove(Constant.USER_SEARCH_HISTORY);
        historyList.clear();
        historySearchAdapter.notifyDataSetChanged();
        tvClearData.setVisibility(View.GONE);
        empty_view.setVisibility(View.VISIBLE);
        tvEmptyText.setText("暂无历史搜索记录");
    }
}
