package com.woyun.warehouse.welfare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.bean.ContentListBean;
import com.woyun.warehouse.bean.GoodsDetailBean;
import com.woyun.warehouse.bean.ResListBean;
import com.woyun.warehouse.bean.SkuListBean;
import com.woyun.warehouse.cart.activity.OrderXiaDanActivity;
import com.woyun.warehouse.cart.activity.OrderXiaDanFuliActivity;
import com.woyun.warehouse.mall.ProductSkuDialog;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.LookImageVideoActivity;
import com.woyun.warehouse.mall.activity.PosterActivity;
import com.woyun.warehouse.mall.adapter.NativeContentAdapter;
import com.woyun.warehouse.mall.adapter.NativeViewPageAdapter;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.utils.UdeskHelp;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.sku.ScreenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import udesk.core.UdeskConst;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray2;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;


/**
 * 福利商品详情-原生
 */
public class GoodsDetailWelfareActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, WbShareCallback {
    private static final String TAG = "GoodsDetailActivity";
    private static final int THUMB_SIZE = 150;
    private static final int REFRESH_COMPLETE = 1000;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.webViewDetail)
    RelativeLayout mLinearLayout;
    @BindView(R.id.img_kf)
    ImageView imgKf;


    @BindView(R.id.img_goods_buy)
    Button imgGoodsBuy;

    @BindView(R.id.rl_mall)
    RelativeLayout rlMall;

    @BindView(R.id.img_goods_share)
    ImageView imgGoodsShare;
    @BindView(R.id.img_bijia)
    ImageView imgBijia;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_show_num)
    TextView tvShowNum;
    @BindView(R.id.tv_vip_back)
    TextView tvVipBack;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_title)
    TextView tvGoodsTitle;
    @BindView(R.id.tv_transport)
    TextView tvTransport;
    @BindView(R.id.tv_sales_volume)
    TextView tvSalesVolume;
    @BindView(R.id.tv_stock)
    TextView tvStock;
    @BindView(R.id.tv_bao_you)
    TextView tvBaoYou;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.img_back_top)
    ImageView imgBackTop;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.rl_viewpage)
    RelativeLayout rlViewPage;

    private List<SkuListBean> skuListBeanList = new ArrayList<>();
    private GoodsDetailBean goodsDetailBean;
    private ProductSkuDialog dialog;
    private boolean isVip;
    private boolean isLogin;

    private int goodsId;//商品id
    private String redPackMoney;//红包余额

    private String goodesWebUrl;
    private String compareUrl;//比价url
    protected AgentWeb mAgentWeb;

    //bannner
    private List<ResListBean> resListBeanList = new ArrayList<>();
    private NativeViewPageAdapter nativeViewPageAdapter;

    private List<ContentListBean> contentListBeanList = new ArrayList<>();
    private List<ResListBean> contentResList = new ArrayList<>();
    private NativeContentAdapter nativeContentAdapter;
    //分享
    private CommonPopupWindow popupWindow;
    private IWXAPI iwxApi;
    private String shareTile, shareContent, shareUrl, shareIconUrl;
    private Bitmap mBitmapCover;
    private String kfGoodsUrl;//发送给客服的商品链接
    private Tencent mTencent;
    private Activity mContext;
    private ShareQQListener mIUiListener;

    private WbShareHandler shareHandler;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
//                    iwxApi.sendReq(shareWxUrl(goodesWebUrl, shareTile, shareContent, 0, shareIconUrl));
                    showSharePop();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail_native_welfare);
        ButterKnife.bind(this);
        mContext = GoodsDetailWelfareActivity.this;
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(GoodsDetailWelfareActivity.this, Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        //微博
        shareHandler = new WbShareHandler(GoodsDetailWelfareActivity.this);
        shareHandler.registerApp();

        isVip = (boolean) SPUtils.getInstance(GoodsDetailWelfareActivity.this).get(Constant.USER_IS_VIP, false);
        isLogin = (boolean) SPUtils.getInstance(GoodsDetailWelfareActivity.this).get(Constant.IS_LOGIN, false);

        toolBar.setNavigationOnClickListener(v -> {
            mAgentWeb.clearWebCache();
            finish();
        });

        goodsId = getIntent().getIntExtra("goods_id", 0);
        redPackMoney = getIntent().getStringExtra("redpack_money");

        if (isVip) {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 1;
        } else {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 0;
        }
        shareUrl = Constant.WEB_SHARE_GOODS2 + "?goodsId=" + goodsId + "&share=" + loginUserId;
        kfGoodsUrl = Constant.WEB_SHARE_GOODS_KF + "?goodsId=" + goodsId;


        initWeb(Constant.WEB_GOODS_URL + "?id=" + goodsId);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(GoodsDetailWelfareActivity.this, 7)));//垂直间距
        }
        initData();

        DisplayMetrics displayMetrics = ScreenUtils.getDisplayMetrics(GoodsDetailWelfareActivity.this);
        int height = displayMetrics.heightPixels;
        setViewHeight(rlViewPage,displayMetrics.widthPixels);
//        Log.e(TAG, "onCreate: 屏幕高2"+height);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e(TAG, "onScrollChange: 滑动后 Y " + scrollY);

                if (scrollY > 2 * height) {//大于2个屏幕的高度
                    imgBackTop.setVisibility(View.VISIBLE);
                } else {
                    imgBackTop.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        getData(goodsId);
    }

    /**
     * @param type 1 加入购物车
     *             2  直接购买
     */
    private void showDilog(final int type) {
        if (dialog == null || goodsDetailBean != null) {
            dialog = new ProductSkuDialog(this);
            dialog.setData(goodsDetailBean, new ProductSkuDialog.Callback() {
                @Override
                public void onAdded(SkuListBean sku, int quantity, String memo) {
//                    Log.e(TAG, "onAdded: goodsId" +goodsDetailBean.getGoodsId());
//                    Log.e(TAG, "onAdded: memo" +memo);
//                    Log.e(TAG, "onAdded: goodsName" +goodsDetailBean.getName());
//                    Log.e(TAG, "onAdded: goodsImage" +goodsDetailBean.getImage());
//                    Log.e(TAG, "onAdded: skuId" +sku.getSkuId());
//                    Log.e(TAG, "onAdded: skuNum" +quantity);
//                    Log.e(TAG, "onAdded: skuName" +sku.getSkuName());
//                    Log.e(TAG, "onAdded: skuImage" +sku.getImage());
//                    Log.e(TAG, "onAdded: unitPrice" +sku.getVipPrice());
                    //判断红包余额是否满足
                    if(!TextUtils.isEmpty(redPackMoney)){
                        if(redPackMoney.equals("0")){
                            showSharePopChai();
                            return;
                        }
//                        double redMoney= Double.parseDouble(redPackMoney);
//                        double skuPrice= Double.parseDouble(sku.getVipPrice());
//                        if(redMoney <skuPrice *  quantity){
//                            ToastUtils.getInstanc(GoodsDetailWelfareActivity.this).showToast("红包余额不足~");
//                            return;
//                        }
                    }

                    String resultMemo = memo.substring(0, memo.lastIndexOf(","));
                    LogUtils.e(TAG, "onAdded:resultMemo= " + resultMemo);
                    if (type == 1) {

                    } else {
                        buyGoods(sku, quantity, resultMemo);
                    }
                }
            });
        }
        dialog.show();
    }

    /**
     * 获取数据
     *
     * @param
     */
    private void getData(int goodsID) {
        ModelLoading.getInstance(GoodsDetailWelfareActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsID);
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailWelfareActivity.this, params, TAG, ReqConstance.I_GOODS_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(GoodsDetailWelfareActivity.this).closeLoading();
                    tokenTimeLimit(GoodsDetailWelfareActivity.this, code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        try {
                            Gson gson = new Gson();
                            goodsDetailBean = gson.fromJson(jsonArray.get(0).toString(), GoodsDetailBean.class);
                            compareUrl = goodsDetailBean.getCompareUrl();
                            pasterData(goodsDetailBean);
                            LogUtils.e(TAG, "requestSuccess: " + goodsDetailBean.getName());


                            shareTile = goodsDetailBean.getName();
                            shareContent = goodsDetailBean.getTitle();
                            shareIconUrl = goodsDetailBean.getImage();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(GoodsDetailWelfareActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(GoodsDetailWelfareActivity.this).closeLoading();

                    imgGoodsBuy.setClickable(false);
                    imgGoodsShare.setClickable(false);
                    ToastUtils.getInstanc(GoodsDetailWelfareActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 处理数据
     *
     * @param goodsDetailBean
     */
    private void pasterData(GoodsDetailBean goodsDetailBean) {
        resListBeanList.clear();
        contentListBeanList.clear();
        List<SkuListBean> skuList = goodsDetailBean.getSkuList();
        skuListBeanList.addAll(skuList);
        //ui
        tvShowNum.setText("1/" + goodsDetailBean.getResList().size());
        tvPrice.setText(goodsDetailBean.getVipPrice());
//        tvVipBack.setText("会员返" + goodsDetailBean.getBkCoin());
        tvVipBack.setText("会员价");
        tvGoodsPrice.setText("市场价:" + goodsDetailBean.getPrice());
        tvGoodsPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvGoodsTitle.setText(goodsDetailBean.getName());

        tvTransport.setText("邮费：" + goodsDetailBean.getTransport());
        tvSalesVolume.setText("已售：" + goodsDetailBean.getSellNum());
        tvStock.setText("库存：" + goodsDetailBean.getStock());
//        if (isVip) {
//            tvBaoYou.setText("VIP包邮");
//        } else {
//            tvBaoYou.setText("普通用户满" + goodsDetailBean.getFreeShopping() + "包邮");
//        }

        tvBaoYou.setText("全场一件包邮");

        resListBeanList = goodsDetailBean.getResList();
        contentListBeanList = goodsDetailBean.getContentList();

        nativeContentAdapter = new NativeContentAdapter(GoodsDetailWelfareActivity.this, contentListBeanList);
        recyclerView.setAdapter(nativeContentAdapter);

        nativeViewPageAdapter = new NativeViewPageAdapter(GoodsDetailWelfareActivity.this, resListBeanList);
        viewPager.setAdapter(nativeViewPageAdapter);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int index, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int index) {
                tvShowNum.setText(index + 1 + "/" + resListBeanList.size());
                Jzvd.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //查看大图
        nativeViewPageAdapter.setOnItemClickListener(new NativeViewPageAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int index) {
                Intent toLook = new Intent(GoodsDetailWelfareActivity.this, LookImageVideoActivity.class);
                toLook.putExtra("reslist", (Serializable) resListBeanList);
                toLook.putExtra("index", index);
                startActivity(toLook, ActivityOptions.makeSceneTransitionAnimation(GoodsDetailWelfareActivity.this).toBundle());
            }
        });
        //内容查看大图  下标 判断他之前有多少种type=3  文字然后减去 得到最新的下标
        nativeContentAdapter.setOnItemClickListener(new NativeContentAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int position) {
                contentResList.clear();
                int a = 0;
                for (int i = 0; i < contentListBeanList.size(); i++) {
                    if (contentListBeanList.get(i).getType() == 1 || contentListBeanList.get(i).getType() == 2) {
                        ResListBean resListBean = new ResListBean();
                        resListBean.setType(contentListBeanList.get(i).getType());
                        resListBean.setVideoUrl(contentListBeanList.get(i).getVideoUrl());
                        resListBean.setImage(contentListBeanList.get(i).getImage());
                        contentResList.add(resListBean);
                    }
                }
                for (int j = 0; j < position; j++) {
                    if (contentListBeanList.get(j).getType() == 3) {
                        a++;
                    }
                }

                LogUtils.e(TAG, "onItemClick: a===" + a);

                Intent toLook = new Intent(GoodsDetailWelfareActivity.this, LookImageVideoActivity.class);
                toLook.putExtra("reslist", (Serializable) contentResList);
                if (a > position) {
                    toLook.putExtra("index", position);
                } else {
                    toLook.putExtra("index", position - a);
                }

                startActivity(toLook, ActivityOptions.makeSceneTransitionAnimation(GoodsDetailWelfareActivity.this).toBundle());
            }
        });


    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    @OnClick({R.id.img_goods_buy, R.id.img_goods_share, R.id.img_bijia, R.id.img_back_top, R.id.img_kf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_kf://客服
                if (!isLogin) {
                    goLogin();
                    return;
                }
                LogUtils.e(TAG, "onViewClicked: kf");
//                UdeskSDKManager.getInstance().initApiKey(getApplicationContext(), Constant.UDESK_DOMAN,
//                        Constant.UDESK_KEY, Constant.UDESK_APPID);
                String sdkToken = loginUserId;

                Map<String, String> info = new HashMap<String, String>();
                //以下信息是可选
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME, (String) SPUtils.getInstance(GoodsDetailWelfareActivity.this).get(Constant.USER_NICK_NAME, ""));//昵称
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE, (String) SPUtils.getInstance(GoodsDetailWelfareActivity.this).get(Constant.USER_MOBILE, ""));//手机

                UdeskConfig.Builder builder = new UdeskConfig.Builder();

                //toolBar 背景色
                builder.setUdeskTitlebarBgResId(R.color.white)//设置标题栏TitleBar的背景色
                        .setUdeskTitlebarTextLeftRightResId(R.color.text_black)//设置标题栏TitleBar，左右两侧文字的颜色
                        .setUdeskbackArrowIconResId(R.mipmap.back_black) // 设置返回箭头图标资源id
                        .setUdeskProductLinkColorResId(R.color.white) //设置商品信息 带链接时显示的颜色

                        .setDefualtUserInfo(info)
                        .setCustomerUrl((String) SPUtils.getInstance(GoodsDetailWelfareActivity.this).get(Constant.USER_AVATAR, ""))//用户头像
                        .setCommodity(UdeskHelp.getInstance().createCommodity(goodsDetailBean, kfGoodsUrl))
                        .setProduct(UdeskHelp.getInstance().createProduct(goodsDetailBean, kfGoodsUrl));
                UdeskSDKManager.getInstance().entryChat(getApplicationContext(), builder.build(), sdkToken);

                break;

            case R.id.img_goods_buy://购买
                if (!isLogin) {
                    goLogin();
                    return;
                }
                showDilog(2);
                break;

            case R.id.img_goods_share:
                returnBitMap(shareIconUrl);
//                showSharePop();
                break;
            case R.id.img_bijia://比价
                if (TextUtils.isEmpty(compareUrl)) {
                    ToastUtils.getInstanc(GoodsDetailWelfareActivity.this).showToast("未找到同类商品~");
                } else {
                    Intent priva = new Intent(GoodsDetailWelfareActivity.this, MyWebViewActivity.class);
                    priva.putExtra("web_url", compareUrl);
                    startActivity(priva);
                }
                break;
            case R.id.img_back_top://置顶
                nestedScrollView.fling(0);
                nestedScrollView.smoothScrollTo(0, 0);
                break;
        }
    }


    /**
     * 直接购买
     */
    private void buyGoods(SkuListBean sku, int num, String memo) {
        double totalPrice = 0.00f;
        CartShopBean.CartListBean entity = new CartShopBean.CartListBean();
        entity.setGoodsId(goodsDetailBean.getGoodsId());
        entity.setGoodsName(goodsDetailBean.getName());
        entity.setGoodsImage(goodsDetailBean.getImage());
        entity.setSkuId(sku.getSkuId());
        entity.setSkuNum(num);
        entity.setSkuName(sku.getSkuName());
        entity.setSkuImage(sku.getImage());

//        if (isVip) {
            double price = Double.parseDouble(sku.getVipPrice());
            totalPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(num));
            entity.setUnitPrice(sku.getVipPrice());
            entity.setTransport(goodsDetailBean.getTransport());
//        } else {
//            double price = Double.parseDouble(sku.getPrice());
//            totalPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(num));
//            entity.setUnitPrice(sku.getPrice());
//            entity.setTransport(goodsDetailBean.getTransport());
//        }

        entity.setMemo(memo);
        List<CartShopBean.CartListBean> selectList = new ArrayList<>();
        selectList.add(entity);

        Intent intent = new Intent(GoodsDetailWelfareActivity.this, OrderXiaDanFuliActivity.class);
        intent.putExtra("total_price", totalPrice);
        intent.putExtra("select_data", (Serializable) selectList);
        startActivity(intent);
//        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.e(TAG, "onDestroy: ");
//        webView.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        ModelLoading.getInstance(GoodsDetailWelfareActivity.this).closeLoading();
    }

    private void goLogin() {
        mAgentWeb.clearWebCache();
        Intent intent = new Intent(GoodsDetailWelfareActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 设置Margin 值
     *
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }

    }


    @Override
    public void onBackPressed() {
        mAgentWeb.clearWebCache();
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 弹窗 商品详情链接分享
     */
    private void showSharePop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(GoodsDetailWelfareActivity.this).inflate(R.layout.popup_share_haibao, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(GoodsDetailWelfareActivity.this)
                .setView(R.layout.popup_share_haibao)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 分享拆红包
     */
    private void showSharePopChai() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(GoodsDetailWelfareActivity.this).inflate(R.layout.popup_fuli_no_red, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(GoodsDetailWelfareActivity.this)
                .setView(R.layout.popup_fuli_no_red)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case  R.layout.popup_share_haibao:
                ImageView shareWeiXin = (ImageView) view.findViewById(R.id.img_share_weixin);
                ImageView shareCircle = (ImageView) view.findViewById(R.id.img_share_circle);
                ImageView shareHB = (ImageView) view.findViewById(R.id.img_share_haibao);
                ImageView shareQQ = (ImageView) view.findViewById(R.id.img_share_qq);
                TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);

                shareWeiXin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        iwxApi.sendReq(shareWxUrl(shareUrl, shareTile, shareContent, 0, shareIconUrl));
                    }
                });

                shareCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        iwxApi.sendReq(shareWxUrl(shareUrl,
                                shareTile, shareContent, 1, shareIconUrl));
                    }
                });

                //生成海报
                shareHB.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        Intent intent=new Intent(GoodsDetailWelfareActivity.this,PosterActivity.class);
                        intent.putExtra("share_goods_id",goodsId);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(GoodsDetailWelfareActivity.this).toBundle());

//                /**
//                 * 第三方应用发送请求消息到微博，唤起微博分享界面。
//                 */
//                sendMessage(true, false);
                    }
                });

                shareQQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        final Bundle params = new Bundle();
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareUrl);
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent);// 摘要
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareIconUrl);// 网络图片地址　
                        //params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
//                        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
                        // 分享操作要在主线程中完成
                        mTencent.shareToQQ(mContext, params, mIUiListener);
//                ToastUtils.getInstanc(MyCenterActivity.this).showToast("qq");

                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                break;

            case  R.layout.popup_fuli_no_red:
                //获得PopupWindow布局里的View
                ImageView  img_main_share=view.findViewById(R.id.img_main_get);
                ImageView closed=view.findViewById(R.id.img_close);

                closed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                //分享
                img_main_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        setResult(WelfareFragment.FINISH_RESULT);
                        finish();
                    }
                });
                break;
        }

    }

    /**
     * 微博分享
     *
     * @param hasText
     * @param hasImage
     */
    private void sendMessage(boolean hasText, boolean hasImage) {
        sendMultiMessage(hasText, hasImage);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }

        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        shareHandler.shareMessage(weiboMessage, false);

    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText(shareContent);
        textObject.title = shareTile;
        textObject.actionUrl = shareUrl;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_agent_center);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText(String shareContent) {
        String text = shareContent + shareUrl;
        return text;
    }

    /**
     * 分享网页
     *
     * @param url
     * @param title
     * @param miaoshu
     * @param id      0 好友  1 朋友圈
     * @param
     * @return
     */
    public SendMessageToWX.Req shareWxUrl(String url, String title, String miaoshu, int id, final String shareIconUrl) {
        // 初始化一个WXWebpageObject对象，填写Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题描述
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = miaoshu;
        //网络图片
        Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmapCover, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bmpToByteArray2(thumbBmp, true);
        mBitmapCover.recycle();
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = id;
        return req;
    }


    public void returnBitMap(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    mBitmapCover = BitmapFactory.decodeStream(is);
                    is.close();
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    /**
     * QQ分享回掉
     */
    class ShareQQListener implements IUiListener {
        @Override
        public void onComplete(Object object) {
            LogUtils.e(TAG, "onComplete: ");
//            Toast.makeText(MyCenterActivity.this, "分享完成:", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(UiError error) {
            LogUtils.e(TAG, "onError: ");
            Toast.makeText(GoodsDetailWelfareActivity.this, "分享失败:" + error.errorMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            LogUtils.e(TAG, "onCancel: ");
//            Toast.makeText(MyCenterActivity.this, "分享取消", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        shareHandler.doResultIntent(data, this);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, mIUiListener);
            }
        }
    }

    //微博分享回调
    @Override
    public void onWbShareSuccess() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(this,
                getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: ",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
    }


    private void initWeb(String webUrl) {

        LogUtils.e(TAG, webUrl);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
//                .setWebLayout(new WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go(webUrl);
        //java.lang.IllegalStateException: Unable to create layer for WebView, size 1080x8448 exceeds max size
        mAgentWeb.getWebCreator().getWebView().setLayerType(View.LAYER_TYPE_NONE, null);
//
//        //注入对象
//        if (mAgentWeb != null) {
//            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, GoodsDetailNativeWebActivity.this));
//        }
//
//        mAgentWeb.getJsAccessEntrace().quickCallJs("getImage()");

    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            ModelLoading.getInstance(GoodsDetailWelfareActivity.this).showLoading("", true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.e(TAG, "onPageFinished: ");
            ModelLoading.getInstance(GoodsDetailWelfareActivity.this).closeLoading();
            //webview加载完成之后重新测量webview的高度
            ViewGroup.LayoutParams params = mLinearLayout.getLayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels;
            //获取网页的高度
            WebView mainWebView = mAgentWeb.getWebCreator().getWebView();
            int htmlHeight = mainWebView.getContentHeight();//获取html高度
//            Log.e(TAG, "onPageFinished: 高度"+htmlHeight);
            float scale = mainWebView.getScale();//手机上网页缩放比例
            int webViewHeight = mainWebView.getHeight();//WebView控件的高度
            float v = mainWebView.getContentHeight() * mainWebView.getScale();//得到的是网页在手机上真实的高度
            params.height = (int) v;
            mLinearLayout.setLayoutParams(params);

        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {

            if (newProgress == 100) {

            }
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

    };
}
