package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CustomerBean;
import com.woyun.warehouse.bean.VoteHomeBean;

import java.text.NumberFormat;
import java.util.List;

/**
 *
 *  客服
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {
    private static final String TAG = "PaseVoteAdapter";
    private Context context;
    private List<CustomerBean> dataList;
    private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public CustomerAdapter(Context context, List<CustomerBean> dataList) {
        this.dataList = dataList;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Log.e(TAG, "onBindViewHolder: "+position );
        CustomerBean voteBean = dataList.get(position);
        Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.img_qr_code);
        holder.tv_wx_account.setText(voteBean.getWxh());

        if(onButtonClickListener!=null){
            holder.btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(holder.btn_save,position);
                }
            });

            holder.btn_copy_account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(holder.btn_copy_account,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_qr_code;
        TextView tv_wx_account;
        Button btn_save,btn_copy_account;


      public  MyViewHolder(View itemView) {
            super(itemView);
          img_qr_code=itemView.findViewById(R.id.img_qr_code);
          tv_wx_account = (TextView) itemView.findViewById(R.id.tv_wx_account);

          btn_save= (Button) itemView.findViewById(R.id.btn_save);
          btn_copy_account= (Button) itemView.findViewById(R.id.btn_copy_account);

        }
    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
