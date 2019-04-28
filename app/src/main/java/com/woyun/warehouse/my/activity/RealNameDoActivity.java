package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MyApplication;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.OOSBean;
import com.woyun.warehouse.bean.RealNameBean;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.GlideImageLoader;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 上传信息实名认证
 */
public class RealNameDoActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "RealNameDoActivity";
    private static final int REQUEST_CODE_SELECT = 101;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_id)
    EditText editId;
    @BindView(R.id.img_id_zheng)
    ImageView imgIdZheng;
    @BindView(R.id.img_id_fan)
    ImageView imgIdFan;
    @BindView(R.id.img_delete_z)
    ImageView imgDeleteZ;
    @BindView(R.id.img_delete_f)
    ImageView imgDeleteF;

    private OOSBean oosBean;
    private OSS oss;
    private CommonPopupWindow popupWindow;
    private int imgViewType = 0;
    private String imgName;
    private String cardFurl, cardBurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name_do);
        MyApplication.getInstance().addActivity(RealNameDoActivity.this);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        requestUploadImageDir(loginUserId);
        initData();

    }


    private void initData() {
        RealNameBean real_entity = (RealNameBean) getIntent().getSerializableExtra("real_entity");
        if (real_entity != null) {
//            ToastUtils.getInstanc(RealNameDoActivity.this).showToast("重新认证过来的");
            editName.setText(real_entity.getRealName());
            editId.setText(real_entity.getCardId());
            cardFurl=real_entity.getCardFurl();
            cardBurl=real_entity.getCardBurl();
            Glide.with(RealNameDoActivity.this).load(cardFurl).into(imgIdZheng);
            Glide.with(RealNameDoActivity.this).load(cardBurl).into(imgIdFan);
            imgDeleteZ.setVisibility(View.VISIBLE);
            imgDeleteF.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    @OnClick({R.id.tv_commit, R.id.img_id_zheng, R.id.img_id_fan,R.id.img_delete_z,R.id.img_delete_f})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                if(TextUtils.isEmpty(editName.getText().toString().trim())){
                    ToastUtils.getInstanc(RealNameDoActivity.this).showToast("姓名不能为空");
                    return ;
                }
                if(TextUtils.isEmpty(editId.getText().toString().trim())){
                    ToastUtils.getInstanc(RealNameDoActivity.this).showToast("身份证号码不能为空");
                    return ;
                }
                if(TextUtils.isEmpty(cardFurl)||TextUtils.isEmpty(cardBurl)){
                    ToastUtils.getInstanc(RealNameDoActivity.this).showToast("身份证照片不能为空");
                    return ;
                }

                addRealName(loginUserId, editName.getText().toString(), editId.getText().toString(), cardFurl, cardBurl, "");
                break;
            case R.id.img_id_zheng:
                imgViewType = 0;
                showAll();
                break;
            case R.id.img_id_fan:
                imgViewType = 1;
                showAll();
                break;
            case R.id.img_delete_z://删除正面照
                cardFurl="";
                Glide.with(RealNameDoActivity.this).load("").error(R.mipmap.img_id_zheng).into(imgIdZheng);
                imgDeleteZ.setVisibility(View.GONE);
                break;
            case R.id.img_delete_f://删除反
                cardBurl="";
                Glide.with(RealNameDoActivity.this).load("").error(R.mipmap.img_id_fan).into(imgIdFan);
                imgDeleteF.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 获取目录
     * 上传图片之前调用该接口获得调起阿里对象存储的授权TST相关参数，获得参数后对应阿里OOS SDK 填写所需参数初始化后再进行上传相关操作
     *
     * @param userId 用户名
     */
    private void requestUploadImageDir(String userId) {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);

            RequestInterface.sysPrefix(this, params, TAG, ReqConstance.I_GET_UPLOAD_REALNAME_IMG_AUTH, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    if (responseCode == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) responseData.get(0);
                            oosBean = gson.fromJson(object.toString(), OOSBean.class);
                            initOSS(oosBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     */
    private void initOSS(OOSBean oosBean) {
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(oosBean.getAccessKeyId(), oosBean.getAccessKeySecret(), oosBean.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        oss = new OSSClient(getApplicationContext(), oosBean.getRegion(), credentialProvider, conf);

    }

    /**
     * 上传
     *
     * @param oss
     * @param bucket
     * @param folder        目录
     * @param objectName    文件名
     * @param upLoadfileUrl 上传文件路径
     */
    private void uploadToOss(OSS oss, String bucket, String folder, String objectName, String upLoadfileUrl) {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, folder + objectName, upLoadfileUrl);
// 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                Log.e("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.e("PutObject", "UploadSuccess");
//                resultUrl="http://image.dymeet.com/avatar/"+imgName;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String url = oosBean.getDomain() + oosBean.getFolder();
                        if (imgViewType == 0) {
                            Glide.with(RealNameDoActivity.this).load(url + imgName).into(imgIdZheng);
                            cardFurl = url + imgName;
                            imgDeleteZ.setVisibility(View.VISIBLE);
                        } else {
                            cardBurl = url + imgName;
                            Glide.with(RealNameDoActivity.this).load(url + imgName).into(imgIdFan);
                            imgDeleteF.setVisibility(View.VISIBLE);
                        }
                    }
                });

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.e("ErrorCode", serviceException.getErrorCode());
                    LogUtils.e("RequestId", serviceException.getRequestId());
                    LogUtils.e("HostId", serviceException.getHostId());
                    LogUtils.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        Button btn_take_photo = (Button) view.findViewById(R.id.btn_take_photo);
        Button btn_select_photo = (Button) view.findViewById(R.id.btn_select_photo);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        //拍照
        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                takePhoto();
            }
        });
        btn_select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                selectPhoto();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    /**
     * 相册选则(单选)
     */
    private void selectPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);
        Intent intent1 = new Intent(RealNameDoActivity.this, ImageGridActivity.class);
        startActivityForResult(intent1, REQUEST_CODE_SELECT);

//        ImagePicker imagePicker = ImagePicker.getInstance();
//        imagePicker.setMultiMode(false);
//        imagePicker.setSelectLimit(1);
//        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
//        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
//        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(700);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(440);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(700);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(440);//保存文件的高度。单位像素
//        Intent intent1 = new Intent(RealNameDoActivity.this, ImageGridActivity.class);
//        startActivityForResult(intent1, 100);
    }

    /**
     * 拍照
     *
     * @param
     */
    private void takePhoto() {
       /** ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(700);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(440);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(700);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(440);//保存文件的高度。单位像素
        Intent intent = new Intent(RealNameDoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);**/


        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        Intent intent = new Intent(RealNameDoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, REQUEST_CODE_SELECT);
    }

    //全屏弹出
    public void showAll() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_img_select, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_img_select)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    ArrayList<ImageItem> images = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 101) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                LogUtils.e(TAG, "==============" + images.get(0).path);
                int random = (int) ((Math.random() * 9 + 1) * 100000);
                imgName = System.currentTimeMillis() + String.valueOf(random) + ".jpg";
                uploadToOss(oss, oosBean.getBucket(), oosBean.getFolder(), imgName, images.get(0).path);

            }
        } else {
//            Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 申请
     *
     * @param cardFurl 正面
     * @param cardBurl 反面
     * @param cardPos  手持
     */
    private void addRealName(String userId, String realName, String cardId, String cardFurl, String cardBurl, String cardPos) {
        ModelLoading.getInstance(RealNameDoActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("realName", realName);
            params.put("cardId", cardId);
            params.put("cardFurl", cardFurl);
            params.put("cardBurl", cardBurl);
            params.put("cardPos", cardPos);
            RequestInterface.sysPrefix(this, params, TAG, ReqConstance.I_REAL_NAME_REQ, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    ModelLoading.getInstance(RealNameDoActivity.this).closeLoading();
                    ToastUtils.getInstanc(RealNameDoActivity.this).showToast(responseMessage);
                    if (responseCode == 0) {
                        MyApplication.getInstance().closeActivity();
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(RealNameDoActivity.this).closeLoading();
                    ToastUtils.getInstanc(RealNameDoActivity.this).showToast(responseMessage);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
