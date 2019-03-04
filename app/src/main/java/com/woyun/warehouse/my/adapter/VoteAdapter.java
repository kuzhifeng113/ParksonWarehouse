package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.VoteBean;

import java.util.List;

/**
 *
 *demo
 */
public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.MyViewHolder> {
    private Context context;
    private List<VoteBean> dataList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public VoteAdapter(Context context, List<VoteBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voteold_ranking, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        VoteBean voteBean = dataList.get(position);
        holder.tvName.setText(voteBean.getName());
        holder.tvPrice.setText(voteBean.getPrice());
        holder.tvYprice.setText(voteBean.getY_price());
        holder.tvNumBuy.setText(voteBean.getHowBuy());
        holder.tvYprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

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
        TextView tvName,tvPrice,tvYprice,tvNumBuy;


      public  MyViewHolder(View itemView) {
            super(itemView);
          tvName = (TextView) itemView.findViewById(R.id.tv_name);
          tvPrice = (TextView) itemView.findViewById(R.id.tv_y_price);
          tvYprice = (TextView) itemView.findViewById(R.id.tv_y_price);

          tvNumBuy= (TextView) itemView.findViewById(R.id.tv_num_buy);
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
