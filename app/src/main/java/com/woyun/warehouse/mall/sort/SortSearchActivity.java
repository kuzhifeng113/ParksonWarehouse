package com.woyun.warehouse.mall.sort;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.SearchBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeVipActivity;
import com.woyun.warehouse.mall.activity.SearchActivity;
import com.woyun.warehouse.mall.activity.SearchResultActivity;
import com.woyun.warehouse.mall.adapter.HistorySearchAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 分类 搜索activity
 */
public class SortSearchActivity extends BaseActivity implements CheckListener {
    private static final String TAG = "SortSearchActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_search)
    TextView editText;
    @BindView(R.id.recycler_view_big)
    RecyclerView recyclerViewBig;

    private LinearLayoutManager mLinearLayoutManager;
    private Context mContext;
    private SortAdapter sortAdapter;
    private SortDetailFragment mSortDetailFragment;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;
    private SortBean mSortBean;
    private List<SortBean> sortBeanList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_search);
        ButterKnife.bind(this);
        mContext=this;

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editText.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SortSearchActivity.this, SearchActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SortSearchActivity.this).toBundle());
//                startActivity(intent);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

        mLinearLayoutManager = new LinearLayoutManager(mContext);
        recyclerViewBig.setLayoutManager(mLinearLayoutManager);
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    /**
     * 获取大小分类数据
     */
    private void initData() {
        ModelLoading.getInstance(SortSearchActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.goodsPrefix(SortSearchActivity.this, params, TAG, ReqConstance.I_GOODS_CATEGORY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(SortSearchActivity.this).closeLoading();
                    if (code == 0) {
                        Gson gson = new Gson();
                        List<SortBean> data = gson.fromJson(jsonArray.toString(), new TypeToken<List<SortBean>>() {
                        }.getType());
                        Log.e(TAG, "requestSuccess: "+data.size() );
                        parserData(data);
                    } else {

                        ToastUtils.getInstanc(SortSearchActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(SortSearchActivity.this).closeLoading();
                    ToastUtils.getInstanc(SortSearchActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //初始化数据
    private void parserData(List<SortBean> data) {
        sortBeanList.addAll(data);
        List<String> list = new ArrayList<>();
        //初始化左侧列表数据
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getName());
        }

        sortAdapter = new SortAdapter(mContext, list, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (mSortDetailFragment != null) {
                    isMoved = true;
                    targetPosition = position;
                    setChecked(position, true);
                }
            }
        });
        recyclerViewBig.setAdapter(sortAdapter);
        createFragment();
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    private void setChecked(int position, boolean isLeft) {
        Log.d("p-------->", String.valueOf(position));
        if (isLeft) {
            sortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
//                count += mSortBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
                count += sortBeanList.get(i).getCategoryList().size();
            }
            count += position;
            mSortDetailFragment.setData(count);

            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag

        } else {
            if (isMoved) {
                isMoved = false;
            } else
                sortAdapter.setCheckedPosition(position);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);

    }


    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mSortDetailFragment = new SortDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("right", (Serializable) sortBeanList);
        mSortDetailFragment.setArguments(bundle);
        mSortDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = recyclerViewBig.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - recyclerViewBig.getHeight() / 2);
            recyclerViewBig.smoothScrollBy(0, y);
        }

    }
}
