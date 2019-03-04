package com.woyun.warehouse.my.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.AgentOpenBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.bean.WxPayBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.my.adapter.AgentOpenAdapter;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

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
 * 代理开通
 */
public class AgentOpenActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "AgentOpenActivity";
    private static final int SDK_PAY_FLAG = 2;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_user_name)
    EditText editUserName;
    @BindView(R.id.edit_user_age)
    EditText editUserSex;
    @BindView(R.id.edit_user_phone)
    EditText editUserPhone;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_open)
    Button btnOpen;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_wx_account)
    TextView tvWxAccount;
    @BindView(R.id.btn_copy_account)
    Button btnCopyAccount;

    //弹窗控件
    //开通代理控件
    private TextView ptv_no_address, ptv_name, ptv_phone, ptv_detial_address, ptv_close, ptv_pay_money, tv_agent_xuzhi;
    private ImageView pimg_edit, img_wechat_pay, img_ali_pay;
    private RelativeLayout prl_have_address;

    private CommonPopupWindow popupWindow;
    private ShipAddressBean orderAddres;
    private AgentOpenAdapter cartVipAdapter;
    private List<AgentOpenBean.GoodsListBean> goodsListBeanList = new ArrayList<>();

    private String agentPrice;// 费用
    private String vipTypeId;
    private IWXAPI api;
    private String province, city, county;//省市区
    private String downUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_open);
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(AgentOpenActivity.this);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        cartVipAdapter = new AgentOpenAdapter(AgentOpenActivity.this, goodsListBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(cartVipAdapter);
        initData();

        //选中事件
        cartVipAdapter.setCheckInterface(new AgentOpenAdapter.CheckInterface() {
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

        cartVipAdapter.setOnItemClickListener(position -> {
//            Intent detail=new Intent(AgentOpenActivity.this, GoodsDetailVipActivity.class);
            Intent detail = new Intent(AgentOpenActivity.this, GoodsDetailActivity.class);
            detail.putExtra("goods_id", goodsListBeanList.get(position).getGoodsId());
            detail.putExtra("from_id", 3);
            startActivity(detail);
        });

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultStatus = payResult.getResultStatus();
            if (resultStatus.equals("9000")) {
//                cehckISVip();
                SPUtils.getInstance(AgentOpenActivity.this).put(Constant.USER_IS_AGENT, true);
                ToastUtils.getInstanc(AgentOpenActivity.this).showToast("支付成功！！！");
                Intent agent = new Intent(AgentOpenActivity.this, AgentCenterActivity.class);
                startActivity(agent);
                finish();
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
                province = addressEntity.getProvince();
                city = addressEntity.getCity();
                county = addressEntity.getCounty();
                Log.e(TAG, "onActivityResult: " + addressEntity.getProvince());
            }
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
     * \
     * 代理开通查询礼包信息
     */
    private void initData() {
        ModelLoading.getInstance(AgentOpenActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.agentPrefix(AgentOpenActivity.this, params, TAG, ReqConstance.I_AGENT_QUERY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            AgentOpenBean openBean = gson.fromJson(object.toString(), AgentOpenBean.class);
                            goodsListBeanList.addAll(openBean.getGoodsList());
                            cartVipAdapter.notifyDataSetChanged();
                            vipTypeId = String.valueOf(openBean.getVipType().getId());
                            agentPrice = openBean.getVipType().getPrice();

                            downUrl = openBean.getEwm();
                            Glide.with(AgentOpenActivity.this).load(openBean.getEwm()).into(imgQrCode);
                            tvWxAccount.setText(openBean.getWxh());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
//                        imgJhVip.setClickable(false);
//                        imgBuyVip.setClickable(false);
                        ToastUtils.getInstanc(AgentOpenActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
//                    imgJhVip.setClickable(false);
//                    imgBuyVip.setClickable(false);
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast(Constant.NET_ERROR);
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

    @OnClick({R.id.btn_save, R.id.btn_copy_account, R.id.btn_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (!TextUtils.isEmpty(downUrl)) {
                    String path = Environment.getExternalStorageDirectory() + "/BSC/erCode.jpg";
                    Log.e(TAG, "onViewClicked:sd卡== " + path);
                    File file = new File(path);
                    if (!file.exists()) {
                        Log.e(TAG, "onViewClicked: 文件不存在");
                        ModelLoading.getInstance(AgentOpenActivity.this).showLoading("", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = dowmPic(downUrl);//下载
                                onSaveBitmap(bitmap, AgentOpenActivity.this);//保存到本地
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                                        ToastUtils.getInstanc(AgentOpenActivity.this).showToast("保存成功");
                                    }
                                });
                            }
                        }).start();
                    } else {
                        ToastUtils.getInstanc(AgentOpenActivity.this).showToast("已保存");
                    }
                } else {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("二维码链接失效");
                }
                break;
            case R.id.btn_copy_account:
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvWxAccount.getText().toString().trim());
                ToastUtils.getInstanc(AgentOpenActivity.this).showToast("复制成功");
                break;
            case R.id.btn_open:
                if (TextUtils.isEmpty(editUserName.getText().toString().trim())) {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("姓名不能为空哦");
                    return;
                }

                if (TextUtils.isEmpty(editUserSex.getText().toString().trim())) {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("性别不能为空");
                    return;
                }
                if (editUserSex.getText().toString().trim().length() != 1) {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("请输入正确性别（男/女）");
                    return;
                }
                if (!editUserSex.getText().toString().trim().contains("男") && !editUserSex.getText().toString().trim().contains("女")) {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("请输入正确性别（男/女）");
                    return;
                }

                if (editUserPhone.getText().toString().trim().length() != 11) {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("请输入正确手机号码~");
                    return;
                }

                if (isCheckOneGoods()) {
                    getDefaultAddress(loginUserId);
                } else {
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast("请选择礼包~");
                    return;
                }
                break;
        }
    }

    /**
     * 默认地址+仓币+余额
     */
    private void getDefaultAddress(String userId) {
        ModelLoading.getInstance(AgentOpenActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.userPrefix(AgentOpenActivity.this, params, TAG + "ADDRESS", ReqConstance.I_DEFALUT_ADDREDSS_MONEY_CB, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            orderAddres = gson.fromJson(jsonResult, ShipAddressBean.class);
                            showBuyVip();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(AgentOpenActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 购买代理
     */
    private void showBuyVip() {
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
        ptv_no_address = (TextView) view.findViewById(R.id.tv_no_address);
        prl_have_address = (RelativeLayout) view.findViewById(R.id.rl_have_address);
        ptv_close = (TextView) view.findViewById(R.id.tv_close);
        ptv_name = (TextView) view.findViewById(R.id.tv_name);
        ptv_phone = (TextView) view.findViewById(R.id.tv_phone);
        ptv_detial_address = (TextView) view.findViewById(R.id.tv_detial_address);
        ptv_pay_money = (TextView) view.findViewById(R.id.tv_pay_money);
        pimg_edit = (ImageView) view.findViewById(R.id.img_edit);
        tv_agent_xuzhi = view.findViewById(R.id.tv_agent_xuzhi);
        img_wechat_pay = view.findViewById(R.id.img_wechat_pay);
        img_ali_pay = view.findViewById(R.id.img_ali_pay);

        ptv_pay_money.setText(agentPrice);
        //须知
        tv_agent_xuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vipRules = new Intent(AgentOpenActivity.this, MyWebViewActivity.class);
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
            province = orderAddres.getProvince();
            city = orderAddres.getCity();
            county = orderAddres.getCounty();
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
                Intent intent = new Intent(AgentOpenActivity.this, MyAddressActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        //修改地址
        pimg_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(AgentOpenActivity.this, MyAddressActivity.class);
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
                SPUtils.getInstance(AgentOpenActivity.this).put(Constant.PAY_TYPE, "2");
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
        ModelLoading.getInstance(AgentOpenActivity.this).showLoading("", true);
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
            params.put("agentName", editUserName.getText().toString().trim());
            params.put("agentSex", editUserSex.getText().toString().trim());
            params.put("agentSex", editUserPhone.getText().toString().trim());
            params.put("vipTypeId", vipTypeId);
            params.put("province", province);
            params.put("city", city);
            params.put("county", county);
            params.put("address", ptv_detial_address.getText().toString().trim());
            params.put("receiveName", ptv_name.getText().toString().trim());
            params.put("phone", ptv_phone.getText().toString());
            params.put("pwd", "");
            params.put("tradeType", Constant.PAY_TYPE_AGENT);//0代理开通，1VIP开通，2代理购买VIP，3商城订单，4代理提现，5礼包领取
            params.put("payType", payType);
            params.put("usecb", false);
            params.put("useba", false);
            params.put("billDetailList", array);

            RequestInterface.payPrefix(AgentOpenActivity.this, params, TAG, ReqConstance.I_PAY_SHOPPING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
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
                        ToastUtils.getInstanc(AgentOpenActivity.this).showToast("生成订单失败，请重试...");
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentOpenActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentOpenActivity.this).showToast(Constant.NET_ERROR);
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
                PayTask alipay = new PayTask(AgentOpenActivity.this);
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

}
