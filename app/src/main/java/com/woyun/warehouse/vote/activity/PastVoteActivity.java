package com.woyun.warehouse.vote.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.VoteHomeBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.vote.adapter.PaseVoteAdapter;
import com.woyun.warehouse.vote.adapter.VoteHomeAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;


/**
 * 以往投票期数
 */
public class PastVoteActivity extends BaseActivity {
    private static final String TAG = "PastVoteActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private boolean isClick;
    private int voteId;//投票id
    private String voteName;
    private List<VoteHomeBean> oldBeanList=new ArrayList<>();

    private List<VoteHomeBean.GoodsListBean> listNewVotes = new ArrayList<>();
    private PaseVoteAdapter paseVoteAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_vote);
        ButterKnife.bind(this);
        voteId = getIntent().getIntExtra("vote_id", 0);
        voteName = getIntent().getStringExtra("vote_name");
        oldBeanList= (List<VoteHomeBean>) getIntent().getSerializableExtra("list_data");
        LogUtils.e(TAG, "onCreate: "+oldBeanList.size() );
        tvTitle.setText(voteName);

        paseVoteAdapter = new PaseVoteAdapter(PastVoteActivity.this, listNewVotes,isVip);
        recyclerView.setLayoutManager(new LinearLayoutManager(PastVoteActivity.this));
        recyclerView.setAdapter(paseVoteAdapter);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

        paseVoteAdapter.setOnItemClickListener(new PaseVoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent detail=new Intent(PastVoteActivity.this, GoodsDetailActivity.class);
                detail.putExtra("goods_id",listNewVotes.get(position).getGoodsId());
                detail.putExtra("from_id",1);
                detail.putExtra("is_History",true);//是否是历史期数
                detail.putExtra("wan_count",listNewVotes.get(position).getWantNum());
                detail.putExtra("total_count",listNewVotes.get(position).getTotalNum());
                detail.putExtra("is_vote",listNewVotes.get(position).isIsVote());
                detail.putExtra("is_shelf",listNewVotes.get(position).isIShelf());
                startActivity(detail);
            }
        });

        paseVoteAdapter.setOnButtonClickListener(new PaseVoteAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int index) {
                Intent detail=new Intent(PastVoteActivity.this, GoodsDetailActivity.class);
                detail.putExtra("goods_id",listNewVotes.get(index).getGoodsId());
                detail.putExtra("from_id",1);
                detail.putExtra("is_History",true);//是否是历史期数
                detail.putExtra("wan_count",listNewVotes.get(index).getWantNum());
                detail.putExtra("total_count",listNewVotes.get(index).getTotalNum());
                detail.putExtra("is_vote",listNewVotes.get(index).isIsVote());
                detail.putExtra("is_shelf",listNewVotes.get(index).isIShelf());
                startActivity(detail);
            }
        });

    }

    private void initData() {
        //触发自动刷新
        refreshLayout.autoRefresh();
//        mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listNewVotes.clear();
                        if (voteId != 0) {
                            getData(loginUserId, voteId);
                        }
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });

    }

    private void getData(String userId, int votId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("voteId", votId);
            RequestInterface.voteRequest(PastVoteActivity.this, params, TAG, ReqConstance.I_VOTE_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        LogUtils.e(TAG, "requestSuccess: " + jsonResult);
                        Gson gson = new Gson();
                        List<VoteHomeBean> data = gson.fromJson(jsonResult, new TypeToken<List<VoteHomeBean>>() {
                        }.getType());
                        pasterData(data);
                        LogUtils.e(TAG, "requestSuccess: " + data.size());

                    } else {
                        ToastUtils.getInstanc(PastVoteActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(PastVoteActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void pasterData(List<VoteHomeBean> data) {
        for (int i = 0; i < data.size(); i++) {
            listNewVotes.addAll(data.get(i).getGoodsList());
        }
        paseVoteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.tv_title)
    public void onViewClicked() {
        if (!isClick) {
            switchTitle(true);
            showPicker();
            isClick = true;
        } else {
            isClick = false;
            switchTitle(false);
        }

    }

    private void switchTitle(boolean isClcik) {
        Drawable drawabledowm;
        if (isClcik) {
            drawabledowm = getResources().getDrawable(R.mipmap.ic_top);
        } else {
            drawabledowm = getResources().getDrawable(R.mipmap.ic_down);
        }
        drawabledowm.setBounds(0, 0, drawabledowm.getMinimumWidth(), drawabledowm.getMinimumHeight());
        tvTitle.setCompoundDrawables(null, null, drawabledowm, null);
    }

    /**
     * 弹出选择往期期数
     */
    private void showPicker(){
        List<String> sexList=new ArrayList<>();
        for(int i=0;i<oldBeanList.size();i++){
            sexList.add(oldBeanList.get(i).getTitle());
        }
        final SinglePicker<String> sexpicker = new SinglePicker<>(this, sexList);
        sexpicker.setCanLoop(false);//不禁用循环
        sexpicker.setLineVisible(true);
        sexpicker.setTextSize(18);
        sexpicker.setSelectedIndex(0);
        sexpicker.setWheelModeEnable(true);
        //启用权重 setWeightWidth 才起作用
        sexpicker.setWeightEnable(true);
        sexpicker.setWeightWidth(1);
        sexpicker.setSubmitTextColor(Color.parseColor("#20BEAD"));
        sexpicker.setSelectedTextColor(Color.parseColor("#20BEAD"));
        sexpicker.setUnSelectedTextColor(Color.GRAY);
        //确定点击
        sexpicker.setOnItemPickListener(new OnItemPickListener<String>() {
           @Override
           public void onItemPicked(int index, String s) {
               tvTitle.setText(oldBeanList.get(index).getTitle());
                voteId= oldBeanList.get(index).getVoteId();
               LogUtils.e(TAG, "onItemPicked: "+voteId );
               listNewVotes.clear();
               getData(loginUserId,voteId);
               switchTitle(false);
               isClick=false;
           }
       });

        //取消
        sexpicker.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                switchTitle(false);
                isClick=false;
            }
        });

        sexpicker.show();
    }
}
