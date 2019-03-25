package com.woyun.warehouse.vip;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 切换动画
 */
public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

//自由控制缩放比例
        private static final float MAX_SCALE = 1.2f;//1.2f
        private static final float MIN_SCALE = 0.85f;//0.85f

        @Override
        public void transformPage(View page, float position) {

            if (position <= 1) {

                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);

                page.setScaleX(scaleFactor);

                if (position > 0) {
                    page.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    page.setTranslationX(scaleFactor * 2);
                }
                page.setScaleY(scaleFactor);
            } else {

                page.setScaleX(MIN_SCALE);
                page.setScaleY(MIN_SCALE);
            }
        }

}
