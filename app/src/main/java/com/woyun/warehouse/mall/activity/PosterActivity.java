package com.woyun.warehouse.mall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;


/**
 * 海报
 */
public class PosterActivity extends BaseActivity {
    private static final String TAG = "PosterActivity";
    private static final int REFRESH_COMPLETE = 1000;
    private static final int REFRESH_COMPLETE_CIRCLE = 1001;
    private static final int REFRESH_COMPLETE_SAVE = 1002;

    @BindView(R.id.imgView)
    ImageView imgView;
    @BindView(R.id.ll_weixin)
    LinearLayout llWeixin;
    @BindView(R.id.ll_weixin_circle)
    LinearLayout llWeixinCircle;
    @BindView(R.id.ll_save)
    LinearLayout llSave;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private String shareImage;
    private int goodsId;//商品id

    private Bitmap mBitmapCover;
    private IWXAPI iwxApi;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
//                    iwxApi.sendReq(shareWxUrl(goodesWebUrl, shareTile, shareContent, 0, shareIconUrl));
                    shareUrlImage(mBitmapCover, true);
                    break;
                case REFRESH_COMPLETE_CIRCLE:
                    shareUrlImage(mBitmapCover, false);
                    break;
                case REFRESH_COMPLETE_SAVE:
                    onSaveBitmap(mBitmapCover, PosterActivity.this);
                    break;
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        //进入退出效果 注意这里 创建的效果对象是 Fade()
        getWindow().setEnterTransition(new Fade().setDuration(260));
        getWindow().setExitTransition(new Fade().setDuration(260));
        ButterKnife.bind(this);
        iwxApi = WXAPIFactory.createWXAPI(PosterActivity.this, Constant.WX_APP_ID);

        goodsId = getIntent().getIntExtra("share_goods_id", 0);
        initData(goodsId, loginUserId);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    private void initData(int goodsId, String userId) {
        ModelLoading.getInstance(PosterActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("goodsId", goodsId);
            params.put("userid", userId);
            RequestInterface.goodsPrefix(PosterActivity.this, params, TAG, ReqConstance.I_GOODS_SHARE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(PosterActivity.this).closeLoading();
                    if (code == 0 && jsonArray.length() > 0) {
                        try {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                            shareImage = jsonObject.getString("shareImage");
                            Glide.with(PosterActivity.this).load(shareImage).into(new GlideDrawableImageViewTarget(imgView) {
                                @Override
                                public void onLoadStarted(Drawable placeholder) {
                                    // 开始加载图片
                                    progressBar.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                    progressBar.setVisibility(View.GONE);


                                }

                                @Override
                                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    super.onResourceReady(resource, glideAnimation);
                                    // 图片加载完成
                                    imgView.setImageDrawable(resource);
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtils.getInstanc(PosterActivity.this).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(PosterActivity.this).closeLoading();
                    ToastUtils.getInstanc(PosterActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.ll_weixin, R.id.ll_weixin_circle, R.id.ll_save, R.id.tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_weixin:
                returnBitMap(shareImage, false, true);
                break;
            case R.id.ll_weixin_circle:
                returnBitMap(shareImage, false, false);
                break;
            case R.id.ll_save:
                ModelLoading.getInstance(PosterActivity.this).showLoading("", true);
                returnBitMap(shareImage, true, false);
                break;
            case R.id.tv_cancle:
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
        }
    }


    /**
     * 分享图片 网络url-->bmp
     * true 分享到朋友，false分享到朋友圈
     *
     * @return
     */
    public void shareUrlImage(Bitmap bmp, boolean isFriends) {
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbdata = Bitmap.createScaledBitmap(bmp, 150, 150, true);
        bmp.recycle();//释放图像所占用的图片内存资源
        msg.thumbData = bmpToByteArray(thumbdata, true);//设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isFriends ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxApi.sendReq(req);

    }

    public void returnBitMap(final String url, boolean isSave, boolean isFriend) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;

                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    mBitmapCover = BitmapFactory.decodeStream(is);
                    is.close();
                    if (isSave) {
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE_SAVE);
                        return;
                    }
                    Log.e(TAG, "run:====== ");
                    if (isFriend) {
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                    } else {
                        mHandler.sendEmptyMessage(REFRESH_COMPLETE_CIRCLE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Android保存图片到系统图库
     *
     * @param mBitmap
     * @param context
     */
    private void onSaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/BSC/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "BSC");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = goodsId + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelLoading.getInstance(PosterActivity.this).closeLoading();
        ToastUtils.getInstanc(PosterActivity.this).showToast("已保存");
        // 第二步：其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
////   /storage/emulated/0/Boohee/1493711988333.jpg
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        // 第三步：最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
        //context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }


}
