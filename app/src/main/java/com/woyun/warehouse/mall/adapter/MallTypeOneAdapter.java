package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 *  商城 type=1 子适配
 */
public class MallTypeOneAdapter extends RecyclerView.Adapter<MallTypeOneAdapter.MyViewHolder> {
    private static final String TAG = "MallTypeOneAdapter";
    private Context context;
    private List<MallHomeBean.PackListBean.GoodsListBean> dataList;
    private int showNum;//限制显示的条数
    private OnTypeItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public MallTypeOneAdapter(Context context, List<MallHomeBean.PackListBean.GoodsListBean> dataList,int showNum) {
//        Log.e(TAG, "MallTypeOneAdapter:@@@@@@@@@@ "+showNum );
        this.dataList = dataList;
        this.context=context;
        this.showNum=showNum;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mall_one_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MallHomeBean.PackListBean.GoodsListBean goodsListBean = dataList.get(position);
        Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img_goods);
        holder.tv_goods_name.setText(goodsListBean.getName());
        holder.tv_goods_title.setText(goodsListBean.getTitle());

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
        TextView tv_goods_name,tv_goods_title;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img_goods=itemView.findViewById(R.id.round_img_goods);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
          tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
        }
    }

    public  interface OnTypeItemClickListener {
        void onItemClick(int position);

    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
