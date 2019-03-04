package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.bean.OrderDetailBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.my.activity.AgentBuyActivity;

import java.util.List;

/**
 * 订单详情 适配
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {
    private Context context;
    private List<OrderDetailBean.BillDetailListBean> dataList;

    public OrderDetailAdapter(Context context, List<OrderDetailBean.BillDetailListBean> dataList) {
        this.dataList = dataList;
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final OrderDetailBean.BillDetailListBean shoppingCartBean = dataList.get(position);
        if(!TextUtils.isEmpty(shoppingCartBean.getSkuImage())){
            Glide.with(context).load(shoppingCartBean.getSkuImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        }else{
            Glide.with(context).load(shoppingCartBean.getGoodsImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        }
        holder.tv_goods_name.setText(shoppingCartBean.getGoodsName());
        holder.tv_price.setText("￥" + String.valueOf(shoppingCartBean.getUnitPrice()));
        holder.tv_goods_attr.setText(shoppingCartBean.getMemo());
        holder.tv_num.setText("X " + shoppingCartBean.getSkuNum());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail=new Intent(context, GoodsDetailActivity.class);
                detail.putExtra("goods_id",dataList.get(position).getGoodsId());
                detail.putExtra("from_id",3);
                context.startActivity(detail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_show_pic;
        TextView tv_goods_name, tv_goods_attr, tv_price, tv_num;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_show_pic = itemView.findViewById(R.id.round_img);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_goods_attr = (TextView) itemView.findViewById(R.id.tv_title);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);

        }
    }

}
