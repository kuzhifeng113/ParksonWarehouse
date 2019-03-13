package com.woyun.warehouse.mall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.woyun.warehouse.baseparson.event.UnReadMessEvent;
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.mall.activity.AllCategoriesActivity;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MessageActivity;
import com.woyun.warehouse.mall.activity.SearchActivity;
import com.woyun.warehouse.mall.adapter.MallHomeAdapter;
import com.woyun.warehouse.my.activity.VipCenterActivity;
import com.woyun.warehouse.utils.APKVersionCodeUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CornerTransform;
import com.woyun.warehouse.view.JudgeNestedScrollView;
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
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

/**
 * 商城
 */
public class MallFragment extends BaseFragment {
    private static final String TAG = "MallFragment";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.img_ding)
    ImageView imgDing;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    JudgeNestedScrollView scrollView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.iv_menu)
    ImageView ivMenu;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.mall_banner)
    Banner mallBanner;
    @BindView(R.id.toolbar_username)
    TextView toolbarUsername;
    @BindView(R.id.img_mall_xin)
    ImageView imgMallXin;
    @BindView(R.id.img_mall_lv)
    ImageView imgMallLv;
    @BindView(R.id.img_mall_bao)
    ImageView imgMallBao;

    private int mOffset = 0;
    private int mScrollY = 0;
    int toolBarPositionY = 0;


    private List<MallHomeBean.PackListBean> mallHomeBeanList = new ArrayList<>();
    private List<MallHomeBean.AdvBannerListBean> advBannerListBeans = new ArrayList<>();
    private MallHomeAdapter mallHomeAdapter;
    List<String> imageBanners = new ArrayList<>();

    final List<ImageInfo> imageInfoList = new ArrayList<>();

    public static MallFragment newInstance() {
        MallFragment fragment = new MallFragment();
        return fragment;
    }
    private int error = 0;
    Badge badge,badge2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_mall, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        badge=new QBadgeView(getActivity());
        badge2=new QBadgeView(getActivity());
        initView();
        toolBar.setVisibility(View.GONE);
        ImmersionBar.setTitleBar(getActivity(), toolBar);
        mallHomeAdapter = new MallHomeAdapter(getActivity(), mallHomeBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(recyclerView.getItemDecorationCount()==0){
            recyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 30)));//垂直间距
        }
        recyclerView.setAdapter(mallHomeAdapter);
        initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(UnReadMessEvent event) {
        Log.e(TAG, "Event:==未读消息=== "+event.getNum());
            setUnreadNum(event.getNum());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    private void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                toolBar.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                toolBar.setAlpha(1 - Math.min(percent, 1));
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
                    imgDing.setVisibility(View.VISIBLE);
                    ivMenu.setVisibility(View.GONE);
                    ivBack.setImageResource(R.mipmap.back_white);
                    ivMenu.setImageResource(R.mipmap.icon_menu_white);
                } else {
                    toolBar.setVisibility(View.VISIBLE);
                    imgDing.setVisibility(View.GONE);
                    ivMenu.setVisibility(View.VISIBLE);
                    ivBack.setImageResource(R.mipmap.back_black);
                    ivMenu.setImageResource(R.mipmap.icon_menu_black);
                }

                lastScrollY = scrollY;
            }
        });
        buttonBarLayout.setAlpha(0);
        toolBar.setBackgroundColor(0);
    }

    private void initData() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mallHomeBeanList.clear();
                advBannerListBeans.clear();
                getData(false);
                mRefreshLayout.finishRefresh();
            }
        });
    }

    private void dealWithViewPager() {
        toolBarPositionY = toolBar.getHeight();
//        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//        params.height = ScreenUtil.getScreenHeightPx(getApplicationContext()) - toolBarPositionY - magicIndicator.getHeight() + 1;
//        viewPager.setLayoutParams(params);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        getData(true);
    }


    /**
     * 获取数据
     * 版本号
     * 平台AND 或者 IOS
     * @param  isShow  是否显示loading
     */
    private void getData(boolean isShow) {
        if(isShow){
            ModelLoading.getInstance(getActivity()).showLoading("",true);
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

                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(),code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Gson gson = new Gson();
                        List<MallHomeBean> datas = gson.fromJson(jsonResult, new TypeToken<List<MallHomeBean>>() {
                        }.getType());
                        pasterData(datas);
                        LogUtils.e(TAG, "requestSuccess: " + datas.size());
//                        int unreadNum=datas.get(0).getUnreadNum();
//                        setUnreadNum(unreadNum);
//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    Log.e(TAG, "requestError: getData");
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
    private void pasterData(List<MallHomeBean> data) {
        for (int i = 0; i < data.size(); i++) {
            mallHomeBeanList.addAll(data.get(i).getPackList());
            advBannerListBeans.addAll(data.get(i).getAdvBannerList());
        }
        initBannner(advBannerListBeans);
        mallHomeAdapter.notifyDataSetChanged();
        ModelLoading.getInstance(getActivity()).closeLoading();
        LogUtils.e(TAG, "" + mallHomeBeanList.size());
    }

    /**
     * 轮播图
     */
    private void initBannner(final List<MallHomeBean.AdvBannerListBean> listBanner) {
        imageBanners.clear();
        for(int i=0;i<listBanner.size();i++){
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

                CornerTransform transformation = new CornerTransform(context, DensityUtils.dp2px(context, 12));
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
                Intent webView=new Intent();
              int type= listBanner.get(position).getType();
                if(type==3){
//                    webView.setClass(getActivity(), GoodsDetailActivity.class);
                    webView.setClass(getActivity(), GoodsDetailNativeActivity.class);
                    webView.putExtra("goods_id",listBanner.get(position).getGoodsId());
                    webView.putExtra("from_id",2);
                    startActivity(webView);
                }else if(type==4){
                    if(!TextUtils.isEmpty(listBanner.get(position).getTagUrl())){
                        webView.setClass(getActivity(),MyWebViewActivity.class);
                        webView.putExtra("web_url",listBanner.get(position).getTagUrl());
                        startActivity(webView);
                    }
                }else if(type==5){//会员中心
                    Intent intent=new Intent();
                   boolean isLogin= (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN,false);
                    if(isLogin){
                        intent.setClass(getActivity(),VipCenterActivity.class);
                    }else{
                        intent.setClass(getActivity(),LoginActivity.class);
                    }
                    startActivity(intent);
                }else if(type==6){//所有分类
                    Intent allCate=new Intent(getActivity(), AllCategoriesActivity.class);
                    startActivity(allCate);
                }

            }
        });
    }

    /**
     * 设置消息红点
     * @param num
     */
    private void setUnreadNum(int num){
        badge.bindTarget(imgDing).setBadgeGravity(Gravity.END|Gravity.TOP);
        badge.setBadgePadding(3.5f,true);
        badge.setGravityOffset(9,8,true);

        badge2.bindTarget(ivMenu).setBadgeGravity(Gravity.END|Gravity.TOP);
        badge2.setBadgePadding(3.5f,true);
//        badge2.setGravityOffset(9,8,true);
        if(num>0){
            badge.setBadgeText("");
            badge2.setBadgeText("");
        }else{
            badge.hide(false);
            badge2.hide(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.img_ding, R.id.iv_menu, R.id.rl_search,R.id.img_mall_xin, R.id.img_mall_lv, R.id.img_mall_bao})
    public void onViewClicked(View view) {
        boolean isLogin= (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN,false);
        switch (view.getId()) {
            case R.id.img_ding://消息
                if(!isLogin){
                    Intent goLogin=new Intent(getActivity(), LoginActivity.class);
                    startActivity(goLogin);
                    return;
                }
                Intent messs = new Intent(getActivity(), MessageActivity.class);
                startActivity(messs);
                break;
            case R.id.iv_menu://消息
                if(!isLogin){
                    Intent goLogin=new Intent(getActivity(), LoginActivity.class);
                    startActivity(goLogin);
                    return;
                }
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_search://搜索
                Intent search = new Intent(getActivity(), SearchActivity.class);
                startActivity(search);
//                getActivity().overridePendingTransition(R.anim.activity_fade,R.anim.activity_hold);
                break;
            case R.id.img_mall_xin:
                imageInfoList.clear();
                ImageInfo imageInfo=new ImageInfo();
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
                ImageInfo imageInfo2=new ImageInfo();
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
                ImageInfo imageInfo3=new ImageInfo();
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
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
