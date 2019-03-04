package com.woyun.warehouse.baseparson.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Trace;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.Logistics;
import com.woyun.warehouse.utils.DensityUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 物流信息适配
 */
public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.MyViewHolder> {
    private Context context;
    private List<Logistics.TracesBean> dataList;

    public LogisticsAdapter(Context context, List<Logistics.TracesBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_logistics_info, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //设置相关数据
        Logistics.TracesBean trace = dataList.get(position);
        holder.tv_time.setText(trace.getAcceptTime());
        holder.tv_title.setText(trace.getAcceptStation());
        if(position==0){
            holder.image.setImageResource(R.drawable.oval_logistics_green);
            //绿色的圆
            RelativeLayout.LayoutParams pointParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 13), DensityUtils.dp2px(context, 15));
            pointParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            holder.image.setLayoutParams(pointParams);

            holder.view_top.setVisibility(View.INVISIBLE);

        }else{
            holder.tv_title.setTextColor(Color.parseColor("#AFAFAF"));
            holder.image.setImageResource(R.drawable.oval_logistics_gray);

            //灰色的圆
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 13), DensityUtils.dp2px(context, 15));
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            holder.image.setLayoutParams(lp);

            // 灰色的top 竖线
            RelativeLayout.LayoutParams topParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
            topParams.addRule(RelativeLayout.ABOVE, R.id.image);//让直线置于圆点上面
            topParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            holder.view_top.setLayoutParams(topParams);

           //灰色的bottom 竖线
            RelativeLayout.LayoutParams bottomParams = new RelativeLayout.LayoutParams(DensityUtils.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
            bottomParams.addRule(RelativeLayout.BELOW, R.id.image);//让直线置于圆点下面
            bottomParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            holder.view_bottom.setLayoutParams(bottomParams);


            if(position==dataList.size()-1){//最后条
                holder.view_bottom.setVisibility(View.GONE);
            }
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_time;  //接收时间
        private TextView tv_title;  //接收地点
        private ImageView image;
        private View view_top, view_bottom;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.image);
            view_top = itemView.findViewById(R.id.view_top);
            view_bottom = itemView.findViewById(R.id.view_bottom);
        }
    }

}
