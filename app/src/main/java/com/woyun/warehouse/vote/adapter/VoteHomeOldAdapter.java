package com.woyun.warehouse.vote.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.VoteHomeBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;

import java.util.List;

/**
 *
 *  往期投票适配
 */
public class VoteHomeOldAdapter extends RecyclerView.Adapter<VoteHomeOldAdapter.MyViewHolder> {
    private Context context;
    private List<VoteHomeBean> dataList;
    private boolean isVip;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public VoteHomeOldAdapter(Context context, List<VoteHomeBean> dataList,boolean isVip) {
        this.dataList = dataList;
        this.context=context;
        this.isVip=isVip;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vote_vertical, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        VoteHomeBean voteBean = dataList.get(position);
        holder.tv_old_name.setText(voteBean.getTitle());
        RecyclerView recyclerViewHorizontal = holder.recycler_view_horizontal;
        final List<VoteHomeBean.GoodsListBean> goodsList = voteBean.getGoodsList();
        VoteHorizontalAdapter horizontalAdapter=new VoteHorizontalAdapter(context,goodsList,isVip);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);// 设置 水平 布
//        recyclerViewHorizontal.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewHorizontal.setLayoutManager(manager);
        recyclerViewHorizontal.setAdapter(horizontalAdapter);

        if(onButtonClickListener!=null){
            holder.tv_query_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(holder.tv_query_all,position);
                }
            });
        }

        horizontalAdapter.setOnItemClickListener(new VoteHorizontalAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtils.getInstanc(context).showToast("水平item"+goodsList.get(position).getName());
                Intent detail=new Intent(context, GoodsDetailActivity.class);
                detail.putExtra("goods_id",goodsList.get(position).getGoodsId());
                detail.putExtra("from_id",1);
                detail.putExtra("is_History",true);//是否是历史期数
                detail.putExtra("wan_count",goodsList.get(position).getWantNum());
                detail.putExtra("total_count",goodsList.get(position).getTotalNum());
                detail.putExtra("is_vote",goodsList.get(position).isIsVote());
                detail.putExtra("is_shelf",goodsList.get(position).isIShelf());
                context.startActivity(detail);
            }
        });

        //购买按钮
       horizontalAdapter.setOnButtonClickListener(new VoteHorizontalAdapter.OnButtonClickListener() {
           @Override
           public void onButtonClick(View view, int index) {
//               ToastUtils.getInstanc(context).showToast("水平item"+goodsList.get(index).getName());
               Intent detail2=new Intent(context, GoodsDetailActivity.class);
               detail2.putExtra("goods_id",goodsList.get(index).getGoodsId());
               detail2.putExtra("from_id",1);
               detail2.putExtra("is_History",true);//是否是历史期数
               detail2.putExtra("wan_count",goodsList.get(index).getWantNum());
               detail2.putExtra("total_count",goodsList.get(index).getTotalNum());
               detail2.putExtra("is_vote",goodsList.get(index).isIsVote());
               detail2.putExtra("is_shelf",goodsList.get(index).isIShelf());
               context.startActivity(detail2);
           }
       });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_old_name,tv_query_all;
        RecyclerView recycler_view_horizontal;

      public  MyViewHolder(View itemView) {
            super(itemView);
          tv_old_name=itemView.findViewById(R.id.tv_old_name);
          tv_query_all = (TextView) itemView.findViewById(R.id.tv_query_all);
          recycler_view_horizontal = (RecyclerView) itemView.findViewById(R.id.recycler_view_horizontal);

        }
    }

    public interface  OnButtonClickListener{
        void onButtonClick(View view, int positon);
    }
}
