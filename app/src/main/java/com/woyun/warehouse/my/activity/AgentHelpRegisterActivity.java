package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.AgentHelpVBean;
import com.woyun.warehouse.my.adapter.AgentHelpAdapter;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.KeyboardUtil;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DeleteDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 协助注册
 */
public class AgentHelpRegisterActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "AgentHelpRegisterActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_buy_num)
    TextView tvBuyNum;
    @BindView(R.id.tv_yi_kt)
    TextView tvYiKt;
    @BindView(R.id.tv_s_yu)
    TextView tvSYu;
    @BindView(R.id.recycler_vip_qy)
    RecyclerView recyclerVipQy;

    @BindView(R.id.btn_help)
    Button btnHelp;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private AgentHelpAdapter agentHelpAdapter;
    private int pager = 1;
    private List<AgentHelpVBean.PageBeanBean.ContentBean> listData = new ArrayList<>();
    private CommonPopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = getLayoutInflater().inflate(R.layout.activity_agent_help2, null);
        setContentView(contentView);
        ButterKnife.bind(this);
        mImmersionBar.titleBar(toolBar);
        new KeyboardUtil(this, contentView);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        agentHelpAdapter = new AgentHelpAdapter(AgentHelpRegisterActivity.this, listData);
        recyclerVipQy.setNestedScrollingEnabled(false);
        recyclerVipQy.setLayoutManager(new LinearLayoutManager(this));
        recyclerVipQy.setAdapter(agentHelpAdapter);
        initData(loginUserId, pager);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listData.clear();
                        pager = 1;
                        initData(loginUserId, pager);
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
                        pager++;
                        initData(loginUserId, pager);
                        mRefreshLayout.finishLoadmore();

//                        } else {
//                            Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
//                            mRefreshLayout.finishLoadmoreWithNoMoreData();//将不会再次触发加载更多事件
//                        }
                    }
                }, 1000);
            }
        });

    }

    /**
     * 获取数据
     */
    private void initData(String userId, int page) {
        ModelLoading.getInstance(AgentHelpRegisterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("page", page);
            RequestInterface.agentPrefix(AgentHelpRegisterActivity.this, params, TAG, ReqConstance.I_AGENT_HELP, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentHelpRegisterActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            AgentHelpVBean helpVBean = gson.fromJson(object.toString(), AgentHelpVBean.class);
                            tvBuyNum.setText(String.valueOf(helpVBean.getVipHistoryNum()));
                            tvYiKt.setText(String.valueOf(helpVBean.getVipOpenNum()));
                            tvSYu.setText(String.valueOf(helpVBean.getVipNum()));
                            listData.addAll(helpVBean.getPageBean().getContent());
                            agentHelpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        btnHelp.setEnabled(false);
                        ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {

                    btnHelp.setEnabled(false);
                    ModelLoading.getInstance(AgentHelpRegisterActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void register(String phone, String userId) {
        ModelLoading.getInstance(AgentHelpRegisterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("mobile", phone);
            params.put("userid", userId);
            RequestInterface.agentPrefix(AgentHelpRegisterActivity.this, params, TAG, ReqConstance.I_AGENT_VIP, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentHelpRegisterActivity.this).closeLoading();
                    if (code == 0) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast("开通VIP成功!");
                        listData.clear();
                        initData(loginUserId, 1);
                    } else {
                        ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentHelpRegisterActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast(Constant.NET_ERROR);
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

    @OnClick(R.id.btn_help)
    public void onViewClicked() {
        showPopup();
//            if(editPhone.getText().toString().trim().length()!=11){
//                ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast("请输入正确手机号码~");
//                return;
//            }
//        new DeleteDialog(AgentHelpRegisterActivity.this, R.style.dialogphone, "您确定注册"+editPhone.getText().toString().trim()+"吗？", new DeleteDialog.OnCloseListener() {
//            @Override
//            public void onClick(Dialog dialog, boolean confirm) {
//                if (confirm) {
//                    dialog.dismiss();
//                    register(editPhone.getText().toString().trim(),loginUserId);
//                }
//            }
//        }).show();


    }

    private void showPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(AgentHelpRegisterActivity.this).inflate(R.layout.popup_help_register, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(AgentHelpRegisterActivity.this)
                .setView(R.layout.popup_help_register)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .setOutsideTouchable(true)
                .create();
        popupWindow.showAtLocation(AgentHelpRegisterActivity.this.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        ImageView img_close = view.findViewById(R.id.img_close);
        final TextView edit_phone = view.findViewById(R.id.edit_phone);
        final TextView edit_phone_two = view.findViewById(R.id.edit_phone_two);
        Button btn_bind = view.findViewById(R.id.btn_bind);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edit_phone.getText().toString().trim())||edit_phone.getText().length()!=11) {
                    ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast("请输入手机号码~");
                    return;
                }

                if (TextUtils.isEmpty(edit_phone_two.getText().toString().trim())|| edit_phone_two.getText().length()!=11) {
                    ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast("请确认手机号码~");
                    return;
                }
                if(!edit_phone.getText().toString().equals(edit_phone_two.getText().toString())){
                    ToastUtils.getInstanc(AgentHelpRegisterActivity.this).showToast("两次手机号不一致~");
                    return;
                }
                register(edit_phone.getText().toString().trim(),loginUserId);
            }
        });
    }
}
