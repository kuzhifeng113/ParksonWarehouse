package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.MessageBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.my.activity.InsertAddressActivity;
import com.woyun.warehouse.utils.DateUtils;

import java.util.List;

/**
 * 消息适配
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context context;
    private int messType;
    private List<MessageBean.ContentBean> dataList;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public MessageAdapter(Context context, List<MessageBean.ContentBean> dataList,int messType) {
        this.dataList = dataList;
        this.context = context;
        this.messType=messType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_mesage, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MessageBean.ContentBean voteBean = dataList.get(position);
        holder.tv_mess_type.setText(voteBean.getTitle());
        holder.tv_mess_time.setText(DateUtils.longToStringTime(voteBean.getCreateTime(),DateUtils.YEAR_MONTH_DAY));
        Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.img_mess);
        holder.tv_content.setText(voteBean.getContent());
        if(messType==0){//系统通知
            holder.tv_mess_order.setText("来自系统消息");
        }else{
            holder.tv_mess_order.setText("订单编号："+voteBean.getTradeNo());
        }


        if (onItemClickListener != null) {
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onButtonClickListener!=null){
                    onButtonClickListener.delete(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
       LinearLayout main;
        TextView tv_mess_type, tv_mess_time, tv_content, tv_mess_order;
        ImageView img_mess;
        Button btnDelete;


        public MyViewHolder(View itemView) {
            super(itemView);
            main = (LinearLayout) itemView.findViewById(R.id.main);
            tv_mess_type = (TextView) itemView.findViewById(R.id.tv_mess_type);
            tv_mess_time = (TextView) itemView.findViewById(R.id.tv_mess_time);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_mess_order = (TextView) itemView.findViewById(R.id.tv_mess_order);
            img_mess = (ImageView) itemView.findViewById(R.id.img_mess);
            btnDelete = (Button) itemView.findViewById(R.id.deltete);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnCheckClickListener {
        void checkGroup(int position, boolean isChecked);
    }

    public interface  OnButtonClickListener{
        void delete(int positon);
    }
}
