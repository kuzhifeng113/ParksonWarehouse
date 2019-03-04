package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {
    private static final String TAG = "FeedBackActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.btn_submit)
    Button btnSubmit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    private void initData() {
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if(TextUtils.isEmpty(editText.getText().toString().trim())){
            ToastUtils.getInstanc(FeedBackActivity.this).showToast("说点什么吧...");
            return;
        }
        if(editText.getText().toString().trim().length()<10){
            ToastUtils.getInstanc(FeedBackActivity.this).showToast("不能小于十个字");
            return;
        }
        doFeedBack(loginUserId,editText.getText().toString().trim());
    }

    /**
     * 提交
     * @param userid
     * @param content
     */
    private void doFeedBack(String userid, String content){
        ModelLoading.getInstance(FeedBackActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("content", content);
            params.put("userid", userid);

            RequestInterface.sysPrefix(FeedBackActivity.this, params, TAG, ReqConstance.I_SYS_ADVICE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(FeedBackActivity.this).closeLoading();
                    if (code == 0) {
                        ToastUtils.getInstanc(FeedBackActivity.this).showToast(msg);
                        finish();
                    } else {
                        ToastUtils.getInstanc(FeedBackActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(FeedBackActivity.this).closeLoading();
                    ToastUtils.getInstanc(FeedBackActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
