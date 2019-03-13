package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.mall.activity.AllCategoriesActivity;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.mall.activity.MallLeftActivity;
import com.woyun.warehouse.utils.GridSpacingItemDecoration;

import java.util.List;


/**
 * 之前的--商城adapter
 */
public class MallHomeAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MallHomeAdapter";
    private Context context;
    private List<MallHomeBean.PackListBean> listBeanList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MallHomeAdapter2(Context context, List<MallHomeBean.PackListBean> listBeanList) {
        this.listBeanList = listBeanList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return listBeanList.get(position).getType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {//优质吃货
            return new ZeroViewHolder(layoutInflater.inflate(R.layout.item_mall_zero, null, false));
        }
        if (viewType == 1) {//  一张图  新味速递
            return new OneViewHolder(layoutInflater.inflate(R.layout.item_mall_one, null, false));
        }
        if (viewType == 2) {//1 行 2张图    24H热卖
            return new TwoViewHolder(layoutInflater.inflate(R.layout.item_mall_two, null, false));
        }
        return null;
    }


    /**
     * 根据不同类型布局，绑定不同数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MallHomeBean.PackListBean packListBean = listBeanList.get(position);

        switch (getItemViewType(position)) {
            case 0:
                ZeroViewHolder zeroViewHolder = (ZeroViewHolder) holder;
//                Glide.with(context).load(packListBean.getImage()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.img_default)
//                        .error(R.mipmap.img_default).into(zeroViewHolder.img_left);
                Glide.with(context).load(Constant.WEB_MALL_LEFT).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.img_default)
                        .error(R.mipmap.img_default).into(zeroViewHolder.img_left);
                zeroViewHolder.img_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent left=new Intent(context, MallLeftActivity.class);
//                        left.putExtra("packlist_bean", packListBean);
                        left.putExtra("pack_id", packListBean.getGoodsPackId());
                        context.startActivity(left);
                    }
                });

                zeroViewHolder.img_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, AllCategoriesActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case 1:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
                oneViewHolder.tv_title.setText(packListBean.getTitle());
                Glide.with(context).load(packListBean.getGoodsList().get(0).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.img_default).error(R.mipmap.img_default)
                        .into(oneViewHolder.round_img_goods);
                oneViewHolder.tv_goods_name.setText(packListBean.getGoodsList().get(0).getName());
                oneViewHolder.tv_goods_title.setText(packListBean.getGoodsList().get(0).getTitle());

                oneViewHolder.round_img_goods.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int goodsId=packListBean.getGoodsList().get(0).getGoodsId();
//                        Intent goodsDetail=new Intent(context, GoodsDetailActivity.class);
                        Intent goodsDetail=new Intent(context, GoodsDetailNativeActivity.class);
                        goodsDetail.putExtra("goods_id",goodsId);
                        goodsDetail.putExtra("from_id",2);
                        context.startActivity(goodsDetail);
//                        ToastUtils.getInstanc(context).showToast("type one image"+packListBean.getGoodsList().get(0).getGoodsId());
//                        ToastUtils.getInstanc(context).showToast("type one image"+packListBean.getGoodsList().get(0).getName());
                    }
                });
                break;
            case 2:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                twoViewHolder.tv_title.setText(packListBean.getTitle());

                final List<MallHomeBean.PackListBean.GoodsListBean> goodsList = packListBean.getGoodsList();
                RecyclerView recyclerViewGrid = twoViewHolder.recycler_view_two;
                MallTypeTwoAdapter horizontalAdapter=new MallTypeTwoAdapter(context,goodsList,packListBean.getShowNum());
                recyclerViewGrid.setLayoutManager(new GridLayoutManager(context,2));
//                int spanCount = 2; // 2 columns
//                int spacing = 20; // 20px
//                boolean includeEdge = false;
                recyclerViewGrid.addItemDecoration(new GridSpacingItemDecoration(2,30,false));
//                recyclerViewGrid.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(context, 25)));//垂直间距
                recyclerViewGrid.setAdapter(horizontalAdapter);

                //查看更多
                twoViewHolder.tv_query_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent checkAll=new Intent(context,MallLeftActivity.class);
                        checkAll.putExtra("pack_id",packListBean.getGoodsPackId());
                        context.startActivity(checkAll);
                    }
                });

                //item  点击
                horizontalAdapter.setOnTypeItemClickListener(new MallTypeTwoAdapter.OnTypeItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        LogUtils.e(TAG,"=item  点击="+position);
                        int goodsId=goodsList.get(position).getGoodsId();
//                        Intent goods=new Intent(context, GoodsDetailActivity.class);
                        Intent goods=new Intent(context, GoodsDetailNativeActivity.class);
                        goods.putExtra("goods_id",goodsId);
                        goods.putExtra("from_id",2);
                        context.startActivity(goods);
                    }
                });
                break;
        }
        //item
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });
        }
        //img click
        if (onButtonClickListener != null) {
            holder.itemView.findViewById(R.id.img_head).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onButtonClick(position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }


    /**
     * 第一种ViewHolder
     */
    public class ZeroViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_left, img_right;

        public ZeroViewHolder(View itemView) {
            super(itemView);
            img_left = itemView.findViewById(R.id.img_left);
            img_right = itemView.findViewById(R.id.img_right);
        }
    }

    /**
     * 第二种ViewHolder
     */
    public class OneViewHolder extends RecyclerView.ViewHolder {

        private ImageView round_img_goods;
        private TextView tv_title, tv_goods_name, tv_goods_title;


        public OneViewHolder(View itemView) {
            super(itemView);
            round_img_goods = itemView.findViewById(R.id.round_img_goods);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);

        }
    }

    /**
     * 第三种ViewHolder
     */
    public class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_query_all;
        private RecyclerView recycler_view_two;

        public TwoViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_query_all = (TextView) itemView.findViewById(R.id.tv_query_all);
            recycler_view_two = (RecyclerView) itemView.findViewById(R.id.recycler_view_two);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
}


