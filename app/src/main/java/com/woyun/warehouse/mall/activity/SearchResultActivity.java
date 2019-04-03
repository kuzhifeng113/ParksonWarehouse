package com.woyun.warehouse.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CategoryGoodsBean;
import com.woyun.warehouse.bean.CategorySearchBean;
import com.woyun.warehouse.bean.SearchBean;
import com.woyun.warehouse.mall.adapter.CategoryGoodsAdapter;
import com.woyun.warehouse.mall.adapter.CategorySearchAdapter;
import com.woyun.warehouse.mall.adapter.HotSearchAdapter;
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


/**
 * 搜索activity
 */
public class SearchResultActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_search)
    EditText editText;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmpty;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int pager=1;//当前页
    private List<CategorySearchBean.ContentBean> listData=new ArrayList<>();
    private CategorySearchAdapter goodsAdapter;
    String searchName;
    private List<SearchBean> historyList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getHistoryData();
        goodsAdapter=new CategorySearchAdapter(SearchResultActivity.this,listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
        recyclerView.setAdapter(goodsAdapter);

        goodsAdapter.setOnTypeItemClickListener(new CategorySearchAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                Intent goodsDetail=new Intent(SearchResultActivity.this, GoodsDetailNativeActivity.class);
                Intent goodsDetail=new Intent(SearchResultActivity.this, GoodsDetailNativeWebActivity.class);
                goodsDetail.putExtra("goods_id",listData.get(position).getGoodsId());
                goodsDetail.putExtra("from_id",2);
                startActivity(goodsDetail);
            }
        });

        searchName=getIntent().getStringExtra("search_name");
        editText.setText(searchName);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editText.getText().toString().trim())){
                    pager=1;
                    searchName=editText.getText().toString().trim();
                    listData.clear();
                    initData(pager,searchName);

                    //保存为历史搜索
                    SearchBean bean = new SearchBean();
                    bean.setName(editText.getText().toString().trim());
                    historyList.add(bean);
                    Gson gson = new Gson();
                    String history = gson.toJson(historyList);
                    SPUtils.getInstance(SearchResultActivity.this).put(Constant.USER_SEARCH_HISTORY, history);
                }
                return false;
            }
        });

        initData(pager,searchName);
        operation();
    }

    private void  getHistoryData(){
        historyList.clear();
        String historyLog = (String) SPUtils.getInstance(SearchResultActivity.this).get(Constant.USER_SEARCH_HISTORY, "");
        if (!TextUtils.isEmpty(historyLog)) {
            List<SearchBean> hisLog = new Gson().fromJson(historyLog, new TypeToken<List<SearchBean>>() {
            }.getType());
            historyList.addAll(hisLog);
        }
    }

    private void operation(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        pager = 1;
                        initData(pager,searchName);
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
                        initData(pager,searchName);
                        mRefreshLayout.finishLoadmore();
                    }
                }, 1000);
            }
        });
    }
    /**
     * 根据关键字搜索商品
     */
    private void initData(int page,String name) {
        ModelLoading.getInstance(SearchResultActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("page",page);
            params.put("name",name);
            RequestInterface.goodsPrefix(SearchResultActivity.this, params, TAG, ReqConstance.I_GOODS_SEARCH, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(SearchResultActivity.this).closeLoading();
                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<CategorySearchBean.ContentBean> beanList = gson.fromJson(contentArray.toString(), new TypeToken<List<CategorySearchBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(beanList);

                            if (listData.size() == 0) {
                                tvEmpty.setText("暂无相关商品哟！");
                                ivEmpty.setVisibility(View.VISIBLE);
                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }
                            goodsAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(SearchResultActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(SearchResultActivity.this).closeLoading();
                    ToastUtils.getInstanc(SearchResultActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

}
