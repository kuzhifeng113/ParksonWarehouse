package com.woyun.warehouse.my.activity;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.MyWebViewActivity;
import com.woyun.warehouse.bean.AgentCenterBean;
import com.woyun.warehouse.bean.RealNameBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.AgentRefundDialog;
import com.woyun.warehouse.view.RealNameIngDialog;
import com.woyun.warehouse.view.RealNameNoDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 * 代理中心
 */
public class AgentCenterActivity extends BaseActivity {
    private static final String TAG = "AgentCenterActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_agent_money)
    TextView tvAgentMoney;
    @BindView(R.id.ll_yu_er_mx)
    LinearLayout llYuErMx;
    @BindView(R.id.tv_current_month)
    TextView tvCurrentMonth;
    @BindView(R.id.tv_upton_month)
    TextView tvUptonMonth;
    @BindView(R.id.btn_ti_xian)
    TextView btnTiXian;
    @BindView(R.id.img_agent_rules)
    ImageView imgAgentRules;
    @BindView(R.id.img_agent_goumai)
    ImageView imgAgentGoumai;
    @BindView(R.id.img_agent_register)
    ImageView imgAgentRegister;
    @BindView(R.id.tv_team_num)
    TextView tvTeamNum;
    @BindView(R.id.zeroLevelNum)
    TextView zeroLevelNum;
    @BindView(R.id.oneLevelNum)
    TextView oneLevelNum;
    @BindView(R.id.twoLevelNum)
    TextView twoLevelNum;
    @BindView(R.id.totalReward)
    TextView totalReward;
    @BindView(R.id.zeroLevelReward)
    TextView zeroLevelReward;
    @BindView(R.id.oneLevelReward)
    TextView oneLevelReward;
    @BindView(R.id.twoLevelReward)
    TextView twoLevelReward;
    @BindView(R.id.shoppingReward)
    TextView shoppingReward;
    @BindView(R.id.img_agent_out)
    ImageView imgAgentOut;
    @BindView(R.id.img_qr_code)
    ImageView imgQrCode;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.tv_wx_account)
    TextView tvWxAccount;
    @BindView(R.id.btn_copy_account)
    Button btnCopyAccount;

    private boolean agentOut;//是否申请退出代理
    private boolean withdrawStatus;//是否有提现
    private int withdrawType;//提现类型
    private String fee;//税率
    private String withdrawMoney;
    private String downUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_center);
        ButterKnife.bind(this);
//        mImmersionBar.titleBar(toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);//禁用工具栏title


        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData(loginUserId);
    }

    /**
     * 获取数据
     */
    private void initData(String userId) {
        ModelLoading.getInstance(AgentCenterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.agentPrefix(AgentCenterActivity.this, params, TAG, ReqConstance.I_AGENT_INFO, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(AgentCenterActivity.this).closeLoading();
                    tokenTimeLimit(AgentCenterActivity.this, code);
                    if (code == 0) {
                        try {
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            Gson gson = new Gson();
                            AgentCenterBean centerBean = gson.fromJson(object.toString(), AgentCenterBean.class);
                            agentOut = centerBean.isAgentOut();
                            withdrawStatus = centerBean.isWithdrawStatus();
                            if (withdrawStatus) {
                                withdrawMoney = centerBean.getWithdrawMoney();
                                withdrawType = centerBean.getWithdrawType();
                            }
                            tvAgentMoney.setText(centerBean.getBcMoney());
                            tvCurrentMonth.setText(centerBean.getCurrMonthReward());
                            tvUptonMonth.setText(centerBean.getLastMonthReward());

                            zeroLevelNum.setText(centerBean.getAgentNum() + "人");
                            oneLevelNum.setText(centerBean.getZeroLevelNum() + "人");
                            twoLevelNum.setText(centerBean.getTeamNum() + "人");

                            totalReward.setText(centerBean.getTotalReward());
                            zeroLevelReward.setText(String.valueOf(centerBean.getZeroLevelReward()));//直属会员收益
                            oneLevelReward.setText(String.valueOf(centerBean.getTeamReward()));//团队收益
                            twoLevelReward.setText(String.valueOf(centerBean.getShoppingReward()));//购物
                            fee = centerBean.getFee();
                            downUrl = centerBean.getEwm();
                            Glide.with(AgentCenterActivity.this).load(centerBean.getEwm()).into(imgQrCode);
                            tvWxAccount.setText(centerBean.getWxh());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(AgentCenterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(AgentCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentCenterActivity.this).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.mainColor);
        mImmersionBar.statusBarDarkFont(true).init();

    }

    @OnClick({R.id.ll_yu_er_mx, R.id.btn_ti_xian, R.id.img_agent_rules, R.id.img_agent_goumai, R.id.img_agent_register, R.id.img_agent_out,R.id.btn_save, R.id.btn_copy_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_yu_er_mx://余额明细
                Intent mingxi = new Intent(AgentCenterActivity.this, CangCoinDetailActivity.class);
                mingxi.putExtra("hb_type", 2);
                startActivity(mingxi);
                break;
            case R.id.btn_ti_xian://提现
                //1.是否实名制
                checkRealNameStatus();

//                //2.是否之前有过提现申请
//                if(withdrawStatus){
//                    new AgentRefundDialog(AgentCenterActivity.this, R.style.dialogphone, new AgentRefundDialog.OnCloseListener(){
//                        @Override
//                        public void onClick(Dialog dialog, boolean confirm,TextView tvMoney,TextView tvType) {
//                            if(confirm){
//                                dialog.dismiss();
//                            }
//                        }
//                    },withdrawMoney,withdrawType).show();
//
//                }else{
//                    Intent intent = new Intent(AgentCenterActivity.this, WithDrawActivity.class);
//                    intent.putExtra("tixian_money",tvAgentMoney.getText().toString().trim());
//                    intent.putExtra("fee",fee);
//                    startActivity(intent);
//                }
                break;
            case R.id.img_agent_rules://代理规则

                Intent rules = new Intent(AgentCenterActivity.this, MyWebViewActivity.class);
                rules.putExtra("web_url", Constant.WEB_AGENT);
                startActivity(rules);

                break;
            case R.id.img_agent_goumai://代理购买--会员
                Intent buy = new Intent(AgentCenterActivity.this, AgentBuyActivity.class);
                startActivity(buy);
                break;
            case R.id.img_agent_register://协助注册
//                Intent registher = new Intent(AgentCenterActivity.this, AgentHelpRegisterActivity.class);
//                startActivity(registher);
                break;
            case R.id.img_agent_out://退出代理
                if (agentOut) {
                    ToastUtils.getInstanc(AgentCenterActivity.this).showToast("已申请过退出代理,请等待审核~");
                } else {
                    Intent out = new Intent(AgentCenterActivity.this, AgentOutActivity.class);
                    startActivity(out);
                }
                break;
            case R.id.btn_save://保存
                if (!TextUtils.isEmpty(downUrl)) {
                    String path = Environment.getExternalStorageDirectory() + "/BSC/erCode.jpg";
                    Log.e(TAG, "onViewClicked:sd卡== " + path);
                    File file = new File(path);
                    if (!file.exists()) {
                        Log.e(TAG, "onViewClicked: 文件不存在");
                        ModelLoading.getInstance(AgentCenterActivity.this).showLoading("", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = dowmPic(downUrl);//下载
                                onSaveBitmap(bitmap, AgentCenterActivity.this);//保存到本地
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ModelLoading.getInstance(AgentCenterActivity.this).closeLoading();
                                        ToastUtils.getInstanc(AgentCenterActivity.this).showToast("保存成功");
                                    }
                                });
                            }
                        }).start();
                    } else {
                        ToastUtils.getInstanc(AgentCenterActivity.this).showToast("已保存");
                    }
                } else {
                    ToastUtils.getInstanc(AgentCenterActivity.this).showToast("二维码链接失效");
                }
                break;
            case R.id.btn_copy_account://复制
                ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvWxAccount.getText().toString().trim());
                ToastUtils.getInstanc(AgentCenterActivity.this).showToast("复制成功");
                break;
        }
    }

    /**
     * 查询实名认证状态
     */
    private void checkRealNameStatus() {
        ModelLoading.getInstance(AgentCenterActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);

            RequestInterface.sysPrefix(AgentCenterActivity.this, params, TAG, ReqConstance.I_REAL_NAME_CHECK, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    ModelLoading.getInstance(AgentCenterActivity.this).closeLoading();
                    Log.e(TAG, "requestSuccess: " + responseData.length());
                    if (responseCode == 0) {
                        //有状态返回证明 提交过审核
                        if (responseData.length() > 0) {
                            try {
                                Gson gson = new Gson();
                                JSONObject jsonObject = (JSONObject) responseData.get(0);
                                RealNameBean realNameBean = gson.fromJson(jsonObject.toString(), RealNameBean.class);
                                if (realNameBean.getStatus() == 0) {//审核中
                                    new RealNameIngDialog(AgentCenterActivity.this, R.style.dialogphone, new RealNameIngDialog.OnCloseListener() {
                                        @Override
                                        public void onClick(Dialog dialog, boolean confirm) {
                                            if (confirm) {
                                                dialog.dismiss();
                                            }
                                        }
                                    }).show();

                                } else if (realNameBean.getStatus() == 1) {//审核通过
                                    //2.是否之前有过提现申请
                                    if (withdrawStatus) {
                                        new AgentRefundDialog(AgentCenterActivity.this, R.style.dialogphone, new AgentRefundDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm, TextView tvMoney, TextView tvType) {
                                                if (confirm) {
                                                    dialog.dismiss();
                                                }
                                            }
                                        }, withdrawMoney, withdrawType).show();

                                    } else {
                                        Intent intent = new Intent(AgentCenterActivity.this, WithDrawActivity.class);
                                        intent.putExtra("tixian_money", tvAgentMoney.getText().toString().trim());
                                        intent.putExtra("fee", fee);
                                        startActivity(intent);
                                    }
                                } else {//审核不通过
                                    Intent intent = new Intent(AgentCenterActivity.this, RealNameActivity.class);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {//未提交过实名审核
                            new RealNameNoDialog(AgentCenterActivity.this, R.style.dialogphone, new RealNameNoDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (confirm) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(AgentCenterActivity.this, RealNameDoActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }).show();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(AgentCenterActivity.this).closeLoading();
                    ToastUtils.getInstanc(AgentCenterActivity.this).showToast(responseMessage);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Android保存图片到系统图库
     *
     * @param mBitmap
     * @param context
     */
    private void onSaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/Boohee/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "BSC");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "erCode.jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // 第二步：其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
////   /storage/emulated/0/Boohee/1493711988333.jpg
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 第三步：最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    /**
     * 下载图片okhttp
     *
     * @param url
     * @return
     */
    public Bitmap dowmPic(String url) {
        //获取okHttp对象get请求
        try {
            OkHttpClient client = new OkHttpClient();
            //获取请求对象
            Request request = new Request.Builder().url(url).build();
            //获取响应体
            ResponseBody body = client.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
