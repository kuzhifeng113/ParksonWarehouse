package com.woyun.warehouse.mall.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.sina.weibo.sdk.WbSdk;
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
import com.woyun.warehouse.baseparson.KeFuWebViewActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.bean.GoodsDetailBean;
import com.woyun.warehouse.bean.SkuListBean;
import com.woyun.warehouse.cart.activity.OrderXiaDanActivity;
import com.woyun.warehouse.mall.ProductSkuDialog;
import com.woyun.warehouse.my.activity.AboutMeActivity;
import com.woyun.warehouse.my.activity.ShareActivity;
import com.woyun.warehouse.utils.AndroidInterface;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.zzhoujay.richtext.RichText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray2;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;


/**
 * 商品详情--webview
 */
public class GoodsDetailActivity extends BaseActivity implements CommonPopupWindow.ViewInterface ,WbShareCallback {
    private static final String TAG = "GoodsDetailActivity";
    private static final int THUMB_SIZE = 150;
    private static final int REFRESH_COMPLETE = 1000;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.webViewDetail)
    RelativeLayout mLinearLayout;
    //    @BindView(R.id.img_kf)
//    ImageView imgKf;
    @BindView(R.id.img_collection)
    ImageView imgCollection;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.img_goods_buy)
    ImageView imgGoodsBuy;
    @BindView(R.id.img_goods_join_cart)
    ImageView imgGoodsJoinCart;
    @BindView(R.id.rl_mall)
    RelativeLayout rlMall;
    @BindView(R.id.rl_vote_kf)
    RelativeLayout rlVoteKf;
    @BindView(R.id.tv_vote_num_want)
    TextView tvVoteNumWant;
    @BindView(R.id.preview_progressBar)
    ProgressBar previewProgressBar;
    @BindView(R.id.btn_vote_want)
    TextView btnVoteWant;
    @BindView(R.id.rl_vote)
    RelativeLayout rlVote;
    @BindView(R.id.btn_vote_buy)
    TextView btnVoteBuy;
    @BindView(R.id.img_goods_share)
    ImageView imgGoodsShare;
    @BindView(R.id.img_bijia)
    ImageView imgBijia;
    @BindView(R.id.tv_content)
    TextView tvRichContent;


    private List<SkuListBean> skuListBeanList = new ArrayList<>();
    private GoodsDetailBean goodsDetailBean;
    private ProductSkuDialog dialog;
    private boolean isVip;
    private boolean isFavorite;//是否收藏
    private boolean isLogin;

    private int goodsId;//商品id
    private int fromType;//投票页面过来的 1   还是商城页面过来的2
    private int voteId;//投票id
    private boolean isHistory;//是否是历史期数
    private boolean isVote;//是否投票
    private boolean isShelf;//历史期数商品是否上架
    private int wanNum;
    private int totalNum;
    private String goodesWebUrl;

    private String compareUrl;//比价url

    protected AgentWeb mAgentWeb;
    Badge cartBadge;
    private int cartNum;

    //分享
    private CommonPopupWindow popupWindow;
    private IWXAPI iwxApi;
    private String shareTile, shareContent, shareUrl, shareIconUrl;
    private Bitmap mBitmapCover;

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
        setContentView(R.layout.activity_goods_detail_web);
        ButterKnife.bind(this);
        mContext = GoodsDetailActivity.this;
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(GoodsDetailActivity.this, Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        //微博
        shareHandler = new WbShareHandler(GoodsDetailActivity.this);
        shareHandler.registerApp();

        isVip = (boolean) SPUtils.getInstance(GoodsDetailActivity.this).get(Constant.USER_IS_VIP, false);
        isLogin = (boolean) SPUtils.getInstance(GoodsDetailActivity.this).get(Constant.IS_LOGIN, false);
        cartBadge = new QBadgeView(this);
        toolBar.setNavigationOnClickListener(v -> finish());
        RichText.initCacheDir(this);


        goodsId = getIntent().getIntExtra("goods_id", 0);
        if (isVip) {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 1;
        } else {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 0;
        }
        shareUrl = Constant.WEB_SHARE_GOODS2 + "?goodsId=" + goodsId + "&share=" + loginUserId;
        ModelLoading.getInstance(GoodsDetailActivity.this).showLoading("", true);
        initWeb(goodesWebUrl);
        fromType = getIntent().getIntExtra("from_id", 0);
        voteId = getIntent().getIntExtra("vote_id", 0);
        isHistory = getIntent().getBooleanExtra("is_History", false);
        isVote = getIntent().getBooleanExtra("is_vote", false);
        wanNum = getIntent().getIntExtra("wan_count", 0);
        totalNum = getIntent().getIntExtra("total_count", 0);
        isShelf = getIntent().getBooleanExtra("is_shelf", false);
        tvVoteNumWant.setText(wanNum + "人想要");
//        Log.e(TAG, "onCreate:商品id===" + goodsId);
//        Log.e(TAG, "onCreate:fromType===" + fromType);
//        Log.e(TAG, "onCreate:voteId===" + voteId);
//        Log.e(TAG, "onCreate:isHistory===" + isHistory);
//        Log.e(TAG, "onCreate:wanNum===" + wanNum);
//        Log.e(TAG, "onCreate:totalNum===" + totalNum);
//        Log.e(TAG, "onCreate:isShelf===" + isShelf);

        if (fromType == 1) {//投票页面
            rlMall.setVisibility(View.GONE);
            rlVote.setVisibility(View.VISIBLE);

            if (isHistory) {//历史期数
                btnVoteWant.setVisibility(View.GONE);
                btnVoteBuy.setVisibility(View.VISIBLE);
                if (isShelf) {//上架
                    btnVoteBuy.setClickable(true);
                    btnVoteBuy.setText("购买");
                    btnVoteBuy.setBackgroundResource(R.drawable.shape_vote_buy_detail);
                } else {
                    btnVoteBuy.setClickable(false);
                    btnVoteBuy.setText("已下架");
                    btnVoteBuy.setBackgroundResource(R.drawable.shape_vote_no_buy_detail);
                }

            } else {
                btnVoteWant.setVisibility(View.VISIBLE);
                btnVoteBuy.setVisibility(View.GONE);
                if (isVote) {//已投票
                    btnVoteWant.setText("已预购");
                    btnVoteWant.setClickable(false);
                    btnVoteWant.setEnabled(false);
                    btnVoteWant.setBackgroundResource(R.drawable.shape_vote_detail_want_no);
                } else {
                    btnVoteWant.setText("预购");
                    btnVoteWant.setClickable(true);
                    btnVoteWant.setEnabled(true);
                    btnVoteWant.setBackgroundResource(R.drawable.shape_vote_detail_want);
                }
            }

            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);//保留2位小数
            String percentage = numberFormat.format((float) wanNum / (float) totalNum * 100);
            if (percentage.contains(".")) {
                String result = percentage.substring(0, percentage.indexOf("."));
                previewProgressBar.setProgress(Integer.valueOf(result));
            } else {
                previewProgressBar.setProgress(Integer.valueOf(percentage));
            }
        } else if (fromType == 2) {
            rlMall.setVisibility(View.VISIBLE);
            rlVote.setVisibility(View.GONE);
        } else {//会员礼包---代理礼包
            setMargins(mLinearLayout, 0, 0, 0, 0);
            rlMall.setVisibility(View.GONE);
            rlVote.setVisibility(View.GONE);
        }


        initData();

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
                    String resultMemo = memo.substring(0, memo.lastIndexOf(","));
                    Log.e(TAG, "onAdded:resultMemo= " + resultMemo);
                    if (type == 1) {
                        addCart(sku, quantity, resultMemo);
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

        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsID);
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailActivity.this, params, TAG, ReqConstance.I_GOODS_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
//                    ModelLoading.getInstance(GoodsDetailActivity.this).closeLoading();
                    tokenTimeLimit(GoodsDetailActivity.this, code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        try {
                            Gson gson = new Gson();
                            goodsDetailBean = gson.fromJson(jsonArray.get(0).toString(), GoodsDetailBean.class);
//                            goodsDetailBean = gson.fromJson(jsonArray.get(0).toString(), GoodsDetailBean.class);
                            compareUrl = goodsDetailBean.getCompareUrl();
                            pasterData(goodsDetailBean);
                            LogUtils.e(TAG, "requestSuccess: " + goodsDetailBean.getName());
//                            initWeb(goodesWebUrl);
                            RichText.from(goodsDetailBean.getContent()).into(tvRichContent);
                            cartNum = goodsDetailBean.getCartNum();//购物车数量
                            cartBadge.bindTarget(imgCart).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(cartNum).setExactMode(false);
                            cartBadge.setGravityOffset(-2, -2, true);
                            isFavorite = goodsDetailBean.isIsFavorite();
                            shareTile = goodsDetailBean.getName();
                            shareContent = goodsDetailBean.getTitle();
                            shareIconUrl = goodsDetailBean.getImage();

                            if (isFavorite) {//是否收藏
                                imgCollection.setImageResource(R.mipmap.ic_goods_sc_red);
                            } else {
                                imgCollection.setImageResource(R.mipmap.ic_goods_sc);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
//                    ModelLoading.getInstance(GoodsDetailActivity.this).closeLoading();
                    imgGoodsJoinCart.setClickable(false);
                    imgGoodsBuy.setClickable(false);
                    imgGoodsShare.setClickable(false);
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(s);
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
        List<SkuListBean> skuList = goodsDetailBean.getSkuList();
        skuListBeanList.addAll(skuList);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    private void initWeb(String webUrl) {
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

        //注入对象
        if (mAgentWeb != null) {
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, GoodsDetailActivity.this));
        }

        mAgentWeb.getJsAccessEntrace().quickCallJs("getImage()");

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
            Log.i("Info", "BaseWebActivity onPageStarted");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "onPageFinished: ");
            ModelLoading.getInstance(GoodsDetailActivity.this).closeLoading();
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.e(TAG, "onProgressChanged: ");
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


    @OnClick({R.id.img_collection, R.id.img_cart, R.id.img_goods_buy, R.id.img_goods_join_cart, R.id.rl_vote_kf, R.id.btn_vote_want, R.id.btn_vote_buy, R.id.img_goods_share, R.id.img_bijia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.img_kf://客服
//                if (!isLogin) {
//                    goLogin();
//                    return;
//                }
//                Intent kefu = new Intent(GoodsDetailActivity.this, KeFuWebViewActivity.class);
//                kefu.putExtra("web_url", Constant.WEB_KE_FU);
//                startActivity(kefu);
//                break;
            case R.id.img_collection://收藏
                if (!isLogin) {
                    goLogin();
                    return;
                }
                if (isFavorite) {
                    //取消
                    deleteFavorite();
                } else {
                    insertFavorite();
                }
                break;
            case R.id.img_cart://去往购物车
                if (!isLogin) {
                    goLogin();
                    return;
                }
                Intent intent = new Intent(GoodsDetailActivity.this, MainActivity.class);
                intent.putExtra("go_cart", true);
                startActivity(intent);
                finish();
                break;
            case R.id.img_goods_buy://购买
                if (!isLogin) {
                    goLogin();
                    return;
                }
                showDilog(2);
                break;
            case R.id.img_goods_join_cart://加入购物车
                if (!isLogin) {
                    goLogin();
                    return;
                }
                showDilog(1);
                break;
            case R.id.rl_vote_kf://投票--客服
                if (!isLogin) {
                    goLogin();
                    return;
                }
                Intent voteKeFu = new Intent(GoodsDetailActivity.this, KeFuWebViewActivity.class);
                voteKeFu.putExtra("web_url", Constant.WEB_KE_FU);
                startActivity(voteKeFu);
                break;
            case R.id.btn_vote_want://投票--我想要
                if (!isLogin) {
                    goLogin();
                    return;
                }
                Log.e(TAG, "onViewClicked:投票--我想要 ");
                doVote(loginUserId, voteId, goodsId);
                break;
            case R.id.btn_vote_buy://投票--购买
                if (!isLogin) {
                    goLogin();
                    return;
                }
                Log.e(TAG, "onViewClicked: 投票--购买");
                showDilog(2);
                break;
            case R.id.img_goods_share:
                returnBitMap(shareIconUrl);
//                showSharePop();
                break;
            case R.id.img_bijia://比价
                if (TextUtils.isEmpty(compareUrl)) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast("未找到同类商品~");
                } else {
                    Intent priva = new Intent(GoodsDetailActivity.this, MyWebViewActivity.class);
                    priva.putExtra("web_url", compareUrl);
                    startActivity(priva);
                }
                break;
        }
    }

    /**
     * 添加收藏
     *
     * @param
     */
    private void insertFavorite() {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsDetailBean.getGoodsId());
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailActivity.this, params, TAG, ReqConstance.I_GOODS_FAVORITE_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        imgCollection.setImageResource(R.mipmap.ic_goods_sc_red);
                        isFavorite = true;
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    } else {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 取消收藏
     *
     * @param
     */
    private void deleteFavorite() {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsDetailBean.getGoodsId());
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailActivity.this, params, TAG, ReqConstance.I_GOODS_FAVORITE_DELETE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        imgCollection.setImageResource(R.mipmap.ic_goods_sc);
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                        isFavorite = false;

                    } else {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加入购物车
     *
     * @param sku
     */
    private void addCart(SkuListBean sku, int num, String memo) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);
            params.put("goodsId", goodsDetailBean.getGoodsId());
            params.put("goodsName", goodsDetailBean.getName());
            params.put("goodsImage", goodsDetailBean.getImage());
            params.put("skuId", sku.getSkuId());
            params.put("skuNum", num);
            params.put("skuName", sku.getSkuName());
            params.put("skuImage", sku.getImage());
            if (isVip) {
                params.put("unitPrice", sku.getVipPrice());
            } else {
                params.put("unitPrice", sku.getPrice());
            }
//            if (isVip) {
//                params.put("transport", 0);
//            } else {
            params.put("transport", goodsDetailBean.getTransport());
//            }

            params.put("memo", memo);//SKU 的specValue值拼接，“黑色，L”


            RequestInterface.cartPrefix(GoodsDetailActivity.this, params, TAG, ReqConstance.I_CART_ADD, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    if (code == 0) {
                        cartBadge.setBadgeNumber(cartNum + 1);
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                        String jsonResult = jsonArray.toString();
                    } else {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

        if (isVip) {
            double price = Double.parseDouble(sku.getVipPrice());
            totalPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(num));
            entity.setUnitPrice(sku.getVipPrice());
            entity.setTransport(goodsDetailBean.getTransport());
        } else {
            double price = Double.parseDouble(sku.getPrice());
            totalPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(num));
            entity.setUnitPrice(sku.getPrice());
            entity.setTransport(goodsDetailBean.getTransport());
        }

        entity.setMemo(memo);
        List<CartShopBean.CartListBean> selectList = new ArrayList<>();
        selectList.add(entity);

        Intent intent = new Intent(GoodsDetailActivity.this, OrderXiaDanActivity.class);
        intent.putExtra("total_price", totalPrice);
        intent.putExtra("select_data", (Serializable) selectList);
        startActivity(intent);
//        finish();
    }


    /**
     * 投票
     */
    private void doVote(String userid, int votid, int goodsid) {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userid);
            params.put("voteId", votid);
            params.put("goodsId", goodsid);
            RequestInterface.voteRequest(GoodsDetailActivity.this, params, TAG, ReqConstance.I_VOTE_GOODS_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                        btnVoteWant.setText("已预购");
                        btnVoteWant.setClickable(false);
                        btnVoteWant.setEnabled(false);
                        btnVoteWant.setBackgroundResource(R.drawable.shape_vote_detail_want_no);
                        tvVoteNumWant.setText(wanNum + 1 + "人想要");
                    } else {
                        ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
//        webView.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
        mAgentWeb.clearWebCache();
        ModelLoading.getInstance(GoodsDetailActivity.this).closeLoading();
    }

    private void goLogin() {
        Intent intent = new Intent(GoodsDetailActivity.this, LoginActivity.class);
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

    /**
     * 弹窗 商品详情链接分享
     */
    private void showSharePop() {

        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(GoodsDetailActivity.this).inflate(R.layout.popup_share, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(GoodsDetailActivity.this)
                .setView(R.layout.popup_share)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView shareWeiXin = (ImageView) view.findViewById(R.id.img_share_weixin);
        ImageView shareCircle = (ImageView) view.findViewById(R.id.img_share_circle);
        ImageView shareWb = (ImageView) view.findViewById(R.id.img_share_wb);
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

        shareWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                /**
                 * 第三方应用发送请求消息到微博，唤起微博分享界面。
                 */
                sendMessage(true,false);
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
    }

    /**
     * 微博分享
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
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_agent_center);
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText(String shareContent) {
        String text =shareContent+shareUrl ;
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
            Log.e(TAG, "onComplete: ");
//            Toast.makeText(MyCenterActivity.this, "分享完成:", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(UiError error) {
            Log.e(TAG, "onError: ");
            Toast.makeText(GoodsDetailActivity.this, "分享失败:" + error.errorMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ");
//            Toast.makeText(MyCenterActivity.this, "分享取消", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        shareHandler.doResultIntent(data,this);
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
}
