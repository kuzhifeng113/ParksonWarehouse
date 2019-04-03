package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.woyun.warehouse.R;

/**
 **
 *  忘记二级级密码
 */
public class TwoPassWordForgetDialog extends Dialog  {
	private static final String TAG = "TwoPassWordForgetDialog";
	private Context mContext;

	//	CirclePresenter presenter,
	public TwoPassWordForgetDialog(Context context) {
		super(context, R.style.dialogphone);
		mContext = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_forget_two_pwd);
//		initWindowParams();

	}


}
