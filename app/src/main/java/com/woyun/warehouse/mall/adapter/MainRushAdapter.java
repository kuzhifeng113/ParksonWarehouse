package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.MallHomeFourBean;

import java.util.List;

/**
 * 首页4.0 抢购商品适配
 */
public class MainRushAdapter extends RecyclerView.Adapter<MainRushAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeFourBean.RushBuyBean.GoodsListBean> dataList;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public MainRushAdapter(Context context, List<MallHomeFourBean.RushBuyBean.GoodsListBean> dataList) {
        this.dataList = dataList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_index_grab, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MallHomeFourBean.RushBuyBean.GoodsListBean voteBean = dataList.get(position);
        holder.tvName.setText(voteBean.getName());
        holder.tv_price.setText("￥"+voteBean.getVipPrice());

            Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView tvName,tv_price;
        ImageView iv_show_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            iv_show_pic = (ImageView) itemView.findViewById(R.id.iv_show_pic);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }


}
