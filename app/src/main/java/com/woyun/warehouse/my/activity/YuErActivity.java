package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.woyun.warehouse.bean.CangCoinTwoBean;
import com.woyun.warehouse.bean.RealNameBean;
import com.woyun.warehouse.my.adapter.YuErAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.AgentRefundDialog;
import com.woyun.warehouse.view.RealNameIngDialog;
import com.woyun.warehouse.view.RealNameNoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 余额2.0
 */
public class YuErActivity extends BaseActivity {
    private static final String TAG = "YuErActivity";
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
    @BindView(R.id.tv_ky)
    TextView tvKy;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_wei_ffang)
    TextView tvWeiFfang;
    @BindView(R.id.img_tixian)
    ImageView imgTixian;

    private List<CangCoinTwoBean.PageBean.ContentBean> listData = new ArrayList<>();
    private YuErAdapter cangCoinAdapter;
    private int pager = 1;
    private int type = 2;
    private String fee;//手续费
    private String personal;//个税
    private boolean withdrawStatus;//是否有提现
    private int withdrawType;//提现类型  提现类型：1微信，2支付宝
    private String withdrawMoney;//提现金额

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_er_detail);
        ButterKnife.bind(this);
        tvEmptyText.setText("暂无余额记录呦！");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cangCoinAdapter = new YuErAdapter(YuErActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cangCoinAdapter);
        getData(loginUserId, pager, type);

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume:余额" );
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

                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取全部仓币  1仓币，2余额；只有vip才传type=1
     */
    private void getData(String userId, final int page, int type) {
        ModelLoading.getInstance(YuErActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            params.put("type", type);
            RequestInterface.userPrefix(YuErActivity.this, params, TAG, ReqConstance.I_CB_BALANCE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(YuErActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            CangCoinTwoBean cangCoinTwoBean = gson.fromJson(object.toString(), CangCoinTwoBean.class);
                            tvMoney.setText(String.valueOf(cangCoinTwoBean.getBcMoney()));
                            tvWeiFfang.setText("未发放："+String.valueOf(cangCoinTwoBean.getGrantMoney()));
                            listData.addAll(cangCoinTwoBean.getPage().getContent());
                            fee=cangCoinTwoBean.getFee();
                            personal=cangCoinTwoBean.getPersonal();
                            withdrawStatus=cangCoinTwoBean.isWithdrawStatus();
                            if (withdrawStatus) {
                                withdrawMoney = cangCoinTwoBean.getWithdrawMoney();
                                withdrawType = cangCoinTwoBean.getWithdrawType();
                            }
                            if (listData.size() == 0) {
                                ivEmpty.setVisibility(View.VISIBLE);

                            } else {
                                ivEmpty.setVisibility(View.GONE);
                            }

                            cangCoinAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(YuErActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(YuErActivity.this).closeLoading();
                    ToastUtils.getInstanc(YuErActivity.this).showToast(s);
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

    @OnClick(R.id.img_tixian)
    public void onViewClicked() {
//        Intent intent=new Intent(YuErActivity.this,WithDrawActivity.class);
//        intent.putExtra("tixian_money", tvMoney.getText().toString().trim());
//        intent.putExtra("fee", fee);
//        intent.putExtra("personal", personal);
//        startActivity(intent);

        checkRealNameStatus();

    }

    /**
     * 查询实名认证状态
     */
    private void checkRealNameStatus() {
        ModelLoading.getInstance(YuErActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);

            RequestInterface.sysPrefix(YuErActivity.this, params, TAG, ReqConstance.I_REAL_NAME_CHECK, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    ModelLoading.getInstance(YuErActivity.this).closeLoading();
                    Log.e(TAG, "requestSuccess: " + responseData.length());
                    if (responseCode == 0) {
                        //有状态返回证明 提交过审核
                        if (responseData.length() > 0) {
                            try {
                                Gson gson = new Gson();
                                JSONObject jsonObject = (JSONObject) responseData.get(0);
                                RealNameBean realNameBean = gson.fromJson(jsonObject.toString(), RealNameBean.class);
                                if (realNameBean.getStatus() == 0) {//审核中
                                    new RealNameIngDialog(YuErActivity.this, R.style.dialogphone, new RealNameIngDialog.OnCloseListener() {
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm) {
                                                dialog.dismiss();
                                            }
                                        }
                                    }).show();

                                } else if (realNameBean.getStatus() == 1) {//审核通过
                                    //2.是否之前有过提现申请
                                    if (withdrawStatus) {
                                        new AgentRefundDialog(YuErActivity.this, R.style.dialogphone, new AgentRefundDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm, TextView tvMoney, TextView tvType) {
                                                if (confirm) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        }, withdrawMoney, withdrawType).show();

                                    } else {
                                        Intent intent = new Intent(YuErActivity.this, WithDrawActivity.class);
                                        intent.putExtra("tixian_money", tvMoney.getText().toString().trim());
                                        intent.putExtra("fee", fee);
                                        intent.putExtra("personal", personal);
                                        startActivity(intent);
                                    }
                                } else {//审核不通过
                                    Intent intent = new Intent(YuErActivity.this, RealNameActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {//未提交过实名审核
                            new RealNameNoDialog(YuErActivity.this, R.style.dialogphone, new RealNameNoDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(YuErActivity.this, RealNameDoActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(YuErActivity.this).closeLoading();
                    ToastUtils.getInstanc(YuErActivity.this).showToast(responseMessage);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
