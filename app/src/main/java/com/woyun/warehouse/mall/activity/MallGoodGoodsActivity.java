package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Fade;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
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
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.bean.ResListBean;
import com.woyun.warehouse.bean.SearchBean;
import com.woyun.warehouse.mall.adapter.MallGoodGoodsAdapter;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import cn.jzvd.JzvdStd;


/**
 * 首页  各地好货  必选名品
 */
public class MallGoodGoodsActivity extends BaseActivity {
    private static final String TAG = "MallGoodGoodsActivity";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_content)
    RelativeLayout llContent;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.edit_search)
    ClearEditText editSearch;
    @BindView(R.id.tv_empty)
    TextView tvEmptyText;

    private int categoryId;//分类ID categoryId=12 各地好货,categoryId=13 必选名品
    private int pager = 1;//当前页
    private List<GoodCategoryBean.PageBean.ContentBean> listData = new ArrayList<>();
    private List<GoodCategoryBean.PageBean.ContentBean> tempListData = new ArrayList<>();//临时
    private MallGoodGoodsAdapter mallGoodGoodsAdapter;

    private List<ResListBean> resListBeanList = new ArrayList<>();//图片与视频资源

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_good_goods);
        getWindow().setEnterTransition(new Fade().setDuration(500));
        ButterKnife.bind(this);
        initView();

        llContent.setFocusableInTouchMode(true);
        llContent.requestFocus();


        mallGoodGoodsAdapter = new MallGoodGoodsAdapter(MallGoodGoodsActivity.this, listData, categoryId);
        recyclerView.setLayoutManager(new LinearLayoutManager(MallGoodGoodsActivity.this));
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(MallGoodGoodsActivity.this, 16)));//垂直间距
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mallGoodGoodsAdapter);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
//                Jzvd.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Jzvd jzvd = view.findViewById(R.id.videoplayer);
                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
//                Jzvd.onChildViewDetachedFromWindow(view);
            }
        });

        //点击购买
        mallGoodGoodsAdapter.setOnButtonClickListener((view, positon) -> {
            if (view.getId() == R.id.btn_buy) {
                toDeatailActivity(positon);
//                ToastUtils.getInstanc(MallGoodGoodsActivity.this).showToast("clickBuy"+positon);
            }
        });

        mallGoodGoodsAdapter.setOnItemClickListener(position -> {
            toDeatailActivity(position);
//            ToastUtils.getInstanc(MallGoodGoodsActivity.this).showToast("itemClick"+position);
        });


        //图片
        mallGoodGoodsAdapter.setOnSmallClickListener(new MallGoodGoodsAdapter.OnSmallClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSmallClick(int positon, int index) {
                resListBeanList.clear();
                List<GoodCategoryBean.PageBean.ContentBean.ResListBean> sourList = listData.get(positon).getResList();

                for (int i = 0; i < sourList.size(); i++) {
                    ResListBean resListBean = new ResListBean();
                    resListBean.setImage(sourList.get(i).getImage());
                    resListBean.setVideoUrl(sourList.get(i).getVideoUrl());
                    resListBean.setType(sourList.get(i).getType());
                    resListBeanList.add(resListBean);
                }

                Intent intent = new Intent(MallGoodGoodsActivity.this, LookImageVideoActivity.class);
                intent.putExtra("reslist", (Serializable) resListBeanList);
                intent.putExtra("index", index);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MallGoodGoodsActivity.this).toBundle());
            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                    pager = 1;
                    String searchName = editSearch.getText().toString().trim();
                    tempListData.addAll(listData);
                    listData.clear();
                    getData(pager, searchName,categoryId);

                }
                return false;
            }
        });

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "beforeTextChanged: " );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onTextChanged: " );
            }

            @Override
            public void afterTextChanged(Editable s) {
               if(TextUtils.isEmpty(s.toString())){
                   mallGoodGoodsAdapter.setData(tempListData);
               }
            }
        });
        getData(pager, "", categoryId);
        operation();
    }

    private void operation(){
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tempListData.clear();
                        listData.clear();
                        pager = 1;
                        getData(pager,"",categoryId);
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
                        getData(pager,"",categoryId);
                        mRefreshLayout.finishLoadmore();
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        categoryId = getIntent().getIntExtra("goodsmall_type", 0);
        if (categoryId == 12) {
            ivBg.setImageResource(R.mipmap.bgg_gdhh);
            llContent.setBackgroundColor(Color.parseColor("#A110DB"));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#A110DB"));
        } else {
            ivBg.setImageResource(R.mipmap.bgg_bxmp);
            llContent.setBackgroundColor(Color.parseColor("#CB362C"));
            collapsingToolbarLayout.setContentScrimColor(Color.parseColor("#CB362C"));
        }
        mImmersionBar.titleBar(toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//禁用工具栏title


    }

    private void toDeatailActivity(int index) {
        Intent in = new Intent(MallGoodGoodsActivity.this, GoodsDetailNativeActivity.class);
        in.putExtra("goods_id", listData.get(index).getGoodsId());
        in.putExtra("from_id", 2);
        startActivity(in);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * 获取数据
     *
     * @param page
     * @param name
     * @param categoryId
     */
    private void getData(int page, String name, int categoryId) {
        ModelLoading.getInstance(MallGoodGoodsActivity.this).showLoading("", true);

        try {
            JSONObject params = new JSONObject();
            params.put("page", page);
            params.put("name", name);
            params.put("categoryId", categoryId);

            RequestInterface.goodsPrefix(MallGoodGoodsActivity.this, params, TAG, ReqConstance.I_GOODS_PAGE_BY_CATEGORY, 1, new HSRequestCallBackInterface() {

                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MallGoodGoodsActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);

                            GoodCategoryBean goodCategoryBean = gson.fromJson(object.toString(), GoodCategoryBean.class);
                            listData.addAll(goodCategoryBean.getPage().getContent());
                            if (listData.size() == 0) {
                                tvEmptyText.setVisibility(View.VISIBLE);
                            } else {
                                tvEmptyText.setVisibility(View.GONE);
                            }

                            mallGoodGoodsAdapter.setData(listData);
//                            mallGoodGoodsAdapter.notifyDataSetChanged();

                        } else {
                            ToastUtils.getInstanc(MallGoodGoodsActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MallGoodGoodsActivity.this).closeLoading();
                    ToastUtils.getInstanc(MallGoodGoodsActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            if (mRefreshLayout.isRefreshing()) {
////                Log.e(TAG, "initData: finish");
//                mRefreshLayout.finishRefresh();
        }

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
