package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.my.adapter.ShipAddressAdapter;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.SwipeItemLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 收货地址
 */
public class MyAddressActivity extends BaseActivity {
    private static final String TAG = "MyAddressActivity";
    public static int  REQUEST_CODE=1;
    public static int  RESUTL_CODE=2;
    public static int  REQUEST_EDIT=3;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    private List<ShipAddressBean> listData = new ArrayList<>();
    private ShipAddressAdapter shipAddressAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addres);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shipAddressAdapter = new ShipAddressAdapter(MyAddressActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recyclerView.setAdapter(shipAddressAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                listData.clear();
                initData(loginUserId);
            }
        });


        shipAddressAdapter.setOnButtonClickListener(new ShipAddressAdapter.OnButtonClickListener() {
            @Override
            public void delete(final int positon) {
                new DeleteDialog(MyAddressActivity.this, R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            dialog.dismiss();
                            deleteAddress(listData.get(positon).getId(),loginUserId,positon);
                        }
                    }
                }).show();
            }
        });

        shipAddressAdapter.setOnItemClickListener(new ShipAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                boolean flag=getIntent().getBooleanExtra("from_my",false);//从我的页面进来的
                if(!flag){
                    Intent result=new Intent();
                    result.putExtra("address_entity",listData.get(position));
                    setResult(RESUTL_CODE,result);
                    finish();
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listData.clear();
        initData(loginUserId);
    }

    private void initData(String userId) {
        ModelLoading.getInstance(MyAddressActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.userPrefix(MyAddressActivity.this, params, TAG, ReqConstance.I_USERADDRESS_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MyAddressActivity.this).closeLoading();
                    if (code == 0) {
                        Gson gson = new Gson();
                        List<ShipAddressBean> shipAddressBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<ShipAddressBean>>() {
                        }.getType());
                        listData.addAll(shipAddressBeans);
                        shipAddressAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.getInstanc(MyAddressActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MyAddressActivity.this).closeLoading();
                    ToastUtils.getInstanc(MyAddressActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.finishRefresh();
            }
        }
    }

    /**
     * 删除地址
     * @param addresId
     */
    private void deleteAddress(int addresId, String userId, final int index){
        ModelLoading.getInstance(MyAddressActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("id", addresId);
            params.put("userid", userId);

            RequestInterface.userPrefix(MyAddressActivity.this, params, TAG, ReqConstance.I_USERADDRESS_DELETE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MyAddressActivity.this).closeLoading();
                    if (code == 0) {
                        listData.remove(index);
                        shipAddressAdapter.notifyDataSetChanged();
                        ToastUtils.getInstanc(MyAddressActivity.this).showToast(msg);
                    } else {
                        ToastUtils.getInstanc(MyAddressActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MyAddressActivity.this).closeLoading();
                    ToastUtils.getInstanc(MyAddressActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.img_add)
    public void onViewClicked() {//添加地址
        Intent intent = new Intent(MyAddressActivity.this, InsertAddressActivity.class);
        if(listData.size()==0){//未设置默认地址
            intent.putExtra("isSetDefaultAddress",true);
        }
        startActivity(intent);
    }


}
