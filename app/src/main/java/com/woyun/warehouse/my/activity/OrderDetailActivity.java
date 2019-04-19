package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
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
import com.woyun.warehouse.baseparson.LogisticsActivity;
import com.woyun.warehouse.baseparson.event.OrderIndexEvent;
import com.woyun.warehouse.bean.OrderDetailBean;
import com.woyun.warehouse.bean.WxPayBean;
import com.woyun.warehouse.my.adapter.OrderDetailAdapter;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.utils.UdeskHelp;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DeleteDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.udesk.UdeskSDKManager;
import cn.udesk.callback.INavigationItemClickCallBack;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.NavigationMode;
import cn.udesk.presenter.ChatActivityPresenter;
import udesk.core.UdeskConst;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "OrderDetailActivity";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_detial_address)
    TextView tvDetialAddress;
    @BindView(R.id.img_edit)
    ImageView imgEdit;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_bc_coin)
    TextView tvBcCoin;
    @BindView(R.id.tv_bc_money)
    TextView tvBcMoney;
    @BindView(R.id.tv_transport)
    TextView tvTransport;
    @BindView(R.id.edit_memo)
    TextView editMemo;
    @BindView(R.id.tv_to_pay)
    TextView tvToPay;

    List<OrderDetailBean.BillDetailListBean> selectList = new ArrayList<>();//购买的商品

    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    @BindView(R.id.ll_jiaoyi_mx)
    LinearLayout llJiaoyiMx;
    @BindView(R.id.tv_shiji_pay)
    TextView tvShijiPay;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.tv_order_status)
    TextView tvOrderStatus;
    @BindView(R.id.tv_cancel_order)
    TextView tvCancelOrder;
    @BindView(R.id.ll_dai_fukuan)
    LinearLayout llDaiFukuan;
    @BindView(R.id.tv_complete_requestsh)
    TextView tvCompleteRequestsh;
    @BindView(R.id.ll_yi_compelte)
    LinearLayout llYiCompelte;
    @BindView(R.id.tv_cofrim_shou_hou)
    TextView tvFhShouHou;
    @BindView(R.id.tv_look_logistics)
    TextView tvLookLogistics;
    @BindView(R.id.ll_yi_delivery)
    LinearLayout llYiDelivery;
    @BindView(R.id.tv_delete_order)
    TextView tvDeleteOrder;
    @BindView(R.id.ll_yi_cancel)
    LinearLayout llYiCancel;
    @BindView(R.id.tv_invoice_name)
    TextView tvInvoiceName;
    @BindView(R.id.ll_invoice)
    LinearLayout llInvoice;
    @BindView(R.id.tv_yuer_des)
    TextView tvYuerDes;
    @BindView(R.id.tv_share_money)
    TextView tvShareMoney;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.view_share)
    View viewShare;

    private OrderDetailAdapter orderDeatailAdapter;

    private String tradeNo;//订单号
    private int orderIndex;//最外层recycleview 下标
    private OrderDetailBean orderDetailBean;
    private CommonPopupWindow popupWindow;
    private IWXAPI api;

    private long orderTime;//订单时间
    private int minute;//这是分钟
    private int second;//秒
    private TextView tvPayTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        orderDeatailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, selectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderDeatailAdapter);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();


    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
//            Log.e(TAG, "handleMessage:支付"+resultStatus );
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                ToastUtils.getInstanc(OrderDetailActivity.this).showToast("支付成功！！！");
            }
        }
    };

    private Timer timer;
    private TimerTask timerTask;
    //这是接收回来处理的消息
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (minute == 0) {
                if (second == 0) {
                    tvPayTime.setText("Time out !");
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        tvPayTime.setText("0" + minute + ":" + second);
                    } else {
                        tvPayTime.setText("0" + minute + ":0" + second);
                    }
                }
            } else {
                if (second == 0) {
                    second = 59;
                    minute--;
                    if (minute >= 10) {
                        tvPayTime.setText(minute + ":" + second);
                    } else {
                        tvPayTime.setText("0" + minute + ":" + second);
                    }
                } else {
                    second--;
                    if (second >= 10) {
                        if (minute >= 10) {
                            tvPayTime.setText(minute + ":" + second);
                        } else {
                            tvPayTime.setText("0" + minute + ":" + second);
                        }
                    } else {
                        if (minute >= 10) {
                            tvPayTime.setText(minute + ":0" + second);
                        } else {
                            tvPayTime.setText("0" + minute + ":0" + second);
                        }
                    }
                }
            }
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null) {
//            if (requestCode == MyAddressActivity.REQUEST_CODE) {
//                ShipAddressBean addressEntity = (ShipAddressBean) data.getSerializableExtra("address_entity");
//
//                tvName.setText(addressEntity.getName());
//                tvPhone.setText(addressEntity.getPhone());
//                tvDetialAddress.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCounty() + addressEntity.getAddress());
//                Log.e(TAG, "onActivityResult: " + addressEntity.getProvince());
//            }
//        }
//    }

    private void initData() {
        tradeNo = getIntent().getStringExtra("tradeNo");
        orderIndex = getIntent().getIntExtra("order_index", 0);
        Log.e(TAG, "initData: 最外层" + orderIndex);
        getData(loginUserId, tradeNo);
    }

    /**
     * 获取订单信息
     *
     * @param userId
     * @param orderId
     */
    private void getData(String userId, String orderId) {
        ModelLoading.getInstance(OrderDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("tradeNo", orderId);
            RequestInterface.payPrefix(OrderDetailActivity.this, params, TAG, ReqConstance.I_PAY_BILL_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            orderDetailBean = gson.fromJson(jsonResult, OrderDetailBean.class);
                            tvName.setText(orderDetailBean.getReceiveName());
                            tvPhone.setText(orderDetailBean.getPhone());
                            tvDetialAddress.setText(orderDetailBean.getAddress());
                            tvAllPrice.setText("￥" + String.valueOf(orderDetailBean.getTotalMoney()));
                            tvTransport.setText("￥" + String.valueOf(orderDetailBean.getTransport()));
                            tvBcCoin.setText("-￥" + String.valueOf(orderDetailBean.getBcCoin()));
                            tvBcMoney.setText("-￥" + String.valueOf(orderDetailBean.getBcMoney()));
                            tvShijiPay.setText("￥" + String.valueOf(orderDetailBean.getTotalFee()));
                            //红包商品支付的  余额---》红包余额
                            if (orderDetailBean.getPayType() == Constant.PAY_TYPE_RED_PACK) {
                                tvYuerDes.setText("红包余额");
                            } else {
                                tvYuerDes.setText("余额");
                            }
                            //分享减免是否显示
                            tvShareMoney.setText("-￥" + String.valueOf(orderDetailBean.getShareMoney()));

                            if(orderDetailBean.getShareMoney().equals("0")){
                                rlShare.setVisibility(View.GONE);
                                viewShare.setVisibility(View.GONE);
                            }else{
                                rlShare.setVisibility(View.VISIBLE);
                                viewShare.setVisibility(View.VISIBLE);
                            }

                            if (TextUtils.isEmpty(orderDetailBean.getMemo())) {
                                editMemo.setText("暂无备注~~~");
                            } else {
                                editMemo.setText(orderDetailBean.getMemo());
                            }
                            orderTime = orderDetailBean.getCreateTime();
                            tvOrderTime.setText("订单时间：" + DateUtils.longToStringTimed(orderDetailBean.getCreateTime()));
                            tvOrderNo.setText(orderDetailBean.getTradeNo());
                            if (orderDetailBean.getUserInvoice() != null) {
                                llInvoice.setVisibility(View.VISIBLE);
                                if (orderDetailBean.getUserInvoice().getType() == 1) {//单位
                                    tvInvoiceName.setText("单位名称：" + orderDetailBean.getUserInvoice().getName());
                                } else {
                                    tvInvoiceName.setText("个人姓名：" + orderDetailBean.getUserInvoice().getName());
                                }
                            } else {
                                llInvoice.setVisibility(View.GONE);
                            }

                            selectList.addAll(orderDetailBean.getBillDetailList());
                            orderDeatailAdapter.notifyDataSetChanged();

                            if (!orderDetailBean.isStatus()) {//未支付
                                llJiaoyiMx.setVisibility(View.VISIBLE);
                                tvOrderStatus.setText("待付款");
                                llDaiFukuan.setVisibility(View.VISIBLE);
                                llYiCompelte.setVisibility(View.GONE);
                                llYiDelivery.setVisibility(View.GONE);
                                llYiCancel.setVisibility(View.GONE);

                                if (orderDetailBean.getBillStatus() == 3) {//已取消
                                    tvOrderStatus.setText("已取消");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.GONE);
                                    llYiDelivery.setVisibility(View.GONE);
                                    llYiCancel.setVisibility(View.VISIBLE);
                                }
                            } else {//已经支付
//                                llJiaoyiMx.setVisibility(View.GONE);
                                if (orderDetailBean.getBillStatus() == 0) {// 0待处理，1已发货，2已收货，3取消订单  4 退款处理中  5 已退款
                                    tvOrderStatus.setText("待发货");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.GONE);
                                    llYiDelivery.setVisibility(View.GONE);
                                    llYiCancel.setVisibility(View.GONE);
                                } else if (orderDetailBean.getBillStatus() == 1) {
                                    tvOrderStatus.setText("已发货");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.GONE);
                                    llYiDelivery.setVisibility(View.VISIBLE);
                                    llYiCancel.setVisibility(View.GONE);
                                } else if (orderDetailBean.getBillStatus() == 2) {
                                    tvOrderStatus.setText("已完成");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.VISIBLE);
                                    llYiDelivery.setVisibility(View.GONE);
                                    llYiCancel.setVisibility(View.GONE);
                                } else if (orderDetailBean.getBillStatus() == 4) {
                                    tvOrderStatus.setText("售后处理中");
                                    tvCompleteRequestsh.setText("联系客服");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.VISIBLE);
                                    llYiDelivery.setVisibility(View.GONE);
                                    llYiCancel.setVisibility(View.GONE);
                                } else if (orderDetailBean.getBillStatus() == 5) {//已退款
                                    tvOrderStatus.setText("已退款");
                                    llDaiFukuan.setVisibility(View.GONE);
                                    llYiCompelte.setVisibility(View.GONE);
                                    llYiDelivery.setVisibility(View.GONE);
                                    llYiCancel.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                        tvToPay.setClickable(false);
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderDetailActivity.this).showToast("网络不在状态...");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算合计价格
     */
//    private void calculationPrice() {
////        描述：
////        1，非VIP只能微信支付宝支付，并且计算邮费
////        2，VIP才可以使用仓币
////        3，代理才可以使用仓币+余额
//        double zongjia = 0.0;
//        double transPortPrice = Double.valueOf(tvTransport.getText().toString());//邮费
//        double bcCoinPrice = Double.valueOf(orderAddres.getBcCoin());//仓币
//        double bcMoneyPrice = Double.valueOf(orderAddres.getBcMoney());//余额
//
//        if (isVip) {// VIP
//            switchBcmoney.setClickable(false);
//            if(switchBcoin.isChecked()){
//                zongjia = BigDecimalUtil.geSub(totalPrice, bcCoinPrice);
//            }else{
//                zongjia = BigDecimalUtil.geSub(totalPrice, 0);
//            }
//            if (isAgent) {//代理
//                switchBcmoney.setClickable(true);
//                if(switchBcoin.isChecked()){
//                    zongjia = BigDecimalUtil.geSub(totalPrice, bcCoinPrice);
//                }else{
//                    zongjia = BigDecimalUtil.geSub(totalPrice, 0);
//                }
//
//                if(switchBcmoney.isChecked()){
//                    zongjia = BigDecimalUtil.geSub(zongjia, bcMoneyPrice);
//                }else{
//                    zongjia = BigDecimalUtil.geSub(zongjia, 0);
//                }
//            }
//        } else {//普通用户是不能用仓币
//            switchBcoin.setClickable(false);
//            switchBcmoney.setClickable(false);
//            zongjia = BigDecimalUtil.getAdd(totalPrice, transPortPrice);
//        }
//
//        tvHejiPrice.setText("" + zongjia);
//    }
    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        TextView tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        TextView tv_close = (TextView) view.findViewById(R.id.tv_close);
        ImageView img_wechat_pay = (ImageView) view.findViewById(R.id.img_wechat_pay);
        ImageView img_ali_pay = (ImageView) view.findViewById(R.id.img_ali_pay);
        tv_pay_money.setText(tvShijiPay.getText().toString());

        LinearLayout linearLayout = view.findViewById(R.id.ll_pay_time);
        tvPayTime = view.findViewById(R.id.tv_pay_time);
        TextView tv_pay_text = view.findViewById(R.id.tv_pay_text);
        linearLayout.setVisibility(View.VISIBLE);

        tvPayTime.setText(DateUtils.longToStringTimed(orderTime));
        //判断订单失效 30分钟
        Date date = new Date(System.currentTimeMillis());
        long diff = date.getTime() - orderTime;
        Log.e(TAG, "timeCha: " + 30 * 60 * 1000);

        if (diff > 60 * 30 * 1000) {
            tv_pay_text.setVisibility(View.GONE);
            tvPayTime.setText("该订单支付时间已过期，不能支付!!!");
            img_ali_pay.setEnabled(false);
            img_wechat_pay.setEnabled(false);
        } else {
            //30分钟-相差的时间=剩下的时间  倒计时
            diff = 30 * 60 * 1000 - diff;
            img_ali_pay.setEnabled(true);
            img_wechat_pay.setEnabled(true);
            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
//            System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒");
            minute = Integer.valueOf(String.valueOf(min));
            second = Integer.valueOf(String.valueOf(s));
            Log.e(TAG, "getChildView: 能支付!!! 显示倒计时" + minute);
            Log.e(TAG, "getChildView: 能支付!!! 显示倒计时" + second);
            tvPayTime.setText(minute + ":" + second);
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 0;
                    handler.sendMessage(msg);
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        }


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

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }


    //全屏弹出
    public void showPayPop(View view) {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_pay, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_pay)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 支付操作
     *
     * @param userId
     * @param payType 支付类型：0仓币，1.微信，2支付宝，4仓币+余额
     */
    private void payOperation(String userId, final int payType) {
        ModelLoading.getInstance(OrderDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            for (OrderDetailBean.BillDetailListBean selectEntity : selectList) {
                JSONObject object = new JSONObject();
                object.put("skuName", selectEntity.getSkuName());
                object.put("unitPrice", selectEntity.getUnitPrice());
                object.put("goodsId", selectEntity.getGoodsId());
                object.put("goodsImage", selectEntity.getGoodsImage());
                object.put("goodsName", selectEntity.getGoodsName());
                object.put("userid", selectEntity.getUserid());
                object.put("skuId", selectEntity.getSkuId());
                object.put("skuImage", selectEntity.getSkuImage());
                object.put("skuNum", selectEntity.getSkuNum());
                jsonArray.put(object);
            }

            params.put("userid", userId);
            params.put("address", tvDetialAddress.getText().toString());
            params.put("receiveName", tvName.getText().toString());
            params.put("phone", tvPhone.getText().toString());
            params.put("memo", editMemo.getText().toString());
            params.put("pwd", "");
            params.put("tradeType", Constant.PAY_TYPE_SHOP);
            params.put("payType", payType);
            params.put("billDetailList", jsonArray);

            RequestInterface.payPrefix(OrderDetailActivity.this, params, TAG, ReqConstance.I_PAY_SHOPPING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
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
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderDetailActivity.this).showToast("网络不在状态...");
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
                PayTask alipay = new PayTask(OrderDetailActivity.this);
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

    @OnClick({R.id.tv_kefu, R.id.tv_copy, R.id.tv_cancel_order, R.id.tv_complete_requestsh, R.id.tv_cofrim_shou_hou, R.id.tv_look_logistics, R.id.tv_delete_order, R.id.tv_to_pay, R.id.img_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_kefu://右上角客服
//                Intent kefu=new Intent(OrderDetailActivity.this, BscKeFuActivity.class);
//                kefu.putExtra("web_url",Constant.WEB_KE_FU);
//                startActivity(kefu);
                enterKeFu();
                break;
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvOrderNo.getText().toString().trim());
                ToastUtils.getInstanc(OrderDetailActivity.this).showToast("复制成功");
                break;
            case R.id.tv_cancel_order://取消订单：
                new DeleteDialog(OrderDetailActivity.this, R.style.dialogphone, "您确定取消吗？", new DeleteDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            cancelOrderDo(tradeNo, loginUserId);
                        }
                    }
                }).show();

                break;
            case R.id.tv_complete_requestsh://已完成申请售后
//                Intent requestsh=new Intent(OrderDetailActivity.this, BscKeFuActivity.class);
//                requestsh.putExtra("web_url",Constant.WEB_KE_FU);
//                startActivity(requestsh);
                enterKeFu();
                break;
            case R.id.tv_cofrim_shou_hou://已发货---确认收货
                confirmReciveOrder(tradeNo);
                break;
            case R.id.tv_look_logistics://已发货---查看物流
                Intent intent = new Intent(OrderDetailActivity.this, LogisticsActivity.class);
                intent.putExtra("trade_no", tradeNo);
                startActivity(intent);
                break;
            case R.id.tv_delete_order://删除订单
                new DeleteDialog(OrderDetailActivity.this, R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            deleteOrderDo(tradeNo, loginUserId);
                        }
                    }
                }).show();


                break;
//            case R.id.img_edit://修改地址
//                Intent edit = new Intent(OrderDetailActivity.this, MyAddressActivity.class);
//                startActivityForResult(edit, MyAddressActivity.REQUEST_CODE);
//
//                break;
            case R.id.tv_to_pay:
                showPayPop(tvToPay);
                Log.e(TAG, "onViewClicked:tv_to_pay ");
                break;
        }
    }


    /**
     * 取消订单
     */
    private void cancelOrderDo(String tradeNo, String userId) {
        ModelLoading.getInstance(OrderDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("userid", userId);
            RequestInterface.payPrefix(OrderDetailActivity.this, params, TAG, ReqConstance.I_PAY_CANCLE_BILL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    if (code == 0) {
                        llYiCancel.setVisibility(View.VISIBLE);
                        llDaiFukuan.setVisibility(View.GONE);
                        tvOrderStatus.setText("已取消");
                    } else {
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderDetailActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除订单
     *
     * @param tradeNo
     * @param userId
     */
    private void deleteOrderDo(String tradeNo, String userId) {
        ModelLoading.getInstance(OrderDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("userid", userId);
            RequestInterface.payPrefix(OrderDetailActivity.this, params, TAG, ReqConstance.I_PAY_DELETE_BILL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    if (code == 0) {
                        EventBus.getDefault().post(new OrderIndexEvent(orderIndex));
                        finish();
                    } else {
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderDetailActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 确认收货
     */
    private void confirmReciveOrder(String tradeNoOrder) {
        ModelLoading.getInstance(OrderDetailActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);
            params.put("tradeNo", tradeNoOrder);
            RequestInterface.payPrefix(OrderDetailActivity.this, params, TAG, ReqConstance.I_PAY_BILL_RECEIPT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    if (code == 0) {
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                        getData(loginUserId, tradeNoOrder);
                    } else {
                        ToastUtils.getInstanc(OrderDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderDetailActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderDetailActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入客服
     */
    private void enterKeFu() {
        UdeskSDKManager.getInstance().initApiKey(OrderDetailActivity.this, Constant.UDESK_DOMAN,
                Constant.UDESK_KEY, Constant.UDESK_APPID);
        String sdkToken = (String) SPUtils.getInstance(OrderDetailActivity.this).get(Constant.USER_ID, "");

        Map<String, String> info = new HashMap<String, String>();
        //以下信息是可选
        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, (String) SPUtils.getInstance(OrderDetailActivity.this).get(Constant.USER_NICK_NAME, ""));//昵称
        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, (String) SPUtils.getInstance(OrderDetailActivity.this).get(Constant.USER_MOBILE, ""));//手机

        UdeskConfig.Builder builder = new UdeskConfig.Builder();
        builder.setDefualtUserInfo(info);
        builder.setCustomerUrl((String) SPUtils.getInstance(OrderDetailActivity.this).get(Constant.USER_AVATAR, ""));//用户头像
        builder.setFirstMessage("你好我想咨询下这个订单号：" + tradeNo);
        builder.setNavigations(true, UdeskHelp.getInstance().getNavigations(), new INavigationItemClickCallBack() {
            @Override
            public void callBack(Context context, ChatActivityPresenter mPresenter, NavigationMode navigationMode) {
                if (navigationMode.getId() == 1) {
                    mPresenter.sendTxtMessage(tradeNo);
                }
            }
        });
        UdeskSDKManager.getInstance().entryChat(OrderDetailActivity.this, builder.build(), sdkToken);
    }
}
