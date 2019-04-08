package com.woyun.warehouse.find.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 福利
 */
public class FuLiFragment extends BaseFragment {
    private static final String TAG = "FuLiFragment";
    private static final String FIND_TYPE = "find_type";

    Unbinder unbinder;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_empty)
    ImageView ivEmpty;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;


    private String userId;
    private int findTypeId;

    public static FuLiFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(FIND_TYPE, page);
        FuLiFragment fragment = new FuLiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findTypeId = getArguments().getInt(FIND_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_fuli, container, false);
        unbinder = ButterKnife.bind(this, view);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        tv.setText("福利" + findTypeId);
        return view;
    }


    private void intView(){
        //创建VirtualLayoutManager对象
        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(getActivity());
        recyclerView.setLayoutManager(virtualLayoutManager);

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

    }
    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        Log.e(TAG, "onFragmentFirstVisible:");
        initData();
    }


    private void initData() {
        //触发自动刷新
        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        listData.clear();
//                        pager=1;
                        getData();
                        mRefreshLayout.finishRefresh();
                        mRefreshLayout.resetNoMoreData();
                    }
                }, 500);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        pager++;
//                        getData(userId, pager);
                        mRefreshLayout.finishLoadmore();

                    }
                }, 1000);
            }
        });
    }

    /**
     * 模拟获取数据
     */
    private void getData(){


    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
