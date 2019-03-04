package com.woyun.warehouse.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.MallHomeBean;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.utils.AddressPickTask;
import com.woyun.warehouse.utils.LogUtils;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;


/**
 * 添加编辑地址
 */
public class InsertAddressActivity extends BaseActivity {
    private static final String TAG = "RealNameActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_choose_address)
    RelativeLayout rlChooseAddress;
    @BindView(R.id.edit_detail_address)
    EditText editDetailAddress;
    @BindView(R.id.checkbox_address)
    CheckBox checkboxAddress;

    private String provinceValue,cityValue,countyValue;
    private boolean isEdit;//是否编辑
    private ShipAddressBean addressBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_address);
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
//        isSetDefaultAddress
        boolean isSetDefaultAddress=getIntent().getBooleanExtra("isSetDefaultAddress",false);
        isEdit= getIntent().getBooleanExtra("isEdit",false);
        addressBean= (ShipAddressBean) getIntent().getSerializableExtra("address");
        if(isEdit && addressBean!=null){//编辑修改
            tvTitle.setText("编辑收货地址");
            editName.setText(addressBean.getName());
            editPhone.setText(addressBean.getPhone());
            provinceValue=addressBean.getProvince();
            cityValue=addressBean.getCity();
            countyValue=addressBean.getCounty();
            tvAddress.setText(provinceValue+" "+cityValue+" "+countyValue);
            editDetailAddress.setText(addressBean.getAddress());
            if(addressBean.getStatus()==1){
                checkboxAddress.setChecked(true);
            }else{
                checkboxAddress.setChecked(false);
            }
        }
        if(isSetDefaultAddress){//未设置默认地址
            checkboxAddress.setChecked(true);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }

    @OnClick({R.id.tv_complete, R.id.rl_choose_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_complete:
                if(TextUtils.isEmpty(editName.getText().toString().trim())){
                   ToastUtils.getInstanc(InsertAddressActivity.this).showToast("姓名不能为空");
                   return;
                }
                if(TextUtils.isEmpty(editPhone.getText().toString().trim())){
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast("手机号码不为空");
                    return;
                }else if(editPhone.getText().toString().trim().length()!=11){
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast("手机号码不符合规则");
                    return;
                }

                if(TextUtils.isEmpty(tvAddress.getText().toString().trim())){
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast("地址不能为空");
                    return;
                }

                if(TextUtils.isEmpty(editDetailAddress.getText().toString().trim())){
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast("详细地址不能为空");
                    return;
                }
                if(isEdit){
                    completeAddress(String.valueOf(addressBean.getId()),loginUserId);
                }else{
                    completeAddress("",loginUserId);
                }
                break;
            case R.id.rl_choose_address:
                showAddressPicker();
                break;
        }
    }

    /**
     * @param id     id		地址ID，新增为null
     * @param userId
     */
    private void completeAddress(String id, String userId) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("id", id);
            params.put("userid", userId);
            if(checkboxAddress.isChecked()){
                params.put("status", 1);
            }else{
                params.put("status", 0);
            }
            params.put("name", editName.getText().toString().trim());
            params.put("province", provinceValue);
            params.put("city", cityValue);
            params.put("county",countyValue);
            params.put("address", editDetailAddress.getText().toString().trim());
            params.put("phone", editPhone.getText().toString().trim());

            RequestInterface.userPrefix(InsertAddressActivity.this, params, TAG, ReqConstance.I_USERADDRESS_INSERT, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(msg);
                    if (code == 0) {
                        if(id.equals("")){
                            SPUtils.getInstance(InsertAddressActivity.this).put(Constant.USER_DEFAULT_ADDRESS, true);
                        }
                       finish();
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.getInstanc(InsertAddressActivity.this).showToast("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                provinceValue=province.getAreaName();
                cityValue=city.getAreaName();
                countyValue=county.getAreaName();
                if (county == null) {
                    tvAddress.setText(province.getAreaName() + " " + city.getAreaName());
//                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(province.getAreaName() + city.getAreaName());
                } else {
                    provinceValue=province.getAreaName();
                    tvAddress.setText(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
//                    ToastUtils.getInstanc(InsertAddressActivity.this).showToast(province.getAreaName() + city.getAreaName() + county.getAreaName());
                }

            }
        });
        task.execute("北京", "北京", "东城区");
    }
}
