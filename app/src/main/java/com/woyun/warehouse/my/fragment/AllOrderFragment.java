package com.woyun.warehouse.my.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
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
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.LogisticsActivity;
import com.woyun.warehouse.baseparson.event.OrderIndexEvent;
import com.woyun.warehouse.bean.OrderListBean;
import com.woyun.warehouse.bean.WxPayBean;

import com.woyun.warehouse.my.adapter.AllOrderAdapter;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.utils.UdeskHelp;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DeleteDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.udesk.UdeskSDKManager;
import cn.udesk.callback.INavigationItemClickCallBack;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.NavigationMode;
import cn.udesk.presenter.ChatActivityPresenter;
import udesk.core.UdeskConst;

/**
 * 所有订单
 */
public class AllOrderFragment extends BaseFragment implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "AllOrderFragment";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.id_empty_view)
    View emptyView;

    private String userId;
    private int pager = 1;
    private List<OrderListBean.ContentBean> listData = new ArrayList<>();
    private AllOrderAdapter allOrderAdapter;
    private IWXAPI api;
    private CommonPopupWindow popupWindow;

    double totalFee = 0.0f;
    private String orderNo;

    private long orderTime;//订单时间
    private int minute;//这是分钟
    private int second;//秒
    private TextView tvPayTime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        api = WXAPIFactory.createWXAPI(getActivity(), Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        allOrderAdapter = new AllOrderAdapter(getActivity(), listData);
        recyclerView.setAdapter(allOrderAdapter);

        initClickEvent();
        return view;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(OrderIndexEvent orderIndexEvent) {
        listData.remove(orderIndexEvent.getIndex());
        allOrderAdapter.notifyDataSetChanged();
        if(listData.size()==0){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                ToastUtils.getInstanc(getActivity()).showToast("支付成功！！！");
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

    private void initClickEvent() {
        //btn event
        allOrderAdapter.setOnButtonClickListener(new AllOrderAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, final int positon) {
                switch (view.getId()) {
                    case R.id.tv_cancel_order:
                        new DeleteDialog(getActivity(), R.style.dialogphone, "您确定取消吗？", new DeleteDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    cancelOrderDo(listData.get(positon).getTradeNo(), userId, positon);
                                }
                            }
                        }).show();

                        break;
                    case R.id.tv_pay_order:
//                        ToastUtils.getInstanc(getActivity()).showToast("付款" + positon);
                        showPayPop(positon);
                        break;
                    case R.id.tv_request_sh://已完成 ---申请售后
//                        Intent kefu=new Intent(getActivity(), BscKeFuActivity.class);
//                        kefu.putExtra("web_url",Constant.WEB_KE_FU);
//                        startActivity(kefu);
                        UdeskSDKManager.getInstance().initApiKey(getActivity(), Constant.UDESK_DOMAN,
                                Constant.UDESK_KEY, Constant.UDESK_APPID);
                        String sdkToken= (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID,"");

                        Map<String, String> info = new HashMap<String, String>();
                        //以下信息是可选
                        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, (String) SPUtils.getInstance(getActivity()).get(Constant.USER_NICK_NAME,""));//昵称
                        info.put(UdeskConst.UdeskUserInfo.CELLPHONE,(String) SPUtils.getInstance(getActivity()).get(Constant.USER_MOBILE,""));//手机

                        UdeskConfig.Builder builder = new UdeskConfig.Builder();
                        builder.setDefualtUserInfo(info);
                        builder.setCustomerUrl((String) SPUtils.getInstance(getActivity()).get(Constant.USER_AVATAR,""));//用户头像
                        builder.setFirstMessage("你好我想咨询下这个订单号："+listData.get(positon).getTradeNo());
                        builder.setNavigations(true, UdeskHelp.getInstance().getNavigations(), new INavigationItemClickCallBack() {
                            @Override
                            public void callBack(Context context, ChatActivityPresenter mPresenter, NavigationMode navigationMode) {
                                if (navigationMode.getId() == 1) {
                                    mPresenter.sendTxtMessage(listData.get(positon).getTradeNo());
                                }
                            }
                        });
                        UdeskSDKManager.getInstance().entryChat(getActivity(), builder.build(), sdkToken);

                        break;
                    case R.id.tv_confirm_receipt:
//                        ToastUtils.getInstanc(getActivity()).showToast("已发货  确认收货" + positon);
                        PendingReceiveFragment.confirmOrder(getActivity(),userId,listData.get(positon).getTradeNo(),positon,listData,allOrderAdapter);
                        break;
                    case R.id.tv_look_logistics:
//                        ToastUtils.getInstanc(getActivity()).showToast("已发货 查看物流" + positon);
                        Intent intent=new Intent(getActivity(), LogisticsActivity.class);
                        intent.putExtra("trade_no",listData.get(positon).getTradeNo());
                        startActivity(intent);
                        break;
                    case R.id.tv_delete_order:
//                        ToastUtils.getInstanc(getActivity()).showToast("取消 删除订单" + positon);
                        new DeleteDialog(getActivity(), R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    deleteOrderDo(listData.get(positon).getTradeNo(), userId, positon);
                                }
                            }
                        }).show();

                        break;
                    case R.id.tv_delete_ywcorder://（已完成--删除）
                        new DeleteDialog(getActivity(), R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    deleteOrderDo(listData.get(positon).getTradeNo(), userId, positon);
                                }
                            }
                        }).show();
                        break;
                    case R.id.tv_check_logistics://（已完成--查看物流）
                        Intent toIntent=new Intent(getActivity(), LogisticsActivity.class);
                        toIntent.putExtra("trade_no",listData.get(positon).getTradeNo());
                        startActivity(toIntent);
                        break;
                }
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
    }

    private void initData() {
        //触发自动刷新
        mRefreshLayout.autoRefresh();
//      mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        pager = 1;
                        getData(userId, pager);
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
                        getData(userId, pager);
                        mRefreshLayout.finishLoadmore();

//                        } else {
//                            Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        }
                    }
                }, 1000);
            }
        });
    }

    /**
     * 获取全部订单
     */
    private void getData(String userId, int page) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            RequestInterface.payPrefix(getActivity(), params, TAG, ReqConstance.I_PAY_BILL_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("content");
                            List<OrderListBean.ContentBean> shipAddressBeans = gson.fromJson(contentArray.toString(), new TypeToken<List<OrderListBean.ContentBean>>() {
                            }.getType());
                            listData.addAll(shipAddressBeans);
                            if (listData.size() == 0) {
                                emptyView.setVisibility(View.VISIBLE);
                            } else {
                                emptyView.setVisibility(View.GONE);
                            }
                            allOrderAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 取消订单
     */
    private void cancelOrderDo(String tradeNo, String userId, final int position) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("userid", userId);
            RequestInterface.payPrefix(getActivity(), params, TAG, ReqConstance.I_PAY_CANCLE_BILL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    if (code == 0) {
                        listData.get(position).setBillStatus(3);//已取消
                        if (listData.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                        }
                        allOrderAdapter.notifyDataSetChanged();
//                       listData.get(position)

                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
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
     * @param position
     */
    private void deleteOrderDo(String tradeNo, String userId, final int position) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("userid", userId);
            RequestInterface.payPrefix(getActivity(), params, TAG, ReqConstance.I_PAY_DELETE_BILL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    if (code == 0) {
                        listData.remove(position);
                        if (listData.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                        }
                        allOrderAdapter.notifyDataSetChanged();
//                       listData.get(position)

                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新发起支付  付款
     *
     * @param tradeNo
     * @param payType 支付类型：1.微信，2支付宝
     */
    private void payOrderDo(String tradeNo, final int payType) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("payType", payType);
            RequestInterface.payPrefix(getActivity(), params, TAG, ReqConstance.I_PAY_BILL_AGAIN, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
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
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                        }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 支付弹出
     */
    public void showPayPop(int position) {
        totalFee = listData.get(position).getTotalFee();
        orderNo=listData.get(position).getTradeNo();
        orderTime = listData.get(position).getCreateTime();

        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_pay, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_pay)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        TextView tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        TextView tv_close = (TextView) view.findViewById(R.id.tv_close);
        ImageView img_wechat_pay = (ImageView) view.findViewById(R.id.img_wechat_pay);
        ImageView img_ali_pay = (ImageView) view.findViewById(R.id.img_ali_pay);
        LinearLayout linearLayout = view.findViewById(R.id.ll_pay_time);
        tvPayTime = view.findViewById(R.id.tv_pay_time);
        TextView tv_pay_text = view.findViewById(R.id.tv_pay_text);
        linearLayout.setVisibility(View.VISIBLE);

        tv_pay_money.setText(String.valueOf(totalFee));
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
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                }
                payOrderDo(orderNo,1);
            }
        });

        //支付宝
        img_ali_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                }
                payOrderDo(orderNo,2);
            }
        });

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    if (timerTask != null) {
                        timerTask = null;
                    }
                }
            }
        });
    }

    /**
     * 支付宝支付
     */
    private void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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
