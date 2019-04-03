package com.woyun.warehouse.mall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gyf.barlibrary.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.baseparson.event.RefreshIndexEvent;
import com.woyun.warehouse.baseparson.event.UnReadMessEvent;
import com.woyun.warehouse.bean.MallHomeTwoBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MallGoodGoodsActivity;
import com.woyun.warehouse.mall.activity.MessageActivity;
import com.woyun.warehouse.mall.sort.SortSearchActivity;
import com.woyun.warehouse.utils.APKVersionCodeUtils;
import com.woyun.warehouse.utils.AddressPickTask;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.CornerTransform;
import com.woyun.warehouse.vote.fragment.HostFragmentTwo;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.udesk.model.Tag;
import me.leolin.shortcutbadger.ShortcutBadger;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 商城2.0
 */
public class MallFragmentTwo extends BaseFragment implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "MallFragmentTwo";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
//    @BindView(R.id.tv_username)
//    TextView tvUsername;
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
    RoundedImageView imgLeft;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;

    @BindView(R.id.tablayout)
    TabLayout hostTab;
    @BindView(R.id.host_viewpager)
    ViewPager hostViewpager;
    @BindView(R.id.img_tool_mess)
    ImageView imgDing;


    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;

    private List<Fragment> fragments;
    private List<MallHomeTwoBean.CategoryListBean> listTiles = new ArrayList<>();
    private Fragment[] arrays;

    private List<MallHomeTwoBean.AdvBannerListBean> advBannerListBeans = new ArrayList<>();
    //    private MallHomeAdapter mallHomeAdapter;
    List<String> imageBanners = new ArrayList<>();
    final List<ImageInfo> imageInfoList = new ArrayList<>();

    private TextView tv_tab;

    public static MallFragmentTwo newInstance() {
        MallFragmentTwo fragment = new MallFragmentTwo();
        return fragment;
    }

    private int error = 0;
    Badge badge;
    private CommonPopupWindow popupWindow;
    private String provinceValue,cityValue,countyValue;
    private ShipAddressBean addressBean;
    private TextView tvAddress,editName,editPhone,editDetailAddress;
    private boolean show_address_pop;//是否弹过填写地址窗
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_mall_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        badge = new QBadgeView(getActivity());
        initView();
        ImmersionBar.setTitleBar(getActivity(), toolBar);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        if (recyclerView.getItemDecorationCount() == 0) {
//            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 30)));//垂直间距
//        }
//        recyclerView.setAdapter(mallHomeAdapter);
        initData();


        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UnReadMessEvent event) {
        LogUtils.e(TAG, "Event:==未读消息=== " + event.getNum());
        setUnreadNum(event.getNum());
        if(event.isZhanKai()){//appBar 是否展开
            LogUtils.e(TAG,"appBar 是否展开"+event.isZhanKai());
            setAppBarToTop(appBar);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
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
                        LogUtils.e(TAG, "run:==== " );
                        listTiles.clear();
                        advBannerListBeans.clear();
                        getData(false);
                        EventBus.getDefault().post(new RefreshIndexEvent(true));
//                        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
//                        List<Fragment> fragments = supportFragmentManager.getFragments();
//                        for(Fragment fragment:fragments){
//                            if(fragment.equals(HostFragmentTwo.class)&&fr){
//
//                            }
//                        }
                        mRefreshLayout.finishRefresh();
                    }
                }, 1500);

            }
        });
    }

    private void initHotCate() {
        fragments = new ArrayList<>();
        fragments.add(new HostFragmentTwo());
//        titles.add("标题1");
//        titles.add("标题2");
//        titles.add("标题3");
        hostTab.setupWithViewPager(hostViewpager);
        Myadapter adapter = new Myadapter(getActivity().getSupportFragmentManager());
        //联动
        hostViewpager.setAdapter(adapter);
        hostViewpager.setOffscreenPageLimit(listTiles.size());
//        hostViewpager.setOffscreenPageLimit(0);
        LogUtils.e(TAG, "当前viewPage 选中的第几个"+hostViewpager.getCurrentItem());
//        adapter.notifyDataSetChanged();

//用来循环适配器中的视图总数
        for (int i = 0; i < adapter.getCount(); i++) {
            //获取每一个tab对象
            TabLayout.Tab tabAt = hostTab.getTabAt(i);
            //将每一个条目设置我们自定义的视图
            tabAt.setCustomView(R.layout.tablayout_item);
            //默认选中第一个
            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                tabAt.getCustomView().findViewById(R.id.tv_tab).setSelected(true);//第一个tab被选中
                //设置选中标签的文字大小
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextSize(18);
//                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextAppearance(getActivity(),R.style.TabLayoutTextStyle);
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                ((TextView) tabAt.getCustomView().findViewById(R.id.tv_tab)).setTextColor(Color.parseColor("#F5336F"));
            }
            //通过tab对象找到自定义视图的ID
            TextView textView = (TextView) tabAt.getCustomView().findViewById(R.id.tv_tab);
            textView.setText(listTiles.get(i).getName());//设置tab上的文字
        }

        hostTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //定义方法，判断是否选中
                updateTabView(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }

    /**
     *  用来改变tabLayout选中后的字体大小及颜色
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        tv_tab = (TextView) tab.getCustomView().findViewById(R.id.tv_tab);
        if(isSelect) {
            //设置标签选中
            tv_tab.setSelected(true);
            //选中后字体变大
            tv_tab.setTextSize(18);
            tv_tab .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_tab.setTextColor(Color.parseColor("#F5336F"));

        }else{
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
     * 获取数据
     * 版本号
     * 平台AND 或者 IOS
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
            params.put("ver", APKVersionCodeUtils.getVersionCode(getActivity()));
            params.put("platform", "AND");
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID, ""));
            RequestInterface.goodsPrefix(getActivity(), params, TAG, ReqConstance.I_GOODS_HOME, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {

                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Gson gson = new Gson();
                        List<MallHomeTwoBean> datas = gson.fromJson(jsonResult, new TypeToken<List<MallHomeTwoBean>>() {
                        }.getType());
                        pasterData(datas,isShow);
                        LogUtils.e(TAG, "requestSuccess: " + datas.size());
                        int unreadNum=datas.get(0).getUnreadNum();
//                        setUnreadNum(unreadNum);
                        SPUtils.getInstance(getActivity()).put(Constant.USER_DEFAULT_ADDRESS,datas.get(0).isDefaultAddress());
                        boolean isDefaultAddress= (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_DEFAULT_ADDRESS,false);
                        boolean isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
                        if(isLogin){
                            if(!isDefaultAddress&&!show_address_pop){
                                showSetAddress();
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
    private void pasterData(List<MallHomeTwoBean> data,boolean isShow) {
        for (int i = 0; i < data.size(); i++) {
            listTiles.addAll(data.get(i).getCategoryList());
            advBannerListBeans.addAll(data.get(i).getAdvBannerList());
        }
        initBannner(advBannerListBeans);
        if(isShow){
            initHotCate();
        }
//        mallHomeAdapter.notifyDataSetChanged();
        ModelLoading.getInstance(getActivity()).closeLoading();
    }

    /**
     * 轮播图
     */
    private void initBannner(final List<MallHomeTwoBean.AdvBannerListBean> listBanner) {
        imageBanners.clear();
        for (int i = 0; i < listBanner.size(); i++) {
            imageBanners.add(listBanner.get(i).getUrl());
        }
//        imageBanners.add(R.mipmap.bg_vote);
//        imageBanners.add(R.mipmap.bg_vote);
//        imageBanners.add(R.mipmap.bg_vote);
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
                } else if (type == 5) {//会员中心--赚钱
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainActivity.class);
                    intent.putExtra("go_makemoney", true);
                    startActivity(intent);
                } else if (type == 6) {//所有分类
//                    Intent allCate = new Intent(getActivity(), AllCategoriesActivity.class);
//                    startActivity(allCate);
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

    @OnClick({R.id.img_mall_xin, R.id.img_mall_lv, R.id.img_mall_bao, R.id.img_left, R.id.img_right, R.id.img_tool_mess,R.id.rl_search})
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
            case R.id.img_left:
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
            case R.id.rl_search://搜索
//                Intent search = new Intent(getActivity(), SearchActivity.class);
                Intent search = new Intent(getActivity(), SortSearchActivity.class);
                startActivity(search);
//                getActivity().overridePendingTransition(R.anim.activity_fade,R.anim.activity_hold);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_address_info:
                //获得PopupWindow布局里的View
                final TextView tvClose = view.findViewById(R.id.tv_close);
                editName = view.findViewById(R.id.edit_name);
                editPhone = view.findViewById(R.id.edit_phone);
                final RelativeLayout rlChooseAddress = view.findViewById(R.id.rl_choose_address);
               tvAddress = view.findViewById(R.id.tv_address);
                editDetailAddress = view.findViewById(R.id.edit_detail_address);
                final CheckBox checkboxAddress=view.findViewById(R.id.checkbox_address);
                final Button btnCommit=view.findViewById(R.id.btn_commit);
                editPhone.setText((String) SPUtils.getInstance(getActivity()).get(Constant.USER_MOBILE, ""));
                tvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                rlChooseAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAddressPicker();
                    }
                });

                btnCommit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (popupWindow != null) {
//                            popupWindow.dismiss();
//                        }
                        if(TextUtils.isEmpty(editName.getText().toString().trim())){
                            ToastUtils.getInstanc(getActivity()).showToast("姓名不能为空");
                            return;
                        }
                        if(TextUtils.isEmpty(editPhone.getText().toString().trim())){
                            ToastUtils.getInstanc(getActivity()).showToast("手机号码不为空");
                            return;
                        }else if(editPhone.getText().toString().trim().length()!=11){
                            ToastUtils.getInstanc(getActivity()).showToast("手机号码不符合规则");
                            return;
                        }

                        if(TextUtils.isEmpty(tvAddress.getText().toString().trim())){
                            ToastUtils.getInstanc(getActivity()).showToast("地址不能为空");
                            return;
                        }

                        if(TextUtils.isEmpty(editDetailAddress.getText().toString().trim())){
                            ToastUtils.getInstanc(getActivity()).showToast("详细地址不能为空");
                            return;
                        }

                        completeAddress("",(String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID,""),checkboxAddress);
                    }
                });

                break;
        }
    }

    /**
     * 弹窗默认地址
     *
     * @param
     */
    public void showSetAddress() {
        show_address_pop=true;
//        SPUtils.getInstance(getActivity()).put("isFirstVotes", true);
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_address_info, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(getActivity())
                .setView(R.layout.popup_address_info)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
//                .setOutsideTouchable(false)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(getActivity().findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
//        SPUtils.getInstance(getActivity()).put("isFirstVote", true);
    }

    private void showAddressPicker() {
        AddressPickTask task = new AddressPickTask(getActivity());
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.getInstanc(getActivity()).showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                provinceValue=province.getAreaName();
                cityValue=city.getAreaName();
                countyValue=county.getAreaName();
                if (county == null) {
                    tvAddress.setText(province.getAreaName() + " " + city.getAreaName());
//                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(province.getAreaName() + city.getAreaName());
                } else {
                    provinceValue=province.getAreaName();
                    tvAddress.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
//                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }

            }
        });
        task.execute("北京", "北京", "东城区");
    }

    //适配器
    class Myadapter extends FragmentPagerAdapter {

        public Myadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getfragment(position);
        }

        @Override
        public int getCount() {
            return listTiles.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTiles.get(position).getName();
        }
        private int mChildCount = 0;
        @Override
        public void notifyDataSetChanged() {
            mChildCount = getCount();
            super.notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if ( mChildCount > 0) {
                // 这里利用判断执行若干次不缓存，刷新
                mChildCount --;
                // 返回这个是强制ViewPager不缓存，每次滑动都刷新视图
                return POSITION_NONE;
            }
            return super.getItemPosition(object);
        }
    }

    //动态创建Fragment的方法
    public Fragment getfragment(int position) {
        arrays = new Fragment[listTiles.size()];
        Fragment fg = arrays[position];
        if (fg == null) {
            fg = HostFragmentTwo.getInitUrl(listTiles.get(position).getCategoryId());
            arrays[position] = fg;
        }
        return fg;
    }


    /**
     * 完成地址设置
     * @param id
     * @param userId
     * @param checkBox
     */
    private void completeAddress(String id, String userId,CheckBox checkBox) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("id", id);
            params.put("userid", userId);
            if(checkBox.isChecked()){
                params.put("status", 1);
            }else{
                params.put("status", 0);
            }
            params.put("name", editName.getText().toString().trim());
            params.put("province", provinceValue);
            params.put("city", cityValue);
            params.put("county",countyValue);
            params.put("address", editDetailAddress.getText().toString().trim());
            params.put("phone", editPhone.getText().toString().trim());

            RequestInterface.userPrefix(getActivity(), params, TAG, ReqConstance.I_USERADDRESS_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(getActivity()).showToast(msg);
                    if (code == 0) {
                     SPUtils.getInstance(getActivity()).put(Constant.USER_DEFAULT_ADDRESS, true);
                        if(popupWindow!=null){
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

    private void setAppBarToTop(AppBarLayout appBar){
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
