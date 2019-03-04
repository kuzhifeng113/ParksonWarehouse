package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.woyun.warehouse.R;

/**
 **
 *  设置2级密码
 */
public class TwoPassWordDialog extends Dialog  {
	private static final String TAG = "TwoPassWordDialog";
	private Context mContext;

	//	CirclePresenter presenter,
	public TwoPassWordDialog(Context context) {
		super(context, R.style.dialogphone);
		mContext = context;

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_set_two_pwd);
//		initWindowParams();

	}


}
