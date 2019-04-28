package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jkb.vcedittext.VerificationAction;
import com.jkb.vcedittext.VerificationCodeEditText;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.LoginActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.event.OrderIndexEvent;
import com.woyun.warehouse.baseparson.event.WxUserInfoEvent;
import com.woyun.warehouse.cart.activity.OrderXiaDanActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.KeybordS;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.MD5Util;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.TwoPassWordDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 账号绑定
 */
public class BindAccountActivity extends BaseActivity  implements CommonPopupWindow.ViewInterface{
    private static final String TAG = "BindAccountActivity";
    public static int  RESUTL_CODE=2000;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_wechat)
    TextView tvWechat;
    @BindView(R.id.rl_bind_wx)
    RelativeLayout rlBindWx;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.rl_bind_alipay)
    RelativeLayout rlBindAlipay;

    private String weChat,aliPay,alipayName;
    private CommonPopupWindow popupWindow;
    private String weChatName;//绑定微信的昵称

    private IWXAPI iwxapi;
    public static String uuid = null;
    private int fromType;//1 从提现页面过来的
    private String passWord;//2级密码
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //通过WXAPIFactory工厂获取IWXApI的示例
        iwxapi = WXAPIFactory.createWXAPI(this,Constant.WX_APP_ID);
        iwxapi.registerApp(Constant.WX_APP_ID);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(WxUserInfoEvent wxUserInfoEvent) {
//        Log.e(TAG, "Event: "+wxUserInfoEvent.getName() );
//        Log.e(TAG, "Event: "+wxUserInfoEvent.getOpenId() );
        if(wxUserInfoEvent!=null){
            weChatName=wxUserInfoEvent.getName();
            doBind(loginUserId,1,wxUserInfoEvent.getOpenId(),wxUserInfoEvent.getName(),"","",passWord);
        }
    }

    private void initData() {
//        fromType=getIntent().getIntExtra("from_type",0);
        LogUtils.e(TAG, "initData: fromType=="+fromType );
        weChat= (String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_WX,"");
        weChatName= (String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_WX_NAME,"");
        aliPay=(String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_ZFB,"");
        alipayName=(String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_ZFB_NAME,"");

        if(TextUtils.isEmpty(weChatName)){
            tvWechat.setText("未绑定");
            tvWechat.setTextColor(getResources().getColor(R.color.text_content));
        }else{
            tvWechat.setText("("+weChatName+") 已绑定");
            tvWechat.setTextColor(getResources().getColor(R.color.mainColor));
        }

        if(TextUtils.isEmpty(aliPay)){
            tvAlipay.setText("未绑定");
            tvAlipay.setTextColor(getResources().getColor(R.color.text_content));
        }else{
            tvAlipay.setText("("+aliPay+") 已绑定");
            tvAlipay.setTextColor(getResources().getColor(R.color.mainColor));
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.rl_bind_wx, R.id.rl_bind_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_bind_wx:
                String isSetTwoPwd = (String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_TWO_PWD, "");
                if (TextUtils.isEmpty(isSetTwoPwd)) {//未设置2级密码
                    new DeleteDialog(BindAccountActivity.this, R.style.dialogphone, "确定去设置二级密码？", new DeleteDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                                Intent goPwd = new Intent(BindAccountActivity.this, TwoPassWordActivity.class);
                                goPwd.putExtra("pay_flag", true);
                                startActivity(goPwd);

                            }
                        }
                    }).show();
                } else {
                    showTwoPassWord(1);
                }
                break;
            case R.id.rl_bind_alipay:
                String isSetTwoPwd2 = (String) SPUtils.getInstance(BindAccountActivity.this).get(Constant.USER_TWO_PWD, "");
                if (TextUtils.isEmpty(isSetTwoPwd2)) {//未设置2级密码
                    new DeleteDialog(BindAccountActivity.this, R.style.dialogphone, "确定去设置二级密码？", new DeleteDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                dialog.dismiss();
                                Intent goPwd = new Intent(BindAccountActivity.this, TwoPassWordActivity.class);
                                goPwd.putExtra("pay_flag", true);
                                startActivity(goPwd);

                            }
                        }
                    }).show();
                } else {
                    showTwoPassWord(2);
                }
//                showBindAliPay();
                break;
        }
    }

    private void showBindAliPay(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(BindAccountActivity.this).inflate(R.layout.popup_bind_alipay, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(BindAccountActivity.this)
                .setView(R.layout.popup_bind_alipay)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
//                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(BindAccountActivity.this.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
         ImageView img_close = view.findViewById(R.id.img_close);
         final TextView edit_name = view.findViewById(R.id.edit_name);
         final TextView edit_account_alipay = view.findViewById(R.id.edit_account_alipay);
         Button btn_bind = view.findViewById(R.id.btn_bind);


        edit_name.setText(alipayName);
        edit_account_alipay.setText(aliPay);
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
                if(TextUtils.isEmpty(edit_name.getText().toString().trim())){
                    ToastUtils.getInstanc(BindAccountActivity.this).showToast("请输入姓名~");
                    return;
                }

                if(TextUtils.isEmpty(edit_account_alipay.getText().toString().trim())){
                    ToastUtils.getInstanc(BindAccountActivity.this).showToast("请输入支付宝账号~");
                    return;
                }
                doBind(loginUserId,2,"","",edit_account_alipay.getText().toString().trim(),edit_name.getText().toString().trim(),passWord);

            }
        });
    }

    /**
     *
     * @param userid
     * @param type   1 绑定 微信  2  支付宝
     * @param wx
     * @param zfb
     * @param name
     */
    private void doBind(String userid, final int type, String wx,String wxName, final String zfb, final String name,String pwd){
        ModelLoading.getInstance(BindAccountActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userid);
            params.put("type", type);
            if(type==1){
                params.put("wx", wx);
                params.put("name", wxName);
            }else{
                params.put("zfb", zfb);
                params.put("name", name);
            }
            params.put("pwd",MD5Util.getMD5(pwd));
            LogUtils.e(TAG, "doBind: 密码"+pwd );
            RequestInterface.userPrefixVersiontTwo(BindAccountActivity.this, params, TAG, ReqConstance.I_USER_BINDING, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(BindAccountActivity.this).closeLoading();
                    if (code == 0) {
                        if(type==2){
                            tvAlipay.setText("("+zfb+") 已绑定");
                            tvAlipay.setTextColor(getResources().getColor(R.color.mainColor));
                            SPUtils.getInstance(BindAccountActivity.this).put(Constant.USER_ZFB,zfb);
                            SPUtils.getInstance(BindAccountActivity.this).put(Constant.USER_ZFB_NAME,name);
                        }else{
                            tvWechat.setText("("+wxName+") 已绑定");
                            tvWechat.setTextColor(getResources().getColor(R.color.mainColor));
                            SPUtils.getInstance(BindAccountActivity.this).put(Constant.USER_WX_NAME,wxName);
                            SPUtils.getInstance(BindAccountActivity.this).put(Constant.USER_WX,wx);
                        }

                        ToastUtils.getInstanc(BindAccountActivity.this).showToast(msg);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        if(fromType==1){
                            finish();
                        }
                    } else {
                        ToastUtils.getInstanc(BindAccountActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(BindAccountActivity.this).closeLoading();
                    ToastUtils.getInstanc(BindAccountActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /** 微信登录 **/
    private void wxLogin() {
        if (!iwxapi.isWXAppInstalled()) {
            Toast.makeText(BindAccountActivity.this, "未安装微信客户端，请先下载",
                    Toast.LENGTH_LONG).show();
            return;
        }
        SPUtils.getInstance(BindAccountActivity.this).put(Constant.USER_WX_TYPE,1);
        uuid = UUID.randomUUID().toString();
        final SendAuth.Req req = new SendAuth.Req();
        //snsapi_base属于基础接口，若应用已拥有其它scope权限，则默认拥有snsapi_base的权限。
        // 使用snsapi_base可以让移动端网页授权绕过跳转授权登录页请求用户授权的动作，直接跳转第三方网页带上授权临时票据（code），
        // 但会使得用户已授权作用域（scope）仅为snsapi_base，从而导致无法获取到需要用户授权才允许获得的数据和基础功能
        req.scope = "snsapi_userinfo";
        req.state = uuid;
        LogUtils.e(TAG, "wxLogin: " + uuid);
        iwxapi.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 弹出2级密码输入框
     *
     * @param
     */
    public void showTwoPassWord(int type) {
        final TwoPassWordDialog twoPassWordDialog = new TwoPassWordDialog(BindAccountActivity.this);
        twoPassWordDialog.show();
        final TextView tvTitle = twoPassWordDialog.findViewById(R.id.tv_title);
        TextView tvCancel = twoPassWordDialog.findViewById(R.id.tv_cancel);
        final VerificationCodeEditText editPwd = twoPassWordDialog.findViewById(R.id.edit_pwd);
//            editPwd.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        //输入框监听事件
        editPwd.setOnVerificationCodeChangedListener(new VerificationAction.OnVerificationCodeChangedListener() {
            @Override
            public void onVerCodeChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void onInputCompleted(CharSequence s) {
                //输入完成请求接口
                String code = s.toString();
                passWord=s.toString();

                if(type==1){//微信
                    if(!TextUtils.isEmpty(weChatName)){
                        new DeleteDialog(BindAccountActivity.this, R.style.dialogphone, "您确定覆盖之前绑定的微信吗?", new DeleteDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                if (confirm) {
                                    dialog.dismiss();
                                    wxLogin();
                                }
                            }
                        }).show();
                    }else{
                        wxLogin();
                    }

                }else if(type==2){//支付宝
                    showBindAliPay();
                }

                if (KeybordS.isSoftShowing(BindAccountActivity.this)) {
                    KeybordS.closeKeybord(editPwd, BindAccountActivity.this);
                } else {

                }
                twoPassWordDialog.dismiss();
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPassWordDialog.dismiss();
            }
        });
    }
}
