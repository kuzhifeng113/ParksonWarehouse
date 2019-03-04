package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.woyun.warehouse.bean.MoneyBean;
import com.woyun.warehouse.bean.VoteHomeBean;

import java.text.NumberFormat;
import java.util.List;

/**
 * money 水平recycleview 适配
 */
public class MoneyHorizontalAdapter extends RecyclerView.Adapter<MoneyHorizontalAdapter.MyViewHolder> {
    private static final String TAG = "VoteHorizontalAdapter";
    private Context context;

//    public void setDataList(List<MoneyBean> dataList) {
//        this.dataList = dataList;
//        notifyDataSetChanged();
//    }

    private List<MoneyBean> dataList;
    private OnItemClickListener onItemClickListener;



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MoneyHorizontalAdapter(Context context, List<MoneyBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_money_vip, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MoneyBean voteBean = dataList.get(position);

        if(voteBean.isCheck()){//选中
            Glide.with(context).load(voteBean.getIcon()).placeholder(R.mipmap.icon_default_zq).error(R.mipmap.vip_small).into(holder.img_small);
            holder.tv_small_title.setText(voteBean.getName());
            holder.tv_small_title.setTextColor(Color.parseColor("#373A42"));
        }else{
            Glide.with(context).load(voteBean.getUnicon()).placeholder(R.mipmap.icon_default_zq).error(R.mipmap.vip_small).into(holder.img_small);
            holder.tv_small_title.setText(voteBean.getName());
            holder.tv_small_title.setTextColor(Color.parseColor("#AFAFAF"));
        }

        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_small;
        TextView tv_small_title;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_small = itemView.findViewById(R.id.img_small);
            tv_small_title = (TextView) itemView.findViewById(R.id.tv_small_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view,int position);

    }


}
