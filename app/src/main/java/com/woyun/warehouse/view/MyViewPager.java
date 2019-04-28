package com.woyun.warehouse.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.woyun.warehouse.utils.LogUtils;

/**
 * 那是因为viewpager的切换与
 * photoview的缩放有时候会有手势冲突，当缩放的时候会报出异常，根据官方文档提示，需要自己写一个自定义viewpager，
 * 重写onInterceptTouchEvent来解决该问题
 * 对多点触控场景时, {@link android.support.v4.view.ViewPager#onInterceptTouchEvent(MotionEvent)}中
 *                  pointerIndex = -1. 发生IllegalArgumentException: pointerIndex out of range 处理
 ---------------------
 * viewPage
 */
public class MyViewPager extends ViewPager  {


    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //这里就是我们想要处理的问题
            e.printStackTrace();
            LogUtils.e("TAG", "onInterceptTouchEvent: " );

            return false;
        }
    }
}
