package com.woyun.warehouse.mall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
import com.woyun.warehouse.R;
import com.woyun.warehouse.bean.GoodCategoryBean;
import com.woyun.warehouse.bean.ResListBean;
import com.woyun.warehouse.view.DragPhotoView;

import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 *   查看大图 viewPage 适配
 */
public class LookViewPageAdapter extends PagerAdapter {
    private Context mContext;
    private List<ResListBean> imageBanners;
    private ItemClickListener itemClickListener;


    public LookViewPageAdapter(Context context, List<ResListBean> imageBanners) {
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
            PhotoView photoView=new PhotoView(container.getContext());

//            DragPhotoView photoView=new DragPhotoView(container.getContext());
            Glide.with(mContext).load(imageBanners.get(position).getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(photoView);
            //必须添加一个onExitListener,在拖拽到底部时触发.
//            photoView.setOnExitListener(new DragPhotoView.OnExitListener() {
//                @Override
//                public void onExit(DragPhotoView view, float translateX, float translateY, float w, float h) {
//                    itemClickListener.onItemClick(position);
//                }
//            });
//
//            photoView.setOnTapListener(new DragPhotoView.OnTapListener() {
//                @Override
//                public void onTap(DragPhotoView view) {
//                    itemClickListener.onItemClick(position);
//                }
//            });
            if(itemClickListener!=null){
                photoView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClickListener.onItemClick(position);
                    }
                });

            }
            container.addView(photoView);
            return photoView;

        } else {//视频
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_video, null);
            JzvdStd jzvdStd = view.findViewById(R.id.videoplayer);
            ImageView imgPlay = view.findViewById(R.id.img_play);
            imgPlay.setVisibility(View.GONE);
            jzvdStd.setUp(
                    imageBanners.get(position).getVideoUrl(),
                    "", Jzvd.SCREEN_WINDOW_LIST);
            Glide.with(mContext).load(imageBanners.get(position).getImage()).into(jzvdStd.thumbImageView);

            container.addView(view);
            return view;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    public interface ItemClickListener {
        void onItemClick(int index);
    }


}
