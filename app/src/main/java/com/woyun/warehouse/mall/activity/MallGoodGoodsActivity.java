package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.mall.adapter.MallGoodGoodsAdapter;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;

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
    private static final String TAG = "MallLeftGoodsActivity";

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.img_back)
    ImageView imgBack;

    private int categoryId;//分类ID categoryId=12 各地好货,categoryId=13 必选名品
    private int pager = 1;//当前页
    private List<GoodCategoryBean.PageBean.ContentBean> listData = new ArrayList<>();
    private MallGoodGoodsAdapter mallGoodGoodsAdapter;

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
//        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        mallGoodGoodsAdapter = new MallGoodGoodsAdapter(MallGoodGoodsActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(MallGoodGoodsActivity.this));
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(MallGoodGoodsActivity.this, 16)));//垂直间距
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(mallGoodGoodsAdapter);

        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                Log.e(TAG, "onChildViewAttachedToWindow:111 ");
//                Jzvd.onChildViewAttachedToWindow(view, R.id.videoplayer);
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                Log.e(TAG, "onChildViewDetachedFromWindow:222 ");
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

        mallGoodGoodsAdapter.setOnButtonClickListener(new MallGoodGoodsAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int positon) {
            }
        });

        //图片或者视频点击
        mallGoodGoodsAdapter.setOnSmallClickListener(new MallGoodGoodsAdapter.OnSmallClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onSmallClick(int positon, int index) {
                Intent intent=new Intent(MallGoodGoodsActivity.this,LookImageVideoActivity.class);
                intent.putExtra("reslist", (Serializable) listData.get(positon).getResList());
                intent.putExtra("index",index);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MallGoodGoodsActivity.this).toBundle());
            }
        });

        getData(pager, "", categoryId);
    }

    private void initView() {
        categoryId = getIntent().getIntExtra("goodsmall_type", 0);
        mImmersionBar.titleBar(toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//禁用工具栏title


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
                            mallGoodGoodsAdapter.notifyDataSetChanged();
//                            packListBeans.addAll(categoryGoodsBeanTwo.getGoodsPackList());
//                            listData.addAll(beanList);
//                            if (listData.size() == 0) {
//                                tvEmptyText.setText("暂无相关商品");
//                                ivEmpty.setVisibility(View.VISIBLE);
//                            } else {
//                                ivEmpty.setVisibility(View.GONE);
//                            }
//                            goodsAdapter.notifyDataSetChanged();
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
