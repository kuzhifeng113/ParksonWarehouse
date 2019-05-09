package com.woyun.warehouse.mall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
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
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.baseparson.event.RefreshIndexEvent;
import com.woyun.warehouse.baseparson.event.UnReadMessEvent;
import com.woyun.warehouse.bean.MallHomeFourBean;
import com.woyun.warehouse.grabbuy.activity.GrabDetailActivity;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MallGoodGoodsActivity;
import com.woyun.warehouse.mall.activity.MessageActivity;
import com.woyun.warehouse.mall.adapter.MainHotAdapter;
import com.woyun.warehouse.mall.adapter.MainRushAdapter;
import com.woyun.warehouse.mall.adapter.MainSmallAdapter;
import com.woyun.warehouse.mall.sort.SortDetailActivity;
import com.woyun.warehouse.mall.sort.SortSearchActivity;
import com.woyun.warehouse.my.activity.VipEnterActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.GridSpacingItemDecoration;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.TimeTools;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.CornerTransform;
import com.woyun.warehouse.vote.fragment.HostFragmentTwo;
import com.woyun.warehouse.welfare.GoodsDetailWelfareActivity;
import com.woyun.warehouse.welfare.WelfareFragment;
import com.wuhenzhizao.titlebar.utils.ScreenUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
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
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import me.leolin.shortcutbadger.ShortcutBadger;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;

/**
 * 商城4.0
 */
public class MallFragmentFour extends BaseFragmentTwo implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "MallFragmentFour";
    private static final int THUMB_SIZE = 150;
    private static final int TIME_DESC = 10001;
    private static final int COMPLETE_WAEL = 10002;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.tv_tool_title)
    TextView tvTitle;
    @BindView(R.id.mall_banner)
    Banner mallBanner;
    @BindView(R.id.img_mall_xin)
    ImageView imgMallXin;
    @BindView(R.id.img_mall_lv)
    ImageView imgMallLv;
    @BindView(R.id.img_mall_bao)
    ImageView imgMallBao;
    @BindView(R.id.img_left)
    ImageView imgLeft;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.recyclerViewSmall)
    RecyclerView recyclerViewSmall;//小分类图标
    @BindView(R.id.recyclerViewGrab)
    RecyclerView recyclerViewGrab;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_more_qg)
    TextView tvMoreGrab;
    @BindView(R.id.recyclerViewHotBig)
    RecyclerView recycleViewHot;
    @BindView(R.id.tv_red_des)
    TextView tvRedDec;
    @BindView(R.id.tv_red_area)
    TextView tvRedArea;
    @BindView(R.id.img_red_goods)
    ImageView imgRedGoods;

    @BindView(R.id.img_share)
    ImageView imgShare;

    @BindView(R.id.imgTop)
    ImageView imgTop;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    //    @BindView(R.id.tablayout)
//    TabLayout hostTab;
//    @BindView(R.id.host_viewpager)
//    ViewPager hostViewpager;
    @BindView(R.id.img_tool_mess)
    ImageView imgDing;


    private List<MallHomeFourBean.AdvBannerListBean> advBannerListBeans = new ArrayList<>();
    private List<MallHomeFourBean.CategoryListBeanX> smallList = new ArrayList<>();
    private MainSmallAdapter smallAdapter;

    private MallHomeFourBean.RedGoodsBean redGoodsBean;

    private List<MallHomeFourBean.RushBuyBean.GoodsListBean> rushBuyBeanList = new ArrayList<>();
    private MainRushAdapter rushAdapter;

    private List<MallHomeFourBean.GoodsCategoryListBean> hotListData = new ArrayList<>();
    private MainHotAdapter hotAdapter;

    private long startTime, endTime, chaTime;

    List<String> imageBanners = new ArrayList<>();
    final List<ImageInfo> imageInfoList = new ArrayList<>();

    private TextView tv_tab;

    private String redPackMoney;//分享金额

    //分享
    private String shareTile, shareContent, shareDownUrl, shareIcon;
    private ShareQQListener mIUiListener;
    private WbShareHandler shareHandler;
    private IWXAPI iwxApi;
    private Tencent mTencent;
    private String loginUserId;
    private String nickName;
    private Bitmap mBitmapCover;

    public static MallFragmentFour newInstance() {
        MallFragmentFour fragment = new MallFragmentFour();
        return fragment;
    }

    private int error = 0;
    Badge badge;
    private CommonPopupWindow popupWindow;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
//                case REFRESH_COMPLETE:
//                    showSharePop();
//                    break;
                case TIME_DESC:
                    chaTime = chaTime - 1000;
                    if (chaTime > 0) {
                        mHandler.sendEmptyMessageDelayed(TIME_DESC, 1000);
                        if(tvEndTime!=null){
                            tvEndTime.setText(TimeTools.getCountTimeByLong(chaTime));
                        }
                    } else {
                        if(tvEndTime!=null){
                            tvEndTime.setText("已结束");
                        }
                    }
                    break;
                case COMPLETE_WAEL:
                    showSharePop();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_mall_four, container, false);
        unbinder = ButterKnife.bind(this, view);
        getShareData();
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(getActivity(), Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getActivity());
        //微博
        shareHandler = new WbShareHandler(getActivity());
        shareHandler.registerApp();

        EventBus.getDefault().register(this);
        badge = new QBadgeView(getActivity());
        initView();
        ImmersionBar.setTitleBar(getActivity(), toolBar);

        recyclerViewSmall.setNestedScrollingEnabled(false);
        recyclerViewSmall.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        smallAdapter = new MainSmallAdapter(getActivity(), smallList);
        recyclerViewSmall.setAdapter(smallAdapter);
        //
        smallAdapter.setOnItemClickListener(position -> {
            Intent search = new Intent();
            if (position != smallList.size() - 1) {
                search.setClass(getActivity(),SortDetailActivity.class);
                search.putExtra("category_id",hotListData.get(position).getCategoryId());
                search.putExtra("category_name",hotListData.get(position).getName());
            }else{
                search.setClass(getActivity(),SortSearchActivity.class);
            }

            startActivity(search);
        });

        recyclerViewGrab.setNestedScrollingEnabled(false);
        recyclerViewGrab.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerViewGrab.addItemDecoration(new GridSpacingItemDecoration(3, DensityUtils.dp2px(getActivity(), 5), false));
        rushAdapter = new MainRushAdapter(getActivity(), rushBuyBeanList);
        recyclerViewGrab.setAdapter(rushAdapter);

        rushAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), GrabDetailActivity.class);
            intent.putExtra("goods_id", rushBuyBeanList.get(position).getGoodsId());
            intent.putExtra("rush_id", rushBuyBeanList.get(position).getRushBuyId());
            startActivity(intent);
        });

        recycleViewHot.setNestedScrollingEnabled(false);
        recycleViewHot.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleViewHot.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 30)));//垂直间距
        hotAdapter = new MainHotAdapter(getActivity(), hotListData);
        recycleViewHot.setAdapter(hotAdapter);

        //imgClick
        hotAdapter.setOnButtonClickListener(position -> {
//            Intent intent = new Intent(getActivity(), SortSearchActivity.class);
//            intent.putExtra("position", position);
//            intent.putExtra("categoryId", hotListData.get(position).getCategoryId());
//            startActivity(intent);

            Intent intent=new Intent(getActivity(),SortDetailActivity.class);
            intent.putExtra("category_id",hotListData.get(position).getCategoryId());
            intent.putExtra("category_name",hotListData.get(position).getName());
            startActivity(intent);
        });

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        if (recyclerView.getItemDecorationCount() == 0) {
//            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 30)));//垂直间距
//        }
//        recyclerView.setAdapter(mallHomeAdapter);
        initData();

        DisplayMetrics displayMetrics = ScreenUtils.getDisplayMetrics(getActivity());
        int height = displayMetrics.heightPixels;

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                Log.e(TAG, "onScrollChange: 滑动后 Y " + scrollY);

                if (scrollY > 2.5 * height) {//大于2个屏幕的高度
                    imgTop.setVisibility(View.VISIBLE);
                } else {
                    imgTop.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void getShareData() {
        loginUserId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        shareTile = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_TITLE, "");
        shareContent = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_CONTENT, "");
        shareIcon = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_ICON, "");
        shareDownUrl = (String) SPUtils.getInstance(getActivity()).get(Constant.SHARE_URL, "");
        nickName = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_NICK_NAME, "");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UnReadMessEvent event) {
        LogUtils.e(TAG, "Event:==未读消息=== " + event.getNum());
        setUnreadNum(event.getNum());
        if (event.isZhanKai()) {//appBar 是否展开
            LogUtils.e(TAG, "appBar 是否展开" + event.isZhanKai());
            setAppBarToTop(appBar);
        }
    }


    private void initView() {
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
//
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//禁用工具栏title
//
//        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
//                Log.d("STATE", state.name());
//                if (state == State.EXPANDED) {
////                    //展开状态
////                    llContent.setVisibility(View.VISIBLE);
////                    tvTitle.setVisibility(View.GONE);
////                } else if (state == State.COLLAPSED) {
////                    llContent.setVisibility(View.GONE);
////                    tvTitle.setVisibility(View.VISIBLE);
////                    //折叠状态
////                } else {
////                    //中间状态
////                    llContent.setVisibility(View.VISIBLE);
////                    tvTitle.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    private void initData() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LogUtils.e(TAG, "run:==== ");
                        advBannerListBeans.clear();
                        smallList.clear();
                        rushBuyBeanList.clear();
                        hotListData.clear();
                        getData(false);
                        EventBus.getDefault().post(new RefreshIndexEvent(true));
                        mRefreshLayout.finishRefresh();
                    }
                }, 1500);

            }
        });
    }

//    private void initHotCate() {
//        fragments = new ArrayList<>();
//        fragments.add(new HostFragmentTwo());
//        hostTab.setupWithViewPager(hostViewpager);
//        Myadapter adapter = new Myadapter(getActivity().getSupportFragmentManager());
//        //联动
//        hostViewpager.setAdapter(adapter);
//        hostViewpager.setOffscreenPageLimit(listTiles.size());
////        hostViewpager.setOffscreenPageLimit(0);
//        LogUtils.e(TAG, "当前viewPage 选中的第几个"+hostViewpager.getCurrentItem());
////        adapter.notifyDataSetChanged();
//
////用来循环适配器中的视图总数
//        for (int i = 0; i < adapter.getCount(); i++) {
//            //获取每一个tab对象
//            TabLayout.Tab tabAt = hostTab.getTabAt(i);
//            //将每一个条目设置我们自定义的视图
//            tabAt.setCustomView(R.layout.tablayout_item);
//            //默认选中第一个
//            if (i == 0) {
//                // 设置第一个tab的TextView是被选择的样式
//                tabAt.getCustomView().findViewById(R.id.tv_tab).setSelected(true);//第一个tab被选中
//                //设置选中标签的文字大小
//                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextSize(18);
////                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextAppearance(getActivity(),R.style.TabLayoutTextStyle);
//                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextColor(Color.parseColor("#F5336F"));
//            }
//            //通过tab对象找到自定义视图的ID
//            TextView textView = (TextView) tabAt.getCustomView().findViewById(R.id.tv_tab);
//            textView.setText(listTiles.get(i).getName());//设置tab上的文字
//        }
//
//        hostTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //定义方法，判断是否选中
//                updateTabView(tab, true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                //定义方法，判断是否选中
//                updateTabView(tab, false);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//
//    }

    /**
     * 用来改变tabLayout选中后的字体大小及颜色
     *
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        tv_tab = (TextView) tab.getCustomView().findViewById(R.id.tv_tab);
        if (isSelect) {
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体变大
            tv_tab.setTextSize(18);
            tv_tab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_tab.setTextColor(Color.parseColor("#F5336F"));

        } else {
            //设置标签取消选中
            tv_tab.setSelected(false);
            //恢复为默认字体大小
            tv_tab.setTextSize(14);
            //设置不为加粗
            tv_tab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_tab.setTextColor(Color.parseColor("#AFAFAF"));

        }
    }


    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getData(true);

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);

    }


    /**
     * 获取商城首页 数据
     *
     * @param isShow 是否显示loading
     */
    private void getData(boolean isShow) {
        if (isShow) {
            ModelLoading.getInstance(getActivity()).showLoading("", true);
        }
        //获取数据
        try {
            JSONObject params = new JSONObject();
//            params.put("ver", APKVersionCodeUtils.getVersionCode(getActivity()));
//            params.put("platform", "AND");
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
            RequestInterface.goodsPrefix(getActivity(), params, TAG, ReqConstance.I_GOODS_HOME, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Gson gson = new Gson();
                        List<MallHomeFourBean> datas = gson.fromJson(jsonResult, new TypeToken<List<MallHomeFourBean>>() {
                        }.getType());
                        pasterData(datas, isShow);
                        LogUtils.e(TAG, "requestSuccess: " + datas.size());
                        int redPack = datas.get(0).getRedPack();
                        redPackMoney = datas.get(0).getRedPackMoney();


                        SPUtils.getInstance(getActivity()).put(Constant.USER_DEFAULT_ADDRESS, datas.get(0).isDefaultAddress());

                        boolean isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
                        if (isLogin) {
                            if (redPack == 0) {
                                showGetRedPack();
                            }
                        }
                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    LogUtils.e(TAG, "requestError: getData");
                    error++;
                    if (error < 3) {
                        getData(true);
                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(s);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void pasterData(List<MallHomeFourBean> data, boolean isShow) {
        for (int i = 0; i < data.size(); i++) {
            advBannerListBeans.addAll(data.get(i).getAdvBannerList());
            smallList.addAll(data.get(i).getCategoryList());
            rushBuyBeanList.addAll(data.get(i).getRushBuy().getGoodsList());
            hotListData.addAll(data.get(i).getGoodsCategoryList());
        }
        MallHomeFourBean.CategoryListBeanX end = new MallHomeFourBean.CategoryListBeanX();
        end.setName("全部分类");
        end.setImage("");

        smallList.add(end);
        initBannner(advBannerListBeans);
        smallAdapter.notifyDataSetChanged();
        rushAdapter.notifyDataSetChanged();
        hotAdapter.notifyDataSetChanged();
        //红包
        redGoodsBean = data.get(0).getRedGoods();
        tvRedDec.setText("￥" + redGoodsBean.getVipPrice());
        Glide.with(getActivity()).load(redGoodsBean.getImage()).asBitmap().fitCenter().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(imgRedGoods);

        //抢购
        startTime = data.get(0).getRushBuy().getStartTime();
        endTime = data.get(0).getRushBuy().getEndTime();
        long currentTimeMillis = System.currentTimeMillis();
        //当前时间大于开始时间 小于结束时间
        if (currentTimeMillis < startTime) {//未开始
//            imgGoodsBuy.setClickable(false);
            tvEndTime.setText("未开始");
        } else if (currentTimeMillis > endTime) {
            tvEndTime.setText("已结束");
        } else {
            chaTime = endTime - currentTimeMillis;
            mHandler.sendEmptyMessage(TIME_DESC);
        }


        ModelLoading.getInstance(getActivity()).closeLoading();
    }

    /**
     * 轮播图
     */
    private void initBannner(final List<MallHomeFourBean.AdvBannerListBean> listBanner) {
        imageBanners.clear();
        for (int i = 0; i < listBanner.size(); i++) {
            imageBanners.add(listBanner.get(i).getUrl());
        }

        mallBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        mallBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
//                Glide.with(context).load(path).asBitmap().into(imageView);
                //第一个是上下文，第二个是圆角的弧度
//                Glide.with(context).load(path).transform(new GlideRoundTransform(context, 13)).into(imageView);
                CornerTransform transformation = new CornerTransform(context, DensityUtils.dp2px(context, 8));
                //false 圆角
                transformation.setExceptCorner(false, false, false, false);
                Glide.with(context)
                        .load(path)
                        .asBitmap()
                        .skipMemoryCache(true)
                        .transform(transformation)
                        .placeholder(R.mipmap.img_default)
                        .error(R.mipmap.img_default)
                        .into(imageView);

//                //Glide 加载图片简单用法
//                Glide.with(context).load(path).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

            }
        });
        //设置图片集合
        mallBanner.setImages(imageBanners);
        //设置banner动画效果
        mallBanner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        mallBanner.isAutoPlay(true);
        //设置轮播时间
        mallBanner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        mallBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        mallBanner.start();

        mallBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                ToastUtils.getInstanc(getActivity()).showToast("click" + listBanner.get(position).getTagUrl());
                //type=3跳转的商品ID 1.图片，2.视频,3.跳转商品，4.跳转网页
                Intent webView = new Intent();
                int type = listBanner.get(position).getType();
                if (type == 3) {
//                    webView.setClass(getActivity(), GoodsDetailActivity.class);
                    webView.setClass(getActivity(), GoodsDetailNativeActivity.class);
                    webView.putExtra("goods_id", listBanner.get(position).getGoodsId());
                    webView.putExtra("from_id", 2);
                    startActivity(webView);
                } else if (type == 4) {
                    if (!TextUtils.isEmpty(listBanner.get(position).getTagUrl())) {
                        webView.setClass(getActivity(), MyWebViewActivity.class);
                        webView.putExtra("web_url", listBanner.get(position).getTagUrl());
                        startActivity(webView);
                    }
                } else if (type == 5) {//会员中心
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), VipEnterActivity.class);
                    startActivity(intent);
                } else if (type == 6) {//限时抢购商品
                    Intent intent = new Intent(getActivity(), GrabDetailActivity.class);
                    intent.putExtra("goods_id", listBanner.get(position).getGoodsId());
                    intent.putExtra("rush_id", listBanner.get(position).getRushId());
                    startActivity(intent);
                } else if (type == 7) {//红包福利商品
                    Intent intent = new Intent(getActivity(), GoodsDetailWelfareActivity.class);
                    intent.putExtra("goods_id", listBanner.get(position).getGoodsId());
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 设置消息红点
     *
     * @param num
     */
    private void setUnreadNum(int num) {
        badge.bindTarget(imgDing).setBadgeGravity(Gravity.END | Gravity.TOP);
        badge.setBadgePadding(3.5f, true);
        badge.setGravityOffset(9, 8, true);

//        badge2.setGravityOffset(9,8,true);
        if (num > 0) {
            badge.setBadgeText("");
            ShortcutBadger.applyCount(getActivity(), num); //for 1.1.4+
        } else {
            badge.hide(false);
            ShortcutBadger.removeCount(getActivity()); //for 1.1.4+
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_mall_xin, R.id.img_mall_lv, R.id.img_mall_bao, R.id.img_left, R.id.img_right, R.id.img_tool_mess, R.id.tv_more_qg, R.id.tv_red_area, R.id.img_red_goods, R.id.img_share,R.id.imgTop})
    public void onViewClicked(View view) {
        boolean isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
        switch (view.getId()) {
            case R.id.img_mall_xin:
                imageInfoList.clear();
                ImageInfo imageInfo = new ImageInfo();
                imageInfo.setOriginUrl(Constant.WEB_MALL_ONE);
                imageInfoList.add(imageInfo);
                ImagePreview
                        .getInstance()
                        .setContext(getActivity())
                        .setIndex(0)
                        .setImageInfoList(imageInfoList)
                        .setShowDownButton(true)
                        .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                        .setFolderName("BigImageViewDownload")
                        .setScaleLevel(1, 3, 8)
                        .setZoomTransitionDuration(300)
                        .setEnableClickClose(true)// 是否启用点击图片关闭。默认启用
                        .setEnableDragClose(true)// 是否启用上拉/下拉关闭。默认不启用
                        .setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角。默认显示
                        .setCloseIconResId(R.drawable.ic_action_close)// 设置关闭按钮图片资源
                        .setShowDownButton(false)// 是否显示下载按钮，在页面右下角。默认显示
                        .setDownIconResId(R.drawable.icon_download_new)// 设置下载按钮图片资源
                        .setShowIndicator(false)// 设置是否显示顶部的指示器（1/9）。默认显示
                        .start();
                break;
            case R.id.img_mall_lv:
                imageInfoList.clear();
                ImageInfo imageInfo2 = new ImageInfo();
                imageInfo2.setOriginUrl(Constant.WEB_MALL_TWO);
                imageInfo2.setThumbnailUrl(Constant.WEB_MALL_TWO);
                imageInfoList.add(imageInfo2);
                ImagePreview
                        .getInstance()
                        .setContext(getActivity())
                        .setIndex(0)
                        .setImageInfoList(imageInfoList)
                        .setShowDownButton(true)
                        .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                        .setFolderName("BigImageViewDownload")
                        .setScaleLevel(1, 3, 8)
                        .setZoomTransitionDuration(300)
                        .setEnableClickClose(true)// 是否启用点击图片关闭。默认启用
                        .setEnableDragClose(true)// 是否启用上拉/下拉关闭。默认不启用
                        .setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角。默认显示
                        .setCloseIconResId(R.drawable.ic_action_close)// 设置关闭按钮图片资源
                        .setShowDownButton(false)// 是否显示下载按钮，在页面右下角。默认显示
                        .setDownIconResId(R.drawable.icon_download_new)// 设置下载按钮图片资源
                        .setShowIndicator(false)// 设置是否显示顶部的指示器（1/9）。默认显示
                        .start();
                break;
            case R.id.img_mall_bao:
                imageInfoList.clear();
                ImageInfo imageInfo3 = new ImageInfo();
                imageInfo3.setOriginUrl(Constant.WEB_MALL_THREE);
                imageInfo3.setThumbnailUrl(Constant.WEB_MALL_THREE);
                imageInfoList.add(imageInfo3);
                ImagePreview
                        .getInstance()
                        .setContext(getActivity())
                        .setIndex(0)
                        .setImageInfoList(imageInfoList)
                        .setShowDownButton(true)
                        .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                        .setFolderName("BigImageViewDownload")
                        .setScaleLevel(1, 3, 8)
                        .setZoomTransitionDuration(300)
                        .setEnableClickClose(true)// 是否启用点击图片关闭。默认启用
                        .setEnableDragClose(true)// 是否启用上拉/下拉关闭。默认不启用
                        .setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角。默认显示
                        .setCloseIconResId(R.drawable.ic_action_close)// 设置关闭按钮图片资源
                        .setShowDownButton(false)// 是否显示下载按钮，在页面右下角。默认显示
                        .setDownIconResId(R.drawable.icon_download_new)// 设置下载按钮图片资源
                        .setShowIndicator(false)// 设置是否显示顶部的指示器（1/9）。默认显示
                        .start();
                break;
            case R.id.img_left://
                Intent intentLeft = new Intent(getActivity(), MallGoodGoodsActivity.class);
                intentLeft.putExtra("goodsmall_type", 12);
                startActivity(intentLeft);
                break;
            case R.id.img_right:
                Intent intentRight = new Intent(getActivity(), MallGoodGoodsActivity.class);
                intentRight.putExtra("goodsmall_type", 13);
                startActivity(intentRight);
//                Intent intent = new Intent(getActivity(), MainActivity.class);
//                intent.putExtra("go_makemoney", true);
//                startActivity(intent);
                break;
            case R.id.img_tool_mess:
                if (!isLogin) {
                    Intent goLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(goLogin);
                    return;
                }
                Intent mess = new Intent(getActivity(), MessageActivity.class);
                startActivity(mess);
                break;
//            case R.id.rl_search://搜索
//                Intent search = new Intent(getActivity(), SortSearchActivity.class);
//                startActivity(search);
//                break;
            case R.id.tv_more_qg://更多抢购
                Intent grab = new Intent(getActivity(), MainActivity.class);
                grab.putExtra("go_grab", true);
                startActivity(grab);
                break;
            case R.id.tv_red_area://红包购物专区
                Intent redArea = new Intent(getActivity(), MainActivity.class);
                redArea.putExtra("go_redarea", true);
                startActivity(redArea);
                break;
            case R.id.img_red_goods:
                Intent intent = new Intent(getActivity(), GoodsDetailWelfareActivity.class);
                intent.putExtra("goods_id", redGoodsBean.getGoodsId());
                startActivity(intent);
                break;
            case R.id.img_share://分享
                if (!isLogin) {
                    Intent goLo = new Intent(getActivity(), LoginActivity.class);
                    startActivity(goLo);
                    return;
                }
                returnBitMap(shareIcon);
//                showSharePop();
                break;
            case R.id.imgTop://置顶
                nestedScrollView.fling(0);
                nestedScrollView.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        mHandler.removeCallbacksAndMessages(null);

    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_main_redpack://红包弹窗
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
                //领取红包
                img_main_get.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        getRedPack((String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
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

                        iwxApi.sendReq(shareWxUrl(shareDownUrl + "?share=" + loginUserId, shareTile,shareContent, 0, shareIcon));
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
                        sendMessage(true,false);

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
                        LogUtils.e(TAG, "onClick:### "+shareIcon );
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


//    //适配器
//    class Myadapter extends FragmentPagerAdapter {
//
//        public Myadapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return getfragment(position);
//        }
//
//        @Override
//        public int getCount() {
//            return listTiles.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return listTiles.get(position).getName();
//        }
//        private int mChildCount = 0;
//        @Override
//        public void notifyDataSetChanged() {
//            mChildCount = getCount();
//            super.notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemPosition(@NonNull Object object) {
//            if ( mChildCount > 0) {
//                // 这里利用判断执行若干次不缓存，刷新
//                mChildCount --;
//                // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
//                return POSITION_NONE;
//            }
//            return super.getItemPosition(object);
//        }
//    }

//    //动态创建Fragment的方法
//    public Fragment getfragment(int position) {
//        arrays = new Fragment[listTiles.size()];
//        Fragment fg = arrays[position];
//        if (fg == null) {
//            fg = HostFragmentTwo.getInitUrl(listTiles.get(position).getCategoryId());
//            arrays[position] = fg;
//        }
//        return fg;
//    }


    /**
     * 领取红包弹窗  redPack  0 未领取  已领取
     */
    public void showGetRedPack() {
//        SPUtils.getInstance(getActivity()).put("isFirstVotes", true);
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_main_redpack, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_main_redpack)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
//                .setOutsideTouchable(false)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
//        SPUtils.getInstance(getActivity()).put("isFirstVote", true);
    }

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


    /**
     * 领红包
     *
     * @param userId
     * @param
     */
    private void getRedPack(String userId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);

            RequestInterface.redpackPrefix(getActivity(), params, TAG, ReqConstance.I_REDPACK_PACK, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        ToastUtils.getInstanc(getActivity()).showToast("领取红包成功~");
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                    mHandler.sendEmptyMessage(COMPLETE_WAEL);
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
     * 返回顶部
     *
     * @param appBar
     */
    private void setAppBarToTop(AppBarLayout appBar) {
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) appBar.getLayoutParams()).getBehavior();
        if (behavior instanceof AppBarLayout.Behavior) {
            AppBarLayout.Behavior appBarLayoutBehavior = (AppBarLayout.Behavior) behavior;
            int topAndBottomOffset = appBarLayoutBehavior.getTopAndBottomOffset();
            if (topAndBottomOffset != 0) {
                appBarLayoutBehavior.setTopAndBottomOffset(0);
            }
        }
    }
}
