package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.woyun.warehouse.R;

/**
 **
 *  购买Vip 弹窗
 */
public class BuyVipDialog extends Dialog  {
	private static final String TAG = "TwoPassWordDialog";
	private Context mContext;

	//	CirclePresenter presenter,
	public BuyVipDialog(Context context) {
		super(context, R.style.dialogphone);
		mContext = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_buy_vip);
		initWindowParams();

	}

	private void initWindowParams() {
		Window dialogWindow = getWindow();
		// 获取屏幕宽、高用
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

//		lp.width = (int) (display.getWidth() * 0.65); // 宽度设置为屏幕的0.65

		dialogWindow.setGravity(Gravity.BOTTOM);
		dialogWindow.setAttributes(lp);

	}

}
