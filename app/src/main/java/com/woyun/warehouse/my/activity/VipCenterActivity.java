package com.woyun.warehouse.my.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
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
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.bean.VipCenterBean;
import com.woyun.warehouse.bean.WxPayBean;
import com.woyun.warehouse.cart.adapter.PopDownAdapter;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.my.adapter.CartVipAdapter;
import com.woyun.warehouse.my.adapter.VipQuanYiAdapter;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.BuyVipDialog;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DropEditText;
import com.woyun.warehouse.view.InvoiceDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 * 会员中心
 */
public class VipCenterActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "VipCenterActivity";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_tan_hao)
    TextView tvTanHao;
    @BindView(R.id.tv_bc_coin)
    TextView tvBcCoin;
    @BindView(R.id.ll_bc_coin_mx)
    LinearLayout llBcCoinMx;
    @BindView(R.id.tv_yi_hde)
    TextView tvYiHde;
    @BindView(R.id.tv_wei_ffang)
    TextView tvWeiFfang;
    @BindView(R.id.tv_year_vip)
    TextView tvYearVip;
    @BindView(R.id.recycler_vip_qy)
    RecyclerView recyclerVipQy;
    @BindView(R.id.recycler_vip_lb)
    RecyclerView recyclerVipLb;

    @BindView(R.id.img_jh_vip)
    ImageView imgJhVip;
    @BindView(R.id.img_buy_vip)
    ImageView imgBuyVip;
    @BindView(R.id.ll_jhbuy_vip)
    LinearLayout llJhbuyVip;
    @BindView(R.id.tv_vip_time)
    TextView tvVipTime;
    @BindView(R.id.ll_xu_fei)
    LinearLayout llXuFei;
    @BindView(R.id.img_detail)
    ImageView imgDetail;
    @BindView(R.id.tv_vip_gz)
    TextView tvVipGz;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.tv_wx_account)
    TextView tvWxAccount;
    @BindView(R.id.btn_copy_account)
    Button btnCopyAccount;

    private boolean issVip;
    private boolean istakeGift;//是否领取过
    private VipQuanYiAdapter quanYiAdapter;
    private List<String> quanYiImages = new ArrayList<>();

    private CartVipAdapter cartVipAdapter;
    private List<VipCenterBean.GoodsListBean> goodsListBeanList = new ArrayList<>();

    private CommonPopupWindow popupWindow;
    private ShipAddressBean orderAddres;
    //领取礼包控件
    private TextView gtv_no_address, gtv_name, gtv_phone, gtv_detial_address, gtv_close;
    private ImageView gimg_edit;
    private RelativeLayout grl_have_address;
    private Button gbtn_confirm_receive;

    //开通会员控件
    private TextView ptv_no_address, ptv_name, ptv_phone, ptv_detial_address, ptv_close, ptv_pay_money, tv_invoice, tv_vip_xuzhi;
    private EditText edit_invide_code;
    private ImageView pimg_edit, img_wechat_pay, img_ali_pay;
    private RelativeLayout prl_have_address;

    private String vipPrice;//vip 年费
    private String vipTypeId;//VIP，代理类型ID
    private String endTimeVip;//到期时间
    private IWXAPI api;
    private String downUrl;

    private InvoiceDialog invoiceDialog;
    private List<ShipAddressBean.InvoiceListBean> peopleDatas = new ArrayList<>();//个人发票集合
    private List<ShipAddressBean.InvoiceListBean> unitDatas = new ArrayList<>();//单位发票集合
    private List<ShipAddressBean.InvoiceListBean> allInvoiceDatas = new ArrayList<>();//所有发票集合
    private boolean isEditInvoice;//是否修改发票
    private int editInvoiceId;//修改发票的id
    private boolean isUseInvoice;//是否使用发票
    private int invoiceId;
    private String province, city, county;//省市区

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_center);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        mImmersionBar.titleBar(toolBar);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        quanYiAdapter = new VipQuanYiAdapter(VipCenterActivity.this, quanYiImages);
        recyclerVipQy.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerVipQy.setNestedScrollingEnabled(false);
        recyclerVipQy.setAdapter(quanYiAdapter);

        cartVipAdapter = new CartVipAdapter(VipCenterActivity.this, goodsListBeanList);
        recyclerVipLb.setNestedScrollingEnabled(false);
        recyclerVipLb.setLayoutManager(new LinearLayoutManager(this));
        recyclerVipLb.setAdapter(cartVipAdapter);
        initData(loginUserId);

        cartVipAdapter.setOnItemClickListener(position -> {
//            Intent detail=new Intent(VipCenterActivity.this, GoodsDetailVipActivity.class);
            Intent detail = new Intent(VipCenterActivity.this, GoodsDetailActivity.class);
            detail.putExtra("goods_id", goodsListBeanList.get(position).getGoodsId());
            detail.putExtra("end_time", endTimeVip);
            detail.putExtra("from_id", 3);
            startActivity(detail);
        });


        //选中事件
        cartVipAdapter.setCheckInterface(new CartVipAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                goodsListBeanList.get(position).setCheck(isChecked);
                if (isChecked) {
                    fanXuan(position);
                }
//                else{
//                    fanXuan2(position);
//                }
                cartVipAdapter.notifyDataSetChanged();
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        quanYiImages.clear();
                        goodsListBeanList.clear();
                        initData(loginUserId);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String share= (String) SPUtils.getInstance(VipCenterActivity.this).get(Constant.SHARE_KEY,"");
//        ToastUtils.getInstanc(VipCenterActivity.this).showToast(share);
    }

    //支付成功后回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SaveUserEvent event) {
        Log.e(TAG, "Event: " + event.isSave());
        if (event.isSave()) {
            initData(loginUserId);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                ToastUtils.getInstanc(VipCenterActivity.this).showToast("支付成功！！！");
                initData(loginUserId);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 100) {
                ShipAddressBean addressEntity = (ShipAddressBean) data.getSerializableExtra("address_entity");
                gtv_no_address.setVisibility(View.GONE);
                grl_have_address.setVisibility(View.VISIBLE);
                gtv_name.setText(addressEntity.getName());
                gtv_phone.setText(addressEntity.getPhone());
                gtv_detial_address.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCounty() + addressEntity.getAddress());
                province = addressEntity.getProvince();
                city = addressEntity.getCity();
                county = addressEntity.getCounty();
                Log.e(TAG, "onActivityResult: " + addressEntity.getProvince());
            }
            if (requestCode == 101) {
                ShipAddressBean addressEntity = (ShipAddressBean) data.getSerializableExtra("address_entity");
                ptv_no_address.setVisibility(View.GONE);
                prl_have_address.setVisibility(View.VISIBLE);
                ptv_name.setText(addressEntity.getName());
                ptv_phone.setText(addressEntity.getPhone());
                ptv_detial_address.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCounty() + addressEntity.getAddress());
                province = addressEntity.getProvince();
                city = addressEntity.getCity();
                county = addressEntity.getCounty();
                Log.e(TAG, "onActivityResult: " + addressEntity.getProvince());
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

    private void fanXuan2(int positon) {
        for (int i = 0; i < goodsListBeanList.size(); i++) {
            if (positon == i) {
                goodsListBeanList.get(i).setCheck(false);
            } else {
                goodsListBeanList.get(i).setCheck(true);
            }
        }
    }


    /**
     * 获取数据
     */
    private void initData(String userId) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.userPrefix(VipCenterActivity.this, params, TAG, ReqConstance.I_USER_VIP_CENTER, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    tokenTimeLimit(VipCenterActivity.this, code);
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            VipCenterBean vipCenterBean = gson.fromJson(object.toString(), VipCenterBean.class);
                            SPUtils.getInstance(VipCenterActivity.this).put(Constant.USER_IS_VIP, vipCenterBean.isIsVip());
                            issVip = vipCenterBean.isIsVip();
                            istakeGift = vipCenterBean.isTakeGift();//是否领取过
                            vipPrice = vipCenterBean.getVipType().getPrice();
                            vipTypeId = String.valueOf(vipCenterBean.getVipType().getId());
                            if (issVip) {
                                tvYearVip.setText("年费会员");
                                //是否领取
                                if (istakeGift) {
                                    llJhbuyVip.setVisibility(View.GONE);
                                    llXuFei.setVisibility(View.VISIBLE);
                                    if (vipCenterBean.getVip() != null) {
                                        long endDate = vipCenterBean.getVip().getEndDate();
                                        String end = DateUtils.longToStringTime2(endDate);
                                        endTimeVip = end;
                                        tvVipTime.setText(end + "到期");
                                    }

                                } else {
                                    llJhbuyVip.setVisibility(View.VISIBLE);
                                    llXuFei.setVisibility(View.GONE);
                                }
                            } else {
                                tvYearVip.setText("普通用户");
                                llJhbuyVip.setVisibility(View.VISIBLE);
                                llXuFei.setVisibility(View.GONE);
                            }

                            quanYiImages.clear();
                            goodsListBeanList.clear();
                            List<String> imgs = vipCenterBean.getProfitImage();
                            quanYiImages.addAll(imgs);
                            quanYiAdapter.notifyDataSetChanged();

                            tvBcCoin.setText(String.valueOf(vipCenterBean.getBcCoin()));
                            tvYiHde.setText(String.valueOf(vipCenterBean.getTakeBcCoin()));
                            tvWeiFfang.setText(String.valueOf(vipCenterBean.getGrantCbCoin()));

                            List<VipCenterBean.GoodsListBean> goodsList = vipCenterBean.getGoodsList();
                            goodsListBeanList.addAll(goodsList);
                            cartVipAdapter.notifyDataSetChanged();
                            downUrl = vipCenterBean.getVipewm();
                            Glide.with(VipCenterActivity.this).load(vipCenterBean.getVipewm()).into(imgQrCode);
                            tvWxAccount.setText(vipCenterBean.getWxh());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        imgJhVip.setClickable(false);
                        imgBuyVip.setClickable(false);
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    imgJhVip.setClickable(false);
                    imgBuyVip.setClickable(false);
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.vip_center);
        mImmersionBar.statusBarDarkFont(true).init();

    }

    @OnClick({R.id.tv_vip_gz, R.id.tv_tan_hao, R.id.ll_bc_coin_mx, R.id.img_jh_vip, R.id.img_buy_vip, R.id.ll_xu_fei, R.id.img_detail,R.id.btn_copy_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip_gz:
                Intent vipRules = new Intent(VipCenterActivity.this, MyWebViewActivity.class);
                vipRules.putExtra("web_url", Constant.WEB_VIP);
                startActivity(vipRules);
                break;
            case R.id.img_detail:
            case R.id.tv_tan_hao:
                if (imgDetail.getVisibility() == View.VISIBLE) {
                    imgDetail.setVisibility(View.GONE);
                } else {
                    imgDetail.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ll_bc_coin_mx://仓币明细
                Intent mingxi = new Intent(VipCenterActivity.this, CangCoinDetailActivity.class);
                mingxi.putExtra("hb_type", 1);
                startActivity(mingxi);
                break;
            case R.id.img_jh_vip://激活领取
                if (issVip) {
                    if (istakeGift) {//已经领取了
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("您已领取过该礼包!");
                    } else {//未领取
                        if (isCheckOneGoods()) {
                            getDefaultAddress(loginUserId, 1);
                        } else {
                            ToastUtils.getInstanc(VipCenterActivity.this).showToast("请选择年费会员礼包!");
                        }
                    }
                } else {
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast("您还不是会员！");
                }
                break;
            case R.id.img_buy_vip://购买vip
                if (issVip) {
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast("您已开通VIP！");
                } else {
                    if (isCheckOneGoods()) {
                        getDefaultAddress(loginUserId, 2);
                    } else {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("请选择年费会员礼包!");
                    }
                }
                break;
            case R.id.ll_xu_fei://续费
//                if (isCheckOneGoods()) {
//                    getDefaultAddress(loginUserId, 3);
//                } else {
//                    ToastUtils.getInstanc(VipCenterActivity.this).showToast("请选择年费会员礼包!");
//                }
                break;
            case R.id.btn_copy_account://复制微信账号
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvWxAccount.getText().toString().trim());
                ToastUtils.getInstanc(VipCenterActivity.this).showToast("复制成功");
                break;
        }
    }

    /**
     * 默认地址+仓币+余额
     *
     * @param userId
     * @type 1  领取礼包弹窗   2 购买会员弹窗  3 续费弹窗
     */
    private void getDefaultAddress(String userId, final int type) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.userPrefix(VipCenterActivity.this, params, TAG + "ADDRESS", ReqConstance.I_DEFALUT_ADDREDSS_MONEY_CB, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            orderAddres = gson.fromJson(jsonResult, ShipAddressBean.class);
                            allInvoiceDatas = orderAddres.getInvoiceList();
                            if (type == 1) {
                                showReceiveLiBao();
                            } else if (type == 2) {
//                                    showBuyVip();
                                showDialogBuy(type);
                            } else {
                                showDialogBuy(type);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 领取礼包
     *
     * @param userId
     */
    private void receiveGiftRequest(String userId) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
        try {
            JSONArray array = new JSONArray();
            for (int i = 0; i < goodsListBeanList.size(); i++) {
                if (goodsListBeanList.get(i).isCheck()) {
                    JSONObject object = new JSONObject();
                    object.put("goodsId", goodsListBeanList.get(i).getGoodsId());
                    object.put("goodsImage", goodsListBeanList.get(i).getImage());
                    object.put("goodsName", goodsListBeanList.get(i).getName());
                    object.put("userid", userId);
                    array.put(object);
                }
            }

            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("province", province);
            params.put("city", city);
            params.put("county", county);
            params.put("address", gtv_detial_address.getText().toString().trim());
            params.put("receiveName", gtv_name.getText().toString().trim());
            params.put("phone", gtv_phone.getText().toString().trim());

            params.put("billDetailList", array);
            RequestInterface.userPrefix(VipCenterActivity.this, params, TAG, ReqConstance.I_USER_VIP_GIFT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    if (code == 0) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    } else {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 领取礼包弹窗
     */
    private void showReceiveLiBao() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_reveive_gifts, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_reveive_gifts)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .setOutsideTouchable(false)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

    }

    /**
     * dialog 购买(续费)Vip
     *
     * @param type 3 续费  不需要传fuserid
     */
    private void showDialogBuy(int type) {
        final BuyVipDialog view = new BuyVipDialog(VipCenterActivity.this);
        view.show();
        //popup_buy_vip 布局里的View
        ptv_no_address = (TextView) view.findViewById(R.id.tv_no_address);
        prl_have_address = (RelativeLayout) view.findViewById(R.id.rl_have_address);
        tv_invoice = view.findViewById(R.id.tv_invoice);
        tv_vip_xuzhi = view.findViewById(R.id.tv_vip_xuzhi);
        ptv_close = (TextView) view.findViewById(R.id.tv_close);
        ptv_name = (TextView) view.findViewById(R.id.tv_name);
        ptv_phone = (TextView) view.findViewById(R.id.tv_phone);
        ptv_detial_address = (TextView) view.findViewById(R.id.tv_detial_address);
        edit_invide_code = (EditText) view.findViewById(R.id.edit_invide_code);
        ptv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        pimg_edit = (ImageView) view.findViewById(R.id.img_edit);
        gbtn_confirm_receive = (Button) view.findViewById(R.id.btn_confirm_receive);
        img_wechat_pay = view.findViewById(R.id.img_wechat_pay);
        img_ali_pay = view.findViewById(R.id.img_ali_pay);

        ptv_pay_money.setText(vipPrice);

        //会员须知
        tv_vip_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vipRules = new Intent(VipCenterActivity.this, MyWebViewActivity.class);
                vipRules.putExtra("web_url", Constant.WEB_VIP_NOTICE);
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
            province = orderAddres.getProvince();
            city = orderAddres.getCity();
            county = orderAddres.getCounty();
        }

        //发票
        tv_invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInvoiceDialog();
            }
        });

        ptv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.dismiss();
            }
        });

        //添加地址
        ptv_no_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VipCenterActivity.this, MyAddressActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //修改地址
        pimg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(VipCenterActivity.this, MyAddressActivity.class);
                startActivityForResult(edit, 101);
            }
        });

        //微信支付
        img_wechat_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ptv_name.getText().toString())) {
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast("还未添加地址哦");
                    return;
                }
                view.dismiss();
                SPUtils.getInstance(VipCenterActivity.this).put(Constant.PAY_TYPE, "1");
                payOperation(loginUserId, 1, edit_invide_code.getText().toString().trim(), type);
            }
        });

        //支付宝
        img_ali_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(ptv_name.getText().toString())) {
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast("还未添加地址哦");
                    return;
                }
                view.dismiss();
                payOperation(loginUserId, 2, edit_invide_code.getText().toString().trim(), type);
            }
        });
    }

    /**
     * 发票数据处理
     */
    private void parseDatas(List<ShipAddressBean.InvoiceListBean> allInvoiceDatas) {
        peopleDatas.clear();
        unitDatas.clear();
        for (ShipAddressBean.InvoiceListBean invoiceListBean : allInvoiceDatas) {
            if (invoiceListBean.getType() == 0) {
                peopleDatas.add(invoiceListBean);
            } else {
                unitDatas.add(invoiceListBean);
            }
        }
    }

    /**
     * 发票弹窗
     */
    private void showInvoiceDialog() {
        parseDatas(allInvoiceDatas);
        invoiceDialog = new InvoiceDialog(VipCenterActivity.this);
        invoiceDialog.show();

        RadioGroup radioGroup = invoiceDialog.findViewById(R.id.radio_group);
        RadioButton ckboxPersonal = invoiceDialog.findViewById(R.id.checkbox_personal);
        RadioButton ckboxUnit = invoiceDialog.findViewById(R.id.checkbox_unit);
        DropEditText editName = invoiceDialog.findViewById(R.id.edit_name);
        EditText editCode = invoiceDialog.findViewById(R.id.edit_code);
        EditText editEMail = invoiceDialog.findViewById(R.id.edit_email);
        Button btnConfirm = invoiceDialog.findViewById(R.id.btn_confirm);
        TextView tvCancle = invoiceDialog.findViewById(R.id.tv_close);

        tvCancle.setOnClickListener(v -> {
            if (invoiceDialog != null) {
                invoiceDialog.dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.checkbox_personal:
                    isEditInvoice = false;
                    editName.setText("");
                    editCode.setText("");
                    editEMail.setText("");
                    editName.setHint("名字");
                    ckboxPersonal.setTextColor(Color.parseColor("#20bead"));
                    ckboxUnit.setTextColor(Color.parseColor("#AFAFAF"));
                    editCode.setVisibility(View.GONE);

                    PopDownAdapter adapter = new PopDownAdapter(this, peopleDatas);
                    editName.setAdapter(adapter);
                    editName.setOnItemBeanListener(new DropEditText.OnItemBeanListener() {
                        @Override
                        public void onItemBean(int position) {
                            editName.setText(peopleDatas.get(position).getName());
                            editEMail.setText(peopleDatas.get(position).getEmail());
                            isEditInvoice = true;
                            editInvoiceId = peopleDatas.get(position).getInvoiceId();
                        }
                    });
                    break;
                case R.id.checkbox_unit:
                    isEditInvoice = false;
                    editName.setText("");
                    editCode.setText("");
                    editEMail.setText("");

                    editName.setHint("单位名称");
                    ckboxUnit.setTextColor(Color.parseColor("#20bead"));
                    ckboxPersonal.setTextColor(Color.parseColor("#AFAFAF"));
                    editCode.setVisibility(View.VISIBLE);

                    PopDownAdapter sadapter = new PopDownAdapter(this, unitDatas);
                    editName.setAdapter(sadapter);
                    editName.setOnItemBeanListener(new DropEditText.OnItemBeanListener() {
                        @Override
                        public void onItemBean(int position) {
                            editName.setText(unitDatas.get(position).getName());
                            editCode.setText(unitDatas.get(position).getTaxNumber());
                            editEMail.setText(unitDatas.get(position).getEmail());

                            isEditInvoice = true;
                            editInvoiceId = unitDatas.get(position).getInvoiceId();
                        }
                    });
                    break;
            }
        });

        //第一次弹窗出现时 取第一个值赋值个人
        if (ckboxPersonal.isChecked()) {
            if (peopleDatas.size() > 0) {
                PopDownAdapter adapter = new PopDownAdapter(this, peopleDatas);
                editName.setAdapter(adapter);
                editName.setOnItemBeanListener(new DropEditText.OnItemBeanListener() {
                    @Override
                    public void onItemBean(int position) {
                        editName.setText(peopleDatas.get(position).getName());
                        editEMail.setText(peopleDatas.get(position).getEmail());
                        isEditInvoice = true;
                        editInvoiceId = peopleDatas.get(position).getInvoiceId();
                    }
                });
            }
        }

        //保存发票
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ckboxPersonal.isChecked()) {//个人
                    if (TextUtils.isEmpty(editName.getText().toString())) {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("名字不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(editEMail.getText().toString())) {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("邮箱不能为空！");
                        return;
                    }
                    if (isEditInvoice) {//修改
                        updateInvonice(editInvoiceId, loginUserId, editName.getText().toString(), "", 0, editEMail.getText().toString());
                    } else {
                        addInvonice(loginUserId, editName.getText().toString(), "", 0, editEMail.getText().toString());
                    }
                }

                if (ckboxUnit.isChecked()) {//单位
                    if (TextUtils.isEmpty(editName.getText().toString())) {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("单位名称不能为空！");
                        return;
                    }
                    if (TextUtils.isEmpty(editCode.getText().toString())) {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("纳税人识别号不能为空！");
                        return;
                    }

                    if (TextUtils.isEmpty(editEMail.getText().toString())) {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("邮箱不能为空！");
                        return;
                    }
                    if (isEditInvoice) {
                        updateInvonice(editInvoiceId, loginUserId, editName.getText().toString(), editCode.getText().toString(), 1, editEMail.getText().toString());
                    } else {
                        addInvonice(loginUserId, editName.getText().toString(), editCode.getText().toString(), 1, editEMail.getText().toString());
                    }
                }

            }
        });

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

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_reveive_gifts://领取礼包
                //获得popup_reveive_gifts 布局里的View
                gtv_no_address = (TextView) view.findViewById(R.id.tv_no_address);
                grl_have_address = (RelativeLayout) view.findViewById(R.id.rl_have_address);
                gtv_close = (TextView) view.findViewById(R.id.tv_close);
                gtv_name = (TextView) view.findViewById(R.id.tv_name);
                gtv_phone = (TextView) view.findViewById(R.id.tv_phone);
                gtv_detial_address = (TextView) view.findViewById(R.id.tv_detial_address);
                gimg_edit = (ImageView) view.findViewById(R.id.img_edit);
                gbtn_confirm_receive = (Button) view.findViewById(R.id.btn_confirm_receive);

                if (TextUtils.isEmpty(orderAddres.getProvince())) {//没设置地址
                    gtv_no_address.setVisibility(View.VISIBLE);
                    grl_have_address.setVisibility(View.GONE);
                } else {
                    gtv_no_address.setVisibility(View.GONE);
                    grl_have_address.setVisibility(View.VISIBLE);
                    gtv_name.setText(orderAddres.getName());
                    gtv_phone.setText(orderAddres.getPhone());
                    gtv_detial_address.setText(orderAddres.getProvince() + orderAddres.getCity() + orderAddres.getCounty() + orderAddres.getAddress());
                    province = orderAddres.getProvince();
                    city = orderAddres.getCity();
                    county = orderAddres.getCounty();
                }

                gtv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                //添加地址
                gtv_no_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VipCenterActivity.this, MyAddressActivity.class);
                        startActivityForResult(intent, 100);
                    }
                });

                //修改地址
                gimg_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent edit = new Intent(VipCenterActivity.this, MyAddressActivity.class);
                        startActivityForResult(edit, 100);
                    }
                });

                //确认领取
                gbtn_confirm_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(gtv_name.getText().toString().trim())) {
                            ToastUtils.getInstanc(VipCenterActivity.this).showToast("请完善地址信息!");
                            return;
                        }
                        receiveGiftRequest(loginUserId);
                    }
                });
                break;
            case R.layout.popup_buy_vip://购买Vip(弃用)
                break;
        }
    }


    /**
     * 支付操作
     *
     * @param userId
     * @param payType    1.微信，2支付宝
     * @param inviteCode 代理邀请码
     * @param type       ==2时 需要传 推荐人邀请人的id
     */
    private void payOperation(String userId, final int payType, String inviteCode, int type) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
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
            if (type == 2) {
                params.put("fuserid", SPUtils.getInstance(VipCenterActivity.this).get(Constant.SHARE_KEY, ""));
            }
            Log.e(TAG, "payOperation: fuserid" + SPUtils.getInstance(VipCenterActivity.this).get(Constant.SHARE_KEY, ""));
            params.put("invoice", isUseInvoice);
            params.put("invoiceId", invoiceId);
            Log.e(TAG, "payOperation:==== " + isUseInvoice);
            Log.e(TAG, "payOperation:==== " + invoiceId);
            params.put("userid", userId);
            params.put("vipTypeId", vipTypeId);
            params.put("province", province);
            params.put("city", city);
            params.put("county", county);
            params.put("address", ptv_detial_address.getText().toString().trim());
            params.put("receiveName", ptv_name.getText().toString().trim());
            params.put("phone", ptv_phone.getText().toString());
            params.put("inviteCode", inviteCode);
            params.put("pwd", "");
            params.put("tradeType", Constant.PAY_TYPE_VIP);//交易类型：1vip购买，2代理购买会员，3商城订单
            params.put("payType", payType);
            params.put("usecb", false);
            params.put("useba", false);
            params.put("billDetailList", array);

            RequestInterface.payPrefix(VipCenterActivity.this, params, TAG, ReqConstance.I_PAY_SHOPPING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
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
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast("生成订单失败，请重试...");
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
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
                PayTask alipay = new PayTask(VipCenterActivity.this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void showKey() {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void hideKey(EditText editText) {
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //参数：1，自己的EditText。2，时间。
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    //保存二维码
    @OnClick(R.id.btn_save)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(downUrl)) {
            String path = Environment.getExternalStorageDirectory() + "/BSC/erCode.jpg";
            Log.e(TAG, "onViewClicked:sd卡== " + path);
            File file = new File(path);
            if (!file.exists()) {
                Log.e(TAG, "onViewClicked: 文件不存在");
                ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = dowmPic(downUrl);//下载
                        onSaveBitmap(bitmap, VipCenterActivity.this);//保存到本地
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                                ToastUtils.getInstanc(VipCenterActivity.this).showToast("保存成功");
                            }
                        });
                    }
                }).start();
            } else {
                ToastUtils.getInstanc(VipCenterActivity.this).showToast("已保存");
            }
        } else {
            ToastUtils.getInstanc(VipCenterActivity.this).showToast("二维码链接失效");
        }

    }

    /**
     * Android保存图片到系统图库
     *
     * @param mBitmap
     * @param context
     */
    private void onSaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/Boohee/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "BSC");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "erCode.jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 第二步：其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
////   /storage/emulated/0/Boohee/1493711988333.jpg
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 第三步：最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    /**
     * 下载图片okhttp
     *
     * @param url
     * @return
     */
    public Bitmap dowmPic(String url) {
        //获取okHttp对象get请求
        try {
            OkHttpClient client = new OkHttpClient();
            //获取请求对象
            Request request = new Request.Builder().url(url).build();
            //获取响应体
            ResponseBody body = client.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改发票
     *
     * @param invoId      发票id
     * @param userId
     * @param invoiceName 个人 姓名  单位 --单位名称
     * @param taxNumber   企业组织代码
     * @param type        类型：0个人，1公司
     * @param email       邮箱
     */
    private void updateInvonice(int invoId, String userId, String invoiceName, String taxNumber, int type, String email) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("invoiceId", invoId);
            params.put("userid", userId);
            params.put("name", invoiceName);
            params.put("taxNumber", taxNumber);
            params.put("type", type);
            params.put("email", email);

            RequestInterface.userPrefix(VipCenterActivity.this, params, TAG, ReqConstance.I_USER_INVOICE_UPDATE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    if (code == 0) {
                        isEditInvoice = false;
                        isUseInvoice = true;
//                        ToastUtils.getInstanc(OrderXiaDanActivity.this).showToast(msg);
                        if (invoiceDialog != null) {
                            invoiceDialog.dismiss();
                        }
                        //修改数据源
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            ShipAddressBean.InvoiceListBean updateBean = gson.fromJson(jsonResult, ShipAddressBean.InvoiceListBean.class);
                            deleteOldAddNew(invoId, updateBean);
                            invoiceId = updateBean.getInvoiceId();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 添加发票
     *
     * @param userId
     * @param invoiceName 个人 姓名  单位 --单位名称
     * @param taxNumber   企业组织代码
     * @param type        类型：0个人，1公司
     * @param email       邮箱
     */
    private void addInvonice(String userId, String invoiceName, String taxNumber, int type, String email) {
        ModelLoading.getInstance(VipCenterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("name", invoiceName);
            params.put("taxNumber", taxNumber);
            params.put("type", type);
            params.put("email", email);

            RequestInterface.userPrefix(VipCenterActivity.this, params, TAG, ReqConstance.I_USER_INVOICE_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    if (code == 0) {
                        isEditInvoice = false;
                        isUseInvoice = true;
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                        if (invoiceDialog != null) {
                            invoiceDialog.dismiss();
                        }
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            ShipAddressBean.InvoiceListBean updateBean = gson.fromJson(jsonResult, ShipAddressBean.InvoiceListBean.class);
                            allInvoiceDatas.add(updateBean);
                            invoiceId = updateBean.getInvoiceId();
                            Log.e(TAG, "requestSuccess:是否使用" + isUseInvoice);
                            Log.e(TAG, "requestSuccess:发票id" + invoiceId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(VipCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(VipCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(VipCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 id 移除原来的添加新的
     */
    private void deleteOldAddNew(int invoiceid, ShipAddressBean.InvoiceListBean newInvoiceBean) {
        for (ShipAddressBean.InvoiceListBean invoiceListBean : allInvoiceDatas) {
            if (invoiceid == invoiceListBean.getInvoiceId()) {//修改
                invoiceListBean.setInvoiceId(newInvoiceBean.getInvoiceId());
                invoiceListBean.setEmail(newInvoiceBean.getEmail());
                invoiceListBean.setName(newInvoiceBean.getName());
                invoiceListBean.setTaxNumber(newInvoiceBean.getTaxNumber());
            }
        }
    }

}
