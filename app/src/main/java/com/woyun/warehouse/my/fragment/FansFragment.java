package com.woyun.warehouse.my.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.SplashActivity;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.baseparson.event.FansEvent;
import com.woyun.warehouse.baseparson.event.RefreshGrabEvent;
import com.woyun.warehouse.bean.FansBean;
import com.woyun.warehouse.bean.GrabGoodsBean;
import com.woyun.warehouse.grabbuy.adapter.GrabGoodsAdapter;
import com.woyun.warehouse.my.adapter.FansAdapter;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

/**
 * 粉丝
 */
public class FansFragment extends BaseFragmentTwo {
    private static final String TAG = "FansFragment";
    private static final String FANS_IS_VIP = "fans_isvip";
    Unbinder unbinder;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int isVip;//是否是vip
    private String userId;
    private FansAdapter fansAdapter;
    private List<FansBean> listDatas = new ArrayList<>();

    private RxPermissions rxPermissions;
    //回调用来接收参数
    public static FansFragment getInstance(int timeId) {
        FansFragment myFragment = new FansFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FANS_IS_VIP, timeId);
        myFragment.setArguments(bundle);
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fans, null, false);
        unbinder = ButterKnife.bind(this, rootView);
        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fansAdapter = new FansAdapter(getActivity(), listDatas);
        recyclerView.setAdapter(fansAdapter);


        fansAdapter.setOnButtonClickListener(new FansAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int positon) {
                checkPermission(listDatas.get(positon).getMobile());
            }
        });

        return rootView;
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        isVip = arguments.getInt(FANS_IS_VIP);
        LogUtils.e(TAG, "isVIp==" + isVip);

    }


    private void initData() {
        if (mRefreshLayout != null) {
            //触发自动刷新
            mRefreshLayout.autoRefresh();
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.getLayout().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            listDatas.clear();
                            getData(userId, isVip);
                            mRefreshLayout.finishRefresh();
                            mRefreshLayout.resetNoMoreData();
                        }
                    }, 500);
                }
            });
        }

    }

    /**
     * 获取全部订单
     * 是否vip，isVip=0表示非vip，isVip=1表示vip
     */
    private void getData(String userId, int issVip) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("isVip", issVip);
            RequestInterface.userPrefix(getActivity(), params, TAG + issVip, ReqConstance.I_FUSERID_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        String jsonResult = jsonArray.toString();
                        Gson gson = new Gson();
                        List<FansBean> grabGoodsBeanList = gson.fromJson(jsonResult, new TypeToken<List<FansBean>>() {
                        }.getType());
//                            Log.e(TAG, "requestSuccess: "+grabGoodsBeanList.size() );
                        EventBus.getDefault().post(new FansEvent(issVip, grabGoodsBeanList.size()));
                        listDatas.addAll(grabGoodsBeanList);
                        fansAdapter.notifyDataSetChanged();
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
        } finally {
            if (mRefreshLayout.isRefreshing()) {
                mRefreshLayout.finishRefresh();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void checkPermission(String phone) {
        rxPermissions = new RxPermissions(getActivity());
        rxPermissions
                .request(Manifest.permission.CALL_PHONE)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        callPhone(phone);
                    } else {
                        // Oups permission denied
                    }
                });
    }
}
