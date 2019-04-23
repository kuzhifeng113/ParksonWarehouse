package com.woyun.warehouse.mall;

import android.app.Dialog;
import android.content.Context;

import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.bean.GoodsDetailBean;
import com.woyun.warehouse.bean.SkuAttribute;
import com.woyun.warehouse.bean.SkuListBean;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.view.sku.OnSkuListener;
import com.woyun.warehouse.view.sku.SkuSelectScrollView;
import com.wuhenzhizao.titlebar.utils.AppUtils;


import java.util.List;

/**
 * sku  规格 弹窗
 */
public class ProductSkuDialog extends Dialog {
    private static final String TAG = "ProductSkuDialog";
    private Context context;
    private GoodsDetailBean product;
    private List<SkuListBean> skuList;
    private Callback callback;

    private SkuListBean selectedSku;
    private String priceFormat;
    private String stockQuantityFormat;

    private LinearLayout dialog_root;
    //view id
    private TextView tv_sku_close, tv_goods_name, tv_goods_price, tv_transport, tvSkuQuantity,tv_allready_check;
    private ImageView iv_sku_logo, btnSkuQuantityMinus, btnSkuQuantityPlus, btnSubmit;
    private EditText etSkuQuantityInput;
    private SkuSelectScrollView scrollSkuList;

    public ProductSkuDialog(@NonNull Context context) {
        this(context, R.style.CommonBottomDialogStyle);

    }

    public ProductSkuDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_product_sku, null);
        setContentView(view);
        dialog_root=view.findViewById(R.id.dialog_root);
        tv_sku_close = view.findViewById(R.id.tv_sku_close);
        tv_goods_name = view.findViewById(R.id.tv_goods_name);
        tv_goods_price = view.findViewById(R.id.tv_goods_price);
        tv_transport = view.findViewById(R.id.tv_transport);//邮费
        tvSkuQuantity = view.findViewById(R.id.tv_sku_quantity);//库存
        tv_allready_check = view.findViewById(R.id.tv_allready_check);//已选
        iv_sku_logo = view.findViewById(R.id.iv_sku_logo);
        btnSkuQuantityMinus = view.findViewById(R.id.btn_sku_quantity_minus);//-
        btnSkuQuantityPlus = view.findViewById(R.id.btn_sku_quantity_plus);//+
        btnSubmit = view.findViewById(R.id.btn_submit);
        etSkuQuantityInput = view.findViewById(R.id.et_sku_quantity_input);
        scrollSkuList = view.findViewById(R.id.scroll_sku_list);

        dialog_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //×
        tv_sku_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //减少
        btnSkuQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 1) {
                    String newQuantity = String.valueOf(quantityInt - 1);
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt - 1);
                }
            }
        });
        //加
        btnSkuQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity) || selectedSku == null) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt < selectedSku.getNum()) {
                    String newQuantity = String.valueOf(quantityInt + 1);
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(quantityInt + 1);
                }
            }
        });

        //输入数量
        etSkuQuantityInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != EditorInfo.IME_ACTION_DONE || selectedSku == null) {
                    return false;
                }
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return false;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt <= 1) {
                    etSkuQuantityInput.setText("1");
                    etSkuQuantityInput.setSelection(1);
                    updateQuantityOperator(1);
                } else if (quantityInt >= selectedSku.getNum()) {
                    String newQuantity = String.valueOf(selectedSku.getNum());
                    etSkuQuantityInput.setText(newQuantity);
                    etSkuQuantityInput.setSelection(newQuantity.length());
                    updateQuantityOperator(selectedSku.getNum());
                } else {
                    etSkuQuantityInput.setSelection(quantity.length());
                    updateQuantityOperator(quantityInt);
                }
                return false;
            }
        });

        //scrollSkuList
        scrollSkuList.setListener(new OnSkuListener() {
            @Override
            public void onUnselected(SkuAttribute unselectedAttribute) {
                Log.e(TAG, "onUnselected: " );
                selectedSku = null;
                Glide.with(context).load(product.getImage()).placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(iv_sku_logo);
                boolean isVip= (boolean) SPUtils.getInstance(context).get(Constant.USER_IS_VIP,false);
                if(isVip){
                    tv_goods_price.setText("￥"+product.getVipPrice());
                }else{
                    tv_goods_price.setText("￥"+product.getPrice());
                }
                tvSkuQuantity.setText("库存"+ product.getStock());

                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();
                  tv_allready_check.setText("请选择：" + firstUnselectedAttributeName);
                btnSubmit.setEnabled(false);
                btnSubmit.setImageResource(R.mipmap.ic_goods_confrim_no);

                String quantity = etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }

            @Override
            public void onSelect(SkuAttribute selectAttribute) {
                Log.e(TAG, "onSelect: " );
                String firstUnselectedAttributeName = scrollSkuList.getFirstUnelectedAttributeName();

                tv_allready_check.setText("请选择：" + firstUnselectedAttributeName);
            }

            @Override
            public void onSkuSelected(SkuListBean sku) {
                Log.e(TAG, "onSkuSelected: ");
                selectedSku = sku;
                Glide.with(context).load(selectedSku.getImage()).placeholder(R.mipmap.img_default).into(iv_sku_logo);
                boolean isVip= (boolean) SPUtils.getInstance(context).get(Constant.USER_IS_VIP,false);
                if(isVip){
                    tv_goods_price.setText("￥"+selectedSku.getVipPrice());
                }else{
                    tv_goods_price.setText("￥"+selectedSku.getPrice());
                }

                List<SkuAttribute> attributeList = selectedSku.getSpecMap();

                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < attributeList.size(); i++) {
                    if (i != 0) {
                        builder.append("");
                    }
                    SkuAttribute attribute = attributeList.get(i);
                    builder.append(attribute.getSpecValue() + ",");
                }

//                tv_allready_check.setText("已选：" + builder.toString());
                tv_allready_check.setText(builder.toString());

                tvSkuQuantity.setText("库存"+selectedSku.getNum());
                btnSubmit.setEnabled(true);
                btnSubmit.setImageResource(R.mipmap.ic_goods_confrim);

                /*库存*/
                String quantity = etSkuQuantityInput.getText().toString();
                if (!TextUtils.isEmpty(quantity)) {
                    updateQuantityOperator(Integer.valueOf(quantity));
                } else {
                    updateQuantityOperator(0);
                }
            }
        });

        //确定
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = etSkuQuantityInput.getText().toString();
                if (TextUtils.isEmpty(quantity)) {
                    return;
                }
                int quantityInt = Integer.parseInt(quantity);
                if (quantityInt > 0 && quantityInt <= selectedSku.getNum()) {
                    callback.onAdded(selectedSku, quantityInt,tv_allready_check.getText().toString());
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "商品数量超出库存，请修改数量", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setData(final GoodsDetailBean product, Callback callback) {
        this.product = product;
        this.skuList = product.getSkuList();
        this.callback = callback;

//        priceFormat = context.getString(R.string.comm_price_format);
//        stockQuantityFormat = context.getString(R.string.product_detail_sku_stock);

        updateSkuData();
        updateQuantityOperator(1);
        if(product.getCategoryId()==-1 || product.getCategoryId()==-3
                || product.getCategoryId()==-2){//-1 会员礼包商品  -3  代理礼包商品  categoryId=-2表示限时抢购商品分类
            btnSkuQuantityMinus.setEnabled(false);
            btnSkuQuantityPlus.setEnabled(false);
            etSkuQuantityInput.setEnabled(false);
        }else{
            btnSkuQuantityMinus.setEnabled(true);
            btnSkuQuantityPlus.setEnabled(true);
            etSkuQuantityInput.setEnabled(true);
        }
    }

    //-----1
    private void updateSkuData() {
        scrollSkuList.setSkuList(product.getSkuList());
        SkuListBean firstSku = product.getSkuList().get(0);
        tv_goods_name.setText(product.getName());
        boolean isVip= (boolean) SPUtils.getInstance(context).get(Constant.USER_IS_VIP,false);
//        if(isVip){
//            tv_transport.setText("免运费");
//        }else{
            if(product.getTransport().equals("0")){
                tv_transport.setText("免运费");
            }else{
                tv_transport.setText("运费:"+product.getTransport());
            }
//        }
        if (firstSku.getNum() > 0) {
            selectedSku = firstSku;
            // 选中第一个sku
            scrollSkuList.setSelectedSku(selectedSku);
            Glide.with(context).load(selectedSku.getImage()).placeholder(R.mipmap.img_default).error(R.mipmap.img_default).into(iv_sku_logo);
            if(isVip){
                tv_goods_price.setText("￥"+selectedSku.getVipPrice());
            }else{
                tv_goods_price.setText("￥"+selectedSku.getPrice());
            }
            tvSkuQuantity.setText("库存"+selectedSku.getNum());
            btnSubmit.setEnabled(selectedSku.getNum() > 0);
            btnSubmit.setImageResource(R.mipmap.ic_goods_confrim);
            List<SkuAttribute> attributeList = selectedSku.getSpecMap();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < attributeList.size(); i++) {
                if (i != 0) {
                    builder.append("");
                }
                SkuAttribute attribute = attributeList.get(i);
                builder.append(attribute.getSpecValue() + ",");
            }
            tv_allready_check.setText(builder.toString());

        } else {
            Glide.with(context).load(product.getImage()).placeholder(R.mipmap.img_default).into(iv_sku_logo);
            if(isVip){
                tv_goods_price.setText("￥"+product.getVipPrice());
            }else{
                tv_goods_price.setText("￥"+product.getPrice());
            }

            tvSkuQuantity.setText("库存"+product.getStock());
            btnSubmit.setEnabled(false);
            btnSubmit.setImageResource(R.mipmap.ic_goods_confrim_no);
            tv_allready_check.setText("请选择：" + skuList.get(0).getSpecMap().get(0).getSpecName());
        }
    }

    private void updateQuantityOperator(int newQuantity) {
        if (selectedSku == null) {
            btnSkuQuantityMinus.setEnabled(false);
            btnSkuQuantityPlus.setEnabled(false);
            etSkuQuantityInput.setEnabled(false);
        } else {
            if (newQuantity <= 1) {
                btnSkuQuantityMinus.setEnabled(false);
                btnSkuQuantityPlus.setEnabled(true);
            } else if (newQuantity >= selectedSku.getNum()) {
                btnSkuQuantityMinus.setEnabled(true);
                btnSkuQuantityPlus.setEnabled(false);
            } else {
                btnSkuQuantityMinus.setEnabled(true);
                btnSkuQuantityPlus.setEnabled(true);
            }
            etSkuQuantityInput.setEnabled(true);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 解决键盘遮挡输入框问题
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.getDecorView().setPadding(0, 0, 0, 0);
        // KeyboardConflictCompat.assistWindow(getWindow());
        AppUtils.transparencyBar(getWindow());
    }


    public interface Callback {
        //sku  数量   已选 specValue 值
        void onAdded(SkuListBean sku, int quantity,String memo);
    }
}
