package com.woyun.warehouse.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.ShipAddressBean;

import java.util.List;

public class PopDownAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ShipAddressBean.InvoiceListBean> mDatas;

    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public PopDownAdapter(Context context, List<ShipAddressBean.InvoiceListBean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_adapter, parent, false); //加载布局
            holder = new ViewHolder();
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_text);
            convertView.setTag(holder);
        } else {   //else里面说明，convertView已经被复用了，说明convertView中已经设置过tag了，即holder
            holder = (ViewHolder) convertView.getTag();
        }
        ShipAddressBean.InvoiceListBean bean = mDatas.get(position);
        holder.titleTv.setText(bean.getName());
        return convertView;
    }

    //这个ViewHolder只能服务于当前这个特定的adapter，因为ViewHolder里会指定item的控件，不同的ListView，item可能不同，所以ViewHolder写成一个私有的类
    private class ViewHolder {
        TextView titleTv;

    }
}
