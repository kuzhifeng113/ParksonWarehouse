package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.ZuanQianBean;

import java.util.List;

/**
 *
 *  Vip 礼包适配
 */
public class VipGiftAdapter extends RecyclerView.Adapter<VipGiftAdapter.MyViewHolder> {
    private Context context;
    private List<ZuanQianBean.VipGiftBean> dataList;

    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VipGiftAdapter(Context context, List<ZuanQianBean.VipGiftBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vip_libao, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ZuanQianBean.VipGiftBean vipGiftBean = dataList.get(position);
        holder.tv_name.setText(vipGiftBean.getName());
        Glide.with(context).load(vipGiftBean.getImage()).placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img);
        holder.tv_title.setText(vipGiftBean.getTitle());
        holder.tv_vip_price.setText("￥"+vipGiftBean.getVipPrice());
        holder.tv_price.setText("原价:"+vipGiftBean.getPrice());

        holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
        }

        holder.bt_goumai.setOnClickListener(v -> onButtonClickListener.onButton(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView round_img;
        TextView tv_name,tv_title,tv_vip_price,tv_price;
        Button bt_goumai;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img=itemView.findViewById(R.id.round_img);
          tv_name = itemView.findViewById(R.id.tv_name);
          tv_title = itemView.findViewById(R.id.tv_title);
          tv_vip_price = itemView.findViewById(R.id.tv_vip_price);
          tv_price = itemView.findViewById(R.id.tv_price);
          bt_goumai = itemView.findViewById(R.id.bt_goumai);
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position);
    }
    public interface  OnButtonClickListener{
        void onButton(int positon);
    }
}
