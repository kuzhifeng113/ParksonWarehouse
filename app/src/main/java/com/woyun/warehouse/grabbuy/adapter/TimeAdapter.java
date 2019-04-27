package com.woyun.warehouse.grabbuy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GrabGoodsBean;
import com.woyun.warehouse.bean.RushTimeBean;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.TimeTools;

import java.text.NumberFormat;
import java.util.List;

/**
 *  抢购商品适配
 */
public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyViewHolder> {
    private static final String TAG = "GrabGoodsAdapter";
    private Context context;
    private List<RushTimeBean> listData;
    private OnItemClickListener onItemClickListener;




    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public TimeAdapter(Context context, List<RushTimeBean> listData) {
        this.listData = listData;
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tab_grab_item_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        RushTimeBean goodsListBean = listData.get(position);

        holder.tv_time.setText(TimeTools.descriptiveData(goodsListBean.getStartTime())+goodsListBean.getName());
        holder.tv_status.setText( goodsListBean.getStatus());
        if(goodsListBean.isSelect()){
            //选中后字体变大
            holder.tv_time.setTextSize(16);
            holder.tv_time.setTextColor(Color.parseColor("#ffffff"));

            holder.tv_status.setTextSize(12);
            holder.tv_status.setTextColor(Color.parseColor("#ffffff"));
        }else{
            //选中后字体变大
            holder.tv_time.setTextSize(14);
            holder.tv_time.setTextColor(Color.parseColor("#FFA8B8"));

            holder.tv_status.setTextSize(12);
            holder.tv_status.setTextColor(Color.parseColor("#FFA8B8"));
        }
        //5等份
        LinearLayout.LayoutParams params = new
                LinearLayout.LayoutParams((int) ((context.getResources().getDisplayMetrics().widthPixels) / 3.5f),
//                LinearLayout.LayoutParams(holder.itemView.getWidth(),
//显示4个半
//                LinearLayout.LayoutParams((int) ((context.getResources().getDisplayMetrics().widthPixels - DensityUtils.dp2px(context, 5)) / 4.5f),
                ViewGroup.LayoutParams.MATCH_PARENT);
        holder.itemView.setLayoutParams(params);

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(holder.itemView,position));
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time, tv_status;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_status = (TextView) itemView.findViewById(R.id.tv_status);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView,int position);
    }

}
