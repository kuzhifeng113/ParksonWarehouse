package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.AgentHelpVBean;
import com.woyun.warehouse.bean.OrderDetailBean;
import com.woyun.warehouse.utils.DateUtils;

import java.util.List;

/**
 * 帮忙注册记录适配
 */
public class AgentHelpAdapter extends RecyclerView.Adapter<AgentHelpAdapter.MyViewHolder> {
    private Context context;
    private List<AgentHelpVBean.PageBeanBean.ContentBean> dataList;

    public AgentHelpAdapter(Context context, List<AgentHelpVBean.PageBeanBean.ContentBean> dataList) {
        this.dataList = dataList;
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agent_help_log, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AgentHelpVBean.PageBeanBean.ContentBean contentBean = dataList.get(position);
        holder.tv_phone.setText(contentBean.getVipPhone());
        holder.tv_name.setText(contentBean.getVipName());
        holder.tv_time.setText(DateUtils.longToStringTimed(contentBean.getCreateTime()));

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_phone, tv_name, tv_time;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);


        }
    }

}
