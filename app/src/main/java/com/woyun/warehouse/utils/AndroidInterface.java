package com.woyun.warehouse.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.just.agentweb.AgentWeb;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import cc.shinichi.library.bean.ImageInfo;

/**
 * Created by cenxiaozhong on 2017/5/14.
 *  source code  https://github.com/Justson/AgentWeb
 */

public class AndroidInterface {

    private Handler deliver = new Handler(Looper.getMainLooper());
    private AgentWeb agent;
    private Context context;
    final List<ImageInfo> imageInfoList = new ArrayList<>();
    public AndroidInterface(AgentWeb agent, Context context) {
        this.agent = agent;
        this.context = context;
    }


    //图片
    @JavascriptInterface
    public void callAndroidImage(final String msg) {

        deliver.post(new Runnable() {
            @Override
            public void run() {
                showImage(msg);
                Log.i("Info", "main Thread:" + Thread.currentThread());
//                Toast.makeText(context.getApplicationContext(), "" + msg, Toast.LENGTH_LONG).show();
            }
        });


        Log.i("Info", "Thread:" + Thread.currentThread());

    }

    //视频
    @JavascriptInterface
    public void callAndroidVideo(final String url) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                if(!TextUtils.isEmpty(url)){
                //系统播放器
                String extension = MimeTypeMap.getFileExtensionFromUrl(url);
                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
                mediaIntent.setDataAndType(Uri.parse(url), mimeType);
                context.startActivity(mediaIntent);
                }
                Log.i("Info", "main Thread:" + Thread.currentThread());
//                Toast.makeText(context.getApplicationContext(), "" + url, Toast.LENGTH_LONG).show();
            }
        });


        Log.i("Info", "Thread:" + Thread.currentThread());

    }

    private void showImage(String msg){
        imageInfoList.clear();
        ImageInfo imageInfo=new ImageInfo();
        imageInfo.setOriginUrl(msg);
        imageInfoList.add(imageInfo);
        ImagePreview
                .getInstance()
                .setContext(context)
                .setIndex(0)
                .setImageInfoList(imageInfoList)
                .setShowDownButton(true)
                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                .setFolderName("BigImageViewDownload")
                .setScaleLevel(1, 3, 8)
                .setZoomTransitionDuration(300)
                .setEnableClickClose(true)// 是否启用点击图片关闭。默认启用
                .setEnableDragClose(true)// 是否启用上拉/下拉关闭。默认不启用
                .setShowCloseButton(false)// 是否显示关闭页面按钮，在页面左下角。默认显示
                .setCloseIconResId(R.drawable.ic_action_close)// 设置关闭按钮图片资源
                .setShowDownButton(false)// 是否显示下载按钮，在页面右下角。默认显示
                .setDownIconResId(R.drawable.icon_download_new)// 设置下载按钮图片资源
                .setShowIndicator(false)// 设置是否显示顶部的指示器（1/9）。默认显示
                .start();
    }
}
