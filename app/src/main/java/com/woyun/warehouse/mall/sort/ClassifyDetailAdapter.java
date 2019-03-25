package com.woyun.warehouse.mall.sort;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;

import java.util.List;


public class ClassifyDetailAdapter extends RvAdapter<RightBean> {
    private Context mContext;
    public ClassifyDetailAdapter(Context context, List<RightBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }


    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_right_title : R.layout.item_classify_detail;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    public class ClassifyHolder extends RvHolder<RightBean> {
        TextView tvName;
        ImageView avatar;
        TextView tvTitle;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvName = (TextView) itemView.findViewById(R.id.tvName);
                    avatar = (ImageView) itemView.findViewById(R.id.iv_show_pic);
                    break;
            }

        }

        @Override
        public void bindHolder(RightBean sortBean, int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText(sortBean.getName());
                    break;
                case 1:
                    tvName.setText(sortBean.getName());
                    Glide.with(mContext).load(sortBean.getImgsrc()).asBitmap().placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(avatar);
                    break;
            }

        }
    }
}
