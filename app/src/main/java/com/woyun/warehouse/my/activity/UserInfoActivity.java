package com.woyun.warehouse.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.baseparson.event.SaveUserEvent;
import com.woyun.warehouse.bean.OOSBean;
import com.woyun.warehouse.bean.UserInfoBean;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.GlideImageLoader;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 个人资料
 */
public class UserInfoActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "UserInfoActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nick_name)
    EditText editNickName;
    @BindView(R.id.rl_nick_name)
    RelativeLayout rlNickName;
    @BindView(R.id.tv_sex)
    TextView tvSex;

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.rl_birthday)
    RelativeLayout rlBirthday;
    @BindView(R.id.tv_save)
    TextView tvSave;

    private String imgName;
    private String resultUrl;//上传图片成功地址
    private CommonPopupWindow popupWindow;
    private OOSBean oosBean;
    private OSS oss;
    private UserInfoBean userBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();

    }

    private void initData() {
        upLoadImage(loginUserId);
        String userString = (String) SPUtils.getInstance(UserInfoActivity.this).get(Constant.USER_INFO_BEAN, "");
        Gson gson = new Gson();
        userBean = gson.fromJson(userString, UserInfoBean.class);
        Glide.with(UserInfoActivity.this).load(userBean.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.ic_head_default)
                .into(imgHead);
        editNickName.setText(userBean.getNickname());

        if (userBean.getSex() == 1) {//男
            tvSex.setText("男");
        } else if (userBean.getSex() == 2) {
            tvSex.setText("女");
        } else {
            tvSex.setText("未设置");
        }

        editNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String editName = s.toString();
                LogUtils.e(TAG, "afterTextChanged: " + editName);
            }
        });
        if (TextUtils.isEmpty(userBean.getBirthDate())) {
            tvBirthday.setText("未设置");
        } else {
            tvBirthday.setText(userBean.getBirthDate());
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    ArrayList<ImageItem> images = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                LogUtils.e(TAG, "==============" + images.get(0).path);
                int random = (int) ((Math.random() * 9 + 1) * 100000);
                String ranNum = String.valueOf(random);
                imgName = System.currentTimeMillis() + ranNum + ".jpg";
//                "?x-oss-process=image/circle,r_100"
                LogUtils.e(TAG, "onActivityResult: ========" + imgName);
                uploadOss(oss, oosBean.getBucket(), oosBean.getFolder(), imgName, images.get(0).path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 上传头像
     * 上传头像之前调用该接口获得调起阿里对象存储的授权TST相关参数，获得参数后对应阿里OOS SDK 填写所需参数初始化后再进行上传相关操作
     *
     * @param userId 用户名
     */
    private void upLoadImage(String userId) {
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);

            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_GET_AVATAR_UPLOAD_AUTH, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    tokenTimeLimit(UserInfoActivity.this,responseCode);
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

    private void saveUserInfo(final UserInfoBean userInfoBean) {
        ModelLoading.getInstance(UserInfoActivity.this).showLoading("",true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userInfoBean.getUserid());
            params.put("nickname", editNickName.getText().toString());
            params.put("qq", userInfoBean.getQq());
            params.put("wx", userInfoBean.getWx());
            params.put("wb", userInfoBean.getWb());
            params.put("sex", userInfoBean.getSex());
            params.put("age", userInfoBean.getAge());
            params.put("title", userInfoBean.getTitle());
            params.put("mobile", userInfoBean.getMobile());
            params.put("weight", userInfoBean.getWeight());
            if (TextUtils.isEmpty(resultUrl)) {
                params.put("avatar", userInfoBean.getAvatar());
            } else {
                params.put("avatar", resultUrl);
            }
            params.put("birthDate",userInfoBean.getBirthDate() );

            RequestInterface.userPrefix(this, params, TAG, ReqConstance.I_MODIFY_USER_INFO, 2, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray responseData) {
                    ModelLoading.getInstance(UserInfoActivity.this).closeLoading();
                    tokenTimeLimit(UserInfoActivity.this,responseCode);
                    if (responseCode == 0) {
                        ToastUtils.getInstanc(UserInfoActivity.this).showToast("保存成功");
                        EventBus.getDefault().post(new SaveUserEvent(true));
                        finish();
//                        checkUserId(userInfoBean.getUserid());
                    }else{
                        ToastUtils.getInstanc(UserInfoActivity.this).showToast("保存失败");
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode){
                    ToastUtils.getInstanc(UserInfoActivity.this).showToast(responseMessage);
                    ModelLoading.getInstance(UserInfoActivity.this).closeLoading();
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
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
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
     * @param folder     目录
     * @param objectName 文件名
     * @param fileUrl    文件路径
     */
    private void uploadOss(OSS oss, String bucket, String folder, String objectName, String fileUrl) {
        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(bucket, folder + objectName, fileUrl);
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
                resultUrl = oosBean.getDomain() + oosBean.getFolder() + imgName;
                userBean.setAvatar(resultUrl);
                LogUtils.e("PutObject", "UploadSuccess" + resultUrl);
//                 ?x-oss-process=image/circle,r_100
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(UserInfoActivity.this).load(resultUrl).into(imgHead);
//                        saveUserInfo(userBean);
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


    /**
     * 查询个人信息
     */
    private void checkUserId(String userID) {
        ModelLoading.getInstance(UserInfoActivity.this).showLoading("", true);
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userID);
            RequestInterface.userPrefix(UserInfoActivity.this, params, TAG, ReqConstance.I_GET_USER_INFO, 3, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String responseMessage, int responseCode, JSONArray jsonArray) {
                    ModelLoading.getInstance(UserInfoActivity.this).closeLoading();
                    if (responseCode == 0) {
                        try {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            UserInfoBean userInfoBean = gson.fromJson(object.toString(), UserInfoBean.class);
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_INFO_BEAN, object.toString());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.TOKEN, userInfoBean.getToken());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_IS_REAL, userInfoBean.isIsReal());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_IS_VIP, userInfoBean.isIsVip());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_ID, userInfoBean.getUserid());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_AVATAR, userInfoBean.getAvatar());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_NICK_NAME, userInfoBean.getNickname());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_SEX, userInfoBean.getSex());
                            SPUtils.getInstance(UserInfoActivity.this).put(Constant.USER_MOBILE, userInfoBean.getMobile());
                            finish();
                        } catch (JSONException e) {
//                            modelLoading.closeLoading();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void requestError(String responseMessage, int responseCode) {
                    ModelLoading.getInstance(UserInfoActivity.this).closeLoading();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 拍照
     *
     * @param
     */
    private void takePhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(600);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(600);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(200);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(200);//保存文件的高度。单位像素
        Intent intent = new Intent(UserInfoActivity.this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        startActivityForResult(intent, 100);
    }

    /**
     * 相册选则
     */
    private void selectPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(600);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(600);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(200);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(200);//保存文件的高度。单位像素
        Intent intent1 = new Intent(UserInfoActivity.this, ImageGridActivity.class);
        startActivityForResult(intent1, 100);
    }


    //全屏弹出
    public void showAll(View view) {
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

    @OnClick({R.id.img_head, R.id.rl_nick_name, R.id.tv_sex, R.id.rl_birthday, R.id.tv_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head:
                showAll(imgHead);
                break;
            case R.id.tv_nick_name:
                break;
            case R.id.tv_sex:
                List<String> sexList = new ArrayList<>();
                sexList.add("男");
                sexList.add("女");
                SinglePicker<String> sexpicker = new SinglePicker<>(this, sexList);
                sexpicker.setCanLoop(false);//不禁用循环
                sexpicker.setLineVisible(false);
                sexpicker.setTextSize(18);
                sexpicker.setSelectedIndex(0);
                sexpicker.setWheelModeEnable(true);
                //启用权重 setWeightWidth 才起作用
                sexpicker.setWeightEnable(true);
                sexpicker.setWeightWidth(1);
                sexpicker.setSubmitTextColor(Color.parseColor("#20BEAD"));
                sexpicker.setSelectedTextColor(Color.parseColor("#20BEAD"));
                sexpicker.setUnSelectedTextColor(Color.GRAY);

                sexpicker.setOnItemPickListener(new OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int index, String item) {
                        tvSex.setText(item);
                        if (item.equals("男")) {
                            userBean.setSex(1);
                        } else {
                            userBean.setSex(2);
                        }
                        LogUtils.e(TAG, "onItemPicked: " + userBean.getSex());
                    }
                });
                sexpicker.show();
                break;
            case R.id.rl_birthday://
                showBirthDay();
                break;
            case R.id.tv_save://保存
                saveUserInfo(userBean);
                break;
        }
    }

    public void showBirthDay() {
        final DatePicker picker = new DatePicker(this);
        Calendar instance = Calendar.getInstance();
        int nowYear = instance.get(Calendar.YEAR);
        int nowMon = instance.get(Calendar.MONTH) + 1;
        int nowDay = instance.get(Calendar.DAY_OF_MONTH);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(nowYear - 100, 1, 1);
        picker.setRangeEnd(nowYear, nowMon, nowDay);
        picker.setSelectedItem(nowYear - 100, 1, 1);
        picker.setWeightEnable(true);
        picker.setLineColor(getResources().getColor(R.color.line));
        picker.setSubmitTextColor(Color.parseColor("#20BEAD"));
        picker.setSelectedTextColor(Color.parseColor("#20BEAD"));
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                LogUtils.e(TAG, "onDatePicked: " + year + "-" + month + "-" + day);
                tvBirthday.setText(year + "-" + month + "-" + day);
                userBean.setBirthDate(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

}
