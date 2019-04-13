package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CategoryGoodsBeanTwo;
import com.woyun.warehouse.view.MyGridView;

import java.util.List;
import java.util.Map;

/**
 * 分类商品ListView 2.0适配
 */
public class CategoryGoodsAdapterOne extends BaseAdapter {
    private static final int ONE_TYPE = 0;//1行1个
    private static final int TWO_TYPE = 1;//嵌套GridView
    private static final String TAG = "CategoryGoodsAdapterOne";
    private Context context;
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> dataList;
    private List<CategoryGoodsBeanTwo.PageBean.ContentBean> listDataTwo;
    private List<CategoryGoodsBeanTwo.GoodsPackListBean> packListBeans;

    private OnTypeItemClickListener onItemClickListener;
    private LayoutInflater layoutInflater;
    private int page;//当前页

    MallTypeChildAdapter mallTypeOneAdapter;

    public void setOnTypeItemClickListener(OnTypeItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public CategoryGoodsAdapterOne(Context context, List<CategoryGoodsBeanTwo.PageBean.ContentBean> dataList, int page,
                                   List<CategoryGoodsBeanTwo.GoodsPackListBean> packListBeans,
                                   List<CategoryGoodsBeanTwo.PageBean.ContentBean> listDataTwo
    ) {
        this.dataList = dataList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.page = page;
        this.packListBeans = packListBeans;
        this.listDataTwo = listDataTwo;
//        this.mapData=mapData;
    }

    public void setData(Map<Integer, List<CategoryGoodsBeanTwo.PageBean.ContentBean>> mapData) {
        if (mapData != null) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategoryGoodsBeanTwo.PageBean.ContentBean goodsListBean = dataList.get(position);
        int type = getItemViewType(position);
        ViewHolderOne viewHolderOne = null;
        ViewHolderTwo viewHolderTwo = null;
        if (convertView == null) {
            switch (type) {
                case ONE_TYPE:
                    viewHolderOne = new ViewHolderOne();
                    convertView = layoutInflater.inflate(R.layout.item_mall_ten, null);
                    viewHolderOne.round_img_goods = (ImageView) convertView.findViewById(R.id.round_img_goods);
                    viewHolderOne.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
                    viewHolderOne.tv_goods_title = (TextView) convertView.findViewById(R.id.tv_goods_title);
                    viewHolderOne.tv_sales_volume = (TextView) convertView.findViewById(R.id.tv_sales_volume);
                    viewHolderOne.tv_supplier = (TextView) convertView.findViewById(R.id.tv_supplier);
                    viewHolderOne.tv_vip_back = (TextView) convertView.findViewById(R.id.tv_vip_back);
                    viewHolderOne.tv_vip_price = (TextView) convertView.findViewById(R.id.tv_vip_price);
                    viewHolderOne.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);

                    Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default)
                            .into(viewHolderOne.round_img_goods);
                    viewHolderOne.tv_goods_name.setText(goodsListBean.getName());
                    viewHolderOne.tv_goods_title.setText(goodsListBean.getTitle());
                    viewHolderOne.tv_vip_price.setText(goodsListBean.getVipPrice());
                    viewHolderOne.tv_goods_price.setText("原价:" + goodsListBean.getPrice());
                    viewHolderOne.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                    viewHolderOne.tv_vip_back.setText("会员返" + goodsListBean.getBkCoin());
                    viewHolderOne.tv_vip_back.setText("会员价");
                    viewHolderOne.tv_sales_volume.setText("销量：" + goodsListBean.getSellNum());
                    viewHolderOne.tv_supplier.setText(goodsListBean.getSupplier());

                    convertView.setTag(viewHolderOne);
                    break;
                case TWO_TYPE:
                    viewHolderTwo = new ViewHolderTwo();
                    convertView  = layoutInflater.inflate(R.layout.item_mall_four, null);
                    viewHolderTwo.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    viewHolderTwo.tv_title_des = (TextView) convertView.findViewById(R.id.tv_title_des);
                    viewHolderTwo.recycler_view_four = (MyGridView) convertView.findViewById(R.id.recycler_view_four);

                    if (packListBeans.size() > 0) {
                        if (packListBeans.size() > page - 1) {
                            viewHolderTwo.tv_title.setVisibility(View.VISIBLE);
                            viewHolderTwo.tv_title_des.setVisibility(View.VISIBLE);
                            viewHolderTwo.tv_title.setText(packListBeans.get(page - 1).getName());
                            viewHolderTwo.tv_title_des.setText(packListBeans.get(page - 1).getTitle());
                        } else {
                            viewHolderTwo.tv_title.setVisibility(View.GONE);
                            viewHolderTwo.tv_title_des.setVisibility(View.GONE);
                        }
                    }
                    if (mallTypeOneAdapter == null) {
                        mallTypeOneAdapter = new MallTypeChildAdapter(context,listDataTwo);
                    }

                    viewHolderTwo.recycler_view_four.setAdapter(mallTypeOneAdapter);
                    convertView.setTag(viewHolderTwo);
                    break;

            }

        } else {
            switch (type) {
                case ONE_TYPE:
                    viewHolderOne = (ViewHolderOne) convertView.getTag();

                    Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default)
                            .into(viewHolderOne.round_img_goods);
                    viewHolderOne.tv_goods_name.setText(goodsListBean.getName());
                    viewHolderOne.tv_goods_title.setText(goodsListBean.getTitle());
                    viewHolderOne.tv_vip_price.setText(goodsListBean.getVipPrice());
                    viewHolderOne.tv_goods_price.setText("原价:" + goodsListBean.getPrice());
                    viewHolderOne.tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                    viewHolderOne.tv_vip_back.setText("会员返" + goodsListBean.getBkCoin());
                    viewHolderOne.tv_vip_back.setText("会员价");
                    viewHolderOne.tv_sales_volume.setText("销量：" + goodsListBean.getSellNum());
                    viewHolderOne.tv_supplier.setText(goodsListBean.getSupplier());
                    break;

                case TWO_TYPE:
                    viewHolderTwo = (ViewHolderTwo) convertView.getTag();
                    if (packListBeans.size() > 0) {
                        if (packListBeans.size() > page - 1) {
                            viewHolderTwo.tv_title.setVisibility(View.VISIBLE);
                            viewHolderTwo.tv_title_des.setVisibility(View.VISIBLE);
                            viewHolderTwo.tv_title.setText(packListBeans.get(page - 1).getName());
                            viewHolderTwo.tv_title_des.setText(packListBeans.get(page - 1).getTitle());
                        } else {
                            viewHolderTwo.tv_title.setVisibility(View.GONE);
                            viewHolderTwo.tv_title_des.setVisibility(View.GONE);
                        }
                    }
                    if (mallTypeOneAdapter == null) {
                        mallTypeOneAdapter = new MallTypeChildAdapter(context,listDataTwo);
                    }

                    viewHolderTwo.recycler_view_four.setAdapter(mallTypeOneAdapter);


                    break;
            }
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e(TAG, "getItemViewType: 位置" + position);
//        return position % 10 == 0 ? 1 : 0;
        return dataList.get(position).getViewType();
    }

    class ViewHolderOne {
        private ImageView round_img_goods;
        private TextView tv_goods_name, tv_goods_title, tv_sales_volume, tv_supplier, tv_vip_back, tv_vip_price, tv_goods_price;

    }

    class ViewHolderTwo {
        private TextView tv_title, tv_title_des;
        private MyGridView recycler_view_four;
    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.e(TAG, "onCreateViewHolder:viewType####### "+viewType);
//        if (viewType == 1) {//一行一个
//            return new OneViewHolder(layoutInflater.inflate(R.layout.item_mall_ten, null, false));
//        }
//        if (viewType == 2) {//  1行2个
//            return new TwoViewHolder(layoutInflater.inflate(R.layout.item_mall_four, null, false));
//        }
//         return new OneViewHolder(layoutInflater.inflate(R.layout.item_mall_ten, null, false));
//    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        CategoryGoodsBeanTwo.PageBean.ContentBean goodsListBean = dataList.get(position);
//        switch (getItemViewType(position)) {
//            case 1:
//                OneViewHolder oneViewHolder = (OneViewHolder) holder;
//                Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default)
//                        .into(oneViewHolder.round_img_goods);
//                oneViewHolder.tv_goods_name.setText(goodsListBean.getName());
//                oneViewHolder.tv_goods_title.setText(goodsListBean.getTitle());
//                oneViewHolder.tv_vip_price.setText(goodsListBean.getVipPrice());
//                oneViewHolder.tv_goods_price.setText("原价:"+goodsListBean.getPrice());
//                oneViewHolder.tv_vip_back.setText("会员返"+goodsListBean.getBkCoin());
//                oneViewHolder.tv_sales_volume.setText("销量："+goodsListBean.getSellNum());
//                oneViewHolder.tv_supplier.setText(goodsListBean.getSupplier());
//
//                if( onItemClickListener!= null){
//                    oneViewHolder.itemView.setOnClickListener( new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onItemClickListener.onItemClick(position);
//                        }
//                    });
//                }
//                break;
//            case 2:
//                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
//                Log.e(TAG, "onBindViewHolder: $$$333$$$"+page );
//                Log.e(TAG, "onBindViewHolder: $$$222$$$"+getPage() );
//                if(packListBeans.size()>0){
//                    if(packListBeans.size()>page-1){
//                        twoViewHolder.tv_title.setVisibility(View.VISIBLE);
//                        twoViewHolder.tv_title_des.setVisibility(View.VISIBLE);
//                        twoViewHolder.tv_title.setText(packListBeans.get(page-1).getName());
//                        twoViewHolder.tv_title_des.setText(packListBeans.get(page-1).getTitle());
//                    }else{
//                        twoViewHolder.tv_title.setVisibility(View.GONE);
//                        twoViewHolder.tv_title_des.setVisibility(View.GONE);
//                    }
//                }else{
//                    twoViewHolder.tv_title.setVisibility(View.GONE);
//                    twoViewHolder.tv_title_des.setVisibility(View.GONE);
//                }
//
//                RecyclerView recyclerViewGrid = twoViewHolder.recycler_view_four;
//                recyclerViewGrid.setItemViewCacheSize(4);
//                if(mallTypeOneAdapter==null){
//                    mallTypeOneAdapter=new MallTypeChildAdapter(context);
//                }
//                mallTypeOneAdapter.setData(listDataTwo);
//                recyclerViewGrid.setLayoutManager(new GridLayoutManager(context,2));
//                if(recyclerViewGrid.getItemDecorationCount()==0){
//                    recyclerViewGrid.addItemDecoration(new GridSpacingItemDecoration(2,23,false));
//                }
//                recyclerViewGrid.setAdapter(mallTypeOneAdapter);
//
//                //item  点击
//                mallTypeOneAdapter.setOnTypeItemClickListener(new MallTypeChildAdapter.OnTypeItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        int goodsId=listDataTwo.get(position).getGoodsId();
//                        Intent goods=new Intent(context, GoodsDetailActivity.class);
//                        goods.putExtra("goods_id",goodsId);
//                        goods.putExtra("from_id",2);
//                        context.startActivity(goods);
//                    }
//                });
//
//                break;
//        }
//        holder.itemView.setTag(position);
//    }
//
//    @Override
//    public int getItemCount() {
//        Log.e(TAG, "getItemCount: "+dataList.size() );
//        return dataList.size();
//    }

    /**
     * 第一种ViewHolder
     */
    public class OneViewHolder extends RecyclerView.ViewHolder {
        ImageView round_img_goods;
        TextView tv_goods_name, tv_goods_title, tv_sales_volume, tv_supplier, tv_vip_back, tv_vip_price, tv_goods_price;

        //            private RecyclerView recycler_view_ten_big;
        public OneViewHolder(View itemView) {
            super(itemView);
//            recycler_view_ten_big=itemView.findViewById(R.id.recycler_view_ten_big);
            round_img_goods = itemView.findViewById(R.id.round_img_goods);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_sales_volume = (TextView) itemView.findViewById(R.id.tv_sales_volume);
            tv_supplier = (TextView) itemView.findViewById(R.id.tv_supplier);
            tv_vip_back = (TextView) itemView.findViewById(R.id.tv_vip_back);
            tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
        }
    }

    /**
     * 第二种ViewHolder
     */
    public class TwoViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title, tv_title_des;
        private RecyclerView recycler_view_four;

        public TwoViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_title_des = itemView.findViewById(R.id.tv_title_des);
            recycler_view_four = itemView.findViewById(R.id.recycler_view_four);
        }
    }

    public interface OnTypeItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }
}
