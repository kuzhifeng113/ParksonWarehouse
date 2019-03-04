package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.woyun.warehouse.R;

/**
 * 提现时实名认证审核中弹窗
 */
public class RealNameIngDialog extends Dialog implements View.OnClickListener{

    private ImageView img_close;
    private Button btn_agent_buy;
    private Context mContext;
    private OnCloseListener listener;


    public RealNameIngDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_realing_name);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView(){
        img_close = (ImageView) findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
        btn_agent_buy = (Button) findViewById(R.id.btn_agent_buy);
        btn_agent_buy.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.btn_agent_buy:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }

}