package com.woyun.warehouse.vip;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.bean.MoneyBean;
import com.woyun.warehouse.bean.ZuanQianBean;
import com.woyun.warehouse.my.activity.AgentOpenTwoActivity;
import com.woyun.warehouse.my.activity.ShareActivity;
import com.woyun.warehouse.my.activity.VipCenterTwoActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.GridSpacingItemDecoration;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.JudgeNestedScrollView;
import com.woyun.warehouse.vote.adapter.MoneyHorizontalAdapter;
import com.woyun.warehouse.vote.adapter.VipAgentAdapter;
import com.woyun.warehouse.vote.adapter.VipHomeAdapter;
import com.woyun.warehouse.vote.adapter.VipSliverAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 赚钱 会员2.0
 */
public class VipFragment extends BaseFragment implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "VipFragment";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    @BindView(R.id.recycler_view_vip_qy)
    RecyclerView recyclerViewVip;

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

    @BindView(R.id.img_ding)
    TextView imgDing;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_vip_type)
    TextView tvVipType;
    @BindView(R.id.tv_quanyi_num)
    TextView tvQuanyiNum;
    @BindView(R.id.tv_vip_status)
    TextView tvVipStatus;
    @BindView(R.id.rl_open_vip)
    RelativeLayout rlOpenVip;
    @BindView(R.id.rl_open_agent)
    RelativeLayout rlOpenAgent;
    @BindView(R.id.tv_vip_price)
    TextView tvVipPrice;

    @BindView(R.id.tv_agent_quan_num)
    TextView tvAgentQuanNum;

    @BindView(R.id.recycler_view_agent_one)
    RecyclerView recyclerViewAgentOne;
    @BindView(R.id.recycler_view_agent_two)
    RecyclerView recyclerViewAgentTwo;
    @BindView(R.id.recycler_view_small)
    RecyclerView recyclerViewSmall;
    @BindView(R.id.tv_agent_status)
    TextView tvAgentStatus;
    @BindView(R.id.tv_agent_price)
    TextView tvAgentPrice;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.ic_share)
    ImageView icShare;
    @BindView(R.id.tv_agent_status_one)
    TextView tvAgentStatusOne;
    @BindView(R.id.tv_agent_price_one)
    TextView tvAgentPriceOne;
    @BindView(R.id.rl_open_agent_one)
    RelativeLayout rlOpenAgentOne;
    @BindView(R.id.recycler_view_silver_one)
    RecyclerView recyclerViewSilverOne;
    @BindView(R.id.recycler_view_silver_two)
    RecyclerView recyclerViewSilverTwo;

    private List<MoneyBean> moneyBeanList = new ArrayList<>();
    List<String> imageBanners = new ArrayList<>();
    List<ImageView> mListView = new ArrayList<>();
    private int mOffset = 0;
    private int mScrollY = 0;
    int toolBarPositionY = 0;
    private MoneyHorizontalAdapter horizontalAdapter;
    LinearLayoutManager mLinearLayoutManager;
    private VipHomeAdapter vipHomeAdapter;
    private VipAgentAdapter vipAgentAdapterOne, vipAgentAdapterTwo;
    private VipSliverAdapter vipSliverAdapterOne,vipSliverAdapterTwo;
    private CommonPopupWindow popupWindow;
    private boolean isVip;
    private boolean isLogin;
    //会员权益
    private List<ZuanQianBean.VipListBean> vipListBeanList = new ArrayList<>();

    //银牌代理 3680
    private List<ZuanQianBean.SilverListBean> sliverListBeanList=new ArrayList<>();
    private List<ZuanQianBean.SilverListBean> sliverListBeanListOne=new ArrayList<>();
    private List<ZuanQianBean.SilverListBean> sliverListBeanListTwo=new ArrayList<>();


    //代理权益 6552
    private List<ZuanQianBean.AgentListBean> agentListBeanList = new ArrayList<>();
    private List<ZuanQianBean.AgentListBean> agentListBeanListOne = new ArrayList<>();
    private List<ZuanQianBean.AgentListBean> agentListBeanListTwo = new ArrayList<>();


    public static VipFragment newInstance() {
        VipFragment fragment = new VipFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_vip, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        Log.e(TAG, "onCreateView:@@@@@@@ ");
        toolBar.setVisibility(View.GONE);
        ImmersionBar.setTitleBar(getActivity(), toolBar);
        vipHomeAdapter = new VipHomeAdapter(getActivity(), vipListBeanList);
        recyclerViewVip.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        if (recyclerViewVip.getItemDecorationCount() == 0) {
            recyclerViewVip.addItemDecoration(new GridSpacingItemDecoration(4, 5, false));
        }
        recyclerViewVip.setAdapter(vipHomeAdapter);

        //3680
        vipSliverAdapterOne = new VipSliverAdapter(getActivity(), sliverListBeanListOne);
        recyclerViewSilverOne.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (recyclerViewSilverOne.getItemDecorationCount() == 0) {
            recyclerViewSilverOne.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        }
        recyclerViewSilverOne.setAdapter(vipSliverAdapterOne);

        vipSliverAdapterTwo = new VipSliverAdapter(getActivity(), sliverListBeanListTwo);
        recyclerViewSilverTwo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        if (recyclerViewSilverTwo.getItemDecorationCount() == 0) {
            recyclerViewSilverTwo.addItemDecoration(new GridSpacingItemDecoration(3, 5, false));
        }
        recyclerViewSilverTwo.setAdapter(vipSliverAdapterTwo);

        //6552
        vipAgentAdapterOne = new VipAgentAdapter(getActivity(), agentListBeanListOne);
        recyclerViewAgentOne.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        if (recyclerViewAgentOne.getItemDecorationCount() == 0) {
            recyclerViewAgentOne.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        }
        recyclerViewAgentOne.setAdapter(vipAgentAdapterOne);

        vipAgentAdapterTwo = new VipAgentAdapter(getActivity(), agentListBeanListTwo);
        recyclerViewAgentTwo.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        if (recyclerViewAgentTwo.getItemDecorationCount() == 0) {
            recyclerViewAgentTwo.addItemDecoration(new GridSpacingItemDecoration(3, 5, false));
        }
        recyclerViewAgentTwo.setAdapter(vipAgentAdapterTwo);

        //最下面一排
        horizontalAdapter = new MoneyHorizontalAdapter(getActivity(), moneyBeanList);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 水平 布
        recyclerViewSmall.setLayoutManager(mLinearLayoutManager);
        recyclerViewSmall.setAdapter(horizontalAdapter);


//        //会员权益 点击事件
//        vipHomeAdapter.setOnItemClickListener(new VipHomeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                ArrayList<String> imagesList=new ArrayList<>();
//                for(int i=0;i<vipListBeanList.size();i++){
//                    imagesList.add(vipListBeanList.get(i).getImage());
//                }
//                Intent intent=new Intent(getActivity(), QuanYiTwoActivity.class);
//                intent.putExtra("z_title","会员权益");
//                intent.putStringArrayListExtra("imagesarray",imagesList);
//                intent.putExtra("index",position);
//                startActivity(intent);
//            }
//        });
//        //代理权益 one
//        vipAgentAdapterOne.setOnItemClickListener(new VipAgentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                ArrayList<String> imagesList=new ArrayList<>();
//                for(int i=0;i<agentListBeanList.size();i++){
//                    imagesList.add(agentListBeanList.get(i).getImage());
//                }
//                Intent intent=new Intent(getActivity(), QuanYiTwoActivity.class);
//                intent.putExtra("z_title","代理权益");
//                intent.putStringArrayListExtra("imagesarray",imagesList);
//                intent.putExtra("index",position);
//                startActivity(intent);
//            }
//        });
//
//        //代理权益 two
//        vipAgentAdapterTwo.setOnItemClickListener(new VipAgentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                ArrayList<String> imagesList=new ArrayList<>();
//                for(int i=0;i<agentListBeanList.size();i++){
//                    imagesList.add(agentListBeanList.get(i).getImage());
//                }
//                Intent intent=new Intent(getActivity(), QuanYiTwoActivity.class);
//                intent.putExtra("z_title","代理权益");
//                intent.putStringArrayListExtra("imagesarray",imagesList);
//                intent.putExtra("index",position+2);
//                startActivity(intent);
//            }
//        });
        startAnim(rlOpenVip);
        startAnim(rlOpenAgentOne);
        startAnim(rlOpenAgent);
        horizontalAdapter.setOnItemClickListener(new MoneyHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                for (int i = 0; i < moneyBeanList.size(); i++) {
                    if (position == i) {
                        moneyBeanList.get(i).setCheck(true);
                    } else {
                        moneyBeanList.get(i).setCheck(false);
                    }
                }
                viewPager.setCurrentItem(position);
                scrollToMiddleW(v, position);
                horizontalAdapter.notifyDataSetChanged();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < moneyBeanList.size(); i++) {
                    if (position == i) {
                        moneyBeanList.get(i).setCheck(true);
                    } else {
                        moneyBeanList.get(i).setCheck(false);
                    }
                }
                horizontalAdapter.notifyDataSetChanged();
                scrollToMiddleW(recyclerViewSmall.getChildAt(0), position);
//                recyclerViewSmall.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    //支付成功后回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(SaveUserEvent event) {
        Log.e(TAG, "Event: 支付成功后回调" + event.isSave());
        if (event.isSave()) {
            getData();
        }
    }

    /**
     * 轮播图
     */
    private void initBannner(final List<ZuanQianBean.VipListBean> vipBanner,final List<ZuanQianBean.SilverListBean> agentSilverBanner, final List<ZuanQianBean.AgentListBean> agentBanner) {
        imageBanners.clear();
        moneyBeanList.clear();
        for (int i = 0; i < vipBanner.size(); i++) {
            imageBanners.add(vipBanner.get(i).getImage());
            MoneyBean moneyBean = new MoneyBean();
            moneyBean.setIcon(vipBanner.get(i).getIcon());
            moneyBean.setImage(vipBanner.get(i).getImage());
            moneyBean.setUnicon(vipBanner.get(i).getUnicon());
            moneyBean.setName(vipBanner.get(i).getName());
            if (i == 0) {
                moneyBean.setCheck(true);
            }
            moneyBeanList.add(moneyBean);
        }

        for (int j = 0; j < agentSilverBanner.size(); j++) {
            imageBanners.add(agentSilverBanner.get(j).getImage());
            MoneyBean moneBeanSilver = new MoneyBean();
            moneBeanSilver.setIcon(agentSilverBanner.get(j).getIcon());
            moneBeanSilver.setImage(agentSilverBanner.get(j).getImage());
            moneBeanSilver.setUnicon(agentSilverBanner.get(j).getUnicon());
            moneBeanSilver.setName(agentSilverBanner.get(j).getName());
            moneyBeanList.add(moneBeanSilver);
        }

        for (int j = 0; j < agentBanner.size(); j++) {
            imageBanners.add(agentBanner.get(j).getImage());
            MoneyBean moneBeanAgent = new MoneyBean();
            moneBeanAgent.setIcon(agentBanner.get(j).getIcon());
            moneBeanAgent.setImage(agentBanner.get(j).getImage());
            moneBeanAgent.setUnicon(agentBanner.get(j).getUnicon());
            moneBeanAgent.setName(agentBanner.get(j).getName());
            moneyBeanList.add(moneBeanAgent);
        }
        viewPager.setAdapter(new BannerAdapter(getActivity(), viewPager, imageBanners));
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(imageBanners.size());
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());//3D画廊模式

        //左右都有图
        viewPager.setCurrentItem(0);
//        recyclerViewMall.
        horizontalAdapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            Log.e(TAG, "setUserVisibleHint:###########");
            isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
            isVip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, false);
            boolean isAgent = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_AGENT, false);
            if (isLogin) {
                tvName.setText((String) SPUtils.getInstance(getActivity()).get(Constant.USER_NICK_NAME, ""));
                String headUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_AVATAR, "");
                Glide.with(getActivity()).load(headUrl).error(R.mipmap.ic_head_default).into(imgHead);
                if (isVip) {
                    tvVipType.setText("VIP会员");
                }
                if (isAgent) {
                    tvVipType.setText("金牌代理");
                }

            } else {
                tvName.setText("立即登录");
                tvVipType.setVisibility(View.INVISIBLE);
                tvName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent goIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(goIntent);
                        getActivity().finish();
                    }
                });
            }

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
                    toolBar.setBackgroundColor(Color.parseColor("#F6CB66"));
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
//        boolean isFirstVote = (boolean) SPUtils.getInstance(getActivity()).get("isFirstVotes", false);
//        if (!isFirstVote) {
//            showVoteRules();
//        }
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
                        agentListBeanList.clear();
                        sliverListBeanList.clear();
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //获取数据
    private void getData() {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
            RequestInterface.userPrefix(getActivity(), params, TAG, ReqConstance.I_USER_MAKE_MOENY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0 && jsonArray.length() > 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            ZuanQianBean zuanQianBean = gson.fromJson(object.toString(), ZuanQianBean.class);
                            SPUtils.getInstance(getActivity()).put(Constant.USER_IS_AGENT, zuanQianBean.isIsAgent());
                            SPUtils.getInstance(getActivity()).put(Constant.USER_IS_VIP, zuanQianBean.isIsVip());
                            pasterData(zuanQianBean);
                            tvQuanyiNum.setText(String.valueOf(zuanQianBean.getVipList().size()));
                            tvAgentQuanNum.setText(String.valueOf(zuanQianBean.getAgentList().size()));
                            //是会员
                            if (zuanQianBean.isIsVip()) {
                                tvVipStatus.setText("已推荐");
                                tvVipType.setText("VIP会员");
                                tvVipPrice.setText(zuanQianBean.getNum() + "人");
                                rlOpenVip.setBackgroundResource(R.mipmap.bt_opened_vip);
                                //是代理
                                if (zuanQianBean.isIsAgent()) {
                                    tvVipType.setText("金牌代理");

                                    rlOpenAgentOne.setBackgroundResource(R.mipmap.bt_opened_agent);
                                    rlOpenAgent.setBackgroundResource(R.mipmap.bt_opened_agent);
                                    if (zuanQianBean.getVipNum() == 0) {//39名额全部推荐完 或者10个名额
                                        tvAgentStatus.setText("已返还" + zuanQianBean.getBackMoney());
                                        tvAgentPrice.setVisibility(View.GONE);

                                        tvAgentStatusOne.setText("已返还" + zuanQianBean.getBackMoney());
                                        tvAgentStatusOne.setTextColor(Color.parseColor("#ffffff"));
                                        tvAgentPriceOne.setVisibility(View.GONE);

                                    } else {
                                        tvAgentStatus.setText("剩余可推荐");
                                        tvAgentPrice.setVisibility(View.VISIBLE);
                                        tvAgentPrice.setText(zuanQianBean.getVipNum() + "人");

                                        tvAgentStatusOne.setText("剩余可推荐");
                                        tvAgentPriceOne.setVisibility(View.VISIBLE);
                                        tvAgentPriceOne.setText(zuanQianBean.getVipNum() + "人");
                                    }
                                } else {
                                    tvAgentStatusOne.setText("点击开通金牌代理");
                                    tvAgentPriceOne.setText(zuanQianBean.getSilverPrice()+"元");

                                    tvAgentStatus.setText("点击开通金牌代理");
                                    tvAgentPrice.setText(zuanQianBean.getAgentPrice() + "元");

                                }
                            } else {
                                tvVipType.setText("普通用户");
                                tvVipStatus.setText("点击购买会员");
                                tvVipPrice.setText(zuanQianBean.getVipPrice() + "元");

                                tvAgentStatus.setText("点击开通金牌代理");
                                tvAgentPrice.setText(zuanQianBean.getAgentPrice() + "元");

                                tvAgentStatusOne.setText("点击开通金牌代理");
                                tvAgentPriceOne.setText(zuanQianBean.getSilverPrice() + "元");

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
     * @param zuanQianBean
     */
    private void pasterData(ZuanQianBean zuanQianBean) {
        sliverListBeanListOne.clear();
        sliverListBeanListTwo.clear();

        agentListBeanListOne.clear();
        agentListBeanListTwo.clear();

        vipListBeanList.addAll(zuanQianBean.getVipList());
        sliverListBeanList.addAll(zuanQianBean.getSliverList());
        agentListBeanList.addAll(zuanQianBean.getAgentList());

        for (int i = 0; i < sliverListBeanList.size(); i++) {
            if (i < 2) {
                sliverListBeanListOne.add(sliverListBeanList.get(i));
            } else {
                sliverListBeanListTwo.add(sliverListBeanList.get(i));
            }
        }

        for (int i = 0; i < agentListBeanList.size(); i++) {
            if (i < 2) {
                agentListBeanListOne.add(agentListBeanList.get(i));
            } else {
                agentListBeanListTwo.add(agentListBeanList.get(i));
            }
        }
        initBannner(vipListBeanList,sliverListBeanList, agentListBeanList);
//        initBannner2(vipListBeanList,agentListBeanList);
        vipHomeAdapter.notifyDataSetChanged();
        vipAgentAdapterOne.notifyDataSetChanged();
        vipAgentAdapterTwo.notifyDataSetChanged();
        vipSliverAdapterOne.notifyDataSetChanged();
        vipSliverAdapterTwo.notifyDataSetChanged();
    }


    @OnClick({R.id.img_ding, R.id.text_memu, R.id.rl_open_vip,R.id.rl_open_agent_one, R.id.rl_open_agent, R.id.ic_share})
    public void onViewClicked(View view) {
        boolean isVip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, false);
        boolean isAgent = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_AGENT, false);
        switch (view.getId()) {
            case R.id.img_ding://
//                showVoteRules();
                Intent goweb = new Intent(getActivity(), MyWebViewActivity.class);
                goweb.putExtra("web_url", Constant.WEB_MAKE_MONEY);
                startActivity(goweb);
                break;
            case R.id.text_memu:
//                showVoteRules();
                Intent gowebGz = new Intent(getActivity(), MyWebViewActivity.class);
                gowebGz.putExtra("web_url", Constant.WEB_MAKE_MONEY);
                startActivity(gowebGz);
                break;
            case R.id.rl_open_vip://开通会员

                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                if (!isVip) {
                    Intent intent = new Intent(getActivity(), VipCenterTwoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_open_agent_one://开通3680 的代理
                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                if (!isVip) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先开通会员~");
                    return;
                }
                //vipTypeId=2 购买金牌代理,vipTypeId=3 购买银牌代理
                if (!isAgent) {
                    Intent intent = new Intent(getActivity(), AgentOpenTwoActivity.class);
                    intent.putExtra("vipTypeId",3);
                    startActivity(intent);
                }
                break;
            case R.id.rl_open_agent://不是会员不能开通 6552
                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                if (!isVip) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先开通会员~");
                    return;
                }
                if (!isAgent) {
                    Intent intent = new Intent(getActivity(), AgentOpenTwoActivity.class);
                    intent.putExtra("vipTypeId",2);
                    startActivity(intent);
                }
                break;
            case R.id.ic_share://分享
                if (!isLogin) {
                    ToastUtils.getInstanc(getActivity()).showToast("请先登录~");
                    return;
                }
                Intent share = new Intent(getActivity(), ShareActivity.class);
                startActivity(share);
                break;

        }
    }


    /**
     * 弹窗投票规则
     *
     * @param
     */
    public void showVoteRules() {
        SPUtils.getInstance(getActivity()).put("isFirstVotes", true);
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_vote_rules, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_vote_rules)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        SPUtils.getInstance(getActivity()).put("isFirstVote", true);
    }

    /**
     * Vip 会员才能参与投票
     *
     * @param
     */
    public void showNoVip() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_no_vip_vote, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_no_vip_vote)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_vote_rules:
                //获得PopupWindow布局里的View
                final ImageView img_close = view.findViewById(R.id.img_close);
                final TextView iKnow = view.findViewById(R.id.tv_i_know);
                final ProgressBar progressBar = view.findViewById(R.id.progress_bar);
                WebView webView = view.findViewById(R.id.webView_rule);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.loadUrl(Constant.WEB_VOTE_RULE);
                webView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                        if (progress == 100) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                iKnow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                break;

            case R.layout.popup_no_vip_vote:
                ImageView img_closevip = view.findViewById(R.id.img_close);
                Button btn_join_vip = view.findViewById(R.id.btn_join_vip);

                btn_join_vip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.getInstanc(getActivity()).showToast("成为会员");
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                img_closevip.setOnClickListener(new View.OnClickListener() {
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


    class MyImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mListView.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        //产生一个新的视图

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mListView.get(position));
            return mListView.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mListView.get(position));
        }
    }

    /**
     * Item 横向布局点击居中
     * Item中的点击事件调用此方法则，不在中间位置的View自动滚动到屏幕中间的位置
     */

    private void scrollToMiddleW(View view, int position) {
        int vWidth = view.getWidth();
        Rect rect = new Rect();

        recyclerViewSmall.getGlobalVisibleRect(rect);

        int reWidth = rect.right - rect.left - vWidth; //除掉点击View的宽度，剩下整个屏幕的宽度


        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

        int left = recyclerViewSmall.getChildAt(position - firstPosition).getLeft();//从左边到点击的Item的位置距离

        int half = reWidth / 2;//半个屏幕的宽度

        int moveDis = left - half;//向中间移动的距离
        recyclerViewSmall.smoothScrollBy(moveDis, 0);
    }

    /**
     * Item 竖直布局点击居中
     */
    private void scrollToMiddleH(View view, int position) {
        int vHeight = view.getHeight();
        Rect rect = new Rect();
        recyclerViewSmall.getGlobalVisibleRect(rect);
        int reHeight = rect.top - rect.bottom - vHeight;
        final int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
        int top = recyclerViewSmall.getChildAt(position - firstPosition).getTop();
        int half = reHeight / 2;
        recyclerViewSmall.smoothScrollBy(0, top - half);
    }

    private void startAnim(RelativeLayout view) {
        Animation mAnimation = null;
        mAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.btn_tween);
        view.setAnimation(mAnimation);
        mAnimation.start();
    }
}
