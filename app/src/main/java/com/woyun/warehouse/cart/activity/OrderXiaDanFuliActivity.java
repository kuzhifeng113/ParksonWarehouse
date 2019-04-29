package com.woyun.warehouse.cart.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.jkb.vcedittext.VerificationAction;
import com.jkb.vcedittext.VerificationCodeEditText;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.alipay.PayResult;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.bean.WxPayBean;
import com.woyun.warehouse.cart.adapter.OrderXiaDanAdapter;
import com.woyun.warehouse.cart.adapter.PopDownAdapter;
import com.woyun.warehouse.my.activity.MyAddressActivity;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.my.activity.TwoPassWordActivity;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.KeybordS;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.MD5Util;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.DropEditText;
import com.woyun.warehouse.view.TwoPassWordDialog;

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
 * 下单 福利商品
 */
public class OrderXiaDanFuliActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "OrderXiaDanActivity";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_no_address)
    TextView tvNoAddress;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_detial_address)
    TextView tvDetialAddress;
    @BindView(R.id.img_edit)
    ImageView imgEdit;
    @BindView(R.id.rl_have_address)
    RelativeLayout rlHaveAddress;
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
    EditText editMemo;
    @BindView(R.id.tv_heji_price)
    TextView tvHejiPrice;
    @BindView(R.id.tv_to_pay)
    TextView tvToPay;

    List<CartShopBean.CartListBean> selectList = new ArrayList<>();//购买的商品
    @BindView(R.id.tv_keyong_hbmoney)
    TextView tvkyHbMoney;

    @BindView(R.id.tv_hb_money)
    TextView tvHbMoney;

    @BindView(R.id.tv_share_money)
    TextView tvShareMoney;
    @BindView(R.id.rl_share)
    RelativeLayout rlShare;
    @BindView(R.id.view_share)
    View viewShare;
    @BindView(R.id.switch_hb)
    Switch switchHbMoeny;

    private OrderXiaDanAdapter orderDeatailAdapter;
    private double totalPrice;

    private double trnasPort = 0.0;//邮费
    private ShipAddressBean orderAddres;
    private CommonPopupWindow popupWindow;
    private CommonPopupWindow downWindow;
    private IWXAPI api;
    private String tradeNo;//生成订单的订单号
    private String shareMoney, bcHd;//仓币 余额  分享减免钱 红包余额

    private List<ShipAddressBean.InvoiceListBean> peopleDatas = new ArrayList<>();//个人发票集合
    private List<ShipAddressBean.InvoiceListBean> unitDatas = new ArrayList<>();//单位发票集合
    private List<ShipAddressBean.InvoiceListBean> allInvoiceDatas = new ArrayList<>();//所有发票集合
    private boolean isEditInvoice;//是否修改发票
    private int editInvoiceId;//修改发票的id
    private boolean isUseInvoice;//是否使用发票
    private int invoiceId;
    private String province, city, county;//省市区
    private String manTransport;//满多少包邮

    private int goodsId;//商品id
    private int rushId;//限时抢购id
    private boolean isLimitedTime;//是否是限时抢购商品--不能用仓币

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_fuli_xiadan);
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(OrderXiaDanFuliActivity.this);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        orderDeatailAdapter = new OrderXiaDanAdapter(OrderXiaDanFuliActivity.this, selectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderDeatailAdapter);

        isLimitedTime = getIntent().getBooleanExtra("is_limited_time", false);
        rushId = getIntent().getIntExtra("rush_id", 0);
        goodsId = getIntent().getIntExtra("goods_id", 0);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getAddressData(loginUserId, rushId, goodsId);

//        switchBcoin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                calculationPrice();
//            }
//        });

//        switchBcmoney.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                calculationPrice();
//            }
//        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("支付成功！！！");
                Intent goDetail = new Intent(OrderXiaDanFuliActivity.this, OrderDetailActivity.class);
                goDetail.putExtra("tradeNo", tradeNo);
                startActivity(goDetail);
                finish();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == MyAddressActivity.REQUEST_CODE) {
                ShipAddressBean addressEntity = (ShipAddressBean) data.getSerializableExtra("address_entity");
                tvNoAddress.setVisibility(View.GONE);
                rlHaveAddress.setVisibility(View.VISIBLE);
                tvToPay.setClickable(true);
                tvName.setText(addressEntity.getName());
                tvPhone.setText(addressEntity.getPhone());
                tvDetialAddress.setText(addressEntity.getProvince() + addressEntity.getCity() + addressEntity.getCounty() + addressEntity.getAddress());
                province = addressEntity.getProvince();
                city = addressEntity.getCity();
                county = addressEntity.getCounty();
                LogUtils.e(TAG, "onActivityResult: " + addressEntity.getProvince());
            }
        }
    }

    private void initData() {
        List<CartShopBean.CartListBean> datas = (List<CartShopBean.CartListBean>) getIntent().getSerializableExtra("select_data");
        totalPrice = getIntent().getDoubleExtra("total_price", 0.0);
        tvAllPrice.setText(String.valueOf(totalPrice));
        selectList.addAll(datas);
        orderDeatailAdapter.notifyDataSetChanged();

        LogUtils.e(TAG, "initData: " + selectList.size());
//        if (isVip || isAgent) {
//            tvTransport.setText("0");
//            return;
//        }
        //满多少包邮---不需要了
        LogUtils.e(TAG, "initData:满=== " + manTransport);
        if (totalPrice > Double.parseDouble(manTransport)) {
            tvTransport.setText("0");
            return;
        }

        for (CartShopBean.CartListBean entity : datas) {
            if (trnasPort < Double.valueOf(entity.getTransport())) {
                trnasPort = Double.valueOf(entity.getTransport());
            }
        }

        tvTransport.setText(String.valueOf(trnasPort));

    }

    /**
     * 获取默认地址
     *
     * @param userId
     */
    private void getAddressData(String userId, int rushId, int goodsId) {
        ModelLoading.getInstance(OrderXiaDanFuliActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            if (isLimitedTime) {
                params.put("rushId", rushId);
                params.put("goodsId", goodsId);
            }

            RequestInterface.userPrefix(OrderXiaDanFuliActivity.this, params, TAG, ReqConstance.I_DEFALUT_ADDREDSS_MONEY_CB, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            orderAddres = gson.fromJson(jsonResult, ShipAddressBean.class);
                            manTransport = orderAddres.getTransport();
                            //判断是否显示分享减免
                            shareMoney = orderAddres.getShareMoney();
                            tvShareMoney.setText("-￥" + shareMoney);
                            bcHd = orderAddres.getBcHb();
                            if (!shareMoney.equals("0")) {
                                rlShare.setVisibility(View.VISIBLE);
                                viewShare.setVisibility(View.VISIBLE);
                            } else {
                                rlShare.setVisibility(View.GONE);
                                viewShare.setVisibility(View.GONE);
                            }
                            initData();
                            if (TextUtils.isEmpty(orderAddres.getProvince())) {//没设置地址
                                tvNoAddress.setVisibility(View.VISIBLE);
                                rlHaveAddress.setVisibility(View.GONE);
                                tvToPay.setClickable(false);
                            } else {
                                tvNoAddress.setVisibility(View.GONE);
                                rlHaveAddress.setVisibility(View.VISIBLE);
                                tvName.setText(orderAddres.getName());
                                tvPhone.setText(orderAddres.getPhone());
                                tvDetialAddress.setText(orderAddres.getProvince() + orderAddres.getCity() + orderAddres.getCounty() + orderAddres.getAddress());
                                province = orderAddres.getProvince();
                                city = orderAddres.getCity();
                                county = orderAddres.getCounty();
                            }

                            tvkyHbMoney.setText("可用红包余额：" + orderAddres.getBcHb());

                            allInvoiceDatas = orderAddres.getInvoiceList();
                            calculationPrice();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        tvNoAddress.setVisibility(View.VISIBLE);
                        rlHaveAddress.setVisibility(View.GONE);
                        tvToPay.setClickable(false);
                        ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算合计价格
     */
    private void calculationPrice() {
        LogUtils.e(TAG, "计算价格calculationPrice: ");
//        描述：
        //红包福利商品 只能红包支付
        double zongjia = 0.0;
        double transPortPrice = Double.valueOf(tvTransport.getText().toString());//邮费
        double bcHbPrice = Double.valueOf(bcHd);//可用红包余额

        //红包余额开关选中
        if (switchHbMoeny.isChecked()) {
            if (bcHbPrice > totalPrice) {//红包余额大于商品价格
                tvHbMoney.setText("-￥" + totalPrice);//
                zongjia = BigDecimalUtil.getAdd(0, 0);
            } else {
                tvHbMoney.setText("-￥" + bcHbPrice);
                zongjia = BigDecimalUtil.geSub(totalPrice, bcHbPrice);
            }
        } else {
            tvHbMoney.setText("-￥0");
            zongjia = BigDecimalUtil.geSub(zongjia, 0);
        }

        //加上邮费
        zongjia = BigDecimalUtil.getAdd(zongjia, transPortPrice);
        if (zongjia > 0) {
            tvHejiPrice.setText("￥" + zongjia);
        } else {
            tvHejiPrice.setText("￥" + 0.00);
        }

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

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.tv_no_address, R.id.img_edit, R.id.tv_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_no_address://添加地址
                Intent intent = new Intent(OrderXiaDanFuliActivity.this, MyAddressActivity.class);
                startActivityForResult(intent, MyAddressActivity.REQUEST_CODE);
                break;
            case R.id.img_edit://修改地址
                Intent edit = new Intent(OrderXiaDanFuliActivity.this, MyAddressActivity.class);
                startActivityForResult(edit, MyAddressActivity.REQUEST_CODE);

                break;
            case R.id.tv_to_pay://付款
                if (tvHejiPrice.getText().toString().equals("￥0.0")) {
                    String isSetTwoPwd = (String) SPUtils.getInstance(OrderXiaDanFuliActivity.this).get(Constant.USER_TWO_PWD, "");
                    if (TextUtils.isEmpty(isSetTwoPwd)) {//未设置2级密码
                        new DeleteDialog(OrderXiaDanFuliActivity.this, R.style.dialogphone, "确定去设置二级密码？", new DeleteDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    Intent goPwd = new Intent(OrderXiaDanFuliActivity.this, TwoPassWordActivity.class);
                                    goPwd.putExtra("pay_flag", true);
                                    startActivity(goPwd);

                                }
                            }
                        }).show();
                    } else {
                        showTwoPassWord();
                    }
                }else{
                    showPayPop(tvToPay);
                }

                break;

        }
    }


    /**
     * 微信 支付宝 支付
     *
     * @param view
     */
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
     * 发票弹窗
     *
     * @param view
     */
    public void showInvoicePop(View view) {
        parseDatas(allInvoiceDatas);
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_invonice, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_invonice)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    //向下弹出
    public void showDownPop(View view) {
        if (downWindow != null && downWindow.isShowing()) return;
        downWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_down)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.AnimDown)
                .setViewOnclickListener(this)
                .setOutsideTouchable(true)
                .create();
        downWindow.showAsDropDown(view);

    }

    /**
     * 弹出2级密码输入框
     *
     * @param
     */
    public void showTwoPassWord() {
        final TwoPassWordDialog twoPassWordDialog = new TwoPassWordDialog(OrderXiaDanFuliActivity.this);
        twoPassWordDialog.show();
        final TextView tvTitle = twoPassWordDialog.findViewById(R.id.tv_title);
        TextView tvCancel = twoPassWordDialog.findViewById(R.id.tv_cancel);
        final VerificationCodeEditText editPwd = twoPassWordDialog.findViewById(R.id.edit_pwd);
//            editPwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        //输入框监听事件
        editPwd.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void onInputCompleted(CharSequence s) {
                LogUtils.e(TAG, "onInputCompleted: 输入完成");
                //输入完成请求接口
                String code = s.toString();
                int type = 5;

                LogUtils.e(TAG, "onInputCompleted:PayType " + type);
//                payOperation(loginUserId, type, MD5Util.getMD5(code));

                if (KeybordS.isSoftShowing(OrderXiaDanFuliActivity.this)) {
                    KeybordS.closeKeybord(editPwd, OrderXiaDanFuliActivity.this);
                } else {
                }
                twoPassWordDialog.dismiss();
                if (s.toString().length() == 4) {
                    payOperation(loginUserId, type, MD5Util.getMD5(code));
                }
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPassWordDialog.dismiss();
            }
        });
    }


    /**
     * 支付操作
     *
     * @param userId
     * @param payType 支付类型：0仓币，1.微信，2支付宝， 3 余额  4仓币+余额  5红包余额
     * @param
     */
    private void payOperation(String userId, final int payType, String pwd) {
        LogUtils.e(TAG, "payOperation:################################## " + payType);
        ModelLoading.getInstance(OrderXiaDanFuliActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();

            JSONArray jsonArray = new JSONArray();
            for (CartShopBean.CartListBean selectEntity : selectList) {
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
                object.put("memo", selectEntity.getMemo());
//                Log.e(TAG, "payOperation: 下单==="+selectEntity.getMemo());
                jsonArray.put(object);
            }

            params.put("invoice", isUseInvoice);
            params.put("invoiceId", invoiceId);
            params.put("userid", userId);
            params.put("rushId", rushId);
            params.put("province", province);
            params.put("city", city);
            params.put("county", county);
            params.put("address", tvDetialAddress.getText().toString());
            params.put("receiveName", tvName.getText().toString());
            params.put("phone", tvPhone.getText().toString());
            params.put("memo", editMemo.getText().toString());
            params.put("pwd", pwd);

            params.put("tradeType", Constant.PAY_TYPE_RED_PACK);

            params.put("payType", payType);

            params.put("billDetailList", jsonArray);

            RequestInterface.payPrefix(OrderXiaDanFuliActivity.this, params, TAG, ReqConstance.I_PAY_SHOPPING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                            if (payType == 1) {//wechat
                                WxPayBean bean = gson.fromJson(jsonObject.toString(), WxPayBean.class);
                                SPUtils.getInstance(OrderXiaDanFuliActivity.this).put("order_key", bean.getTradeNo());//订单号
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
                                tradeNo = jsonObject.getString("tradeNo");
                                aliPay(para);
                            } else {//其它支付方式
                                String orderNum = jsonObject.getString("tradeNo");
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                                Intent goDetail = new Intent(OrderXiaDanFuliActivity.this, OrderDetailActivity.class);
                                goDetail.putExtra("tradeNo", orderNum);
                                startActivity(goDetail);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(Constant.NET_ERROR);
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
                PayTask alipay = new PayTask(OrderXiaDanFuliActivity.this);
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
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_pay:
                //获得PopupWindow布局里的View
                TextView tv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
                TextView tv_close = (TextView) view.findViewById(R.id.tv_close);
                ImageView img_wechat_pay = (ImageView) view.findViewById(R.id.img_wechat_pay);
                ImageView img_ali_pay = (ImageView) view.findViewById(R.id.img_ali_pay);
                tv_pay_money.setText(tvHejiPrice.getText().toString());
                //微信支付
                img_wechat_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        payOperation(loginUserId, 1, "");
                    }
                });

                //支付宝
                img_ali_pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        payOperation(loginUserId, 2, "");
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
                break;
            case R.layout.popup_invonice://发票布局
                RadioGroup radioGroup = view.findViewById(R.id.radio_group);
                RadioButton ckboxPersonal = view.findViewById(R.id.checkbox_personal);
                RadioButton ckboxUnit = view.findViewById(R.id.checkbox_unit);
                DropEditText editName = view.findViewById(R.id.edit_name);
                EditText editCode = view.findViewById(R.id.edit_code);
                EditText editEMail = view.findViewById(R.id.edit_email);
                Button btnConfirm = view.findViewById(R.id.btn_confirm);
                TextView tvCancle = view.findViewById(R.id.tv_close);
                tvCancle.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                });
                radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    switch (group.getCheckedRadioButtonId()) {
                        case R.id.checkbox_personal:
                            LogUtils.e(TAG, "getChildView:----个人---------- ");
                            isEditInvoice = false;
                            editName.setText("");
                            editCode.setText("");
                            editEMail.setText("");
                            editName.setHint("名字");
                            ckboxPersonal.setTextColor(Color.parseColor("#FF437A"));
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
                            LogUtils.e(TAG, "getChildView:=====单位======== ");
                            isEditInvoice = false;
                            editName.setText("");
                            editCode.setText("");
                            editEMail.setText("");

                            editName.setHint("单位名称");
                            ckboxUnit.setTextColor(Color.parseColor("#FF437A"));
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
                        LogUtils.e(TAG, "onClick:是否修改" + isEditInvoice);
                        if (ckboxPersonal.isChecked()) {//个人
                            if (TextUtils.isEmpty(editName.getText().toString())) {
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("名字不能为空！");
                                return;
                            }
                            if (TextUtils.isEmpty(editEMail.getText().toString())) {
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("邮箱不能为空！");
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
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("单位名称不能为空！");
                                return;
                            }
                            if (TextUtils.isEmpty(editCode.getText().toString())) {
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("纳税人识别号不能为空！");
                                return;
                            }

                            if (TextUtils.isEmpty(editEMail.getText().toString())) {
                                ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast("邮箱不能为空！");
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
        ModelLoading.getInstance(OrderXiaDanFuliActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("name", invoiceName);
            params.put("taxNumber", taxNumber);
            params.put("type", type);
            params.put("email", email);

            RequestInterface.userPrefix(OrderXiaDanFuliActivity.this, params, TAG, ReqConstance.I_USER_INVOICE_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    if (code == 0) {
                        isEditInvoice = false;
                        isUseInvoice = true;
                        ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            ShipAddressBean.InvoiceListBean updateBean = gson.fromJson(jsonResult, ShipAddressBean.InvoiceListBean.class);
                            allInvoiceDatas.add(updateBean);
                            invoiceId = updateBean.getInvoiceId();
                            LogUtils.e(TAG, "requestSuccess:是否使用" + isUseInvoice);
                            LogUtils.e(TAG, "requestSuccess:发票id" + invoiceId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        ModelLoading.getInstance(OrderXiaDanFuliActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("invoiceId", invoId);
            params.put("userid", userId);
            params.put("name", invoiceName);
            params.put("taxNumber", taxNumber);
            params.put("type", type);
            params.put("email", email);

            RequestInterface.userPrefix(OrderXiaDanFuliActivity.this, params, TAG, ReqConstance.I_USER_INVOICE_UPDATE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    if (code == 0) {
                        isEditInvoice = false;
                        isUseInvoice = true;
//                        ToastUtils.getInstanc(OrderXiaDanActivity.this).showToast(msg);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
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
                        ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(OrderXiaDanFuliActivity.this).closeLoading();
                    ToastUtils.getInstanc(OrderXiaDanFuliActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
