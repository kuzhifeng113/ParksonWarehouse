package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.SearchBean;

import java.util.List;

/**
 *
 *   热门搜索适配
 */
public class HistorySearchAdapter extends RecyclerView.Adapter<HistorySearchAdapter.MyViewHolder> {
    private Context context;
    private List<SearchBean> dataList;
    private OnTypeItemClickListener onItemClickListener;


    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public HistorySearchAdapter(Context context, List<SearchBean> dataList) {
        this.dataList = dataList;
        this.context=context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_history, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        SearchBean searchBean = dataList.get(position);
        holder.btn_hot.setText(searchBean.getName());

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
        TextView btn_hot;
      public  MyViewHolder(View itemView) {
            super(itemView);
          btn_hot=itemView.findViewById(R.id.btn_hot);

        }
    }

    public  interface OnTypeItemClickListener {
        void onItemClick(int position);

    }

}
