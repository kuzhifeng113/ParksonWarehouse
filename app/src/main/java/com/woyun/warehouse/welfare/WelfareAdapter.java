package com.woyun.warehouse.welfare;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.WelfateBean;
import com.woyun.warehouse.bean.ZuanQianBean;

import java.util.List;

/**
 *
 *  红包购物专区 适配
 */
public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.MyViewHolder> {
    private Context context;
    private List<WelfateBean.GoodsListBean> dataList;

    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WelfareAdapter(Context context, List<WelfateBean.GoodsListBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_welfare, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        WelfateBean.GoodsListBean vipGiftBean = dataList.get(position);
        holder.tv_name.setText(vipGiftBean.getName());
        holder.tv_energy.setText("能量X"+vipGiftBean.getPeopleNum());
        Glide.with(context).load(vipGiftBean.getImage()).placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img);
        holder.tv_title.setText("已售"+vipGiftBean.getSellNum()+"件  |  剩余"+vipGiftBean.getStock()+"件");
        holder.tv_vip_price.setText("￥"+vipGiftBean.getVipPrice());
        holder.tv_price.setText("市场价:"+vipGiftBean.getPrice());
        holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(vipGiftBean.isBuy()){//是否买过
            holder.bt_goumai.setBackgroundResource(R.drawable.shape_welfare_no_buy);
            holder.bt_goumai.setTextColor(Color.parseColor("#AFAFAF"));
            holder.bt_goumai.setText("已购买");
        }else{
            holder.bt_goumai.setBackgroundResource(R.drawable.shape_main_btn);
            holder.bt_goumai.setTextColor(Color.parseColor("#ffffff"));
            holder.bt_goumai.setText("立即购买");
        }

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
        TextView tv_name,tv_title,tv_vip_price,tv_price,tv_energy;
        Button bt_goumai;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img=itemView.findViewById(R.id.round_img);
          tv_name = itemView.findViewById(R.id.tv_name);
          tv_title = itemView.findViewById(R.id.tv_title);
          tv_vip_price = itemView.findViewById(R.id.tv_vip_price);
          tv_price = itemView.findViewById(R.id.tv_price);
          bt_goumai = itemView.findViewById(R.id.bt_goumai);
          tv_energy = itemView.findViewById(R.id.tv_energy);
        }
    }
    public  interface OnItemClickListener {
        void onItemClick(int position);
    }
    public interface  OnButtonClickListener{
        void onButton(int positon);
    }
}
