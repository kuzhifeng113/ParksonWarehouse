package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CollectionBean;
import com.woyun.warehouse.my.adapter.CollectionAdapter;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.DeleteDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 我的收藏
 */
public class MyCollectionActivity extends BaseActivity {
    private static final String TAG = "MyCollectionActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_empty_text)
    TextView tvEmptyText;
    @BindView(R.id.id_empty_view)
    View empty_view;
    private List<CollectionBean.ContentBean> listData = new ArrayList<>();
    private CollectionAdapter collectionAdapter;
    private boolean flag;

    private List<CollectionBean.ContentBean> positionList = new ArrayList<>();//选中的
    private List<Integer> idList = new ArrayList<>();//记录id的
    private String ids;
    private int pager = 1;//当前页
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        tvEmptyText.setText("暂无收藏哟！");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collectionAdapter = new CollectionAdapter(MyCollectionActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(collectionAdapter);
        initData(loginUserId,pager);
//        getJiaData();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pager = 1;
                listData.clear();
                initData(loginUserId,pager);
//                getJiaData();
                mRefreshLayout.finishRefresh();
                mRefreshLayout.resetNoMoreData();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pager++;
                        initData(loginUserId,pager);
                        mRefreshLayout.finishLoadmore();

//                        } else {
//                            Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        }
                    }
                }, 1000);
            }
        });

        collectionAdapter.setCheckInterface(new CollectionAdapter.CheckInterface() {
            @Override
            public void checkGroup(int position, boolean isChecked) {
                listData.get(position).setCheck(isChecked);
                if (isAllCheck())
                    checkboxAll.setChecked(true);
                else
                    checkboxAll.setChecked(false);
                collectionAdapter.notifyDataSetChanged();
            }
        });

    }


    /**
     * 遍历list集合
     */
    private boolean isAllCheck() {
        for (CollectionBean.ContentBean group : listData) {
            if (!group.isCheck())
                return false;
        }
        return true;
    }


    private void initData(String userId,int page) {
        ModelLoading.getInstance(MyCollectionActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            RequestInterface.goodsPrefix(MyCollectionActivity.this, params, TAG, ReqConstance.I_GOODS_FAVORITE_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MyCollectionActivity.this).closeLoading();
                    if (code == 0) {
                        Gson gson = new Gson();
                        List<CollectionBean> shipAddressBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<CollectionBean>>() {
                        }.getType());
                        listData.addAll(shipAddressBeans.get(0).getContent());
                        collectionAdapter.notifyDataSetChanged();
                        if(listData.size()>0){
                            empty_view.setVisibility(View.GONE);
                            tvComplete.setVisibility(View.VISIBLE);
                        }else{
                            empty_view.setVisibility(View.VISIBLE);
                            tvComplete.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.getInstanc(MyCollectionActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MyCollectionActivity.this).closeLoading();
                    ToastUtils.getInstanc(MyCollectionActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.finishRefresh();
            }
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.tv_complete, R.id.checkbox_all, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_complete:
                if (!flag) {
                    flag = true;
                    tvComplete.setText("完成");
                    rlBottom.setVisibility(View.VISIBLE);
                    collectionAdapter.isShow(flag);
                } else {
                    flag = false;
                    tvComplete.setText("编辑");
                    rlBottom.setVisibility(View.GONE);
                    collectionAdapter.isShow(flag);
                }
                break;
            case R.id.checkbox_all:
                if (listData.size() != 0) {
                    if (checkboxAll.isChecked()) {
                        for (int i = 0; i < listData.size(); i++) {
                            listData.get(i).setCheck(true);
                        }
                        collectionAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < listData.size(); i++) {
                            listData.get(i).setCheck(false);
                        }
                        collectionAdapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.tv_delete:
                prepareDelete();
                break;
        }
    }

    private void deleteCollection() {
        ModelLoading.getInstance(MyCollectionActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);
            params.put("ids", ids);
            RequestInterface.goodsPrefix(MyCollectionActivity.this, params, TAG, ReqConstance.I_BATCH_DELETE_FAVORITE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MyCollectionActivity.this).closeLoading();
                    if (code == 0) {

                        for (int i = 0; i < positionList.size(); i++) {
                            for (int j = 0; j < listData.size(); j++) {
                                if (positionList.get(i) == listData.get(j)) {
                                    listData.remove(j);
//                                    collectionAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        if(listData.size()==0){
                            empty_view.setVisibility(View.VISIBLE);
                            tvComplete.setVisibility(View.GONE);
                            rlBottom.setVisibility(View.GONE);
                            if(checkboxAll.isChecked()){
                                checkboxAll.setChecked(false);
                            }
                        }else{
                            empty_view.setVisibility(View.GONE);
                        }
                        collectionAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.getInstanc(MyCollectionActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MyCollectionActivity.this).closeLoading();
                    ToastUtils.getInstanc(MyCollectionActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDeleteDialog() {
        new DeleteDialog(MyCollectionActivity.this, R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    dialog.dismiss();

                    deleteCollection();
                }
            }
        }).show();
    }

    /**
     * 记录选中的下标 与id
     */
    private void prepareDelete() {
        positionList.clear();
        idList.clear();
        for (int i = 0; i < listData.size(); i++) {
            CollectionBean.ContentBean collectionBean = listData.get(i);
            if (collectionBean.isCheck()) {
                positionList.add(collectionBean);
                idList.add(listData.get(i).getFavoriteId());
            }
        }

        if (idList != null && idList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int position = 0; position < idList.size(); position++) {
                collectionAdapter.notifyDataSetChanged();
                if (position == idList.size() - 1) {
                    stringBuffer.append(idList.get(position));
                } else {
                    stringBuffer.append(idList.get(position) + ",");
                }
            }
            ids = stringBuffer.toString();
            LogUtils.e(TAG, "stringBuffer: " + stringBuffer.toString());
            stringBuffer = new StringBuffer("");

            showDeleteDialog();
        } else {
            ToastUtils.getInstanc(MyCollectionActivity.this).showToast("请选择要删除的选项");
        }
    }


}
