package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.RealNameBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 实名认证
 */
public class RealNameActivity extends BaseActivity {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.img_real_staus)
    ImageView imgRealStaus;
    @BindView(R.id.tv_real_status)
    TextView tvRealStatus;
    @BindView(R.id.btn_do_real)
    Button btnDoReal;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_no_real_reason)
    TextView tvNoRealReason;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id_num)
    TextView tvIdNum;
    @BindView(R.id.ll_already_real)
    LinearLayout llAlreadyReal;
    @BindView(R.id.btn_real_ing)
    Button btnRealIng;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.btn_real_replay)
    Button btnRealReplay;

    private RealNameBean realNameBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);
        ButterKnife.bind(this);
        MyApplication.getInstance().addActivity(RealNameActivity.this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initData() {
        tvTishi.setText("     实名认证有助于提升账户安全。\n个人信息仅作认证用途，不对外公开");
        checkRealNameStatus();
    }

    /**
     * 查询实名认证状态
     */
    private void checkRealNameStatus() {
        ModelLoading.getInstance(RealNameActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);

            RequestInterface.sysPrefix(RealNameActivity.this, params, TAG, ReqConstance.I_REAL_NAME_CHECK, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    Log.e(TAG, "requestSuccess: " + responseData.length());
                    ModelLoading.getInstance(RealNameActivity.this).closeLoading();
                    if (responseCode == 0) {
                        //有状态返回证明 提交过审核
                        if (responseData.length() > 0) {
                            try {
                                Gson gson = new Gson();
                                JSONObject jsonObject = (JSONObject) responseData.get(0);
                                 realNameBean = gson.fromJson(jsonObject.toString(), RealNameBean.class);
                                if (realNameBean.getStatus()==0){//审核中
                                    tvRealStatus.setText("实名制审核中");
                                    btnDoReal.setVisibility(View.GONE);
                                    btnRealIng.setVisibility(View.VISIBLE);
                                }else if(realNameBean.getStatus()==1){//审核通过
                                    tvRealStatus.setText("已实名制认证");
                                    imgRealStaus.setImageResource(R.mipmap.img_already_real);
                                    btnDoReal.setVisibility(View.GONE);
                                    llAlreadyReal.setVisibility(View.VISIBLE);
                                    tvName.setText(realNameBean.getRealName());
                                    tvIdNum.setText(realNameBean.getCardId());
                                }else{//审核不通过
                                    tvRealStatus.setText("认证不通过");
                                    imgRealStaus.setImageResource(R.mipmap.img_real_nopass);
                                    btnDoReal.setVisibility(View.GONE);
                                    tvNoRealReason.setVisibility(View.VISIBLE);
                                    tvNoRealReason.setText("1：照片模糊\n2：照片缺角");
                                    btnRealReplay.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {

                        }
//
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(RealNameActivity.this).closeLoading();
                    ToastUtils.getInstanc(RealNameActivity.this).showToast(responseMessage);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.btn_do_real, R.id.btn_real_replay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_do_real://立即认证
                Intent doReal = new Intent(RealNameActivity.this, RealNameDoActivity.class);
                startActivity(doReal);
                break;
            case R.id.btn_real_replay://重新认证
                Intent intent=new Intent(RealNameActivity.this,RealNameDoActivity.class);
                intent.putExtra("real_entity",realNameBean);
                startActivity(intent);
                break;
        }
    }


}
