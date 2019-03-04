package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.VoteBean;
import com.woyun.warehouse.bean.VoteHomeBean;
import com.woyun.warehouse.utils.ToastUtils;

import java.text.NumberFormat;
import java.util.List;

/**
 * 水平recycleview 适配
 */
public class VoteHorizontalAdapter extends RecyclerView.Adapter<VoteHorizontalAdapter.MyViewHolder> {
    private static final String TAG = "VoteHorizontalAdapter";
    private Context context;
    private boolean isVip;

    public void setDataList(List<VoteHomeBean.GoodsListBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    private List<VoteHomeBean.GoodsListBean> dataList;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public VoteHorizontalAdapter(Context context, List<VoteHomeBean.GoodsListBean> dataList,boolean isVip) {
        this.dataList = dataList;
        this.context = context;
        this.isVip=isVip;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voteold_ranking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        VoteHomeBean.GoodsListBean voteBean = dataList.get(position);
        Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img);
        holder.tvName.setText(voteBean.getName());
//        if(isVip){
            holder.tvVipPrice.setText("￥" + voteBean.getVipPrice());
            holder.tvPrice.setText("￥" + voteBean.getPrice());

//        }else{
//            holder.tvVipPrice.setText("￥" + voteBean.getPrice());
//            holder.tvPrice.setText("￥" + voteBean.getVipPrice());
//        }
//        holder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvNumBuy.setText(voteBean.getWantNum() + "人想要");


        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);//保留2位小数
        String percentage = numberFormat.format((float) voteBean.getWantNum() / (float) voteBean.getTotalNum() * 100);

        if(percentage.contains(".")){
            String  result= percentage.substring(0,percentage.indexOf("."));
            holder.preview_progressBar.setProgress(Integer.valueOf(result));
        }else{
            holder.preview_progressBar.setProgress(Integer.valueOf(percentage));
        }
        //是否上架
        if (voteBean.isIShelf()) {//上架
            holder.btn_buy.setText("购买");
            holder.btn_buy.setClickable(true);
            holder.btn_buy.setBackgroundResource(R.drawable.shape_vote_buy);
        } else {
            holder.btn_buy.setText("已下架");
            holder.btn_buy.setClickable(false);
            holder.btn_buy.setBackgroundResource(R.drawable.shape_vote_over);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        if (onButtonClickListener != null) {
            holder.btn_buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   onButtonClickListener.onButtonClick(holder.btn_buy,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView round_img;
        TextView tvName, tvVipPrice, tvPrice, tvNumBuy;
        Button btn_buy;
        ProgressBar preview_progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            round_img = itemView.findViewById(R.id.round_img);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvVipPrice = (TextView) itemView.findViewById(R.id.tv_vip_price);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
            tvNumBuy = (TextView) itemView.findViewById(R.id.tv_num_buy);
            btn_buy = (Button) itemView.findViewById(R.id.btn_buy);
            preview_progressBar = itemView.findViewById(R.id.preview_progressBar);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }
}
