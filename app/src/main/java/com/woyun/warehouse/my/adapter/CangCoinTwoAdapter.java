package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CangCoinBean;
import com.woyun.warehouse.bean.CangCoinTwoBean;
import com.woyun.warehouse.utils.DateUtils;

import java.util.List;

/**
 *
 *仓币明细2 适配
 */
public class CangCoinTwoAdapter extends RecyclerView.Adapter<CangCoinTwoAdapter.MyViewHolder> {
    private Context context;
    private List<CangCoinTwoBean.PageBean.ContentBean> dataList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public CangCoinTwoAdapter(Context context, List<CangCoinTwoBean.PageBean.ContentBean> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bcoin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        CangCoinTwoBean.PageBean.ContentBean voteBean = dataList.get(position);
        holder.tv_type.setText(voteBean.getTitle());
        holder.tv_num.setText(voteBean.getMoney());
        if(voteBean.getMoney().startsWith("-")){
            holder.tv_num.setTextColor(Color.parseColor("#FB3E59"));
            holder.tv_num.setText(voteBean.getMoney());
        }else{
            holder.tv_num.setTextColor(context.getResources().getColor(R.color.mainColor));
            holder.tv_num.setText("+"+voteBean.getMoney());
        }
//        holder.tv_time.setText(DateUtils.longToStringTime(voteBean.getCreateTime(),DateUtils.YEAR_MONTH_DAY_HMS));
        holder.tv_time.setText(DateUtils.longToStringTime(voteBean.getCreateTime(),DateUtils.MD_HM));


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
        TextView tv_type,tv_time,tv_num;


      public  MyViewHolder(View itemView) {
            super(itemView);
          tv_type = (TextView) itemView.findViewById(R.id.tv_type);
          tv_num = (TextView) itemView.findViewById(R.id.tv_num);
          tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }

    public  interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
