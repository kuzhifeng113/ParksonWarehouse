package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.MallHomeBean;

import java.util.List;

/**
 *
 *  left 适配
 */
public class MallLeftAdapter extends RecyclerView.Adapter<MallLeftAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeBean.PackListBean.GoodsListBean> dataList;
    private boolean isVip;
    private OnTypeItemClickListener onItemClickListener;


    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MallLeftAdapter(Context context, List<MallHomeBean.PackListBean.GoodsListBean> dataList,boolean isVip) {
        this.dataList = dataList;
        this.context=context;
        this.isVip=isVip;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mall_left, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MallHomeBean.PackListBean.GoodsListBean goodsListBean = dataList.get(position);

        holder.tv_goods_name.setText(goodsListBean.getName());
        holder.tv_goods_title.setText(goodsListBean.getTitle());
//        holder.tv_vip_price.setText("￥"+goodsListBean.getVipPrice());
//        holder.tv_goods_price.setText("￥"+goodsListBean.getPrice());
//        if(isVip){
            holder.tv_vip_price.setText("￥"+goodsListBean.getVipPrice());
            holder.tv_goods_price.setText("￥"+goodsListBean.getPrice());
//        }else{
//            holder.tv_vip_price.setText("￥"+goodsListBean.getPrice());
//            holder.tv_goods_price.setText("￥"+goodsListBean.getVipPrice());
//        }

//        holder.tv_goods_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

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
        ImageView round_img_goods;
        TextView tv_goods_name,tv_goods_title,tv_vip_price,tv_goods_price;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img_goods=itemView.findViewById(R.id.round_img_goods);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
          tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
          tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
          tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);

        }
    }

    public  interface OnTypeItemClickListener {
        void onItemClick(int position);

    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
