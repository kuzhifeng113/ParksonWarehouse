package com.woyun.warehouse.mall.sort;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.woyun.warehouse.R;

import java.util.List;

public class SortAdapter extends RvAdapter<SortBean> {

    private int checkedPosition;//选中 position

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortAdapter(Context context, List<SortBean> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_listview_text;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);
    }

    private class SortHolder extends RvHolder<SortBean> {

        private TextView tvName;
        private View mView,viewRed;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_category_name);
            viewRed=itemView.findViewById(R.id.view_red);
        }

        @Override
        public void bindHolder(SortBean sortBean, int position) {
            tvName.setText(sortBean.getName());
            if (position == checkedPosition) {
                viewRed.setVisibility(View.VISIBLE);
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvName.setTextColor(Color.parseColor("#F5336F"));
                tvName.setTextSize(17);
            } else {
                viewRed.setVisibility(View.INVISIBLE);
                mView.setBackgroundColor(Color.parseColor("#f9f9f9"));
                tvName.setTextColor(Color.parseColor("#AFAFAF"));
                tvName.setTextSize(14);
            }
        }
    }
}
