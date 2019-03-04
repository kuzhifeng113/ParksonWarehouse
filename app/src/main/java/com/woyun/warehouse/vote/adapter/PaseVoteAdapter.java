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
import com.woyun.warehouse.bean.VoteHomeBean;

import java.text.NumberFormat;
import java.util.List;

/**
 *
 *  往期投票适配
 */
public class PaseVoteAdapter extends RecyclerView.Adapter<PaseVoteAdapter.MyViewHolder> {
    private static final String TAG = "PaseVoteAdapter";
    private Context context;
    private List<VoteHomeBean.GoodsListBean> dataList;
    private boolean isVip;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public PaseVoteAdapter(Context context, List<VoteHomeBean.GoodsListBean> dataList,boolean isVip) {
        this.dataList = dataList;
        this.context=context;
        this.isVip=isVip;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_votenew_ranking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Log.e(TAG, "onBindViewHolder: "+position );
        VoteHomeBean.GoodsListBean voteBean = dataList.get(position);
        Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img);
        holder.tvName.setText(voteBean.getName());
        holder.tv_want_num.setText(voteBean.getWantNum()+"人想要");
//        if(isVip){
            holder.tvVipPrice.setText("￥"+voteBean.getVipPrice());
            holder.tvPrice.setText("￥"+voteBean.getPrice());
//        }else{
//            holder.tvVipPrice.setText("￥"+voteBean.getPrice());
//            holder.tvPrice.setText("￥"+voteBean.getVipPrice());
//        }

//        holder.tvPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);//保留2位小数
        String percentage=numberFormat.format((float) voteBean.getWantNum() / (float) voteBean.getTotalNum()  * 100);
        if(percentage.contains(".")){
            String  result= percentage.substring(0,percentage.indexOf("."));
            holder.preview_progressBar.setProgress(Integer.valueOf(result));
        }else{
            holder.preview_progressBar.setProgress(Integer.valueOf(percentage));
        }

        if(voteBean.isIShelf()){
            holder.btn_i_want.setText("购买");
            holder.btn_i_want.setClickable(true);
            holder.btn_i_want.setBackgroundResource(R.drawable.shape_vote_buy);
        }else{
            holder.btn_i_want.setText("已下架");//已结束
            holder.btn_i_want.setClickable(false);
            holder.btn_i_want.setBackgroundResource(R.drawable.shape_vote_over);
        }

        if(position==0){
            holder.tv_no.setVisibility(View.VISIBLE);
            holder.tv_no.setText("NO.1");
            holder.tv_no.setBackgroundResource(R.drawable.shape_vote_one);
        }else if(position==1){
            holder.tv_no.setVisibility(View.VISIBLE);
            holder.tv_no.setText("NO.2");
            holder.tv_no.setBackgroundResource(R.drawable.shape_vote_two);
        }else if(position==2){
            holder.tv_no.setVisibility(View.VISIBLE);
            holder.tv_no.setText("NO.3");
            holder.tv_no.setBackgroundResource(R.drawable.shape_vote_three);
        }else{
            holder.tv_no.setVisibility(View.GONE);
        }

        if( onItemClickListener!= null){
            holder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        if(onButtonClickListener!=null){
            holder.btn_i_want.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(holder.btn_i_want,position);
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
        TextView tvName,tvPrice,tvVipPrice,tv_want_num,tv_no;
        Button btn_i_want;
        ProgressBar preview_progressBar;

      public  MyViewHolder(View itemView) {
            super(itemView);
          round_img=itemView.findViewById(R.id.round_img);
          tvName = (TextView) itemView.findViewById(R.id.tv_name);
          tv_no = (TextView) itemView.findViewById(R.id.tv_no);
          tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
          tvVipPrice = (TextView) itemView.findViewById(R.id.tv_vip_price);
          tv_want_num= (TextView) itemView.findViewById(R.id.tv_want_num);
          btn_i_want= (Button) itemView.findViewById(R.id.btn_i_want);
          preview_progressBar= (ProgressBar) itemView.findViewById(R.id.preview_progressBar);
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
