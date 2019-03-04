package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.AgentOutFee;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 代理退出
 */
public class AgentOutActivity extends BaseActivity {
    private static final String TAG = "AgentOutActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_out_agent)
    Button btnOutAgent;
    @BindView(R.id.tv_vip_num)
    TextView tvVipNum;
    @BindView(R.id.tv_refund)
    TextView tvRefund;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_fee_money)
    TextView tvFeeMoney;
    @BindView(R.id.tv_actrefund)
    TextView tvActrefund;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_out);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData(loginUserId);

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.btn_out_agent)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {

            doOut(loginUserId, editText.getText().toString().trim());
        } else {
            ToastUtils.getInstanc(AgentOutActivity.this).showToast("请填写退出代理的原因!");
        }
    }

    /**
     * 查询可退vip数 可退金额
     * @param userid
     *
     */
    private void initData(String userid) {
        ModelLoading.getInstance(AgentOutActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userid);

            RequestInterface.agentPrefix(AgentOutActivity.this, params, TAG, ReqConstance.I_AGENT_OUT_QUERY, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentOutActivity.this).closeLoading();
                    if (code == 0) {
                        try {
                            Gson gson=new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            AgentOutFee agentOutFee= gson.fromJson(object.toString(), AgentOutFee.class);
                            tvVipNum.setText("可退VIP（"+String.valueOf(agentOutFee.getVipNum())+"个）");
                            tvRefund.setText(String.valueOf(agentOutFee.getRefund()+"元"));
                            tvFee.setText("扣税("+String.valueOf(agentOutFee.getFee())+")");
                            tvFeeMoney.setText(String.valueOf(agentOutFee.getFeeMoney()+"元"));
                            tvActrefund.setText(String.valueOf(agentOutFee.getActrefund()+"元"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        ToastUtils.getInstanc(AgentOutActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentOutActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentOutActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 提交
     *
     * @param userid
     * @param content
     */
    private void doOut(String userid, String content) {
        ModelLoading.getInstance(AgentOutActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("content", content);
            params.put("userid", userid);

            RequestInterface.agentPrefix(AgentOutActivity.this, params, TAG, ReqConstance.I_AGENT_OUT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentOutActivity.this).closeLoading();
                    if (code == 0) {
                        ToastUtils.getInstanc(AgentOutActivity.this).showToast(msg);
                        finish();
                    } else {
                        ToastUtils.getInstanc(AgentOutActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentOutActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentOutActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
