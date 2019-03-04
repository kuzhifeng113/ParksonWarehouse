package com.woyun.httptools.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by Aosen
 */
public class HSTools {
    public static void hidenKeyboard(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS,
//                InputMethodManager.SHOW_IMPLICIT);
        if (context.getCurrentFocus() != null && context.getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取6位随机数
     *
     * @return
     */
    public static String getRandCode() {
        // Random rd = new Random(100000);
        return ((int) ((Math.random() * 9 + 1) * 100000)) + "";
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale+0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * @param img_star1
     * @param img_star2
     * @param img_star3
     * @param img_star4
     * @param img_star5
     * @param zp        评分，变成星星显示 d8_icon 整颗，d9_icon 半颗
     */
//    public static void starUI(ImageView img_star1, ImageView img_star2, ImageView img_star3, ImageView img_star4, ImageView img_star5, float zp) {
//        img_star1.setVisibility(View.GONE);
//        img_star2.setVisibility(View.GONE);
//        img_star3.setVisibility(View.GONE);
//        img_star4.setVisibility(View.GONE);
//        img_star5.setVisibility(View.GONE);
//
//        if (zp > 4.5) {//5ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.VISIBLE);
//            img_star5.setVisibility(View.VISIBLE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//
//        if (zp > 4) {//4.5ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.VISIBLE);
//            img_star5.setVisibility(View.VISIBLE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d9_icon);
//            return;
//        }
//        if (zp > 3.5) {//4ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.VISIBLE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//        if (zp > 3) {//3.5ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.VISIBLE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d9_icon);
//            img_star5.setImageResource(R.drawable.d9_icon);
//            return;
//        }
//        if (zp > 2.5) {//3ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.GONE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//        if (zp > 2) {//2.5
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.VISIBLE);
//            img_star4.setVisibility(View.GONE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d9_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//        if (zp > 1.5) {//2ke
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.GONE);
//            img_star4.setVisibility(View.GONE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//        if (zp > 1) {
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.VISIBLE);
//            img_star3.setVisibility(View.GONE);
//            img_star4.setVisibility(View.GONE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d9_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//        if (zp < 1 || zp == 1) {
//            img_star1.setVisibility(View.VISIBLE);
//            img_star2.setVisibility(View.GONE);
//            img_star3.setVisibility(View.GONE);
//            img_star4.setVisibility(View.GONE);
//            img_star5.setVisibility(View.GONE);
//            img_star1.setImageResource(R.drawable.d8_icon);
//            img_star2.setImageResource(R.drawable.d8_icon);
//            img_star3.setImageResource(R.drawable.d8_icon);
//            img_star4.setImageResource(R.drawable.d8_icon);
//            img_star5.setImageResource(R.drawable.d8_icon);
//            return;
//        }
//    }
}