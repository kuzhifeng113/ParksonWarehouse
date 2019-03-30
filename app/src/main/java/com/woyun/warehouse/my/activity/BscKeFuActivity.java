package com.woyun.warehouse.my.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.CustomerBean;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.vote.adapter.CustomerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;


/**
 * 百盛仓客服
 */
public class BscKeFuActivity extends BaseActivity {
    private static final String TAG = "BscKeFuActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<CustomerBean> customerBeanList = new ArrayList<>();
    private CustomerAdapter customerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsc_kf);
        ButterKnife.bind(this);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        customerAdapter = new CustomerAdapter(BscKeFuActivity.this, customerBeanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(BscKeFuActivity.this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(customerAdapter);

        getData();

        customerAdapter.setOnButtonClickListener(new CustomerAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int positon) {
                switch (view.getId()) {
                    case R.id.btn_save:
//                        ToastUtils.getInstanc(BscKeFuActivity.this).showToast(""+positon);
                        saveImage(customerBeanList.get(positon).getImage(),customerBeanList.get(positon).getId()+"kefu.jpg");
                        break;
                    case R.id.btn_copy_account:
//                        ToastUtils.getInstanc(BscKeFuActivity.this).showToast(""+positon);
                        copy(customerBeanList.get(positon).getWxh());
                        break;
                }
            }
        });
    }


    private void getData() {
        ModelLoading.getInstance(BscKeFuActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            RequestInterface.sysPrefix(BscKeFuActivity.this, params, TAG, ReqConstance.I_SYS_CUSTOMER, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(BscKeFuActivity.this).closeLoading();

                    try {
                        if (code == 0) {
                            Gson gson = new Gson();
                            JSONObject object = (JSONObject) jsonArray.get(0);
                            JSONArray contentArray = object.getJSONArray("customerList");
                            List<CustomerBean> beanList = gson.fromJson(contentArray.toString(), new TypeToken<List<CustomerBean>>() {
                            }.getType());
                            customerBeanList.addAll(beanList);
                            customerAdapter.notifyDataSetChanged();
                        } else {
                            ToastUtils.getInstanc(BscKeFuActivity.this).showToast(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(BscKeFuActivity.this).closeLoading();
                    ToastUtils.getInstanc(BscKeFuActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    /**
     * 保存
     *
     * @param url
     * @param name 命名
     */
    private void saveImage(String url, String name) {
        if (!TextUtils.isEmpty(url)) {
            String path = Environment.getExternalStorageDirectory() + "/BSC/" + name;
            Log.e(TAG, "onViewClicked:sd卡== " + path);
            File file = new File(path);
            if (!file.exists()) {
                Log.e(TAG, "onViewClicked: 文件不存在");
                ModelLoading.getInstance(BscKeFuActivity.this).showLoading("", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = dowmLoadPic(url);//下载
                        onSaveBitmap(bitmap, BscKeFuActivity.this);//保存到本地
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ModelLoading.getInstance(BscKeFuActivity.this).closeLoading();
                                ToastUtils.getInstanc(BscKeFuActivity.this).showToast("保存成功");
                            }
                        });
                    }
                }).start();
            } else {
                ToastUtils.getInstanc(BscKeFuActivity.this).showToast("已保存");
            }
        } else {
            ToastUtils.getInstanc(BscKeFuActivity.this).showToast("二维码链接失效");
        }
    }

    /**
     * 复制
     * @param account
     */
    private void copy(String account) {
        if (!TextUtils.isEmpty(account)) {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            cm.setText(account);
            ToastUtils.getInstanc(BscKeFuActivity.this).showToast("复制成功");
        } else {
            ToastUtils.getInstanc(BscKeFuActivity.this).showToast("复制失败");
        }
    }

//    @OnClick({R.id.btn_save, R.id.btn_copy_account})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_save:
//                if (!TextUtils.isEmpty(downUrl)) {
//                    String path = Environment.getExternalStorageDirectory() + "/BSC/kfu.jpg";
//                    Log.e(TAG, "onViewClicked:sd卡== " + path);
//                    File file = new File(path);
//                    if (!file.exists()) {
//                        Log.e(TAG, "onViewClicked: 文件不存在");
//                        ModelLoading.getInstance(BscKeFuActivity.this).showLoading("", true);
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                final Bitmap bitmap = dowmLoadPic(downUrl);//下载
//                                onSaveBitmap(bitmap, BscKeFuActivity.this);//保存到本地
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        ModelLoading.getInstance(BscKeFuActivity.this).closeLoading();
//                                        ToastUtils.getInstanc(BscKeFuActivity.this).showToast("保存成功");
//                                    }
//                                });
//                            }
//                        }).start();
//                    } else {
//                        ToastUtils.getInstanc(BscKeFuActivity.this).showToast("已保存");
//                    }
//                } else {
//                    ToastUtils.getInstanc(BscKeFuActivity.this).showToast("二维码链接失效");
//                }
//                break;
//            case R.id.btn_copy_account:
//                if (!TextUtils.isEmpty(tvWxAccount.getText().toString())) {
//                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    // 将文本内容放到系统剪贴板里。
//                    cm.setText(tvWxAccount.getText().toString().trim());
//                    ToastUtils.getInstanc(BscKeFuActivity.this).showToast("复制成功");
//                } else {
//                    ToastUtils.getInstanc(BscKeFuActivity.this).showToast("复制失败");
//                }
//
//                break;
//        }
//    }

    /**
     * 下载图片okhttp
     *
     * @param url
     * @return
     */
    public Bitmap dowmLoadPic(String url) {
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
     *
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
        String fileName = "erCode.jpg";
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

//        // 第二步：其次把文件插入到系统图库
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
