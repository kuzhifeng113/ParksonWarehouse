package com.woyun.warehouse.my.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.OrderListBean;

import java.util.List;

/**
 * 全部订单
 */
public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.MyViewHolder> {
    private Context context;
    private List<OrderListBean.ContentBean> dataList;
    private OnItemClickListener onItemClickListener;

    private OnButtonClickListener onButtonClickListener;


    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public AllOrderAdapter(Context context, List<OrderListBean.ContentBean> dataList) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_my_order, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        OrderListBean.ContentBean contentBean = dataList.get(position);
        List<OrderListBean.ContentBean.BillDetailListBean> billDetailList = contentBean.getBillDetailList();
        RecyclerView recyclerViewBill = holder.recyclerView;

        OrderListBillAdapter horizontalAdapter = new OrderListBillAdapter(context, billDetailList,position);

        recyclerViewBill.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewBill.setAdapter(horizontalAdapter);

        holder.tv_gongji.setText("共计" + contentBean.getBillDetailList().size() + "件商品");
        holder.tv_heji_price.setText("￥ " + String.valueOf(contentBean.getTotalMoney()));
        int orderStatus = contentBean.getBillStatus();//订单状态
        //是否支付
        if (!contentBean.isStatus()) {//未支付
            holder.tv_order_status.setText("待付款");
            holder.ll_dai_fukuan.setVisibility(View.VISIBLE);
            holder.ll_yi_compelte.setVisibility(View.GONE);
            holder.ll_yi_delivery.setVisibility(View.GONE);
            holder.ll_yi_cancel.setVisibility(View.GONE);

            if (onButtonClickListener != null) {
                holder.tv_cancel_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onButtonClick(holder.tv_cancel_order, position);
                    }
                });
            }

            if (onButtonClickListener != null) {
                holder.tv_pay_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onButtonClickListener.onButtonClick(holder.tv_pay_order, position);
                    }
                });
            }

            if (orderStatus == 3) {
                holder.tv_order_status.setText("已取消");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.GONE);
                holder.ll_yi_delivery.setVisibility(View.GONE);
                holder.ll_yi_cancel.setVisibility(View.VISIBLE);
                //删除订单
                if (onButtonClickListener != null) {
                    holder.tv_delete_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_delete_order, position);
                        }
                    });
                }
            }
        } else {
            //已支付 待处理
            if (orderStatus == 0) {// 0待处理，1已发货，2已收货，3取消订单
                holder.tv_order_status.setText("待发货");
//                holder.tv_order_status.setText("待处理");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.GONE);
                holder.ll_yi_delivery.setVisibility(View.GONE);
                holder.ll_yi_cancel.setVisibility(View.GONE);
            } else if (orderStatus == 1) {
                holder.tv_order_status.setText("已发货");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.GONE);
                holder.ll_yi_delivery.setVisibility(View.VISIBLE);
                holder.ll_yi_cancel.setVisibility(View.GONE);

                //确认收货
                if (onButtonClickListener != null) {
                    holder.tv_confirm_receipt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_confirm_receipt, position);
                        }
                    });
                }

                //查看物流
                if (onButtonClickListener != null) {
                    holder.tv_look_logistics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_look_logistics, position);
                        }
                    });
                }

            } else if (orderStatus == 2) {//已收货
                holder.tv_order_status.setText("已完成");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.VISIBLE);
                holder.ll_yi_delivery.setVisibility(View.GONE);
                holder.ll_yi_cancel.setVisibility(View.GONE);
                //申请售后
                if (onButtonClickListener != null) {
                    holder.tv_request_sh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_request_sh, position);
                        }
                    });
                }
                //查看物流
                if(onButtonClickListener!=null){
                    holder.tv_check_logistics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_check_logistics,position);
                        }
                    });
                }
                //删除订单
                if(onButtonClickListener!=null){
                    holder.tv_delete_ywcorder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_delete_ywcorder,position);
                        }
                    });
                }
            }else if(orderStatus==4){//售后处理中
                holder.tv_request_sh.setText("联系客服");
                holder.tv_order_status.setText("售后处理中");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.VISIBLE);
                holder.ll_yi_delivery.setVisibility(View.GONE);
                holder.ll_yi_cancel.setVisibility(View.GONE);

                //联系客服
                if (onButtonClickListener != null) {
                    holder.tv_request_sh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_request_sh, position);
                        }
                    });
                }

                //查看物流
                if(onButtonClickListener!=null){
                    holder.tv_check_logistics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_check_logistics,position);
                        }
                    });
                }
                //删除订单
                if(onButtonClickListener!=null){
                    holder.tv_delete_ywcorder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onButtonClickListener.onButtonClick(holder.tv_delete_ywcorder,position);
                        }
                    });
                }



            }else if(orderStatus==5){
                holder.tv_order_status.setText("已退款");
                holder.ll_dai_fukuan.setVisibility(View.GONE);
                holder.ll_yi_compelte.setVisibility(View.GONE);
                holder.ll_yi_delivery.setVisibility(View.GONE);
                holder.ll_yi_cancel.setVisibility(View.VISIBLE);
            }
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
        RecyclerView recyclerView;
        TextView tv_gongji, tv_heji_price, tv_order_status;
        LinearLayout ll_dai_fukuan, ll_yi_compelte, ll_yi_delivery, ll_yi_cancel;
        TextView tv_cancel_order, tv_pay_order, tv_request_sh, tv_confirm_receipt, tv_look_logistics, tv_delete_order,tv_check_logistics,tv_delete_ywcorder;


        public MyViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.recycler_view_bill);
            tv_gongji = itemView.findViewById(R.id.tv_gongji);
            tv_heji_price = itemView.findViewById(R.id.tv_heji_price);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);

            ll_dai_fukuan = (LinearLayout) itemView.findViewById(R.id.ll_dai_fukuan);
            ll_yi_compelte = (LinearLayout) itemView.findViewById(R.id.ll_yi_compelte);
            ll_yi_delivery = (LinearLayout) itemView.findViewById(R.id.ll_yi_delivery);
            ll_yi_cancel = (LinearLayout) itemView.findViewById(R.id.ll_yi_cancel);

            tv_cancel_order = (TextView) itemView.findViewById(R.id.tv_cancel_order);
            tv_pay_order = (TextView) itemView.findViewById(R.id.tv_pay_order);

            tv_request_sh = (TextView) itemView.findViewById(R.id.tv_request_sh);

            tv_confirm_receipt = (TextView) itemView.findViewById(R.id.tv_confirm_receipt);
            tv_look_logistics = (TextView) itemView.findViewById(R.id.tv_look_logistics);
            tv_delete_order = (TextView) itemView.findViewById(R.id.tv_delete_order);
            tv_delete_ywcorder = (TextView) itemView.findViewById(R.id.tv_delete_ywcorder);
            tv_check_logistics = (TextView) itemView.findViewById(R.id.tv_check_logistics);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }
}
