package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.utils.ToastUtils;


import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * 各地好货 viewPage 适配
 */
public class BannerViewPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<GoodCategoryBean.PageBean.ContentBean.ResListBean> imageBanners;
    private ItemClickListener itemClickListener;

    public void setOnCompletionListener(OnCompletionListener onCompletionListener) {
        this.onCompletionListener = onCompletionListener;
    }

    private OnCompletionListener onCompletionListener;



    public BannerViewPageAdapter(Context context, ViewPager viewPager, List<GoodCategoryBean.PageBean.ContentBean.ResListBean> imageBanners) {
        mContext = context;
        this.imageBanners = imageBanners;

    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
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
            Glide.with(mContext).load(imageBanners.get(position).getImage()).asBitmap().into(imageView);

            if(itemClickListener!=null){
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(position);
                    }
                });

            }
            container.addView(view);

            return view;

//            final ImageView imageView = new ImageView(mContext);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(mContext).load(imageBanners.get(position).getImage())
//                    .skipMemoryCache(true)
//                    .into(imageView);
//            container.addView(imageView);
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemClickListener.onItemClick(position);
//                    ToastUtils.getInstanc(mContext).showToast(imageBanners.get(position).getType());
//                }
//            });
//
//            return imageView;

        } else {//视频
//            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_video, null);
//            VideoView videoView = view.findViewById(R.id.videoView);
//            videoView.setVideoURI(Uri.parse(imageBanners.get(position).getVideoUrl()));
//            ImageView imgPlay = view.findViewById(R.id.img_play);
//            MediaController mediaController = new MediaController(mContext);
//            videoView.setMediaController(mediaController);
//            if(onCompletionListener!=null){
//                imgPlay.setOnClickListener(v -> {
//                    ToastUtils.getInstanc(mContext).showToast("播放按钮点击");
//                    onCompletionListener.onCompletion(videoView, position);
//                });
//            }

            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_video, null);
            JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
            ImageView imgPlay = view.findViewById(R.id.img_play);
            imgPlay.setVisibility(View.GONE);
            jzvdStd.setUp(
                    imageBanners.get(position).getVideoUrl(),
                    "", Jzvd.SCREEN_WINDOW_NORMAL);// SCREEN_WINDOW_LIST
            Glide.with(mContext).load(imageBanners.get(position).getImage()).into(jzvdStd.thumbImageView);
//            if(onCompletionListener!=null){
//                imgPlay.setOnClickListener(v -> {
//                    ToastUtils.getInstanc(mContext).showToast("播放按钮点击");
//                    onCompletionListener.onCompletion(videoView, position);
//                });
//            }
            container.addView(view);
            return view;

//            final VideoView videoView = new VideoView(mContext);
//            videoView.setVideoURI(Uri.parse(imageBanners.get(position).getVideoUrl()));
//            //开始播放
//            videoView.start();
//            container.addView(videoView);
//
//            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//
//                }
//            });
//            return videoView;

        }

    }

  /*  @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public interface ItemClickListener {
        void onItemClick(int index);
    }

    public interface OnCompletionListener {
        void onCompletion(View view, int index);
    }

}
