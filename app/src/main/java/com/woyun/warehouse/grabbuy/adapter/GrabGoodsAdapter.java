package com.woyun.warehouse.grabbuy.adapter;

import android.content.Context;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GrabGoodsBean;
import com.woyun.warehouse.utils.TimeTools;

import java.text.NumberFormat;
import java.util.List;

/**
 *  抢购商品适配
 */
public class GrabGoodsAdapter extends RecyclerView.Adapter<GrabGoodsAdapter.MyViewHolder> {
    private static final String TAG = "GrabGoodsAdapter";
    private Context context;
    private List<GrabGoodsBean> listData;
    private OnItemClickListener onItemClickListener;
    private OnButtonClickListener onButtonClickListener;

    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public GrabGoodsAdapter(Context context, List<GrabGoodsBean> listData) {
        this.listData = listData;
        this.context = context;
        countDownMap = new SparseArray<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mall_grap, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GrabGoodsBean goodsListBean = listData.get(position);
        //剩余时间
        long time = goodsListBean.getEndTime();
        time = time - System.currentTimeMillis();
        //将前一个缓存清除
        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
        }
        if (time > 0) {
            holder.countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.tv_end_time.setText("距结束剩余:"+TimeTools.getCountTimeByLong(millisUntilFinished));

                }
                public void onFinish() {
                    holder.tv_end_time.setText("距结束剩余:"+"00:00:00");

                }
            }.start();

            countDownMap.put(holder.tv_end_time.hashCode(), holder.countDownTimer);
        } else {
            holder.tv_end_time.setText("距结束剩余:"+"00:00:00");
        }

        Glide.with(context).load(goodsListBean.getImage()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(holder.round_img);

        holder.tv_vip_price.setText(String.valueOf(goodsListBean.getVipPrice()));
        holder.tv_goods_price.setText("原价:" + goodsListBean.getPrice());
        holder.tv_goods_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        holder.tv_goods_title.setText(goodsListBean.getName());
        holder.tv_goods_title.setText(goodsListBean.getTitle());

        int kuCun=goodsListBean.getStock();
        int sellNum=goodsListBean.getSellNum();

        //已抢够
        holder.tv_yiqiang.setText("已抢购"+goodsListBean.getSellNum()+"件");
        //剩余
        holder.tv_sheng.setText("剩余"+(kuCun-sellNum)+"件");

        //算进度比
        if(sellNum<kuCun){
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(2);//保留2位小数
            String percentage=numberFormat.format((float) sellNum / (float) kuCun  * 100);
            if(percentage.contains(".")){
                String  result= percentage.substring(0,percentage.indexOf("."));
                holder.preview_progressBar.setProgress(Integer.valueOf(result));
            }else{
                holder.preview_progressBar.setProgress(Integer.valueOf(percentage));
            }
        }else{
            holder.preview_progressBar.setProgress(100);
        }


        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
        }

        if (onButtonClickListener != null) {

            holder.btn_buy.setOnClickListener(v ->
                    onButtonClickListener.onButtonClick(v, position)
            );
            holder.round_img.setOnClickListener(v -> onButtonClickListener.onButtonClick(v,position));
        }


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView round_img;
        TextView tv_vip_price, tv_goods_price, tv_goods_title,tv_title_des;
        ProgressBar preview_progressBar;
        TextView tv_yiqiang,tv_sheng,tv_end_time;
        public CountDownTimer countDownTimer;
        Button btn_buy;
        public MyViewHolder(View itemView) {
            super(itemView);

            round_img=itemView.findViewById(R.id.round_img);
            tv_vip_price = (TextView) itemView.findViewById(R.id.tv_vip_price);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_title = (TextView) itemView.findViewById(R.id.tv_goods_title);
            tv_title_des = (TextView) itemView.findViewById(R.id.tv_title_des);
            preview_progressBar =  itemView.findViewById(R.id.preview_progressBar);

            tv_yiqiang = (TextView) itemView.findViewById(R.id.tv_yiqiang);
            tv_sheng = (TextView) itemView.findViewById(R.id.tv_sheng);
            tv_end_time = (TextView) itemView.findViewById(R.id.tv_end_time);

            btn_buy = itemView.findViewById(R.id.btn_buy);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        Log.e("TAG",  "size :  " + countDownMap.size());
        for (int i = 0,length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();

            }
        }
    }
    public void destoryCancelAllTimers(){
        if (countDownMap == null) {
            return;
        }
        countDownMap.clear();
    }
}
