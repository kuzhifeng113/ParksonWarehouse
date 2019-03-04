package com.woyun.warehouse.baseparson.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.VoteBean;

import java.util.List;

public class HorizontalListViewAdapter  extends BaseAdapter{

    private  Context context;
    private  int screenWidth;
    private List<VoteBean> listData;


    public HorizontalListViewAdapter(Context context, int screenWidth, List<VoteBean> listData) {
        this.context = context;
        this.screenWidth = screenWidth;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh =null;
        if(convertView==null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_voteold_ranking, null);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            vh.tv_yprice = (TextView) convertView.findViewById(R.id.tv_y_price);
            vh.tv_yprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
            vh.tv_num_buy = (TextView) convertView.findViewById(R.id.tv_num_buy);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        VoteBean voteBean = listData.get(position);
        vh.tv_name.setText(voteBean.getName());
        vh.tv_price.setText(voteBean.getPrice());
        vh.tv_yprice.setText(voteBean.getY_price());
        vh.tv_num_buy.setText(voteBean.getHowBuy());

        return convertView;

    }


    static class ViewHolder {
        TextView tv_name,tv_price,tv_yprice,tv_num_buy;

    }
}
