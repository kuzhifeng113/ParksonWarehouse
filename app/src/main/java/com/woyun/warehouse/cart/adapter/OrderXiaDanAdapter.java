package com.woyun.warehouse.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CartShopBean;

import java.util.List;

/**
 * 下单 订单适配
 */
public class OrderXiaDanAdapter extends RecyclerView.Adapter<OrderXiaDanAdapter.MyViewHolder> {
    private Context context;
    private List<CartShopBean.CartListBean> dataList;

    public OrderXiaDanAdapter(Context context, List<CartShopBean.CartListBean> dataList) {
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
        final CartShopBean.CartListBean shoppingCartBean = dataList.get(position);
        Glide.with(context).load(shoppingCartBean.getSkuImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        holder.tv_goods_name.setText(shoppingCartBean.getGoodsName());
        holder.tv_price.setText("￥" + String.valueOf(shoppingCartBean.getUnitPrice()));
        holder.tv_goods_attr.setText(shoppingCartBean.getMemo());
        holder.tv_num.setText("X " + shoppingCartBean.getSkuNum());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_show_pic, img_sub, img_add;
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
