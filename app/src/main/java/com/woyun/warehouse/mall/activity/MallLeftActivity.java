package com.woyun.warehouse.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.mall.adapter.MallLeftAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.AppBarStateChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 商城 左侧进入的页面  查看更多
 */
public class MallLeftActivity extends BaseActivity {
    private static final String TAG = "MallLeftActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_packlist_name)
    TextView tvPacklistName;
    @BindView(R.id.tv_packlist_title)
    TextView tvPacklistTitle;
    @BindView(R.id.tv_packlist_size)
    TextView tvPacklistSize;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.img_bg)
    ImageView imgBg;

    private MallHomeBean.PackListBean packListBean;

    private MallLeftAdapter mallLeftAdapter;
    private List<MallHomeBean.PackListBean.GoodsListBean> listDatas = new ArrayList<>();
    private int packId;//id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_left_activity);
        ButterKnife.bind(this);
        initView();
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        packId = getIntent().getIntExtra("pack_id", 0);
//        packListBean = (MallHomeBean.PackListBean) getIntent().getSerializableExtra("packlist_bean");
//        if(packListBean!=null){
//            tvPacklistName.setText(packListBean.getName());
//            tvPacklistTitle.setText(packListBean.getTitle());
//            tvPacklistSize.setText(packListBean.getGoodsList().size()+"件商品");
//        }
        recyclerView.setNestedScrollingEnabled(false);
        mallLeftAdapter = new MallLeftAdapter(MallLeftActivity.this, listDatas, isVip);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mallLeftAdapter);

//        initData();
        getData(packId);
        mallLeftAdapter.setOnTypeItemClickListener(new MallLeftAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int posion) {
//                Intent goodsDetail = new Intent(MallLeftActivity.this, GoodsDetailActivity.class);
                Intent goodsDetail = new Intent(MallLeftActivity.this, GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", listDatas.get(posion).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });

    }

    private void initView() {
        mImmersionBar.titleBar(toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//禁用工具栏title

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    //展开状态
                    llContent.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    llContent.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    //折叠状态
                } else {
                    //中间状态
                    llContent.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.GONE);
                }
            }
        });

    }

//    private void initData() {
//        if(packListBean!=null){
//            List<MallHomeBean.PackListBean.GoodsListBean> goodsList = packListBean.getGoodsList();
//            listDatas.addAll(goodsList);
//            mallLeftAdapter.notifyDataSetChanged();
//        }
//    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();

        mImmersionBar.statusBarDarkFont(true).init();
    }


    /**
     * 获取数据
     *
     * @param
     */
    private void getData(int goodsPackId) {
        ModelLoading.getInstance(MallLeftActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsPackId", goodsPackId);
            RequestInterface.goodsPrefix(MallLeftActivity.this, params, TAG, ReqConstance.I_GOODS_PACK_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MallLeftActivity.this).closeLoading();
                    tokenTimeLimit(MallLeftActivity.this, code);
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            packListBean = gson.fromJson(jsonArray.get(0).toString(), MallHomeBean.PackListBean.class);
                            List<MallHomeBean.PackListBean.GoodsListBean> goodsList = packListBean.getGoodsList();
                            Glide.with(MallLeftActivity.this).load(packListBean.getImage()).placeholder(R.mipmap.img_default).into(imgBg);
                            tvPacklistName.setText(packListBean.getTitle());
                            tvPacklistTitle.setText(packListBean.getName());
                            tvPacklistSize.setText(packListBean.getGoodsList().size() + "件商品");
                            listDatas.addAll(goodsList);
                            mallLeftAdapter.notifyDataSetChanged();
                            tvTitle.setText(packListBean.getTitle());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(MallLeftActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MallLeftActivity.this).closeLoading();
                    ToastUtils.getInstanc(MallLeftActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
