package com.woyun.warehouse.vip;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context mContext;
    private List<String> imageBanners;
    private ItemClickListener itemClickListener;
    private int currentPosition = 0;


    public BannerAdapter(Context context, ViewPager viewPager,List<String> imageBanners) {
        mContext = context;
        this.imageBanners=imageBanners;
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(this);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_vip, null);
        ImageView imageView = view.findViewById(R.id.iv_icon);
        Glide.with(mContext).load(imageBanners.get(position)).into(imageView);
        imageView.setOnClickListener(this);
        container.addView(view);
        return view;
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

    @Override
    public void onClick(View v) {
        if (null != itemClickListener) {
            itemClickListener.onItemClick(currentPosition);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public interface ItemClickListener {
        void onItemClick(int index);
    }

}
