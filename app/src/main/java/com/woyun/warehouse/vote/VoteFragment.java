package com.woyun.warehouse.vote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.woyun.warehouse.bean.VoteHomeBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.my.activity.UserInfoActivity;
import com.woyun.warehouse.utils.DateUtils;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.JudgeNestedScrollView;
import com.woyun.warehouse.vote.activity.PastVoteActivity;
import com.woyun.warehouse.vote.adapter.VoteHomeAdapter;
import com.woyun.warehouse.vote.adapter.VoteHomeOldAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 投票
 */
public class VoteFragment extends BaseFragment implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "VoteFragment";
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
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
    @BindView(R.id.tv_vote_date)
    TextView tvVoteDate;
    @BindView(R.id.toolbar_username)
    TextView toolbarUsername;
    @BindView(R.id.recycler_view_vertical)
    RecyclerView recyclerViewVertical;
    @BindView(R.id.img_ding)
    TextView imgDing;

    private int mOffset = 0;
    private int mScrollY = 0;
    int toolBarPositionY = 0;

    private List<VoteHomeBean.GoodsListBean> listNewVotes = new ArrayList<>();
    private List<VoteHomeBean> listOldVotes = new ArrayList<>();
    private VoteHomeAdapter voteNewHomeAdapter;
    private VoteHomeOldAdapter voteOldHomeAdapter;
    private String userId;
    private int voteId;//投票id
    private CommonPopupWindow popupWindow;
    private boolean isVip;
    private boolean isLogin;

    public static VoteFragment newInstance() {
        VoteFragment fragment = new VoteFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_vote, container, false);
        unbinder = ButterKnife.bind(this, view);
        isVip = (boolean) SPUtils.getInstance(getActivity()).get(Constant.USER_IS_VIP, false);
        isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
        initView();
        toolBar.setVisibility(View.GONE);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        ImmersionBar.setTitleBar(getActivity(), toolBar);
        voteNewHomeAdapter = new VoteHomeAdapter(getActivity(), listNewVotes,isVip);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(voteNewHomeAdapter);

        voteOldHomeAdapter = new VoteHomeOldAdapter(getActivity(), listOldVotes,isVip);
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewVertical.setAdapter(voteOldHomeAdapter);
        initOnClick();
        TextView textView=view.findViewById(R.id.tv_username);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            isLogin = (boolean) SPUtils.getInstance(getActivity()).get(Constant.IS_LOGIN, false);
            userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                if(toolBar!=null){
                    toolBar.setAlpha(1 - Math.min(percent, 1));
                }
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                if(toolBar!=null){
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
                    toolBar.setBackgroundColor(Color.parseColor("#ff5f5f"));
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
//        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//        params.height = ScreenUtil.getScreenHeightPx(getApplicationContext()) - toolBarPositionY - magicIndicator.getHeight() + 1;
//        viewPager.setLayoutParams(params);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
        boolean isFirstVote= (boolean) SPUtils.getInstance(getActivity()).get("isFirstVotes",false);
        if(!isFirstVote){
            showVoteRules();
        }
    }

    /**
     * 点击事件
     */
    private void initOnClick() {
        //我想要--预购
        voteNewHomeAdapter.setOnButtonClickListener(new VoteHomeAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int positon) {
                VoteHomeBean.GoodsListBean goodsListBean = listNewVotes.get(positon);
//                ToastUtils.getInstanc(getActivity()).showToast("我想要" + goodsListBean.getName());
//                if (!isVip) {
//                    showNoVip();
//                } else {
                    if(!isLogin){
                        Intent goLogin=new Intent(getActivity(), LoginActivity.class);
                        startActivity(goLogin);
                        return;
                    }
                    doVote(userId,voteId,listNewVotes.get(positon).getGoodsId(),positon);
//                }
            }
        });

        //当前期数 item 点击
        voteNewHomeAdapter.setOnItemClickListener(new VoteHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent detail=new Intent(getActivity(), GoodsDetailActivity.class);
                detail.putExtra("goods_id",listNewVotes.get(position).getGoodsId());
                detail.putExtra("from_id",1);
                detail.putExtra("vote_id",voteId);
                detail.putExtra("is_History",false);//是否是历史期数
                detail.putExtra("wan_count",listNewVotes.get(position).getWantNum());
                detail.putExtra("total_count",listNewVotes.get(position).getTotalNum());
                detail.putExtra("is_vote",listNewVotes.get(position).isIsVote());
                startActivity(detail);
            }
        });

        //查看全部
        voteOldHomeAdapter.setOnButtonClickListener(new VoteHomeOldAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int positon) {
                Intent pastVote=new Intent(getActivity(), PastVoteActivity.class);
                pastVote.putExtra("vote_id",listOldVotes.get(positon).getVoteId());
                pastVote.putExtra("vote_name",listOldVotes.get(positon).getTitle());
                pastVote.putExtra("list_data", (Serializable) listOldVotes);
                String name = listOldVotes.get(positon).getGoodsList().get(0).getName();
//                ToastUtils.getInstanc(getActivity()).showToast("查看全部" + name);
                startActivity(pastVote);
            }
        });

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
                        listNewVotes.clear();
                        listOldVotes.clear();
                        getData(userId);
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });

//        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (listData.size() < 15) {
//                            getData(userId);
//                            mRefreshLayout.finishLoadmore();
//                        } else {
//                            ToastUtils.getInstanc(getActivity()).showToast("数据全部加载完毕");
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        }
//                    }
//                }, 2000);
//            }
//        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //获取数据
    private void getData(String userId) {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.voteRequest(getActivity(), params, TAG, ReqConstance.I_VOTE_GET_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    MainActivity mainActivity= (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(),code);
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Log.e(TAG, "requestSuccess: " + jsonResult);
                        Gson gson = new Gson();
                        List<VoteHomeBean> data = gson.fromJson(jsonResult, new TypeToken<List<VoteHomeBean>>() {
                        }.getType());
                        pasterData(data);
                        LogUtils.e(TAG, "requestSuccess: " + data.size());

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
     * @param data
     */
    private void pasterData(List<VoteHomeBean> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isIsHistory()) {//历史投票
                listOldVotes.add(data.get(i));
            } else {//当前投票
                voteId=data.get(i).getVoteId();
                tvVoteDate.setText("开始时间：" + DateUtils.longToStringTime(data.get(i).getStartTime(),DateUtils.YEAR_MONTH_DAY));
                listNewVotes.addAll(data.get(i).getGoodsList());
            }
        }
        voteOldHomeAdapter.notifyDataSetChanged();
        voteNewHomeAdapter.notifyDataSetChanged();
    }

    /**
     * 投票
     */
    private void doVote(String userid, int votid, int goodsid, final int index){
            try {
                JSONObject params = new JSONObject();
                params.put("userid", userid);
                params.put("voteId", votid);
                params.put("goodsId", goodsid);
                RequestInterface.voteRequest(getActivity(), params, TAG, ReqConstance.I_VOTE_GOODS_INSERT, 1, new HSRequestCallBackInterface() {
                    @Override
                    public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                        MainActivity mainActivity= (MainActivity) getActivity();
                        mainActivity.tokenTimeLimit(getActivity(),code);
                        if (code == 0) {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
                            listNewVotes.get(index).setIsVote(true);
                            int wantNum=listNewVotes.get(index).getWantNum()+1;
                            listNewVotes.get(index).setWantNum(wantNum);
                            voteNewHomeAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(getActivity()).showToast(msg);
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

    @OnClick({R.id.img_ding, R.id.text_memu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_ding://投票规则
                showVoteRules();
                break;
            case R.id.text_memu:
                showVoteRules();
                break;

        }
    }


    /**
     * 弹窗投票规则
     *
     * @param
     */
    public void showVoteRules() {
        SPUtils.getInstance(getActivity()).put("isFirstVotes",true);
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
        SPUtils.getInstance(getActivity()).put("isFirstVote",true);
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
                webView.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onProgressChanged(WebView view, int progress) {
                       if(progress==100){
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

}
