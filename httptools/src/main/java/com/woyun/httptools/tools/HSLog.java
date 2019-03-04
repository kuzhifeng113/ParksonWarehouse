package com.woyun.httptools.tools;

import android.util.Log;

/**
 * Created by Aosen
 */

public class HSLog {
    private static boolean isDebug = true;

    public static void print(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }
}
