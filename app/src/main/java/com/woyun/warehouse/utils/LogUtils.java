package com.woyun.warehouse.utils;

import android.util.Log;

/**
 * log 公共类
 */
public class LogUtils {
    private static final boolean isDeBug = true;

    public static void e(String tag, String msg) {
        if (isDeBug) {
            Log.e(tag, msg);
        }
    }

}
