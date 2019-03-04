package com.woyun.warehouse.baseparson;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.adapter.LogisticsAdapter;
import com.woyun.warehouse.bean.Logistics;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 物流信息
 */
public class LogisticsActivity extends BaseActivity {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_shipperName)
    TextView tvShipperName;
    @BindView(R.id.tv_logisticCode)
    TextView tvLogisticCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;

    private List<Logistics.TracesBean> listData = new ArrayList<>();
    private LogisticsAdapter logisticsAdapter;
    private String tradeNo;//订单号

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);
        ButterKnife.bind(this);
        tradeNo = getIntent().getStringExtra("trade_no");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    private void initData() {
        logisticsAdapter = new LogisticsAdapter(LogisticsActivity.this, listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(logisticsAdapter);

        getLogisticsData(tradeNo, loginUserId);
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    private void getLogisticsData(String tradeNo, String userId) {
        ModelLoading.getInstance(LogisticsActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("tradeNo", tradeNo);
            params.put("userid", userId);
            RequestInterface.payPrefix(LogisticsActivity.this, params, TAG, ReqConstance.I_PAY_BILL_EXPRESS, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(LogisticsActivity.this).closeLoading();
                    if (code == 0 && jsonArray.length() > 0) {
                        Gson gson = new Gson();
                        List<Logistics> logistics = gson.fromJson(jsonArray.toString(), new TypeToken<List<Logistics>>() {
                        }.getType());
                        tvLogisticCode.setText(logistics.get(0).getLogisticCode());
                        tvShipperName.setText(logistics.get(0).getShipperName());
                        List<Logistics.TracesBean> traces = logistics.get(0).getTraces();
                        pasterData(traces);
//                        collectionAdapter.notifyDataSetChanged();

                    } else {
                        ToastUtils.getInstanc(LogisticsActivity.this).showToast(msg);
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(LogisticsActivity.this).closeLoading();
                    ToastUtils.getInstanc(LogisticsActivity.this).showToast(Constant.NET_ERROR);
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
    private void pasterData(List<Logistics.TracesBean> data) {
        listData.clear();
        for (int i = data.size() - 1; i >= 0; i--) {
            listData.add(data.get(i));
        }
//        initBody(listData);
        logisticsAdapter.notifyDataSetChanged();
        LogUtils.e(TAG, "" + listData.size());
    }

    @OnClick(R.id.tv_copy)
    public void onViewClicked() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(tvLogisticCode.getText().toString().trim());
        ToastUtils.getInstanc(LogisticsActivity.this).showToast("复制成功");
    }
}
