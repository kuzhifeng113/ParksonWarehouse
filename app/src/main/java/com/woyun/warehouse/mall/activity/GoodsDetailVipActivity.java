package com.woyun.warehouse.mall.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.GoodsDetailBean;
import com.woyun.warehouse.bean.SkuListBean;
import com.woyun.warehouse.utils.AndroidInterface;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Vip  代理 开通代理 --------》》商品详情
 */
public class GoodsDetailVipActivity extends BaseActivity {
    private static final String TAG = "GoodsDetailVipActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.webViewDetail)
    RelativeLayout mLinearLayout;

    @BindView(R.id.rl_vip)
    RelativeLayout rlVip;
    @BindView(R.id.tv_vip_buy)
    TextView tvVipBuy;
    @BindView(R.id.btn_agent_open)
    Button btnAgentOpen;
    @BindView(R.id.rl_open_agent)
    RelativeLayout rlOpenAgent;
    @BindView(R.id.btn_agent_buy)
    Button btnAgentBuy;
    @BindView(R.id.rl_buy_agent)
    RelativeLayout rlBuyAgent;


    private List<SkuListBean> skuListBeanList = new ArrayList<>();
    private GoodsDetailBean goodsDetailBean;

    private boolean isVip;
    private boolean isLogin;

    private int goodsId;//商品id
    private int fromType;// 3 vip 中心  4开通代理  5代理中心 代理购买
    private long time;//VIP 到期时间
    private String goodesWebUrl;
    protected AgentWeb mAgentWeb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_vip_detail_web);
        ButterKnife.bind(this);
        isVip = (boolean) SPUtils.getInstance(GoodsDetailVipActivity.this).get(Constant.USER_IS_VIP, false);
        isLogin = (boolean) SPUtils.getInstance(GoodsDetailVipActivity.this).get(Constant.IS_LOGIN, false);

        toolBar.setNavigationOnClickListener(v -> finish());

        goodsId = getIntent().getIntExtra("goods_id", 0);
        String endTime=getIntent().getStringExtra("end_time");
        if (isVip) {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 1;
        } else {
            goodesWebUrl = Constant.WEB_GOODS_DETAIL + "?id=" + goodsId + "&vip=" + 0;
        }

        ModelLoading.getInstance(GoodsDetailVipActivity.this).showLoading("", true);
        initWeb(goodesWebUrl);

        fromType = getIntent().getIntExtra("from_id", 0);
        if (fromType == 3) {//3 vip 中心  4开通代理  5代理中心 代理购买
            rlVip.setVisibility(View.VISIBLE);
            rlOpenAgent.setVisibility(View.GONE);
            rlBuyAgent.setVisibility(View.GONE);
            if(isVip){
                tvVipBuy.setText(endTime+"到期");
            }else{
                tvVipBuy.setText("购买VIP");
            }
//            setMargins(mLinearLayout,0,0,0,0);
        }else if(fromType==4){
            rlVip.setVisibility(View.GONE);
            rlOpenAgent.setVisibility(View.VISIBLE);
            rlBuyAgent.setVisibility(View.GONE);
        }else if(fromType==5){
            rlVip.setVisibility(View.GONE);
            rlOpenAgent.setVisibility(View.GONE);
            rlBuyAgent.setVisibility(View.VISIBLE);
        }
        initData();
    }

    private void initData() {
        getData(goodsId);
    }


    /**
     * 获取数据
     *
     * @param
     */
    private void getData(int goodsID) {
        ModelLoading.getInstance(GoodsDetailVipActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsID);
            params.put("userid", loginUserId);
            RequestInterface.goodsPrefix(GoodsDetailVipActivity.this, params, TAG, ReqConstance.I_GOODS_DETAIL, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(GoodsDetailVipActivity.this).closeLoading();
                    tokenTimeLimit(GoodsDetailVipActivity.this, code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        try {
                            Gson gson = new Gson();
                            goodsDetailBean = gson.fromJson(jsonArray.get(0).toString(), GoodsDetailBean.class);
                            pasterData(goodsDetailBean);
                            LogUtils.e(TAG, "requestSuccess: " + goodsDetailBean.getName());
//                            initWeb(goodesWebUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(GoodsDetailVipActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(GoodsDetailVipActivity.this).closeLoading();
                    ToastUtils.getInstanc(GoodsDetailVipActivity.this).showToast(s);
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
            mAgentWeb.getJsInterfaceHolder().addJavaObject("android", new AndroidInterface(mAgentWeb, GoodsDetailVipActivity.this));
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
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
//            Log.e(TAG, "onProgressChanged: " );
            if(newProgress == 100){
                ModelLoading.getInstance(GoodsDetailVipActivity.this).closeLoading();
            }
            //do you work
//            Log.i("Info","onProgress:"+newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }

    };


    @OnClick({R.id.tv_vip_buy, R.id.btn_agent_open, R.id.btn_agent_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_vip_buy:
                finish();
                break;
            case R.id.btn_agent_open:
                finish();
                break;
            case R.id.btn_agent_buy:
                finish();
                break;
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
//        webView.destroy();
        mAgentWeb.getWebLifeCycle().onDestroy();
    }

    /**
     * 设置Margin 值
     * @param v
     * @param l
     * @param t
     * @param r
     * @param b
     */
    public static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }

    }

}
