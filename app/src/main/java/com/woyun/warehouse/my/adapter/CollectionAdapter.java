package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.bean.CollectionBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.utils.SPUtils;

import java.util.List;

/**
 * 我的收藏适配
 */
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyViewHolder> {
    private Context context;
    private boolean isVip;
    private List<CollectionBean.ContentBean> dataList;
    private boolean isShow = false;//是否显示编辑/完成
    private CheckInterface checkInterface;

    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public CollectionAdapter(Context context, List<CollectionBean.ContentBean> dataList) {
        boolean isVip = (boolean) SPUtils.getInstance(context).get(Constant.USER_IS_VIP, false);
        this.dataList = dataList;
        this.context = context;
    }

    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_collection, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CollectionBean.ContentBean collectionBean = dataList.get(position);
        Glide.with(context).load(collectionBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        holder.tv_goods_name.setText(collectionBean.getName());
        holder.tv_collection_num.setText(collectionBean.getFavoriteNum() + "人收藏");
//        holder.tv_vip_back.setText("会员返"+String.valueOf(collectionBean.getBkCoin()));
        holder.tv_vip_back.setText("会员价");
        holder.tv_vip_price.setText(String.valueOf(collectionBean.getVipPrice()));
        holder.tv_goods_price.setText("原价:" + String.valueOf(collectionBean.getPrice()));
        holder.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if (position == dataList.size() - 1) {
            holder.view_line.setVisibility(View.GONE);
        } else {
            holder.view_line.setVisibility(View.VISIBLE);
        }

        if (isShow) {//编辑状态
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        boolean choosed = collectionBean.isCheck();
        if (choosed) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }


        //单选框按钮
        holder.checkBox.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        collectionBean.setCheck(((CheckBox) v).isChecked());
                        if (checkInterface != null) {
                            checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                        }
                    }
                }
        );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent goodsDetail = new Intent(context, GoodsDetailActivity.class);
                Intent goodsDetail = new Intent(context, GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", dataList.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                context.startActivity(goodsDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView iv_show_pic;
        TextView tv_goods_name, tv_collection_num, tv_vip_price, tv_goods_price, tv_vip_back;
        View view_line;

        public MyViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            iv_show_pic = itemView.findViewById(R.id.iv_show_pic);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_collection_num = (TextView) itemView.findViewById(R.id.tv_collection_num);
            tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_vip_back = (TextView) itemView.findViewById(R.id.tv_vip_back);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

}
