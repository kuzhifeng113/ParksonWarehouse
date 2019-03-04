package com.woyun.warehouse.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.woyun.warehouse.R;

public class DeleteDialog extends Dialog implements View.OnClickListener{

    private Button submitTxt;
    private Button cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;


    public DeleteDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public DeleteDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public DeleteDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    public DeleteDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected DeleteDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete_cart);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        TextView textView=findViewById(R.id.tv_centent);
        submitTxt = (Button) findViewById(R.id.btn_confirm);
        submitTxt.setOnClickListener(this);
        cancelTxt = (Button) findViewById(R.id.btn_cancel);
        textView.setText(content);
        cancelTxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.btn_confirm:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }


    //    public void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View layout = inflater.inflate(R.layout.popup_version_update, null);//获取自定义布局
//        builder.setView(layout);
//
//        TextView tvContexnt=layout.findViewById(R.id.tv_content);
//        Button btn_cancel = (Button) layout.findViewById(R.id.btn_cancel);
//        Button btn_confirm = (Button) layout.findViewById(R.id.btn_confirm);
//        tvContexnt.setText("1.升级版本");
//
//        final AlertDialog dlg = builder.create();
//        dlg.show();
//
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                dlg.dismiss();
//            }
//        });
//
//        btn_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                Toast.makeText(getApplication(), "btn_confirm", Toast.LENGTH_SHORT).show();
//                dlg.dismiss();
//            }
//        });
}