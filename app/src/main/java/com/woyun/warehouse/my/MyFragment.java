package com.woyun.warehouse.my;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.bean.UserInfoTwoBean;
import com.woyun.warehouse.my.activity.CangCoinActivity;
import com.woyun.warehouse.my.activity.MyAddressActivity;
import com.woyun.warehouse.my.activity.MyCollectionActivity;
import com.woyun.warehouse.my.activity.MyOrderActivity;
import com.woyun.warehouse.my.activity.RealNameActivity;
import com.woyun.warehouse.my.activity.SettingActivity;
import com.woyun.warehouse.my.activity.ShangJiaJoinActivity;
import com.woyun.warehouse.my.activity.ShareActivity;
import com.woyun.warehouse.my.activity.UserInfoActivity;
import com.woyun.warehouse.my.activity.VipEnterActivity;
import com.woyun.warehouse.my.activity.YuErActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.HorizontalProgressBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;

/**
 * 我的
 */
public class MyFragment extends BaseFragment implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "MyFragment";
    private static final int THUMB_SIZE = 150;
    Unbinder unbinder;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_vip_type)
    TextView tvVipType;
    @BindView(R.id.ll_all_order)
    LinearLayout llAboutMe;
    @BindView(R.id.ll_dfk_order)
    LinearLayout llSchool;
    @BindView(R.id.ll_dfh_order)
    LinearLayout llVipCenter;
    @BindView(R.id.ll_order_dsh)
    LinearLayout llProxyCenter;

    @BindView(R.id.img_shangjia_join)
    ImageView imgShangJiaJoin;
    @BindView(R.id.ll_my_collection)
    LinearLayout rlMyCollection;
    @BindView(R.id.ll_my_address)
    LinearLayout rlMyAddress;

    @BindView(R.id.ll_real_name)
    LinearLayout rlRealName;
    @BindView(R.id.img_setting)
    ImageView imgSetting;
    @BindView(R.id.ic_share)
    ImageView icShare;

    @BindView(R.id.ll_about_me)
    LinearLayout rlMyOrder;

    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.rl_111)
    RelativeLayout rl111;
    @BindView(R.id.tv_cangbi)
    TextView tvCangbi;
    @BindView(R.id.tv_wffang_coin)
    TextView tvWffangCoin;
    @BindView(R.id.ll_cang_bi)
    LinearLayout llCangBi;
    @BindView(R.id.tv_yuer_num)
    TextView tvYuerNum;
    @BindView(R.id.tv_wffang_yuer)
    TextView tvWffangYuer;
    @BindView(R.id.ll_yu_er)
    LinearLayout llYuEr;
    @BindView(R.id.tv_leiji)
    TextView tvLeiji;
    @BindView(R.id.tv_zhitui)
    TextView tvZhitui;
    //    @BindView(R.id.tv_xin_ren)
//    TextView tvXinRen;
    @BindView(R.id.tv_yiji_daili)
    TextView tvYijiDaili;

    @BindView(R.id.tv_gou_wu)
    TextView tvGouWu;
    @BindView(R.id.tv_yaoqing_code)
    TextView tvYaoqingCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.ll_is_agent)
    LinearLayout llIsAgent;
    @BindView(R.id.tv_xinren_wen)
    TextView tvXinrenWen;

    @BindView(R.id.img_tishi)
    ImageView imgTishi;
    @BindView(R.id.tv_yesterday_money)
    TextView tvLastWeekMoney;
    @BindView(R.id.tv_today_money)
    TextView tvWeekMoney;
    @BindView(R.id.tv_before_money)
    TextView tvMonthMoney;
    @BindView(R.id.tv_agent_wen)
    TextView tvAgentWen;
//    @BindView(R.id.tv_guanli_wen)
//    TextView tvGuanliWen;
    @BindView(R.id.img_agent_tishi)
    ImageView imgAgentTishi;

    @BindView(R.id.tv_gouwu_wen)
    TextView tvGouwuWen;
    @BindView(R.id.img_gouwu_tishi)
    ImageView imgGouwuTishi;

    @BindView(R.id.img_vip_enter)
    ImageView imgVipEnter;

    @BindView(R.id.tv_total_bkmoney)
    TextView tvToalBkMoney;
    @BindView(R.id.tv_leji_des)
    TextView tvLejiDes;
    @BindView(R.id.rl_zt)
    RelativeLayout rlZt;
    @BindView(R.id.progress_bar)
    HorizontalProgressBar progressBar;
    @BindView(R.id.rl_agent_sy)
    RelativeLayout relativeLayoutAgentSy;



    private boolean isAgent;//是否是代理
    private boolean isVip;//是否是VIP
    private String userId;
    private CommonPopupWindow popupWindow;
    private IWXAPI iwxApi;
    //    private Tencent mTencent;
    private Activity mContext;
    private ShareQQListener mIUiListener;
    private String shareTile, shareContent, shareDownUrl, shareIcon;

    private String vipewm, wxh;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_my, container, false);
        LogUtils.e(TAG, "onCreateView: ===");
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        EventBus.getDefault().register(this);
        mContext = getActivity();
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(getActivity(), Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);

//        tvName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(),AllCategoriesActivityTwo.class);
//                startActivity(intent);
//            }
//        });
//        view.findViewById(R.id.rl_zt).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), DemoActivity.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
//            checkUserId(userId);
//            isAgent= (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_AGENT,false);
//            isVip= (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP,false);
//            shareTile = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_TITLE, "");
//            shareContent = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_CONTENT, "");
//            shareIcon = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_ICON, "");
//            shareDownUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_URL, "");
//            LogUtils.e(TAG, "setUserVisibleHint:------ ");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SaveUserEvent event) {
        LogUtils.e(TAG, "Event: " + event.isSave());
        LogUtils.e(TAG, "Event: " + userId);
        if (event.isSave()) {
            checkUserId(userId);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
//        checkUserId(userId);
//        shareTile = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_TITLE, "");
//        shareContent = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_CONTENT, "");
//        shareIcon = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_ICON, "");
//        shareDownUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_URL, "");
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);

        shareTile = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_TITLE, "");
        shareContent = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_CONTENT, "");
        shareIcon = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_ICON, "");
        shareDownUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_URL, "");
        checkUserId(userId);
        LogUtils.e(TAG, "onFragmentVisibleChangeisVip: " + isVip);
        LogUtils.e(TAG, "onFragmentVisibleChangeisAgent: " + isAgent);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @OnClick({R.id.img_head, R.id.img_setting, R.id.ll_all_order, R.id.ll_dfk_order, R.id.ll_dfh_order, R.id.ll_order_dsh, R.id.ic_share, R.id.img_shangjia_join, R.id.ll_about_me, R.id.ll_my_collection, R.id.ll_my_address, R.id.ll_real_name, R.id.tv_copy, R.id.ll_cang_bi, R.id.ll_yu_er, R.id.tv_xinren_wen, R.id.img_tishi, R.id.tv_agent_wen, R.id.img_agent_tishi, R.id.tv_gouwu_wen, R.id.img_gouwu_tishi, R.id.img_vip_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                Intent user = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(user);
                break;
            case R.id.img_setting:
                MyApplication.getInstance().addActivity(getActivity());
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_all_order://全部订单
                Intent allOrder = new Intent(getActivity(), MyOrderActivity.class);
                allOrder.putExtra("index", 0);
                startActivity(allOrder);

                break;
            case R.id.ll_dfk_order://待付款
                Intent school = new Intent(getActivity(), MyOrderActivity.class);
//                school.putExtra("web_url", Constant.WEB_NEWS_SCHOOL);
                school.putExtra("index", 1);
                startActivity(school);
                break;
            case R.id.ll_dfh_order://待发货
                Intent vipCenter = new Intent(getActivity(), MyOrderActivity.class);
                vipCenter.putExtra("index", 2);
                startActivity(vipCenter);
                break;
            case R.id.ll_order_dsh://待收货
                Intent dsh = new Intent(getActivity(), MyOrderActivity.class);
                dsh.putExtra("index", 3);
                startActivity(dsh);
//                isAgent = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_AGENT, false);
//                isVip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, false);
//                if (isVip) {
//                    if (isAgent) {
//                        Intent agent = new Intent(getActivity(), AgentCenterActivity.class);
//                        startActivity(agent);
//                    } else {
//                        new AgentOpenDialog(getActivity(), R.style.dialogphone, new AgentOpenDialog.OnCloseListener() {
//                            @Override
//                            public void onClick(Dialog dialog, boolean confirm) {
//                                if (confirm) {
//                                    dialog.dismiss();
//                                    Intent open = new Intent(getActivity(), AgentOpenActivity.class);
//                                    startActivity(open);
//                                }
//                            }
//                        }).show();
//
//                    }
//                } else {
//                    new DeleteDialog(getActivity(), R.style.dialogphone, "您还不是会员，是否去开通？", new DeleteDialog.OnCloseListener() {
//                        @Override
//                        public void onClick(Dialog dialog, boolean confirm) {
//                            if (confirm) {
//                                dialog.dismiss();
//                                Intent open = new Intent(getActivity(), VipCenterActivity.class);
//                                startActivity(open);
////                               Intent daili=new Intent(getActivity(),)
//                            }
//                        }
//                    }).show();
//                }


                break;
            case R.id.ic_share://分享
//                showSharePop(rlShare);
                Intent share = new Intent(getActivity(), ShareActivity.class);
                startActivity(share);
                break;
            case R.id.img_shangjia_join://商家入驻
                if (!TextUtils.isEmpty(vipewm) && !TextUtils.isEmpty(wxh)) {
                    Intent shangJia = new Intent(getActivity(), ShangJiaJoinActivity.class);
                    shangJia.putExtra("eweima", vipewm);
                    shangJia.putExtra("wxaccount", wxh);
                    startActivity(shangJia);
                }

                break;
            case R.id.ll_about_me://关于我们
                Intent about = new Intent(getActivity(), MyWebViewActivity.class);
                about.putExtra("web_url", Constant.WEB_ABOUT_ME);
                startActivity(about);
                break;
            case R.id.ll_my_collection://我的收藏
                Intent collect = new Intent(getActivity(), MyCollectionActivity.class);
                startActivity(collect);
                break;
            case R.id.ll_my_address://收货地址
                Intent address = new Intent(getActivity(), MyAddressActivity.class);
                address.putExtra("from_my", true);
                startActivity(address);
                break;
            case R.id.ll_real_name://实名认证
                Intent realName = new Intent(getActivity(), RealNameActivity.class);
                startActivity(realName);
                break;
            case R.id.tv_copy:
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvYaoqingCode.getText().toString().trim());
                ToastUtils.getInstanc(getActivity()).showToast("复制成功");
                break;
            case R.id.ll_cang_bi://仓币
                Intent coin = new Intent(getActivity(), CangCoinActivity.class);
                startActivity(coin);
                break;
            case R.id.ll_yu_er://余额
                Intent myYuer = new Intent(getActivity(), YuErActivity.class);
                startActivity(myYuer);
                break;
            case R.id.tv_xinren_wen:
                imgTishi.setVisibility(View.VISIBLE);
                break;
            case R.id.img_tishi:
                if (imgTishi.getVisibility() == View.VISIBLE) {
                    imgTishi.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_agent_wen:
                imgAgentTishi.setVisibility(View.VISIBLE);
                break;
//            case R.id.tv_guanli_wen:
//                imgGuanliTishi.setVisibility(View.VISIBLE);
//                break;
            case R.id.tv_gouwu_wen:
                imgGouwuTishi.setVisibility(View.VISIBLE);
                break;
            case R.id.img_agent_tishi:
                if (imgAgentTishi.getVisibility() == View.VISIBLE) {
                    imgAgentTishi.setVisibility(View.GONE);
                }
                break;
//            case R.id.img_guanli_tishi:
//                if (imgGuanliTishi.getVisibility() == View.VISIBLE) {
//                    imgGuanliTishi.setVisibility(View.GONE);
//                }
//                break;
            case R.id.img_gouwu_tishi://购物提示
                if (imgGouwuTishi.getVisibility() == View.VISIBLE) {
                    imgGouwuTishi.setVisibility(View.GONE);
                }
                break;
            case R.id.img_vip_enter://VIP 入口
                Intent vipEnter = new Intent(getActivity(), VipEnterActivity.class);
                startActivity(vipEnter);
                break;
        }
    }

    private void showSharePop(RelativeLayout rlShare) {
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

    /**
     * 查询个人信息
     */
    private void checkUserId(String userID) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userID);
            RequestInterface.userPrefix(getActivity(), params, TAG, ReqConstance.I_USER_CENTER, 3, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    if (responseCode == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            UserInfoTwoBean userInfoBean = gson.fromJson(object.toString(), UserInfoTwoBean.class);
                            vipewm = userInfoBean.getVipewm();
                            wxh = userInfoBean.getWxh();
                            tvCangbi.setText(String.valueOf(userInfoBean.getBcCoin()));
                            tvWffangCoin.setText(String.valueOf(userInfoBean.getGrantCbCoin()));
                            tvYuerNum.setText(String.valueOf(userInfoBean.getBcMoney()));
                            tvWffangYuer.setText(String.valueOf(userInfoBean.getGrantMoney()));
                            tvLeiji.setText(String.valueOf(userInfoBean.getTotalMoney()));
                            tvZhitui.setText(String.valueOf(userInfoBean.getPushMoney()));

//                            tvToalBkMoney.setText(String.valueOf(userInfoBean.getNewMoney()));


                            tvWeekMoney.setText(String.valueOf(userInfoBean.getWeekMoney()));
                            tvLastWeekMoney.setText(String.valueOf(userInfoBean.getLastWeekMoney()));
                            tvMonthMoney.setText(String.valueOf(userInfoBean.getMonthMoney()));

                            tvYijiDaili.setText(String.valueOf(userInfoBean.getAgentMoney()));
//                            tvGuanLi.setText(String.valueOf(userInfoBean.getManageMoney()));
                            tvGouWu.setText(String.valueOf(userInfoBean.getShoppingMoney()));

//                            SPUtils.getInstance(getActivity()).put(Constant.USER_INFO_BEAN, object.toString());
                            SPUtils.getInstance(getActivity()).put(Constant.TOKEN, userInfoBean.getUserInfo().getToken());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_IS_REAL, userInfoBean.getUserInfo().isIsReal());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_IS_VIP, userInfoBean.getUserInfo().isIsVip());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_ID, userInfoBean.getUserInfo().getUserid());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_AVATAR, userInfoBean.getUserInfo().getAvatar());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_NICK_NAME, userInfoBean.getUserInfo().getNickname());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_MOBILE, userInfoBean.getUserInfo().getMobile());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_IS_AGENT, userInfoBean.getUserInfo().isIsAgent());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_TWO_PWD, userInfoBean.getUserInfo().getPwd());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_WX, userInfoBean.getUserInfo().getWx());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_ZFB, userInfoBean.getUserInfo().getZfb());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_ZFB_NAME, userInfoBean.getUserInfo().getZfbname());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_WX_NAME, userInfoBean.getUserInfo().getWxname());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_DEFAULT_ADDRESS, userInfoBean.getUserInfo().isDefaultAddress());
//                            SPUtils.getInstance(getActivity()).put(Constant.USER_INVITE_CODE, userInfoBean.getUserInfo().getInviteCode());
                            isVip = userInfoBean.getUserInfo().isIsVip();
                            isAgent = userInfoBean.getUserInfo().isIsAgent();
                            tvName.setText(userInfoBean.getUserInfo().getNickname());
                            Glide.with(getActivity()).load(userInfoBean.getUserInfo().getAvatar()).error(R.mipmap.ic_head_default).into(imgHead);
                            if (userInfoBean.getUserInfo().isIsVip()) {
                                tvVipType.setText("VIP会员");
                                llIsAgent.setVisibility(View.VISIBLE);
                                tvYaoqingCode.setText("邀请码：" + userInfoBean.getUserInfo().getMobile());
                                if (userInfoBean.getUserInfo().isIsAgent()) {
                                    tvYaoqingCode.setText("邀请码：" + userInfoBean.getUserInfo().getMobile());
                                    tvVipType.setText("金牌代理");
                                }
                            } else {
                                tvVipType.setText("普通会员");
                                llIsAgent.setVisibility(View.INVISIBLE);
                            }
                            float f=0.0f;
                            if(isAgent){
                                tvToalBkMoney.setText(String.valueOf(userInfoBean.getAgentTotalBkMoney()));
                                double v = userInfoBean.getNewMoney() / userInfoBean.getAgentTotalBkMoney();
                                f= (float) v*100;
                            }else{
                                tvToalBkMoney.setText(String.valueOf(userInfoBean.getTotalBkMoney()));
                                double v = userInfoBean.getNewMoney() / userInfoBean.getTotalBkMoney();
                                f= (float) v*100;
                            }

                            progressBar.setTextString(String.valueOf(userInfoBean.getNewMoney()));
                            progressBar.setProgressWithAnimation(f).setProgressListener(new HorizontalProgressBar.ProgressListener() {
                                @Override
                                public void currentProgressListener(float currentProgress) {
                                }
                            });
                            progressBar.startProgressAnimation();
                            LogUtils.e(TAG, "requestSuccess:是否邀满3个人" + userInfoBean.getUserInfo().isTree());
//                            if (!userInfoBean.getUserInfo().isTree()) {//是否邀满3个人
//                                tvXinRen.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                            } else {
//                                tvXinRen.getPaint().setFlags(0); // 取消设置的的中划
//                            }

                            if (!isAgent) {//不是代理
                                relativeLayoutAgentSy.setVisibility(View.GONE);
                                tvYijiDaili.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                                tvGuanLi.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tvGouWu.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            } else {

                                relativeLayoutAgentSy.setVisibility(View.VISIBLE);
                                tvYijiDaili.getPaint().setFlags(0); // 取消设置的的中划线
//                                tvGuanLi.getPaint().setFlags(0); // 取消设置的的中划
                                tvGouWu.getPaint().setFlags(0);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (responseCode == 1000 || responseCode == 1001) {
                        //鉴权失败，可能是token已过期，请重新登陆
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.tokenTimeLimit(getActivity(), responseCode);
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    String name = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_NICK_NAME, "");
                    String img = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_AVATAR, "");
                    boolean vip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, "");
                    tvName.setText(name);
                    Glide.with(getActivity()).load(img).error(R.mipmap.ic_head_default).into(imgHead);
                    if (vip) {
                        tvVipType.setText("VIP会员");
                    } else {
                        tvVipType.setText("普通会员");
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView shareWeiXin = (ImageView) view.findViewById(R.id.img_share_weixin);
        ImageView shareCircle = (ImageView) view.findViewById(R.id.img_share_circle);
        ImageView shareQQ = (ImageView) view.findViewById(R.id.img_share_qq);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);

        shareWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                iwxApi.sendReq(shareWxUrl(shareDownUrl + "?sharekey=" + userId, shareTile, shareContent, 0, shareIcon));
            }
        });

        shareCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                LogUtils.e(TAG, "onClick: " + shareDownUrl + userId + shareTile + shareContent + shareIcon);
                iwxApi.sendReq(shareWxUrl(shareDownUrl + "?sharekey=" + userId,
                        shareTile, shareContent, 1, shareIcon));
            }
        });

        shareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                LogUtils.e(TAG, "onClick:url =====" + shareDownUrl + "?sharekey=" + userId);
                final Bundle params = new Bundle();
                params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
                params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareDownUrl + "?sharekey=" + userId);
                params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "叮遇");
                params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareIcon);// 网络图片地址　
                //params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
//                        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
                // 分享操作要在主线程中完成
//                mTencent.shareToQQ(mContext, params, mIUiListener);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, mIUiListener);
            }
        }
    }

    public SendMessageToWX.Req shareWxUrl(String url, String title, String miaoshu, int id, final String bitmap) {
        // 初始化一个WXWebpageObject对象，填写Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题描述
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = miaoshu;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = id;
        return req;
    }
}
