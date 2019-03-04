package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.AgentOpenBean;
import com.woyun.warehouse.bean.VipCenterBean;

import java.util.List;

/**
 *
 *  代理开通 礼包 适配
 */
public class AgentOpenAdapter extends RecyclerView.Adapter<AgentOpenAdapter.MyViewHolder> {
    private static final String TAG = "AgentOpenAdapter";
    private Context context;
    private List<AgentOpenBean.GoodsListBean> dataList;
    private boolean isShow = true;//是否显示编辑/完成
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    public AgentOpenAdapter(Context context, List<AgentOpenBean.GoodsListBean> dataList) {
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
                .inflate(R.layout.item_open_agent_cart, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AgentOpenBean.GoodsListBean entityBean = dataList.get(position);
        Glide.with(context).load(entityBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.iv_show_pic);
        holder.tv_goods_name.setText(entityBean.getName());
        holder.tv_vip_price.setText("￥"+String.valueOf(entityBean.getVipPrice()));
        holder.tv_goods_attr.setText(entityBean.getTitle());
//        holder.tv_show_num.setText(""+shoppingCartBean.getSkuNum());

        boolean choosed = entityBean.isCheck();
        if (choosed){
            holder.ckOneChose.setChecked(true);
        }else{
            holder.ckOneChose.setChecked(false);
        }

      //item
        holder.iv_show_pic.setOnClickListener(v -> {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(position);
            }
        });

        holder.ll_right.setOnClickListener(v -> {
            if(onItemClickListener!=null){
                onItemClickListener.onItemClick(position);
            }
        });

        //单选框按钮
        holder.ckOneChose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entityBean.setCheck(((CheckBox) v).isChecked());
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
        TextView tv_goods_name,tv_goods_attr,tv_show_num,tv_vip_price,tv_goods_price;
        LinearLayout ll_right;

      public  MyViewHolder(View itemView) {
            super(itemView);
          ckOneChose=itemView.findViewById(R.id.check_one);
          iv_show_pic=itemView.findViewById(R.id.iv_show_pic);
          tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
          tv_goods_attr = (TextView) itemView.findViewById(R.id.tv_goods_attr);
          tv_show_num = (TextView) itemView.findViewById(R.id.tv_show_num);
          tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
          tv_goods_price = (TextView) itemView.findViewById(R.id.tv_price);
          img_add = (ImageView) itemView.findViewById(R.id.img_add);
          img_sub = (ImageView) itemView.findViewById(R.id.img_sub);
          ll_right = (LinearLayout) itemView.findViewById(R.id.ll_right);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

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
}