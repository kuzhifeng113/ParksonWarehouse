package com.woyun.warehouse.baseparson.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.woyun.warehouse.R;

import java.util.List;

/**
 *
 *demo
 */
public class ListDemoAdapter extends RecyclerView.Adapter<ListDemoAdapter.MyViewHolder> {
    private Context context;
    private List<String> dataList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public ListDemoAdapter( Context context,List<String> dataList) {
        this.dataList = dataList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        String value=dataList.get(position);
         holder.tvName.setText(value);





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
        TextView tvName;


      public  MyViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_text);

        }
    }

    public  interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
