package com.woyun.warehouse.my.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.ShapeFriendBean;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.mall.activity.GoodsDetailActivity;
import com.woyun.warehouse.my.MyFragment;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray;
import static com.woyun.warehouse.utils.ShareWx.bmpToByteArray2;
import static com.woyun.warehouse.utils.ShareWx.buildTransaction;


/**
 * 分享
 */
public class ShareActivity extends BaseActivity implements CommonPopupWindow.ViewInterface,WbShareCallback {
    private static final String TAG = "ShareActivity";
    private static final int THUMB_SIZE = 150;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.btn_share)
    ImageView btnShare;
    @BindView(R.id.tv_num_people)
    TextView tvNumPeople;
    @BindView(R.id.tv_num_money)
    TextView tvNumMoney;
    @BindView(R.id.btn_yq_friends)
    ImageView btnYqFriends;
    private CommonPopupWindow popupWindow;
    private IWXAPI iwxApi;
    private Tencent mTencent;
    private Activity mContext;
    private String shareTile, shareContent, shareDownUrl, shareIcon;
    private int shareType;
    private String nickName;

//    private String dowmUrl="http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg";//二维码图片地址
    private String dowmUrl;//二维码图片地址

    private ShareQQListener mIUiListener;
    private WbShareHandler shareHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);
        mContext = ShareActivity.this;
        mIUiListener = new ShareQQListener();
        iwxApi = WXAPIFactory.createWXAPI(ShareActivity.this, Constant.WX_APP_ID);
        iwxApi.registerApp(Constant.WX_APP_ID);
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());

        //微博
        shareHandler = new WbShareHandler(ShareActivity.this);
        shareHandler.registerApp();

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

        getData();
    }

    private void getData() {
        ModelLoading.getInstance(ShareActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", loginUserId);
            RequestInterface.userPrefix(ShareActivity.this, params, TAG, ReqConstance.I_USER_SHARE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray jsonArray) {
                    ModelLoading.getInstance(ShareActivity.this).closeLoading();
                    tokenTimeLimit(ShareActivity.this, responseCode);
                    if (responseCode == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            ShapeFriendBean shapeFriendBean = gson.fromJson(object.toString(), ShapeFriendBean.class);
                            tvNumMoney.setText(shapeFriendBean.getShareCb());
                            tvNumPeople.setText(shapeFriendBean.getShareNum());
                            Glide.with(ShareActivity.this).load(shapeFriendBean.getShareImage()).into(imgShare);
                            dowmUrl=shapeFriendBean.getShareImage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(ShareActivity.this).closeLoading();
                    btnShare.setEnabled(false);
                    btnYqFriends.setEnabled(false);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        shareTile = (String) SPUtils.getInstance(ShareActivity.this).get(Constant.SHARE_TITLE, "");
        shareContent = (String) SPUtils.getInstance(ShareActivity.this).get(Constant.SHARE_CONTENT, "");
        shareIcon = (String) SPUtils.getInstance(ShareActivity.this).get(Constant.SHARE_ICON, "");
        shareDownUrl = (String) SPUtils.getInstance(ShareActivity.this).get(Constant.SHARE_URL, "");
        nickName = (String) SPUtils.getInstance(ShareActivity.this).get(Constant.USER_NICK_NAME, "");
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    /**
     * 弹窗
     * type ==0  图片   type==1 链接
     */
    private void showSharePop(int type) {
        shareType = type;
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(ShareActivity.this).inflate(R.layout.popup_share, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(ShareActivity.this)
                .setView(R.layout.popup_share)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        ImageView shareWeiXin = (ImageView) view.findViewById(R.id.img_share_weixin);
        ImageView shareCircle = (ImageView) view.findViewById(R.id.img_share_circle);
        ImageView shareWb = (ImageView) view.findViewById(R.id.img_share_wb);
        ImageView shareQQ = (ImageView) view.findViewById(R.id.img_share_qq);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);

        shareWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                if (shareType == 0) {
//                    shareUrlImage("https://dymeet.oss-cn-shenzhen.aliyuncs.com/comm/logo-100.jpg",true);
                        shareImageSdWx(Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg",true);
                } else {
                    iwxApi.sendReq(shareWxUrl(shareDownUrl + "?share=" + loginUserId, shareTile,nickName+shareContent, 0, shareIcon));
                }
            }
        });

        shareCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                Log.e(TAG, "onClick: " + shareDownUrl + loginUserId + shareTile + shareContent + shareIcon);
                if (shareType == 0) {
//                    shareImage("https://timgsa.baidu.com/timg?image.jpg",false);
                    shareImageSdWx(Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg",false);
                } else {
                    iwxApi.sendReq(shareWxUrl(shareDownUrl + "?sharekey=" + loginUserId,
                            shareTile, nickName+shareContent, 1, shareIcon));
                }
            }
        });
        //微博
        shareWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                if(shareType==0){//图片
                    sendMessage(true,true);
                }else{//链接
                    /**
                     * 第三方应用发送请求消息到微博，唤起微博分享界面。
                     */
                    sendMessage(true,false);
                }
            }
        });

        shareQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                Log.e(TAG, "onClick:url =====" + shareDownUrl + "?sharekey=" + loginUserId);
                Log.e(TAG, "onClick:url =====" + shareIcon);
                final Bundle params = new Bundle();
                if(shareType==0){//纯图片分享
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg");//本地图片
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//                    params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//可选
                    mTencent.shareToQQ(mContext, params,  mIUiListener);


                }else{//链接分享
                    params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
                    params.putString(QQShare.SHARE_TO_QQ_TITLE, shareTile);
                    params.putString(QQShare.SHARE_TO_QQ_SUMMARY, shareContent);
                    params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareDownUrl + "?sharekey=" + loginUserId);
                    params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));
                    params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, shareIcon);// 网络图片地址　
                    Log.e(TAG, "onClick:### "+shareIcon );
                    //params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
//                        params.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其他附加功能");
                    // 分享操作要在主线程中完成
                mTencent.shareToQQ(mContext, params, mIUiListener);
//                ToastUtils.getInstanc(MyCenterActivity.this).showToast("qq");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 资源本地图片
     * @param isShareFriend true 分享到朋友，false分享到朋友圈
     */
    private void share2Wx(boolean isShareFriend) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo);
        WXImageObject imgObj = new WXImageObject(bitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);//缩略图大小
        bitmap.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxApi.sendReq(req);
    }

    /**
     * 分享图片 网络url
     * true 分享到朋友，false分享到朋友圈
     * @return
     */
    public void shareUrlImage(String imgurl, boolean isFriends) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    WXImageObject imgObj = new WXImageObject();
                    imgObj.setImagePath(imgurl);

                    WXMediaMessage msg = new WXMediaMessage();
                    msg.mediaObject = imgObj;

                    Bitmap bitmap = BitmapFactory.decodeStream(new URL(imgurl).openStream());
                    Bitmap thumbdata = Bitmap.createScaledBitmap(bitmap, 120, 150, true);
                    bitmap.recycle();//释放图像所占用的图片内存资源
                    msg.thumbData = bmpToByteArray(thumbdata, true);//设置缩略图

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("img");
                    req.message = msg;
                    req.scene = isFriends ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                    iwxApi.sendReq(req);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    /**
     * sd 卡上的图片
     * @param isShareFriend
     */
    private void shareImageSdWx(String path,boolean isShareFriend) {
        Log.e(TAG, "shareImageSdWx: ==="+path );
        //1. 判断文件是否存在
        File file=new File(path);
        if(!file.exists()){
            //文件不存在
            return;
        }

        WXImageObject imgObj = new WXImageObject();
        imgObj.setImagePath(path);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 150, 150, true);//缩略图大小
        bitmap.recycle();
        msg.thumbData = bmpToByteArray2(thumbBmp, true);  // 设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isShareFriend ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxApi.sendReq(req);
    }

    /**
     * 分享网页
     *
     * @param url
     * @param title
     * @param miaoshu
     * @param id      0 好友  1 朋友圈
     * @param
     * @return
     */
    public SendMessageToWX.Req shareWxUrl(String url, String title, String miaoshu, int id, final String shareIconUrl) {
        // 初始化一个WXWebpageObject对象，填写Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        //用WXWebpageObject对象初始化一个WXMediaMessage对象，填写标题描述
        final WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = miaoshu;

        //本地图片
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_200);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_app_logo);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = id;
        return req;
    }

    @OnClick({R.id.btn_share, R.id.btn_yq_friends})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_share://二维码分享
                String path=Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg";
                Log.e(TAG, "onViewClicked:sd卡== "+path );
                File file=new File(path);
                if(file.exists()&&file.isFile()){
                    Log.e(TAG, "onViewClicked: 存在");
                    file.delete();
                }
                ModelLoading.getInstance(ShareActivity.this).showLoading("",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = dowmPic(dowmUrl);//下载
                        onSaveBitmap(bitmap, ShareActivity.this);//保存到本地
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ModelLoading.getInstance(ShareActivity.this).closeLoading();
                                showSharePop(0);
                            }
                        });
                    }
                }).start();
//                if(!file.exists()&&file.isFile()){
//                    Log.e(TAG, "onViewClicked: 文件不存在");
//                    ModelLoading.getInstance(ShareActivity.this).showLoading("",true);
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Bitmap bitmap = dowmPic(dowmUrl);//下载
//                            onSaveBitmap(bitmap, ShareActivity.this);//保存到本地
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ModelLoading.getInstance(ShareActivity.this).closeLoading();
//                                    showSharePop(0);
//                                }
//                            });
//                        }
//                    }).start();
//                }else{
//
//                }
                break;
            case R.id.btn_yq_friends:
                showSharePop(1);
                break;
        }
    }

    /**
     * 下载图片okhttp
     * @param url
     * @return
     */
    public Bitmap dowmPic(String url) {
        //获取okHttp对象get请求
        try {
            OkHttpClient client = new OkHttpClient();
            //获取请求对象
            Request request = new Request.Builder().url(url).build();
            //获取响应体
            ResponseBody body = client.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Android保存图片到系统图库
     * @param mBitmap
     * @param context
     */
    private void onSaveBitmap(final Bitmap mBitmap, final Context context) {
        // 第一步：首先保存图片
        //将Bitmap保存图片到指定的路径/sdcard/Boohee/下，文件名以当前系统时间命名,但是这种方法保存的图片没有加入到系统图库中
        File appDir = new File(Environment.getExternalStorageDirectory(), "BSC");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "sharerweima.jpg";
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

    /**
     * QQ分享回掉
     */
    class ShareQQListener implements IUiListener {
        @Override
        public void onComplete(Object object) {
            Log.e(TAG, "onComplete: ");
//            Toast.makeText(MyCenterActivity.this, "分享完成:", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(UiError error) {
            Log.e(TAG, "onError: ");
            Toast.makeText(mContext, "分享失败:" + error.errorMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ");
//            Toast.makeText(MyCenterActivity.this, "分享取消", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mIUiListener);
        shareHandler.doResultIntent(data,this);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, mIUiListener);
            }
        }
    }

    //微博分享回调
    @Override
    public void onWbShareSuccess() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareFail() {
        Toast.makeText(this,
                getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: ",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onWbShareCancel() {
        Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
    }


    /**
     * 微博分享
     * @param hasText
     * @param hasImage
     */
    private void sendMessage(boolean hasText, boolean hasImage) {
        sendMultiMessage(hasText, hasImage);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }

        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
        shareHandler.shareMessage(weiboMessage, false);

    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText(shareContent);
        textObject.title = shareTile;
        textObject.actionUrl = shareDownUrl;
        return textObject;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bg_agent_center);
        Bitmap  bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/BSC/sharerweima.jpg");
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText(String shareContent) {
        String text =shareContent+shareDownUrl ;
        return text;
    }
}
