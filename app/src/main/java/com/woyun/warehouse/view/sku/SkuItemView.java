package com.woyun.warehouse.view.sku;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.woyun.warehouse.R;

/**
 * 单个sku  textView
 * Created by wuhenzhizao on 2017/7/31.
 */
public class SkuItemView extends android.support.v7.widget.AppCompatTextView {
    private String attributeValue;

    public SkuItemView(Context context) {
        super(context);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SkuItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //选中背景
        setBackgroundResource(R.drawable.sku_item_selector);
        //选中文字
        setTextColor(getResources().getColorStateList(R.color.sku_item_text_selector));
        setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        setSingleLine();
        setGravity(Gravity.CENTER);
        setPadding(ScreenUtils.dp2PxInt(context, 10), 0, ScreenUtils.dp2PxInt(context, 10), 0);

        setMinWidth(ScreenUtils.dp2PxInt(context, 45));
        setMaxWidth(ScreenUtils.dp2PxInt(context, 200));
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        setText(attributeValue);
    }
}
