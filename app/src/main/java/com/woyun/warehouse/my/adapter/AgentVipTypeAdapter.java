package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.AgentBuyVipBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.my.activity.InsertAddressActivity;

import java.util.List;

/**
 * 代理购买vip 适配
 */
public class AgentVipTypeAdapter extends RecyclerView.Adapter<AgentVipTypeAdapter.MyViewHolder> {
    private Context context;
    private List<AgentBuyVipBean.VipTypeListBean> dataList;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public AgentVipTypeAdapter(Context context, List<AgentBuyVipBean.VipTypeListBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agent_buy_vip, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final AgentBuyVipBean.VipTypeListBean voteBean = dataList.get(position);
        holder.tv_price.setText("￥"+voteBean.getPrice());
        holder.tv_num.setText(voteBean.getRemark()+"个");
        if(voteBean.isCheck()){
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.shape_agent_select));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.mainColor));
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.mainColor));
        }else{
            holder.itemView.setBackground(context.getResources().getDrawable(R.drawable.shape_agent_unselect));
            holder.tv_price.setTextColor(context.getResources().getColor(R.color.text_black));
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.text_content));
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
       RelativeLayout main;
        TextView tv_price, tv_num;

        public MyViewHolder(View itemView) {
            super(itemView);
            main = (RelativeLayout) itemView.findViewById(R.id.main);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnCheckClickListener {
        void checkGroup(int position, boolean isChecked);
    }

    public interface  OnButtonClickListener{
        void delete(int positon);
    }
}
