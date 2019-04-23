package com.woyun.warehouse.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.my.activity.OrderDetailActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;


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


}