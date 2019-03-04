package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.woyun.warehouse.R;

/**
 * 退款申请中
 */
public class AgentRefundDialog extends Dialog implements View.OnClickListener{
    private ImageView img_close;
    private Button btn_agent_buy;
    private TextView tv_money,tv_type;
    private Context mContext;
    private OnCloseListener listener;
    private String money;
    private int withdrawType;

    public AgentRefundDialog(Context context, int themeResId, OnCloseListener listener,String money,int withdrawType) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
        this.money=money;
        this.withdrawType=withdrawType;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_refund_agent);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView(){
        tv_money=findViewById(R.id.tv_money);
        tv_type=findViewById(R.id.tv_type);
        img_close = (ImageView) findViewById(R.id.img_close);
        img_close.setOnClickListener(this);
        btn_agent_buy = (Button) findViewById(R.id.btn_agent_buy);
        btn_agent_buy.setOnClickListener(this);
        tv_money.setText(money+"元");
        if(withdrawType==1){
            tv_type.setText("微信");
        }else{
            tv_type.setText("支付宝");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_close:
                if(listener != null){
                    listener.onClick(this, false,tv_money,tv_type);
                }
                this.dismiss();
                break;
            case R.id.btn_agent_buy:
                if(listener != null){
                    listener.onClick(this, true,tv_money,tv_type);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm, TextView tvMoney,TextView tvType);
    }

}