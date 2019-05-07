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
import com.woyun.warehouse.bean.CategoryGoodsBeanTwo;
import com.woyun.warehouse.bean.MallHomeFourBean;

import java.util.List;

/**
 *
 *  首页分类商品子adapter 适配
 */
public class MainHotDetailAdapter extends RecyclerView.Adapter<MainHotDetailAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeFourBean.GoodsCategoryListBean.GoodsListBeanX> dataList;
    private OnTypeItemClickListener onItemClickListener;


    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MainHotDetailAdapter(Context context, List<MallHomeFourBean.GoodsCategoryListBean.GoodsListBeanX> dataList) {
        this.dataList = dataList;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main_hot_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MallHomeFourBean.GoodsCategoryListBean.GoodsListBeanX goodsListBean = dataList.get(position);
        Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default)
                .into(holder.round_img_goods);
        holder.tv_goods_name.setText(goodsListBean.getName());
        holder.tv_goods_title.setText(goodsListBean.getTitle());
        holder.tv_vip_price.setText(goodsListBean.getVipPrice());
        holder.tv_goods_price.setText("市场价:"+goodsListBean.getPrice());
        holder.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.tv_vip_back.setText("会员返"+goodsListBean.getBkCoin());
        holder.tv_vip_back.setText("会员价");
        holder.tv_sales_volume.setText("已售："+goodsListBean.getSellNum());
        holder.tv_supplier.setText(goodsListBean.getSupplier());

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
         TextView tv_goods_name,tv_goods_title,tv_sales_volume,tv_supplier,tv_vip_back,tv_vip_price,tv_goods_price;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img_goods=itemView.findViewById(R.id.round_img_goods);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
          tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
          tv_sales_volume = (TextView) itemView.findViewById(R.id.tv_sales_volume);
          tv_supplier = (TextView) itemView.findViewById(R.id.tv_supplier);
          tv_vip_back = (TextView) itemView.findViewById(R.id.tv_vip_back);
          tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
          tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);

        }
    }

    public  interface OnTypeItemClickListener {
        void onItemClick(int position);

    }

}
