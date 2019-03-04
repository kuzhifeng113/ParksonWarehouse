package com.woyun.warehouse.cart.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.bean.ShoppingCartBean;
import com.woyun.warehouse.utils.ToastUtils;

import java.util.List;

/**
 *
 *  购物车适配
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private static final String TAG = "CartAdapter";
    private Context context;
    private List<CartShopBean.CartListBean> dataList;
    private boolean isShow = true;//是否显示编辑/完成
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    private OnButtonClick onButtonClick;

    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public CartAdapter(Context context, List<CartShopBean.CartListBean> dataList) {
        this.dataList = dataList;
        this.context=context;

    }

    /**
     * 是否显示可编辑
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final CartShopBean.CartListBean shoppingCartBean = dataList.get(position);
        Glide.with(context).load(shoppingCartBean.getSkuImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        holder.tv_goods_name.setText(shoppingCartBean.getGoodsName());

        holder.tv_goods_price.setText("￥"+String.valueOf(shoppingCartBean.getUnitPrice()));
        holder.tv_goods_attr.setText(shoppingCartBean.getMemo());
        holder.tv_show_num.setText(""+shoppingCartBean.getSkuNum());
//        holder.tv_goods_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        boolean choosed = shoppingCartBean.isCheck();
        if (choosed){
            holder.ckOneChose.setChecked(true);
        }else{
            holder.ckOneChose.setChecked(false);
        }

        if(onButtonClick!=null){
            holder.iv_show_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick.imgCick(position);
                }
            });
        }
//        //item  check box 关联
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.ckOneChose.toggle();
//                Log.e(TAG, "onClick: "+holder.ckOneChose.isChecked() );
//                if(checkInterface!=null){
//                    checkInterface.checkGroup(position, holder.ckOneChose.isChecked());//向外暴露接口
//                }
//            }
//        });

        //单选框按钮
        holder.ckOneChose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shoppingCartBean.setCheck(((CheckBox) v).isChecked());
                        if(checkInterface!=null){
                            checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                        }
                    }
                }
        );


        //增加按钮
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position, holder.tv_show_num, holder.ckOneChose.isChecked());//暴露增加接口
            }
        });

        //删减按钮
        holder.img_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position, holder.tv_show_num, holder.ckOneChose.isChecked());//暴露删减接口
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox ckOneChose;
        ImageView iv_show_pic,img_sub,img_add;
        TextView tv_goods_name,tv_goods_attr,tv_show_num,tv_goods_price;

      public  MyViewHolder(View itemView) {
            super(itemView);
          ckOneChose=itemView.findViewById(R.id.check_one);
          iv_show_pic=itemView.findViewById(R.id.iv_show_pic);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
          tv_goods_attr = (TextView) itemView.findViewById(R.id.tv_goods_attr);
          tv_show_num = (TextView) itemView.findViewById(R.id.tv_show_num);
          tv_goods_price = (TextView) itemView.findViewById(R.id.tv_price);
          img_add = (ImageView) itemView.findViewById(R.id.img_add);
          img_sub = (ImageView) itemView.findViewById(R.id.img_sub);

        }
    }

    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }

    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);
        /**
         * 删减操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);
        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }

    public interface OnButtonClick{
        void imgCick(int position);
    }
}
