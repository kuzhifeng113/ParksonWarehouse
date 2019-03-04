package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.my.activity.InsertAddressActivity;

import java.util.List;

/**
 * 地址适配
 */
public class ShipAddressAdapter extends RecyclerView.Adapter<ShipAddressAdapter.MyViewHolder> {
    private Context context;
    private List<ShipAddressBean> dataList;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public ShipAddressAdapter(Context context, List<ShipAddressBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ship_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ShipAddressBean voteBean = dataList.get(position);
        holder.tvName.setText(voteBean.getName());
        holder.tvPhone.setText(voteBean.getPhone());
        holder.tvAddress.setText(voteBean.getProvince() + voteBean.getCity() + voteBean.getCounty() + voteBean.getAddress());


        if (voteBean.getStatus() == 1) {
            holder.tvMoren.setVisibility(View.VISIBLE);
        } else {
            holder.tvMoren.setVisibility(View.GONE);
        }

        if (onItemClickListener != null) {
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit=new Intent(context, InsertAddressActivity.class);
                edit.putExtra("isEdit",true);
                edit.putExtra("address",dataList.get(position));
                context.startActivity(edit);
            }
        });

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
       RelativeLayout main;
        TextView tvName, tvPhone, tvMoren, tvAddress;
        ImageView imgEdit;
        Button btnDelete;


        public MyViewHolder(View itemView) {
            super(itemView);
            main = (RelativeLayout) itemView.findViewById(R.id.main);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);
            tvMoren = (TextView) itemView.findViewById(R.id.tv_moren);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
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
