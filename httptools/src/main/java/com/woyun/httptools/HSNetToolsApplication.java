package com.woyun.httptools;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Aosen
 */
public class HSNetToolsApplication extends MultiDexApplication {

    public static RequestQueue requestQueue;
    public static Context applicationContext;
    private static HSNetToolsApplication instance;



    @Override
    public void onCreate() {
        super.onCreate();
        initVolley();
        applicationContext = this;
        instance = this;

        //Initialize ImageLoader with configuration.
//        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
//                .createDefault(this);
//        ImageLoader.getInstance().init(configuration);
    }

    /**
     * 返回当前实例
     *
     * @return
     */
    public static HSNetToolsApplication getInstance() {
        return instance;
    }

    /**
     * 初始化volley
     */
    private void initVolley() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getHttpRequestQueue() {
        return requestQueue;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
