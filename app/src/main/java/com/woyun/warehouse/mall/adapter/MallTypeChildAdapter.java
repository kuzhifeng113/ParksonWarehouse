package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CategoryGoodsBeanTwo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商城 2.0 type=2  1行2条 子适配
 */
public class MallTypeChildAdapter extends BaseAdapter {
    private static final String TAG = "MallTypeChildAdapter";
    private Context context;
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> dataList;
    private LayoutInflater mInflater;
//    private OnTypeItemClickListener onItemClickListener;
//    private OnButtonClickListener onButtonClickListener;
//
//    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
//        this.onButtonClickListener = onButtonClickListener;
//    }
//
//    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }


    public MallTypeChildAdapter(Context context, List<CategoryGoodsBeanTwo.PageBean.ContentBean> dataList) {
        Log.e(TAG, "MallTypeOneAdapter:@@@@@@@@@@ " + dataList.size());
        this.dataList = dataList;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    public MallTypeChildAdapter(Context context) {
        this.context = context;

    }

    public void setData(List<CategoryGoodsBeanTwo.PageBean.ContentBean> dataList) {
        if (dataList != null) {
            this.dataList = dataList;
            notifyDataSetChanged();
        }
    }

//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_mall_four_detail, parent, false);
//        return new MyViewHolder(view);
//    }

//    @Override
//    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        CategoryGoodsBeanTwo.PageBean.ContentBean goodsListBean = dataList.get(position);
//        Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img_goods);
//        holder.tv_goods_name.setText(goodsListBean.getName());
//        holder.tv_vip_back.setText("会员返"+goodsListBean.getBkCoin());
//        holder.tv_vip_price.setText(goodsListBean.getVipPrice());
//        holder.tv_goods_price.setText("原价:"+goodsListBean.getPrice());
//
////        if( onItemClickListener!= null){
////            holder.itemView.setOnClickListener( new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    onItemClickListener.onItemClick(position);
////                }
////            });
////        }
//    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryGoodsBeanTwo.PageBean.ContentBean goodsListBean = dataList.get(position);
        MyViewHolder myViewHolder = null;
        if (convertView == null) {
            myViewHolder = new MyViewHolder();
            convertView = mInflater.inflate(R.layout.item_mall_four_detail, null);
            myViewHolder.round_img_goods = (ImageView) convertView.findViewById(R.id.round_img_goods);
            myViewHolder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            myViewHolder.tv_vip_back = (TextView) convertView.findViewById(R.id.tv_vip_back);
            myViewHolder.tv_vip_price = (TextView) convertView.findViewById(R.id.tv_vip_price);
            myViewHolder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);

            Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(myViewHolder.round_img_goods);
            myViewHolder.tv_goods_name.setText(goodsListBean.getName());
            myViewHolder.tv_vip_back.setText("会员返" + goodsListBean.getBkCoin());
            myViewHolder.tv_vip_price.setText(goodsListBean.getVipPrice());
            myViewHolder.tv_goods_price.setText("原价:" + goodsListBean.getPrice());

            convertView.setTag(myViewHolder);
//        if( onItemClickListener!= null){
//            holder.itemView.setOnClickListener( new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onItemClickListener.onItemClick(position);
//                }
//            });
//        }

        }else{
            myViewHolder = (MyViewHolder) convertView.getTag();

            Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(myViewHolder.round_img_goods);
            myViewHolder.tv_goods_name.setText(goodsListBean.getName());
            myViewHolder.tv_vip_back.setText("会员返" + goodsListBean.getBkCoin());
            myViewHolder.tv_vip_price.setText(goodsListBean.getVipPrice());
            myViewHolder.tv_goods_price.setText("原价:" + goodsListBean.getPrice());

        }
        return convertView;
    }


    class MyViewHolder {
        private ImageView round_img_goods;
        private TextView tv_goods_name, tv_vip_back, tv_vip_price, tv_goods_price;

    }

}
