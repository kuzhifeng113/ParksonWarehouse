package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.ContentListBean;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;


/**
 * 商品详情content adapter
 */
public class NativeContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "NativeContentAdapter";
    private Context context;
    private List<ContentListBean> listBeanList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NativeContentAdapter(Context context, List<ContentListBean> listBeanList) {
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
        if (viewType == 1) {//图片
            return new OneViewHolder(layoutInflater.inflate(R.layout.item_content_one, null, false));
        }
        if (viewType == 2) {//  视频
            return new TwoViewHolder(layoutInflater.inflate(R.layout.item_content_two, null, false));
        }
        if (viewType == 3) {//富文本
            return new ThreeViewHolder(layoutInflater.inflate(R.layout.item_content_three, null, false));
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
        final ContentListBean contentListBean = listBeanList.get(position);

        switch (getItemViewType(position)) {
            case 1:
                OneViewHolder oneViewHolder = (OneViewHolder) holder;
//                Glide.with(context).load(packListBean.getImage()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.img_default)
//                        .error(R.mipmap.img_default).into(zeroViewHolder.img_left);
                Glide.with(context).load(contentListBean.getImage()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.img_default)
                        .fitCenter().error(R.mipmap.img_default).into(oneViewHolder.imgView);
                //
                oneViewHolder.imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(position);
                    }
                });

                break;
            case 2:
                TwoViewHolder twoViewHolder = (TwoViewHolder) holder;
                twoViewHolder.jzvdStd.setUp(
                        contentListBean.getVideoUrl(),
                        "", Jzvd.SCREEN_WINDOW_LIST);
//                twoViewHolder.jzvdStd.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(contentListBean.getImage()).centerCrop().into( twoViewHolder.jzvdStd.thumbImageView);

                break;
            case 3:
                ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
                String html=contentListBean.getContent();
                CharSequence charSequence= Html.fromHtml(html);
                threeViewHolder.tvContent.setText(charSequence);
                //该语句在设置后必加，不然没有任何效果h
                threeViewHolder.tvContent.setMovementMethod(LinkMovementMethod.getInstance());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return listBeanList.size();
    }


    /**
     * 第一种ViewHolder
     */
    public class OneViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;

        public OneViewHolder(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }

    /**
     * 第二种ViewHolder
     */
    public class TwoViewHolder extends RecyclerView.ViewHolder {

        private JzvdStd jzvdStd;

        public TwoViewHolder(View itemView) {
            super(itemView);
            jzvdStd = itemView.findViewById(R.id.videoplayer);

        }
    }

    /**
     * 第三种ViewHolder
     */
    public class ThreeViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;

        public ThreeViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
}


