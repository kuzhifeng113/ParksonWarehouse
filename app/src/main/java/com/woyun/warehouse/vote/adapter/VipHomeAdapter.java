package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.VoteHomeBean;
import com.woyun.warehouse.bean.ZuanQianBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;

import java.util.List;

/**
 *
 *  Vip 2.0 权益
 */
public class VipHomeAdapter extends RecyclerView.Adapter<VipHomeAdapter.MyViewHolder> {
    private Context context;
    private List<ZuanQianBean.VipListBean> dataList;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public VipHomeAdapter(Context context, List<ZuanQianBean.VipListBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vip_zuan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ZuanQianBean.VipListBean voteBean = dataList.get(position);
        holder.tv_name.setText(voteBean.getName());
        Glide.with(context).load(voteBean.getIcon()).placeholder(R.mipmap.icon_default_zq).error(R.mipmap.icon_default_zq).into(holder.img_icon);

        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_icon;
        TextView tv_name;

      public  MyViewHolder(View itemView) {
            super(itemView);
          img_icon=itemView.findViewById(R.id.img_icon);
          tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position);

    }
}
