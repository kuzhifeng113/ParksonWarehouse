package com.woyun.warehouse.welfare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
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
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.bean.RedPackBean;
import com.woyun.warehouse.bean.WelfateBean;
import com.woyun.warehouse.my.activity.MyFanActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.JudgeNestedScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;

/**
 * 福利 4.0  商品 categoryId=-4
 */
public class WelfareFragment extends BaseFragmentTwo implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "WelfareFragment";
    private static final int THUMB_SIZE = 150;

    public static final int REQUEST_CODE = 200;
    public static final int RESULT_CODE = 201;
    private static final int REFRESH_COMPLETE_WAEL = 1000;
    public static final int STRAT_REQUEST = 2000;
    public static final int FINISH_RESULT = 3000;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;


    @BindView(R.id.scrollView)
    JudgeNestedScrollView scrollView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.text_memu)
    TextView ivMenu;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.toolbar_username)
    TextView toolbarUsername;


    @BindView(R.id.tv_username)
    TextView tvUsername;


    @BindView(R.id.recycler_view_vip_lb)
    RecyclerView recyclerViewVipGift;
    //    @BindView(R.id.img_ding)
//    MarqueeView marqueeView;
    @BindView(R.id.btn_ti_xian)
    TextView btnTiXian;
    @BindView(R.id.tv_redpack_money)
    TextView tvRedpackMoney;
    @BindView(R.id.iv_redbao_mx)
    ImageView ivRedbaoMx;
    @BindView(R.id.btn_chai_red)
    ImageView btnChaiRed;
    @BindView(R.id.tv_unpack_num)
    TextView tvUnPack;
    @BindView(R.id.tv_already_chai_num)
    TextView tvAlreadyChaiNum;
    @BindView(R.id.tv_alreay_chai_money)
    TextView tvAlreayChaiMoney;
    @BindView(R.id.img_share_welfare)
    ImageView imgShareWelfare;

    @BindView(R.id.img_my_fans)
    ImageView imgMyFans;
    @BindView(R.id.tv_fans_num)
    TextView tvFansNum;
    @BindView(R.id.tv_energy)
    TextView tvEnergy;


    private int mOffset = 0;
    private int mScrollY = 0;
    int toolBarPositionY = 0;

    private WelfareAdapter welfareAdapter;
    private CommonPopupWindow popupWindow;

    private boolean isLogin;
    private Animation mAnimation = null;

    private List<WelfateBean.GoodsListBean> vipListBeanList = new ArrayList<>();
    private List<WelfateBean.RedListBean> redListBeanList = new ArrayList<>();
    List<String> redInfo = new ArrayList<>();
    private String redPackMoney;//拆得到的红包金额

    //分享
    private String shareTile, shareContent, shareDownUrl, shareIcon;
    private ShareQQListener mIUiListener;
    private WbShareHandler shareHandler;
    private IWXAPI iwxApi;
    private Tencent mTencent;
    private String loginUserId;
    private String nickName;
    private Bitmap mBitmapCover;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE_WAEL:
                    showSharePop();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_welfare, container, false);
        unbinder = ButterKnife.bind(this, view);


        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(getActivity(), Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getActivity());
        //微博
        shareHandler = new WbShareHandler(getActivity());
        shareHandler.registerApp();

        initView();
        toolBar.setVisibility(View.GONE);
        ImmersionBar.setTitleBar(getActivity(), toolBar);
        welfareAdapter = new WelfareAdapter(getActivity(), vipListBeanList);

        recyclerViewVipGift.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (recyclerViewVipGift.getItemDecorationCount() == 0) {
            recyclerViewVipGift.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 8)));//垂直间距
        }
        recyclerViewVipGift.setAdapter(welfareAdapter);


        //跑马灯
        welfareAdapter.setOnItemClickListener(position -> {
            goToDeatail(position);

        });
        welfareAdapter.setOnButtonClickListener(positon -> {
            goToDeatail(positon);
        });

        return view;
    }

    private void goToDeatail(int positon) {
        Intent intent = new Intent(getActivity(), GoodsDetailWelfareActivity.class);
        intent.putExtra("goods_id", vipListBeanList.get(positon).getGoodsId());
        intent.putExtra("redpack_money", Integer.valueOf(tvEnergy.getText().toString().trim()));
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.e(TAG, "onActivityResult: " + resultCode);
        if (resultCode == RESULT_CODE) {
            getData();
        } else if (resultCode == FINISH_RESULT) {
            showSharePop();
        }
    }

    /**
     * 轮播图
     */
    private void initMarquee(List<WelfateBean.RedListBean> redListBeans) {
        redInfo.clear();
        for (WelfateBean.RedListBean redListBean : redListBeans) {
            if (redListBean.getNickname().length() > 7) {
                redInfo.add(redListBean.getNickname().substring(0, 7) + "邀请1名好友,拆得" + redListBean.getMoney() + "元红包~");
            } else {
                redInfo.add(redListBean.getNickname() + "邀请1名好友,拆得" + redListBean.getMoney() + "元红包~");
            }

        }

//        if(redInfo.size()>1){
//            marqueeView.startWithList(redInfo);
//        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            LogUtils.e(TAG, "setUserVisibleHint:###########");
            isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
            loginUserId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
            shareTile = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_TITLE, "");
            shareContent = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_CONTENT, "");
            shareIcon = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_ICON, "");
            shareDownUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_URL, "");
            nickName = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_NICK_NAME, "");

        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                if (toolBar != null) {
                    toolBar.setAlpha(1 - Math.min(percent, 1));
                }
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                if (toolBar != null) {
                    toolBar.setAlpha(1 - Math.min(percent, 1));
                }
            }
        });


        toolBar.post(new Runnable() {
            @Override
            public void run() {
                dealWithViewPager();
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int lastScrollY = 0;
            int h = DensityUtil.dp2px(170);
            int color = ContextCompat.getColor(getContext(), R.color.white) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
//                magicIndicator.getLocationOnScreen(location);
                int yPosition = location[1];
                if (yPosition < toolBarPositionY) {
//                    magicIndicatorTitle.setVisibility(View.VISIBLE);
                    scrollView.setNeedScroll(false);
                } else {
//                    magicIndicatorTitle.setVisibility(View.GONE);
                    scrollView.setNeedScroll(true);

                }

                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBarLayout.setAlpha(1f * mScrollY / h);
                    toolBar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
//                    ivHeader.setTranslationY(mOffset - mScrollY);
                }
                if (scrollY == 0) {
                    toolBar.setVisibility(View.GONE);
                    ivMenu.setVisibility(View.GONE);
                    ivBack.setImageResource(R.mipmap.back_white);
//                    ivMenu.setImageResource(R.mipmap.icon_menu_white);
                } else {
                    toolBar.setVisibility(View.VISIBLE);
                    ivMenu.setVisibility(View.VISIBLE);
                    toolBar.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ivBack.setImageResource(R.mipmap.back_black);
//                    ivMenu.setImageResource(R.mipmap.icon_menu_black);
                }

                lastScrollY = scrollY;
            }
        });
        buttonBarLayout.setAlpha(0);
        toolBar.setBackgroundColor(0);
    }

    private void dealWithViewPager() {
        toolBarPositionY = toolBar.getHeight();

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();

    }


    //获取数据
    private void initData() {

        //触发自动刷新
        mRefreshLayout.autoRefresh();
//        mRefreshLayout.setEnableAutoLoadMore(true);//开启自动加载功能（非必须）
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        vipListBeanList.clear();
                        redListBeanList.clear();
                        getData();
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 800);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    /**
     * 获取数据
     */
    private void getData() {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
            RequestInterface.redpackPrefix(getActivity(), params, TAG, ReqConstance.I_REDPACK_HOME, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0 && jsonArray.length() > 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            WelfateBean welfateBean = gson.fromJson(object.toString(), WelfateBean.class);

                            pasterData(welfateBean);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(getActivity()).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析数据
     *
     * @param welfateBean
     */
    private void pasterData(WelfateBean welfateBean) {
        if (welfateBean != null) {
            tvRedpackMoney.setText(welfateBean.getRedPackMoney());
            tvAlreadyChaiNum.setText(String.valueOf(welfateBean.getOpenPack()));
            tvEnergy.setText(String.valueOf(welfateBean.getUnPack()));
            tvFansNum.setText(welfateBean.getfNum());
//            tvUnPack.setText("剩余可拆" + welfateBean.getUnPack() + "次");
//            tvAlreayChaiMoney.setText(welfateBean.getTotalMoney() + "元");
            vipListBeanList.clear();
            redListBeanList.clear();
            vipListBeanList.addAll(welfateBean.getGoodsList());
            redListBeanList.addAll(welfateBean.getRedList());

//            initMarquee(redListBeanList);
            welfareAdapter.notifyDataSetChanged();
        }

    }


    @OnClick({R.id.btn_ti_xian, R.id.iv_redbao_mx, R.id.btn_chai_red, R.id.img_share_welfare, R.id.img_my_fans})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ti_xian://提现
                Intent intentWith = new Intent(getActivity(), RedWithDrawActivity.class);
                intentWith.putExtra("tixian_money", tvRedpackMoney.getText().toString().trim());
                startActivityForResult(intentWith, REQUEST_CODE);
                break;
            case R.id.iv_redbao_mx://明细
                Intent intent = new Intent(getActivity(), RedYuErActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_chai_red://拆红包
                getRedPack((String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
                break;
            case R.id.img_share_welfare:
                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                returnBitMap(shareIcon);
//                showSharePop();
//                Intent share = new Intent(getActivity(), ShareActivity.class);
//                startActivity(share);
                break;
            case R.id.img_my_fans:
                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                Intent fans = new Intent(getActivity(), MyFanActivity.class);
                startActivity(fans);
                break;
        }
    }


    /**
     * 弹窗拆红包
     *
     * @param
     */
    public void showChaiRed() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_fuli_redpack, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_fuli_redpack)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setOutsideTouchable(true)
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

    }

    /**
     * 没有拆红包次数
     *
     * @param
     */
    public void showNoNumRed() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_fuli_no_redpack, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_fuli_no_redpack)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setOutsideTouchable(true)
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        ImmersionBar.with(this)
//                .statusBarDarkFont(true).init();
//    }

    /**
     * 分享网页 下载链接
     *
     * @param
     */
    private void showSharePop() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_share, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_share)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_fuli_redpack:
                //获得PopupWindow布局里的View
                ImageView img_main_get = view.findViewById(R.id.img_main_get);
                TextView tv_red_money = view.findViewById(R.id.tv_red_money);
                ImageView close = view.findViewById(R.id.img_close);
                tv_red_money.setText(redPackMoney);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                //分享
                img_main_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        returnBitMap(shareIcon);
                    }
                });
                break;
            case R.layout.popup_fuli_no_redpack:
                //获得PopupWindow布局里的View
                ImageView img_main_share = view.findViewById(R.id.img_main_get);
                ImageView closed = view.findViewById(R.id.img_close);

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
                        returnBitMap(shareIcon);
                    }
                });

                break;

            case R.layout.popup_share:
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

                        iwxApi.sendReq(shareWxUrl(shareDownUrl + "?share=" + loginUserId, shareTile, shareContent, 0, shareIcon));
                    }
                });

                shareCircle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        LogUtils.e(TAG, "onClick: " + shareDownUrl + loginUserId + shareTile + shareContent + shareIcon);
                        iwxApi.sendReq(shareWxUrl(shareDownUrl + "?sharekey=" + loginUserId,
                                shareTile, shareContent, 1, shareIcon));
                    }
                });

                //微博
                shareWb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }

                        //链接
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
                        LogUtils.e(TAG, "onClick:url =====" + shareDownUrl + "?sharekey=" + loginUserId);
                        LogUtils.e(TAG, "onClick:url =====" + shareIcon);
                        final Bundle params = new Bundle();
//                        if(shareType==0){//纯图片分享
//                            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg");//本地图片
//                            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
//                            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
////                    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//可选
//                            mTencent.shareToQQ(mContext, params,  mIUiListener);
//
//
//                        }else{//链接分享
                        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                        params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
                        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent);
                        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareDownUrl + "?sharekey=" + loginUserId);
                        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
                        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareIcon);// 网络图片地址　
                        LogUtils.e(TAG, "onClick:### " + shareIcon);
                        //params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
//                        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
                        // 分享操作要在主线程中完成
                        mTencent.shareToQQ(getActivity(), params, mIUiListener);
//                ToastUtils.getInstanc(MyCenterActivity.this).showToast("qq");
//                        }
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
        }
    }


    /**
     * Item 横向布局点击居中
     * Item中的点击事件调用此方法则，不在中间位置的View自动滚动到屏幕中间的位置
     */


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void startAnim(View view) {

        mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_tween);
        view.setAnimation(mAnimation);
        mAnimation.start();
    }

    @Override
    public void onPause() {
        super.onPause();
//        imgClick.clearAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
//        startAnim(imgClick);
    }

    /**
     * 领红包
     *
     * @param userId
     * @param
     */
    private void getRedPack(String userId) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);

            RequestInterface.redpackPrefix(getActivity(), params, TAG, ReqConstance.I_REDPACK_PACK, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            RedPackBean redPackBean = gson.fromJson(object.toString(), RedPackBean.class);
                            redPackMoney = redPackBean.getMoney();
                            tvRedpackMoney.setText(redPackBean.getRedPackMoney());
                            tvAlreadyChaiNum.setText(String.valueOf(redPackBean.getOpenPack()));
                            tvUnPack.setText("剩余可拆" + redPackBean.getUnPack() + "次");
                            tvAlreayChaiMoney.setText(redPackBean.getTotalMoney() + "元");
                            showChaiRed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (code == -2) {
                        showNoNumRed();
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
            Toast.makeText(getActivity(), "分享失败:" + error.errorMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            LogUtils.e(TAG, "onCancel: ");
//            Toast.makeText(MyCenterActivity.this, "分享取消", Toast.LENGTH_LONG).show();
        }
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

        //本地图片
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(mBitmapCover, THUMB_SIZE, THUMB_SIZE, true);
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        mBitmapCover.recycle();

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = id;
        return req;
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
        textObject.actionUrl = shareDownUrl + "?sharekey=" + loginUserId;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_agent_center);
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/BSC/sharerweima.jpg");
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText(String shareContent) {
        String text = shareContent + shareDownUrl + "?sharekey=" + loginUserId;
        return text;
    }


    /**
     * 网络图片获取bitmap
     *
     * @param url
     * @param
     */
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
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE_WAEL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
