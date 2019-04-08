package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.bean.ResListBean;
import com.woyun.warehouse.utils.ToastUtils;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 商品详情 viewPage 适配
 */
public class NativeViewPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<ResListBean> imageBanners;
    private OnItemClickListener onItemClickListener;

    public NativeViewPageAdapter(Context context, List<ResListBean> imageBanners) {
        mContext = context;
        this.imageBanners = imageBanners;

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return imageBanners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (imageBanners.get(position).getType() == 1) {//图片
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, null);
            ImageView imageView = view.findViewById(R.id.iv_icon);
            ProgressBar progressBar=view.findViewById(R.id.progress_bar);
            Glide.with(mContext).load(imageBanners.get(position).getImage()).into(new GlideDrawableImageViewTarget(imageView) {
                @Override
                public void onLoadStarted(Drawable placeholder) {
                    // 开始加载图片
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    progressBar.setVisibility(View.GONE);


                }

                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    super.onResourceReady(resource, glideAnimation);
                    // 图片加载完成
                    imageView.setImageDrawable(resource);
                    progressBar.setVisibility(View.GONE);
                }
            });


            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(position);
                }
            });

            container.addView(view);
            return view;

        } else {//视频
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_video, null);
            JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
            ImageView imgPlay = view.findViewById(R.id.img_play);
            imgPlay.setVisibility(View.GONE);
            jzvdStd.setUp(
                    imageBanners.get(position).getVideoUrl(),
                    "", Jzvd.SCREEN_WINDOW_NORMAL);// SCREEN_WINDOW_LIST
            Glide.with(mContext).load(imageBanners.get(position).getImage()).centerCrop().into(jzvdStd.thumbImageView);

            container.addView(view);
            return view;
        }

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public interface OnItemClickListener {
        void onItemClick(int index);
    }

}
