package com.woyun.warehouse.mall.adapter;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.utils.ToastUtils;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 各地好货 适配
 */
public class MallGoodGoodsAdapter extends RecyclerView.Adapter<MallGoodGoodsAdapter.MyViewHolder> {
    private static final String TAG = "MallGoodGoodsAdapter";
    private Context context;
    private List<GoodCategoryBean.PageBean.ContentBean> listData;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;
    private OnSmallClickListener onSmallClickListener;
    private BannerViewPageAdapter bannerViewPageAdapter;
    private int type;//12 各地好货   13 必选名品

    public void setOnSmallClickListener(OnSmallClickListener onSmallClickListener) {
        this.onSmallClickListener = onSmallClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public MallGoodGoodsAdapter(Context context, List<GoodCategoryBean.PageBean.ContentBean> listData,int type) {
        this.listData = listData;
        this.context = context;
        this.type=type;

    }

    public void setData(List<GoodCategoryBean.PageBean.ContentBean> listData){
        this.listData=listData;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mall_good_goods, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //设置颜色---根据type
        if(type==12){
            holder.tv_y.setTextColor(Color.parseColor("#B051EC"));
            holder.tv_vip_price.setTextColor(Color.parseColor("#B051EC"));
            holder.tv_youfei.setTextColor(Color.parseColor("#B051EC"));
            holder.tv_sales_volume.setTextColor(Color.parseColor("#B051EC"));
            holder.tv_kucun.setTextColor(Color.parseColor("#B051EC"));
            holder.btn_buy.setBackgroundResource(R.drawable.shape_pouple_btn);
            holder.tv_vip_back.setBackgroundResource(R.drawable.shape_vip_pouple_fan);
        }

        GoodCategoryBean.PageBean.ContentBean goodsListBean = listData.get(position);
        holder.tv_goods_title.setText(goodsListBean.getName());
        holder.tv_vip_price.setText(String.valueOf(goodsListBean.getVipPrice()));
        holder.tv_goods_price.setText("原价:" + goodsListBean.getPrice());
        holder.tv_vip_back.setText("会员返" + goodsListBean.getBkCoin());
        holder.tv_youfei.setText("·全场满" + goodsListBean.getFreeShopping() + "包邮");
        holder.tv_sales_volume.setText("·销量:" + goodsListBean.getSellNum());
        holder.tv_kucun.setText("·库存:" + goodsListBean.getStock());
        holder.tv_supplier.setText(goodsListBean.getSupplier());
        holder.tv_show_num.setText("1/" + listData.get(position).getResList().size());

        holder.viewPager.setCurrentItem(0);
        bannerViewPageAdapter = new BannerViewPageAdapter(context, holder.viewPager, listData.get(position).getResList());
        holder.viewPager.setAdapter(bannerViewPageAdapter);

        holder.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int index, float positionOffset, int positionOffsetPixels) {
//                Log.e(TAG, "onPageScrolled: " + index);
            }

            @Override
            public void onPageSelected(int index) {
                holder.tv_show_num.setText(index + 1 + "/" + listData.get(position).getResList().size());
                Jzvd.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e(TAG, "onPageScrollStateChanged: " + state);
            }
        });

         //图片点击
        bannerViewPageAdapter.setItemClickListener(new BannerViewPageAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int index) {
                if(onSmallClickListener!=null){
                    onSmallClickListener.onSmallClick(position,index);
                }
            }
        });


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
        }

        if (onButtonClickListener != null) {
            holder.btn_buy.setOnClickListener(v -> onButtonClickListener.onButtonClick(v, position));
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        android.support.v4.view.ViewPager viewPager;
        TextView tv_y,tv_show_num, tv_vip_price, tv_vip_back, tv_goods_price, tv_goods_title;
        TextView tv_youfei, tv_sales_volume, tv_kucun, tv_supplier;
        Button btn_buy;

        public MyViewHolder(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.viewPager);
            tv_y=itemView.findViewById(R.id.tv_y);
            tv_show_num = itemView.findViewById(R.id.tv_show_num);
            tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
            tv_vip_back = itemView.findViewById(R.id.tv_vip_back);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_youfei = (TextView) itemView.findViewById(R.id.tv_youfei);
            tv_sales_volume = (TextView) itemView.findViewById(R.id.tv_sales_volume);
            tv_kucun = (TextView) itemView.findViewById(R.id.tv_kucun);
            tv_supplier = (TextView) itemView.findViewById(R.id.tv_supplier);
            btn_buy = itemView.findViewById(R.id.btn_buy);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }

    public interface OnSmallClickListener {
        void onSmallClick(int positon,int index);
    }
}
