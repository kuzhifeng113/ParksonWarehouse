package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.MallHomeFourBean;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;

import java.util.List;

/**
 * 首页4.0 热门 适配
 */
public class MainHotAdapter extends RecyclerView.Adapter<MainHotAdapter.MyViewHolder> {
    private Context context;
    private List<MallHomeFourBean.GoodsCategoryListBean> dataList;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public MainHotAdapter(Context context, List<MallHomeFourBean.GoodsCategoryListBean> dataList) {
        this.dataList = dataList;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_index_hot, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MallHomeFourBean.GoodsCategoryListBean voteBean = dataList.get(position);
            Glide.with(context).load(voteBean.getTitleImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);

        List<MallHomeFourBean.GoodsCategoryListBean.GoodsListBeanX> goodsList = voteBean.getGoodsList();
        MainHotDetailAdapter hotDetailAdapter=new MainHotDetailAdapter(context,goodsList);
        RecyclerView recyclerViewHotGoods = holder.recyclerViewHotGoods;
        recyclerViewHotGoods.setLayoutManager(new LinearLayoutManager(context));
//        recyclerViewHotGoods.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(context, 20)));//垂直间距
        recyclerViewHotGoods.setAdapter(hotDetailAdapter);

        //img click
        if (onButtonClickListener != null) {
            holder.iv_show_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(position);
                }
            });
        }

        //商品详情
        hotDetailAdapter.setOnTypeItemClickListener(posion1 -> {
            Intent intent=new Intent(context, GoodsDetailNativeActivity.class);
            intent.putExtra("goods_id",goodsList.get(posion1).getGoodsId());
            intent.putExtra("from_id", 2);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_show_pic;
        RecyclerView recyclerViewHotGoods;
        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerViewHotGoods =itemView.findViewById(R.id.recyclerViewHotGoods);
            iv_show_pic = itemView.findViewById(R.id.iv_show_pic);

        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }


}
