package com.woyun.warehouse.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;


/**
 * 请求加载框
 *
 */
public class ModelLoading extends Dialog {
    private  Activity activity;
    private TextView tv_loading_hint;
    private final String hintDefault = "";
    private String hint;
    private static ModelLoading  instance;

    public ModelLoading(Activity activity) {
        super(activity, R.style.ModelLoading);
        this.activity = activity;
    }

    public synchronized static ModelLoading getInstance(Activity activity) {
        if (null == instance) {
            instance = new ModelLoading(activity);
        }
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = activity.getLayoutInflater().inflate(R.layout.progress_model,
                null);
        tv_loading_hint = (TextView) v.findViewById(R.id.tv_loading_hint);
        tv_loading_hint.setText(hint);
        v.getBackground().setAlpha(210);
        setCanceledOnTouchOutside(false);
        setContentView(v);
    }

    @Override
    protected void onStart() {
        tv_loading_hint.setText(hint);
        super.onStart();
    }

    public void showLoading(boolean backCancel) {
        showLoading(hintDefault, backCancel);
    }

    /**
     *
     * @param hint  显示文字
     * @param backCancel  是否可以取消
     */
    public void showLoading(String hint, boolean backCancel) {
        showLoading(hint,backCancel,null);
    }

    public void showLoading(String hint, boolean backCancel, WindowManager.LayoutParams params) {
        if (hint == null || hint.equals("")) {
            hint = hintDefault;
        }
        this.hint = hint;
        if (tv_loading_hint != null) {
            tv_loading_hint.setText(hint);
        }
        if (!activity.isFinishing() && !isShowing()) {
            setCancelable(backCancel);
            if (params != null) {
                getWindow().setAttributes(params);
            }
            show();
        }
    }


    public void closeLoading() {
        if (!activity.isFinishing() && isShowing()) {
            dismiss();
        }
    }


    public boolean isDead()
    {
        return activity.isFinishing();
    }

}
