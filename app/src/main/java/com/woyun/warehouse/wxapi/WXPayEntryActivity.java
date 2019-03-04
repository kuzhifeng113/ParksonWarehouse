package com.woyun.warehouse.wxapi;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.cart.activity.OrderXiaDanActivity;
import com.woyun.warehouse.my.activity.AgentCenterActivity;
import com.woyun.warehouse.my.activity.AgentOpenActivity;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.my.activity.VipCenterActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private static final String TAG = "WXPayEntryActivity";
    private IWXAPI api;
	private ModelLoading modelLoading;
	private boolean isEnable;//是否购买
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(WXPayEntryActivity.this);
		modelLoading=new ModelLoading(WXPayEntryActivity.this);
    	api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.e(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==0){
				ToastUtils.getInstanc(WXPayEntryActivity.this).showToast("支付成功");
				String orderNum= (String) SPUtils.getInstance(WXPayEntryActivity.this).get("order_key","");
				//订单支付
				if(!TextUtils.isEmpty(orderNum)){
					SPUtils.getInstance(WXPayEntryActivity.this).remove("order_key");
					Intent goDetail=new Intent(WXPayEntryActivity.this, OrderDetailActivity.class);
					goDetail.putExtra("tradeNo",orderNum);
					startActivity(goDetail);
					MyApplication.getInstance().closeActivity();
					return;
				}

				String payType= (String) SPUtils.getInstance(WXPayEntryActivity.this).get(Constant.PAY_TYPE,"");
				Log.e(TAG, "onResp:payType==== "+payType );
				if(!TextUtils.isEmpty(payType)){
					if(payType.equals("1")){//会员购买
						EventBus.getDefault().post(new SaveUserEvent(true));
						MyApplication.getInstance().closeActivity();
						return;
					}else if(payType.equals("2")){//代理购买支付
						EventBus.getDefault().post(new SaveUserEvent(true));
						 SPUtils.getInstance(WXPayEntryActivity.this).put(Constant.USER_IS_AGENT, true);
						MyApplication.getInstance().closeActivity();
					}
				}

			}else if(resp.errCode==-2){
				ToastUtils.getInstanc(WXPayEntryActivity.this).showToast("已取消");
				finish();
			}else {
				ToastUtils.getInstanc(WXPayEntryActivity.this).showToast("支付失败...");
				finish();
			}


//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage("微信支付结果："+resp.errCode);
//			builder.show();
		}
	}

	/**
	 *  查询我的信息
	 */
	private void checkUserInfo() {
		ModelLoading.getInstance(WXPayEntryActivity.this).showLoading("", true);
		try {
			JSONObject params = new JSONObject();
			params.put("userid", SPUtils.getInstance(WXPayEntryActivity.this).get(Constant.USER_ID,""));
			RequestInterface.userPrefix(WXPayEntryActivity.this, params, TAG, ReqConstance.I_GET_USER_INFO, 3, new HSRequestCallBackInterface() {
				@Override
				public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray jsonArray) {
					ModelLoading.getInstance(WXPayEntryActivity.this).closeLoading();
					if (responseCode == 0) {
						try {
							Gson gson = new Gson();
							JSONObject object = (JSONObject) jsonArray.get(0);
							UserInfoBean userInfoBean = gson.fromJson(object.toString(), UserInfoBean.class);
							SPUtils.getInstance(WXPayEntryActivity.this).put(Constant.USER_IS_VIP, userInfoBean.isIsVip());
							SPUtils.getInstance(WXPayEntryActivity.this).put(Constant.USER_IS_AGENT, userInfoBean.isIsAgent());
							Intent agent=new Intent(WXPayEntryActivity.this, AgentCenterActivity.class);
							startActivity(agent);
							MyApplication.getInstance().closeActivity();

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void requestError(String responseMessage, int responseCode) {
					ModelLoading.getInstance(WXPayEntryActivity.this).closeLoading();
					checkUserInfo();
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}