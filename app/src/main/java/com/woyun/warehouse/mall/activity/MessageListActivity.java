package com.woyun.warehouse.mall.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.MessageBean;
import com.woyun.warehouse.bean.OrderListBean;
import com.woyun.warehouse.mall.adapter.MessageAdapter;
import com.woyun.warehouse.my.activity.CangCoinActivity;
import com.woyun.warehouse.my.activity.CangCoinDetailActivity;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.my.activity.YuErActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.SwipeItemLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 消息列表
 */
public class MessageListActivity extends BaseActivity {
    private static final String TAG = "MyAddressActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.id_empty_view)
    View emptyView;

    private List<MessageBean.ContentBean> listData = new ArrayList<>();
    private MessageAdapter messageAdapter;

    private int messType;
    private int pager = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        messType = getIntent().getIntExtra("mess_type", 0);
        Log.e(TAG, "onCreate: "+messType );
        if (messType == 1) {
            tvTitle.setText("订单通知");
        } else {
            tvTitle.setText("系统消息");
        }

        messageAdapter = new MessageAdapter(MessageListActivity.this, listData, messType);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recyclerView.setAdapter(messageAdapter);

        getData(loginUserId, pager, messType);

        initEvent();

    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listData.clear();
                pager = 1;
                getData(loginUserId, pager, messType);
            }
        });


        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pager++;
                        getData(loginUserId, pager, messType);
                        mRefreshLayout.finishLoadmore();

                    }
                }, 1000);
            }
        });

        messageAdapter.setOnButtonClickListener(new MessageAdapter.OnButtonClickListener() {
            @Override
            public void delete(final int positon) {
//                new DeleteDialog(MessageListActivity.this, R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
//                    @Override
//                    public void onClick(Dialog dialog, boolean confirm) {
//                        if (confirm) {
//                            dialog.dismiss();
//                            deleteAddress(listData.get(positon).getId(), loginUserId, positon);
//                        }
//                    }
//                }).show();
                deleteAddress(listData.get(positon).getId(), loginUserId, positon);
            }
        });

        messageAdapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent();
                //type=1，表示：订单发货，点击通知跳转订单详情
                //type=5，表示：预售产品上架，仅投票的人有通知，跳转商品详情
                //type=7，表示：仓币变动，跳转仓币明细列表
                //type=8，表示：余额变动，跳转余额明细列表
                //type=9，表示：URL网页，直接打开网页地址
                //2  3  4 6---消息列表
                int type=listData.get(position).getType();
                if(type==1){
                    intent.putExtra("tradeNo",listData.get(position).getTradeNo());
                    intent.setClass(MessageListActivity.this, OrderDetailActivity.class);
                    startActivity(intent);
                    return;
                }else if(type==5){
                    intent.putExtra("goods_id",listData.get(position).getRedirectId());//商品详情的
                    intent.setClass(MessageListActivity.this, GoodsDetailActivity.class);
                    startActivity(intent);
                    return;
                }else if(type==7){//仓币明细
                    intent.putExtra("hb_type",1);
//                    intent.setClass(MessageListActivity.this, CangCoinDetailActivity.class);
                    intent.setClass(MessageListActivity.this, CangCoinActivity.class);
                    startActivity(intent);
                    return;
                }else if(type==8 ||type==3){//余额明细
                    intent.putExtra("hb_type",2);
//                    intent.setClass(MessageListActivity.this, CangCoinDetailActivity.class);
                    intent.setClass(MessageListActivity.this, YuErActivity.class);
                    startActivity(intent);
                    return;
                }else if(type==9){//网址
                    intent.putExtra("web_url",listData.get(position).getUrl());
                    intent.setClass(MessageListActivity.this, MyWebViewActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        });
    }

    /**
     * 获取数据
     *
     * @param userId
     */
    private void getData(String userId, int page, int typed) {
        ModelLoading.getInstance(MessageListActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("type", typed);
            RequestInterface.sysPrefix(MessageListActivity.this, params, TAG, ReqConstance.I_GET_MESSAGE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MessageListActivity.this).closeLoading();
                    try {
                        if (code == 0 && jsonArray.length() > 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<MessageBean.ContentBean> datas = gson.fromJson(contentArray.toString(), new TypeToken<List<MessageBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(datas);
                            if (listData.size() == 0) {
                                emptyView.setVisibility(View.VISIBLE);
                                tvEmptyText.setText("暂无相关通知");
                            } else {
                                emptyView.setVisibility(View.GONE);
                            }
                            messageAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(MessageListActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MessageListActivity.this).closeLoading();
                    ToastUtils.getInstanc(MessageListActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mRefreshLayout.isRefreshing()) {
                Log.e(TAG, "initData: finish");
                mRefreshLayout.finishRefresh();
            }
        }
    }

    /**
     * 删除消息
     * @param addresId
     */
    private void deleteAddress(int addresId, String userId, final int index) {
        ModelLoading.getInstance(MessageListActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("ids", addresId);
            params.put("userid", userId);

            RequestInterface.sysPrefix(MessageListActivity.this, params, TAG, ReqConstance.I_DELETE_MESSAGE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MessageListActivity.this).closeLoading();
                    if (code == 0) {
                        listData.remove(index);
                        messageAdapter.notifyDataSetChanged();
//                        ToastUtils.getInstanc(MessageListActivity.this).showToast(msg);
                    } else {
                        ToastUtils.getInstanc(MessageListActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MessageListActivity.this).closeLoading();
                    ToastUtils.getInstanc(MessageListActivity.this).showToast(s);
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
