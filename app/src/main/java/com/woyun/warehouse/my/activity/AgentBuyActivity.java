package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.alipay.PayResult;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.AgentBuyVipBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.bean.WxPayBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.my.adapter.AgentBuyVipAdapter;
import com.woyun.warehouse.my.adapter.AgentVipTypeAdapter;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 代理购买--买会员
 */
public class AgentBuyActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "AgentBuyActivity";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_agentlb)
    RecyclerView recyclerAgentlb;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.btn_buy)
    Button btnBuy;

    private AgentVipTypeAdapter vipTypeAdapter;
    private List<AgentBuyVipBean.VipTypeListBean> vipTypeListBeanList = new ArrayList<>();

    private AgentBuyVipAdapter goodsAdapter;
    private List<AgentBuyVipBean.GoodsListBean> goodsListBeanList = new ArrayList<>();

    private CommonPopupWindow popupWindow;
    private ShipAddressBean orderAddres;
    private String vipPrice;//费用
    private int vipTypeId;

    private TextView ptv_title,ptv_no_address,ptv_name,ptv_phone,ptv_detial_address,ptv_close,ptv_pay_money,tv_agent_xuzhi;
    private ImageView pimg_edit,img_wechat_pay,img_ali_pay;
    private RelativeLayout prl_have_address;

    private IWXAPI api;
    private String province,city,county;//省市区
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_buy);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        vipTypeAdapter = new AgentVipTypeAdapter(AgentBuyActivity.this, vipTypeListBeanList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(AgentBuyActivity.this, 10)));//间距
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(vipTypeAdapter);

        goodsAdapter = new AgentBuyVipAdapter(AgentBuyActivity.this, goodsListBeanList);
        recyclerAgentlb.setNestedScrollingEnabled(false);
        recyclerAgentlb.setLayoutManager(new LinearLayoutManager(this));
        recyclerAgentlb.setAdapter(goodsAdapter);

        initData();

        vipTypeAdapter.setOnItemClickListener(new AgentVipTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                vipTypeSelect(position);
                vipPrice=vipTypeListBeanList.get(position).getPrice();
                vipTypeId=vipTypeListBeanList.get(position).getId();
                vipTypeAdapter.notifyDataSetChanged();
            }
        });

        goodsAdapter.setOnItemClickListener(position -> {
//            Intent detail=new Intent(AgentBuyActivity.this, GoodsDetailVipActivity.class);
            Intent detail=new Intent(AgentBuyActivity.this, GoodsDetailNativeActivity.class);
            detail.putExtra("goods_id",goodsListBeanList.get(position).getGoodsId());
            detail.putExtra("from_id",3);
            startActivity(detail);
        });

        goodsAdapter.setCheckInterface(new AgentBuyVipAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                goodsListBeanList.get(position).setCheck(isChecked);
                if (isChecked) {
                    fanXuan(position);
                }
                goodsAdapter.notifyDataSetChanged();
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vipTypeListBeanList.clear();
                        goodsListBeanList.clear();
                        initData();
                        mRefreshLayout.finishRefresh();

                    }
                }, 500);
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                ToastUtils.getInstanc(AgentBuyActivity.this).showToast("支付成功！！！");
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 101) {
                ShipAddressBean addressEntity = (ShipAddressBean) data.getSerializableExtra("address_entity");
                ptv_no_address.setVisibility(View.GONE);
                prl_have_address.setVisibility(View.VISIBLE);
                ptv_name.setText(addressEntity.getName());
                ptv_phone.setText(addressEntity.getPhone());
                ptv_detial_address.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCounty() + addressEntity.getAddress());
                province=addressEntity.getProvince();
                city=addressEntity.getCity();
                county=addressEntity.getCounty();
                Log.e(TAG, "onActivityResult: " + addressEntity.getProvince());
            }
        }
    }

    /**
     * (选中状态下 )反选 只能选中一个
     */
    private void vipTypeSelect(int positon) {
        for (int i = 0; i < vipTypeListBeanList.size(); i++) {
            if (positon == i) {
                vipTypeListBeanList.get(i).setCheck(true);
            } else {
                vipTypeListBeanList.get(i).setCheck(false);
            }
        }
    }

    /**
     * (选中状态下 )反选 只能选中一个
     */
    private void fanXuan(int positon) {
        for (int i = 0; i < goodsListBeanList.size(); i++) {
            if (positon == i) {
                goodsListBeanList.get(i).setCheck(true);
            } else {
                goodsListBeanList.get(i).setCheck(false);
            }
        }
    }

    /**
     * 获取数据
     */
    private void initData() {
        ModelLoading.getInstance(AgentBuyActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.agentPrefix(AgentBuyActivity.this, params, TAG, ReqConstance.I_AGENT_BUY_VIP, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            AgentBuyVipBean centerBean = gson.fromJson(object.toString(), AgentBuyVipBean.class);
                            vipTypeListBeanList.addAll(centerBean.getVipTypeList());
                            goodsListBeanList.addAll(centerBean.getGoodsList());

                            vipTypeAdapter.notifyDataSetChanged();
                            goodsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(AgentBuyActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentBuyActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 判断是否选中礼包
     *
     * @return
     */
    private boolean isCheckOneGoods() {
        boolean flag = false;
        for (int i = 0; i < goodsListBeanList.size(); i++) {
            if (goodsListBeanList.get(i).isCheck()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断是否选中Vip购买礼包
     *
     * @return
     */
    private boolean isCheckVipType() {
        boolean flag = false;
        for (int i = 0; i < vipTypeListBeanList.size(); i++) {
            if (vipTypeListBeanList.get(i).isCheck()) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.btn_buy)
    public void onViewClicked() {
        if(!isCheckVipType()){
            ToastUtils.getInstanc(AgentBuyActivity.this).showToast("请选择一种价格...");
        }else{
            if(!isCheckOneGoods()){
                ToastUtils.getInstanc(AgentBuyActivity.this).showToast("请选择代理礼包!");
            }else{
                getDefaultAddress(loginUserId);
            }
        }
    }

    /**
     * 默认地址+仓币+余额
     *
     * @param userId
     */
    private void getDefaultAddress(String userId) {
        ModelLoading.getInstance(AgentBuyActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.userPrefix(AgentBuyActivity.this, params, TAG + "ADDRESS", ReqConstance.I_DEFALUT_ADDREDSS_MONEY_CB, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            orderAddres = gson.fromJson(jsonResult, ShipAddressBean.class);
                                showBuy();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(AgentBuyActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentBuyActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 购买vip
     */
    private void showBuy() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_buy_agent, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_buy_agent)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)//是否可点击消失
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得popup_reveive_gifts 布局里的View
        ptv_title=view.findViewById(R.id.ptv_title);
        ptv_no_address = (TextView) view.findViewById(R.id.tv_no_address);
        prl_have_address = (RelativeLayout) view.findViewById(R.id.rl_have_address);
        ptv_close = (TextView) view.findViewById(R.id.tv_close);
        ptv_name = (TextView) view.findViewById(R.id.tv_name);
        ptv_phone = (TextView) view.findViewById(R.id.tv_phone);
        ptv_detial_address = (TextView) view.findViewById(R.id.tv_detial_address);
        ptv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        tv_agent_xuzhi = (TextView) view.findViewById(R.id.tv_agent_xuzhi);
        pimg_edit = (ImageView) view.findViewById(R.id.img_edit);
        img_wechat_pay = view.findViewById(R.id.img_wechat_pay);
        img_ali_pay = view.findViewById(R.id.img_ali_pay);
        ptv_title.setText("代理购买会员");
        ptv_pay_money.setText(vipPrice);
        //须知
        tv_agent_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vipRules = new Intent(AgentBuyActivity.this, MyWebViewActivity.class);
                vipRules.putExtra("web_url", Constant.WEB_AGENT_NOTICE);
                startActivity(vipRules);
            }
        });
        if (TextUtils.isEmpty(orderAddres.getProvince())) {//没设置地址
            ptv_no_address.setVisibility(View.VISIBLE);
            prl_have_address.setVisibility(View.GONE);
        } else {
            ptv_no_address.setVisibility(View.GONE);
            prl_have_address.setVisibility(View.VISIBLE);
            ptv_name.setText(orderAddres.getName());
            ptv_phone.setText(orderAddres.getPhone());
            ptv_detial_address.setText(orderAddres.getProvince() + orderAddres.getCity() + orderAddres.getCounty() + orderAddres.getAddress());
            province=orderAddres.getProvince();
            city=orderAddres.getCity();
            county=orderAddres.getCounty();
        }

        ptv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        //添加地址
        ptv_no_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AgentBuyActivity.this, MyAddressActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //修改地址
        pimg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(AgentBuyActivity.this, MyAddressActivity.class);
                startActivityForResult(edit, 101);
            }
        });

        //微信支付
        img_wechat_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                payOperation(loginUserId, 1);
            }
        });

        //支付宝
        img_ali_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                payOperation(loginUserId, 2);
            }
        });
    }

    /**
     * 支付操作
     *
     * @param userId
     * @param payType 1.微信，2支付宝
     */
    private void payOperation(String userId, final int payType) {
        ModelLoading.getInstance(AgentBuyActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < goodsListBeanList.size(); i++) {
                if (goodsListBeanList.get(i).isCheck()) {
                    JSONObject object = new JSONObject();
                    object.put("goodsId", goodsListBeanList.get(i).getGoodsId());
                    object.put("goodsImage", goodsListBeanList.get(i).getImage());
                    object.put("goodsName", goodsListBeanList.get(i).getName());
                    array.put(object);
                }
            }

            params.put("userid", userId);
            params.put("vipTypeId", vipTypeId);
            params.put("province", province);
            params.put("city", city);
            params.put("county", county);
            params.put("address", ptv_detial_address.getText().toString().trim());
            params.put("receiveName", ptv_name.getText().toString().trim());
            params.put("phone", ptv_phone.getText().toString());
            params.put("pwd", "");
            params.put("tradeType", Constant.PAY_TYPE_AGENT_BUY_VIP);//交易类型：1vip购买，2代理购买会员，3商城订单
            params.put("payType", payType);
            params.put("usecb", false);
            params.put("useba", false);
            params.put("billDetailList", array);

            RequestInterface.payPrefix(AgentBuyActivity.this, params, TAG, ReqConstance.I_PAY_SHOPPING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                            if (payType == 1) {//wechat
                                WxPayBean bean = gson.fromJson(jsonObject.toString(), WxPayBean.class);
                                PayReq request = new PayReq();
                                request.appId = bean.getAppid();
                                request.partnerId = bean.getPartnerid();
                                request.prepayId = bean.getPrepayid();
                                request.packageValue = bean.getPackageX();
                                request.nonceStr = bean.getNoncestr();
                                request.timeStamp = bean.getTimestamp();
                                request.sign = bean.getSign();

                                api.sendReq(request);
                            } else if (payType == 2) {//alipay
                                String para = jsonObject.getString("trade");
                                aliPay(para);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(AgentBuyActivity.this).showToast("生成订单失败，请重试...");
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentBuyActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentBuyActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 支付宝支付
     */
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(AgentBuyActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());


                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
