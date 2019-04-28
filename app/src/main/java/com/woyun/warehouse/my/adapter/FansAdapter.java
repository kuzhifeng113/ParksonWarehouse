package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CangCoinBean;
import com.woyun.warehouse.bean.FansBean;
import com.woyun.warehouse.utils.DateUtils;

import java.util.List;

/**
 *
 * 粉丝适配
 */
public class FansAdapter extends RecyclerView.Adapter<FansAdapter.MyViewHolder> {
    private Context context;
    private List<FansBean> dataList;



    private OnButtonClickListener onButtonClickListener;


    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public FansAdapter(Context context, List<FansBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_fan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        FansBean voteBean = dataList.get(position);
        Glide.with(context).load(voteBean.getAvatar()).placeholder(R.mipmap.ic_head_default).error(R.mipmap.ic_head_default).into(holder.img_head);
        holder.tv_time.setText(DateUtils.longToStringTime(voteBean.getRegisterDate(),DateUtils.MD_HM));
        holder.tv_name.setText(voteBean.getNickname());
        holder.tv_phone.setText(voteBean.getMobile());

        if( onButtonClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_head;
        TextView  tv_name,tv_time,tv_phone;
        Button btn_call;


      public  MyViewHolder(View itemView) {
            super(itemView);
          img_head=itemView.findViewById(R.id.img_head);
          tv_name =  itemView.findViewById(R.id.tv_name);
          tv_phone =  itemView.findViewById(R.id.tv_phone);
          tv_time = itemView.findViewById(R.id.tv_time);
          btn_call =  itemView.findViewById(R.id.btn_call);
        }
    }



    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
