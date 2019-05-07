package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.MallHomeFourBean;
import com.woyun.warehouse.bean.MessageBean;
import com.woyun.warehouse.utils.DateUtils;

import java.util.List;

/**
 * 首页4.0 小图标分类适配
 */
public class MainSmallAdapter extends RecyclerView.Adapter<MainSmallAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeFourBean.CategoryListBeanX> dataList;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    public MainSmallAdapter(Context context, List<MallHomeFourBean.CategoryListBeanX> dataList) {
        this.dataList = dataList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_index_fenlei, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MallHomeFourBean.CategoryListBeanX voteBean = dataList.get(position);
        holder.tvName.setText(voteBean.getName());
        if(TextUtils.isEmpty(voteBean.getImage())){
            holder.iv_show_pic.setImageResource(R.mipmap.small_all);
        }else{
            Glide.with(context).load(voteBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        ImageView iv_show_pic;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            iv_show_pic = (ImageView) itemView.findViewById(R.id.iv_show_pic);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }


}
