package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.woyun.warehouse.mall.ProductSkuDialog;
import com.woyun.warehouse.mall.adapter.NativeContentAdapter;
import com.woyun.warehouse.mall.adapter.NativeViewPageAdapter;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray2;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;


/**
 * 商品详情-原生
 */
public class GoodsDetailNativeActivity extends BaseActivity implements CommonPopupWindow.ViewInterface, WbShareCallback {
    private static final String TAG = "GoodsDetailActivity";
    private static final int THUMB_SIZE = 150;
    private static final int REFRESH_COMPLETE = 1000;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.webViewDetail)
    LinearLayout mLinearLayout;
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

    Badge cartBadge;
    private int cartNum;
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
        setContentView(R.layout.activity_goods_detail_native);
        ButterKnife.bind(this);
        mContext = GoodsDetailNativeActivity.this;
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(GoodsDetailNativeActivity.this, Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        //微博
        shareHandler = new WbShareHandler(GoodsDetailNativeActivity.this);
        shareHandler.registerApp();

        isVip = (boolean) SPUtils.getInstance(GoodsDetailNativeActivity.this).get(Constant.USER_IS_VIP, false);
        isLogin = (boolean) SPUtils.getInstance(GoodsDetailNativeActivity.this).get(Constant.IS_LOGIN, false);
        cartBadge = new QBadgeView(this);
        toolBar.setNavigationOnClickListener(v -> finish());

        goodsId = getIntent().getIntExtra("goods_id", 0);
        if (isVip) {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 1;
        } else {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 0;
        }
        shareUrl = Constant.WEB_SHARE_GOODS2 + "?goodsId=" + goodsId + "&share=" + loginUserId;

        fromType = getIntent().getIntExtra("from_id", 0);
        voteId = getIntent().getIntExtra("vote_id", 0);
        isHistory = getIntent().getBooleanExtra("is_History", false);
        isVote = getIntent().getBooleanExtra("is_vote", false);
        wanNum = getIntent().getIntExtra("wan_count", 0);
        totalNum = getIntent().getIntExtra("total_count", 0);
        isShelf = getIntent().getBooleanExtra("is_shelf", false);
        tvVoteNumWant.setText(wanNum + "人想要");

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

        } else if (fromType == 2) {
            rlMall.setVisibility(View.VISIBLE);
            rlVote.setVisibility(View.GONE);
        } else {//会员礼包---代理礼包
            setMargins(nestedScrollView, 0, 0, 0, 0);
            rlMall.setVisibility(View.GONE);
            rlVote.setVisibility(View.GONE);
        }


        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (recyclerView.getItemDecorationCount() == 0) {
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(GoodsDetailNativeActivity.this, 15)));//垂直间距
        }
        initData();

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
//        Log.e(TAG, "onCreate: 屏幕高2"+height);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e(TAG, "onScrollChange: 滑动后 Y " + scrollY);

                if(scrollY>3*height){//大于3个屏幕的高度
                    imgBackTop.setVisibility(View.VISIBLE);
                }else{
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
        ModelLoading.getInstance(GoodsDetailNativeActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsID);
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailNativeActivity.this, params, TAG, ReqConstance.I_GOODS_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(GoodsDetailNativeActivity.this).closeLoading();
                    tokenTimeLimit(GoodsDetailNativeActivity.this, code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        try {
                            Gson gson = new Gson();
                            goodsDetailBean = gson.fromJson(jsonArray.get(0).toString(), GoodsDetailBean.class);
                            compareUrl = goodsDetailBean.getCompareUrl();
                            pasterData(goodsDetailBean);
                            LogUtils.e(TAG, "requestSuccess: " + goodsDetailBean.getName());
//                            initWeb(goodesWebUrl);
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
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(GoodsDetailNativeActivity.this).closeLoading();
                    imgGoodsJoinCart.setClickable(false);
                    imgGoodsBuy.setClickable(false);
                    imgGoodsShare.setClickable(false);
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(s);
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
        tvVipBack.setText("会员返" + goodsDetailBean.getBkCoin());
        tvGoodsPrice.setText("原价:" + goodsDetailBean.getPrice());
        tvGoodsTitle.setText(goodsDetailBean.getName());

        tvTransport.setText("邮费：" + goodsDetailBean.getTransport());
        tvSalesVolume.setText("销量：" + goodsDetailBean.getSellNum());
        tvStock.setText("库存：" + goodsDetailBean.getStock());
        tvBaoYou.setText("全场满" + goodsDetailBean.getFreeShopping() + "包邮");

        resListBeanList = goodsDetailBean.getResList();
        contentListBeanList = goodsDetailBean.getContentList();

        nativeContentAdapter = new NativeContentAdapter(GoodsDetailNativeActivity.this, contentListBeanList);
        recyclerView.setAdapter(nativeContentAdapter);

        nativeViewPageAdapter = new NativeViewPageAdapter(GoodsDetailNativeActivity.this, resListBeanList);
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
                Intent toLook = new Intent(GoodsDetailNativeActivity.this, LookImageVideoActivity.class);
                toLook.putExtra("reslist", (Serializable) resListBeanList);
                toLook.putExtra("index", index);
                startActivity(toLook, ActivityOptions.makeSceneTransitionAnimation(GoodsDetailNativeActivity.this).toBundle());
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
                for(int j=0; contentListBeanList.size()==position;j++){
                    if(contentListBeanList.get(j).getType()==3){
                        a++;
                    }
                }

                Log.e(TAG, "onItemClick: a===" + a);

                Intent toLook = new Intent(GoodsDetailNativeActivity.this, LookImageVideoActivity.class);
                toLook.putExtra("reslist", (Serializable) contentResList);
                if(a>position){
                    toLook.putExtra("index", position);
                }else{
                    toLook.putExtra("index", position - a);
                }

                startActivity(toLook, ActivityOptions.makeSceneTransitionAnimation(GoodsDetailNativeActivity.this).toBundle());
            }
        });


    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }



    @OnClick({R.id.img_collection, R.id.img_cart, R.id.img_goods_buy, R.id.img_goods_join_cart, R.id.img_goods_share, R.id.img_bijia, R.id.img_back_top})
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
                Intent intent = new Intent(GoodsDetailNativeActivity.this, MainActivity.class);
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

            case R.id.img_goods_share:
                returnBitMap(shareIconUrl);
//                showSharePop();
                break;
            case R.id.img_bijia://比价
                if (TextUtils.isEmpty(compareUrl)) {
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast("未找到同类商品~");
                } else {
                    Intent priva = new Intent(GoodsDetailNativeActivity.this, MyWebViewActivity.class);
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
            RequestInterface.goodsPrefix(GoodsDetailNativeActivity.this, params, TAG, ReqConstance.I_GOODS_FAVORITE_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        imgCollection.setImageResource(R.mipmap.ic_goods_sc_red);
                        isFavorite = true;
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    } else {
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(s);
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
            RequestInterface.goodsPrefix(GoodsDetailNativeActivity.this, params, TAG, ReqConstance.I_GOODS_FAVORITE_DELETE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        imgCollection.setImageResource(R.mipmap.ic_goods_sc);
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                        isFavorite = false;

                    } else {
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(s);
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


            RequestInterface.cartPrefix(GoodsDetailNativeActivity.this, params, TAG, ReqConstance.I_CART_ADD, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    if (code == 0) {
                        cartBadge.setBadgeNumber(cartNum + 1);
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                        String jsonResult = jsonArray.toString();
                    } else {
                        ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(GoodsDetailNativeActivity.this).showToast(s);
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

        Intent intent = new Intent(GoodsDetailNativeActivity.this, OrderXiaDanActivity.class);
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
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
//        webView.destroy();
        ModelLoading.getInstance(GoodsDetailNativeActivity.this).closeLoading();
    }

    private void goLogin() {
        Intent intent = new Intent(GoodsDetailNativeActivity.this, LoginActivity.class);
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
    protected void onPause() {
        super.onPause();
        JzvdStd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
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
        View upView = LayoutInflater.from(GoodsDetailNativeActivity.this).inflate(R.layout.popup_share, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(GoodsDetailNativeActivity.this)
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
                sendMessage(true, false);
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
            Log.e(TAG, "onComplete: ");
//            Toast.makeText(MyCenterActivity.this, "分享完成:", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(UiError error) {
            Log.e(TAG, "onError: ");
            Toast.makeText(GoodsDetailNativeActivity.this, "分享失败:" + error.errorMessage, Toast.LENGTH_LONG).show();
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
}
