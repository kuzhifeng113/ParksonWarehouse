package com.woyun.warehouse.mall.adapter;

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
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.bean.VoteHomeBean;

import java.util.List;

/**
 *
 *  商城 type=2 子适配
 */
public class MallTypeTwoAdapter extends RecyclerView.Adapter<MallTypeTwoAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeBean.PackListBean.GoodsListBean> dataList;
    private OnTypeItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;
    private int showNum;//限制显示的条数

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MallTypeTwoAdapter(Context context, List<MallHomeBean.PackListBean.GoodsListBean> dataList,int showNum) {
        this.dataList = dataList;
        this.context=context;
        this.showNum=showNum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mall_two_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MallHomeBean.PackListBean.GoodsListBean goodsListBean = dataList.get(position);
        Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img_goods);
        holder.tv_goods_name.setText(goodsListBean.getName());
        holder.tv_vip_price.setText("￥"+goodsListBean.getVipPrice());
        holder.tv_goods_price.setText("￥"+goodsListBean.getPrice());

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
//        return dataList.size();
        if(dataList.size()>showNum){
            return  showNum;
        }else{
            return dataList.size();
        }
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView round_img_goods;
        TextView tv_goods_name,tv_vip_price,tv_goods_price;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img_goods=itemView.findViewById(R.id.round_img_goods);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
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
