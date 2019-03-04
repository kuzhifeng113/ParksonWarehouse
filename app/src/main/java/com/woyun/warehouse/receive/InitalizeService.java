package com.woyun.warehouse.receive;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * 冷启动 后台初始化第三方sdk
 */
public class InitalizeService extends IntentService {
    //application 调用  InitalizeService.start(context);
    private static final String ACTION_INIT_WHEN_APP_CREATE="com.woyun.warehouse";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public InitalizeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null){
            String action = intent.getAction();
            if(action.equals(ACTION_INIT_WHEN_APP_CREATE)){
                performInit();
            }
        }
    }

    /**
     *
     */
    public static void start(Context context){
        Intent intent=new Intent(context,InitalizeService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    private void performInit() {
        //第三方sdk 初始化
    }
}
