package com.woyun.warehouse.mall.sort;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.woyun.warehouse.bean.CategoryGoodsBeanTwo;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.adapter.CategoryGoodsAdapterTwo;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 小分类 详情 activity
 */
public class SortDetailActivity extends BaseActivity  {
    private static final String TAG = "SortDetailActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_name)
    TextView tv_name;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;

    private int pager = 1;//当前页
    private Context mContext;
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> allData = new ArrayList<>();//一页的所有数据14 条
    private CategoryGoodsAdapterTwo goodsAdapterOne;
    private int categoryId;
    private String cateGoryName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_detail);
        ButterKnife.bind(this);
        mContext=this;
        categoryId=getIntent().getIntExtra("category_id",0);
        cateGoryName=getIntent().getStringExtra("category_name");
        tv_name.setText(cateGoryName);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        goodsAdapterOne = new CategoryGoodsAdapterTwo(mContext, allData);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(mContext, 30)));//垂直间距
        }
        recyclerView.setAdapter(goodsAdapterOne);

        goodsAdapterOne.setOnTypeItemClickListener(new CategoryGoodsAdapterTwo.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent goodsDetail = new Intent(mContext, GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", allData.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });

        getData(pager,"",categoryId);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        allData.clear();
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
                    }
                }, 1000);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    /**
     * 获取大小分类数据
     */
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

            RequestInterface.goodsPrefix(mContext, params, TAG, ReqConstance.I_GOODS_PAGE_BY_CATEGORY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            CategoryGoodsBeanTwo categoryGoodsBeanTwo = gson.fromJson(object.toString(), CategoryGoodsBeanTwo.class);
                            allData.addAll(categoryGoodsBeanTwo.getPage().getContent());
                            goodsAdapterOne.notifyDataSetChanged();

                            if (allData.size() == 0 && page == 1) {
                                tvEmptyText.setText("暂无相关商品");
                                ivEmpty.setVisibility(View.VISIBLE);
                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtils.getInstanc(mContext).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
//                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(mContext).showToast(s);
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
