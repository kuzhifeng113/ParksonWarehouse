package com.woyun.warehouse.cart;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.MainActivity;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseFragment;
import com.woyun.warehouse.baseparson.BaseFragmentTwo;
import com.woyun.warehouse.bean.CartShopBean;
import com.woyun.warehouse.cart.activity.OrderXiaDanActivity;
import com.woyun.warehouse.cart.adapter.CartAdapter;
import com.woyun.warehouse.cart.adapter.CartLikeAdapter;
import com.woyun.warehouse.mall.activity.GoodsDetailNativeActivity;
import com.woyun.warehouse.utils.BigDecimalUtil;
import com.woyun.warehouse.utils.DensityUtils;
import com.woyun.warehouse.utils.GridSpacingItemDecoration;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.SpacesItemDecoration;
import com.woyun.warehouse.utils.ToastUtils;
import com.woyun.warehouse.view.DeleteDialog;
import com.woyun.warehouse.view.JudgeNestedScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 购物车
 */
public class CartFragment extends BaseFragmentTwo implements CartAdapter.CheckInterface, CartAdapter.ModifyCountInterface {
    private static final String TAG = "CartFragment";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.tv_card_edit)
    TextView tv_card_edit;
    @BindView(R.id.recycler_view_cart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.scrollView)
    JudgeNestedScrollView scrollView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout buttonBarLayout;
    @BindView(R.id.iv_menu)
    TextView tvMemu;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    @BindView(R.id.toolbar_username)
    TextView toolbarUsername;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_like_title)
    TextView tvLikeTitle;
    @BindView(R.id.recycler_view_like)
    RecyclerView recyclerViewLike;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_settlement)
    TextView tvSettlement;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.lll)
    RelativeLayout lll;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout collapse;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    private int mOffset = 0;
    private int mScrollY = 0;
    int toolBarPositionY = 0;

//    private String userId;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private List<CartShopBean.CartListBean> cartShopBeanList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private List<CartShopBean.GoodsListBean> cartLikeList = new ArrayList<>();
    private CartLikeAdapter cartLikeAdapter;
    //    private List<ShoppingCartBean> shoppingCartBeanList=new ArrayList<>();
    private boolean flag = false;
    private List<CartShopBean.CartListBean> positionList = new ArrayList<>();//选中的
    private List<Integer> cartIdList = new ArrayList<>();
    private String ids;//删除id

    int currentCount;

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_cart2, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        toolBar.setVisibility(View.GONE);
        ImmersionBar.setTitleBar(getActivity(), toolBar);
//        userId = (String) SPUtils.getInstance(getActivity()).get(Constant.USER_ID, "");
        cartAdapter = new CartAdapter(getActivity(), cartShopBeanList);
        cartAdapter.setCheckInterface(this);
        cartAdapter.setModifyCountInterface(this);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewCart.setAdapter(cartAdapter);

        cartLikeAdapter = new CartLikeAdapter(getActivity(), cartLikeList);
        recyclerViewLike.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //设置间距
        recyclerViewLike.addItemDecoration(new GridSpacingItemDecoration(2, 34, false));
        recyclerViewLike.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(getActivity(), 25)));//垂直间距
        recyclerViewLike.setAdapter(cartLikeAdapter);
        initData();

        cartAdapter.setOnButtonClick(new CartAdapter.OnButtonClick() {
            @Override
            public void imgCick(int position) {
//                Intent goodsDetail = new Intent(getActivity(), GoodsDetailActivity.class);
                Intent goodsDetail = new Intent(getActivity(), GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", cartShopBeanList.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });

        //喜欢的
        cartLikeAdapter.setOnTypeItemClickListener(new CartLikeAdapter.OnTypeItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtils.getInstanc(getActivity()).showToast("---"+cartLikeList.get(position).getName());
//                Intent goodsDetail = new Intent(getActivity(), GoodsDetailActivity.class);
                Intent goodsDetail = new Intent(getActivity(), GoodsDetailNativeActivity.class);
                goodsDetail.putExtra("goods_id", cartLikeList.get(position).getGoodsId());
                goodsDetail.putExtra("from_id", 2);
                startActivity(goodsDetail);
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            totalCount = 0;
            tvSettlement.setText("结算(" + totalCount + ")");
            cartShopBeanList.clear();
            cartLikeList.clear();
            getData();
            Log.e(TAG, "setUserVisibleHint:------ ");
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

//    @Override
//    protected void initImmersionBar() {
//        super.initImmersionBar();
//        ImmersionBar.with(this)
//                .statusBarDarkFont(true).init();
//    }

    private void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                toolBar.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
//                ivHeader.setTranslationY(mOffset - mScrollY);
                toolBar.setAlpha(1 - Math.min(percent, 1));
            }
        });


        toolBar.post(new Runnable() {
            @Override
            public void run() {
                dealWithViewPager();
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            int lastScrollY = 0;
            int h = DensityUtil.dp2px(170);
            int color = ContextCompat.getColor(getContext(), R.color.white) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
//                magicIndicator.getLocationOnScreen(location);
                int yPosition = location[1];
                if (yPosition < toolBarPositionY) {
//                    magicIndicatorTitle.setVisibility(View.VISIBLE);
                    scrollView.setNeedScroll(false);
                } else {
//                    magicIndicatorTitle.setVisibility(View.GONE);
                    scrollView.setNeedScroll(true);

                }

                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
                    buttonBarLayout.setAlpha(1f * mScrollY / h);
                    toolBar.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
//                    ivHeader.setTranslationY(mOffset - mScrollY);
                }
                if (scrollY == 0) {
                    toolBar.setVisibility(View.GONE);
                    tv_card_edit.setVisibility(View.VISIBLE);
                    tvMemu.setVisibility(View.GONE);
                    ivBack.setImageResource(R.mipmap.back_white);
                } else {
                    toolBar.setVisibility(View.VISIBLE);
                    tv_card_edit.setVisibility(View.GONE);
                    tvMemu.setVisibility(View.VISIBLE);
                    ivBack.setImageResource(R.mipmap.back_black);
                }

                lastScrollY = scrollY;
            }
        });
        buttonBarLayout.setAlpha(0);
        toolBar.setBackgroundColor(0);
    }

    private void initData() {
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                totalCount = 0;
                cartShopBeanList.clear();
                cartLikeList.clear();
//                getMoMiData();
                getData();
                mRefreshLayout.finishRefresh();
            }
        });
    }

    private void dealWithViewPager() {
        toolBarPositionY = toolBar.getHeight();
//        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//        params.height = ScreenUtil.getScreenHeightPx(getApplicationContext()) - toolBarPositionY - magicIndicator.getHeight() + 1;
//        viewPager.setLayoutParams(params);
    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
//        getMoMiData();
//        getData(userId);
    }

    /**
     * 获取数据
     *
     * @param
     */
    private void getData() {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID,""));
            RequestInterface.cartPrefix(getActivity(), params, TAG, ReqConstance.I_CART_GET_LIST, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {
                        try {
                            if(checkboxAll.isChecked()){
                                checkboxAll.setChecked(false);
                            }
                            Gson gson = new Gson();
                            String jsonResult = jsonArray.get(0).toString();
                            CartShopBean cartBean = gson.fromJson(jsonResult, CartShopBean.class);
                            cartShopBeanList.addAll(cartBean.getCartList());
                            cartAdapter.notifyDataSetChanged();

                            cartLikeList.addAll(cartBean.getGoodsList());
                            cartLikeAdapter.notifyDataSetChanged();
                            tvAllPrice.setText("00.00");
                            if (cartBean.getCartList().size() > 0) {
                                llEmpty.setVisibility(View.GONE);
                            } else {
                                llEmpty.setVisibility(View.VISIBLE);
                            }

                            if (cartBean.getGoodsList().size() > 0) {
                                tvLikeTitle.setVisibility(View.VISIBLE);
                            } else {
                                tvLikeTitle.setVisibility(View.GONE);
                            }

                            Log.e(TAG, "requestSuccess: " + cartBean.getCartList().size());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (code == 1000 || code == 1001) {
                        //鉴权失败，可能是token已过期，请重新登陆
                        MainActivity mainActivity= (MainActivity) getActivity();
                        mainActivity.tokenTimeLimit(getActivity(),code);

                    } else {
                        llEmpty.setVisibility(View.VISIBLE);
                        tvLikeTitle.setVisibility(View.GONE);
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(getActivity()).showToast(Constant.NET_ERROR);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    /**
//     * 解析数据
//     *
//     * @param data
//     */
//    private void pasterData(List<MallHomeBean> data) {
//        for (int i = 0; i < data.size(); i++) {
//            mallHomeBeanList.addAll(data.get(i).getPackList());
//        }
//        mallHomeAdapter.notifyDataSetChanged();
//        LogUtils.e(TAG,""+mallHomeBeanList.size());
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


//    private void getMoMiData() {
//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName("上档次的T桖"+i);
//            shoppingCartBean.setAttribute("L码");
//            shoppingCartBean.setId(i);
//            shoppingCartBean.setPrice(30.6);
//            shoppingCartBean.setCount(1);
//            shoppingCartBean.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
//        for (int i = 0; i < 2; i++) {
//            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
//            shoppingCartBean.setShoppingName(i+"瑞士正品夜光男女士手表情侣精钢带男表防水石英学生非天王星机械");
//            shoppingCartBean.setAttribute("黑白色");
//            shoppingCartBean.setPrice(89);
//            shoppingCartBean.setId(i + 2);
//            shoppingCartBean.setCount(3);
//            shoppingCartBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/2160089910/TB2M_NSbB0kpuFjSsppXXcGTXXa_!!2160089910.jpg");
//            shoppingCartBeanList.add(shoppingCartBean);
//        }
//        cartAdapter.notifyDataSetChanged();
////            cartAdapter = new card(this);
////            shoppingCartAdapter.setCheckInterface(this);
////            shoppingCartAdapter.setModifyCountInterface(this);
////            list_shopping_cart.setAdapter(shoppingCartAdapter);
////            shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
//    }

    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < cartShopBeanList.size(); i++) {
            CartShopBean.CartListBean shoppingCartBean = cartShopBeanList.get(i);
            if (shoppingCartBean.isCheck()) {
                totalCount++;
                double price = Double.parseDouble(shoppingCartBean.getUnitPrice());
                double itemPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(shoppingCartBean.getSkuNum()));
                totalPrice = BigDecimalUtil.getAdd(totalPrice, itemPrice);
                Log.e(TAG, "statistics: " + totalPrice);
//                totalPrice=getAdd(totalPrice,getMultiply(price,Double.valueOf(shoppingCartBean.getSkuNum())));
//                totalPrice += price * shoppingCartBean.getSkuNum();
            }
        }

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);//保留2位小数

        tvAllPrice.setText("￥" + numberFormat.format(totalPrice));
        tvSettlement.setText("结算(" + totalCount + ")");
    }


    //实现adapter 接口 实现方法

    /**
     * 增加
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        CartShopBean.CartListBean shoppingCartBean = cartShopBeanList.get(position);
        currentCount = shoppingCartBean.getSkuNum();
        currentCount++;
        shoppingCartBean.setSkuNum(currentCount);
        updateCartNumber(shoppingCartBean, showCountView);
//        ((TextView) showCountView).setText(currentCount + "");
//        cartAdapter.notifyDataSetChanged();
//        statistics();
    }

    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        CartShopBean.CartListBean shoppingCartBean = cartShopBeanList.get(position);
        currentCount = shoppingCartBean.getSkuNum();
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setSkuNum(currentCount);
        updateCartNumber(shoppingCartBean, showCountView);
//        ((TextView) showCountView).setText(currentCount + "");
//        cartAdapter.notifyDataSetChanged();
//        statistics();
    }

    //删除--没用到
    @Override
    public void childDelete(int position) {

    }

    //复选框checkBox按钮
    @Override
    public void checkGroup(int position, boolean isChecked) {
//        if(isChecked){
//            positionList.add(position);
//            cartIdList.add(cartShopBeanList.get(position).getId());
//        }else{
//            positionList.remove(position);
//            cartIdList.remove(new Integer(cartShopBeanList.get(position).getId()));
//        }
        cartShopBeanList.get(position).setCheck(isChecked);
        if (isAllCheck())
            checkboxAll.setChecked(true);
        else
            checkboxAll.setChecked(false);
        cartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 遍历list集合
     *
     * @return
     */
    private boolean isAllCheck() {
        for (CartShopBean.CartListBean group : cartShopBeanList) {
            if (!group.isCheck())
                return false;
        }
        return true;
    }

    /**
     * 结算订单、支付
     */
    private void settlementOrder() {
        //选中的需要提交的商品清单
        List<CartShopBean.CartListBean> selectList = new ArrayList<>();
        for (CartShopBean.CartListBean bean : cartShopBeanList) {
            boolean choosed = bean.isCheck();
            if (choosed) {
                selectList.add(bean);
//                String shoppingName = bean.getGoodsName();
//                int count = bean.getSkuNum();
//                String price = bean.getUnitPrice();
//                String skuName = bean.getSkuName();
//                int id = bean.getId();
//                Log.d(TAG, id + "----id---" + shoppingName + "---" + count + "---" + price);
            }
        }
        if (selectList.size() > 0) {
            Intent intent = new Intent(getActivity(), OrderXiaDanActivity.class);
            intent.putExtra("total_price", totalPrice);
            intent.putExtra("select_data", (Serializable) selectList);
            startActivity(intent);
        }
//        ToastUtils.showShort(getActivity(), "总价：" + totalPrice);

        //跳转到支付界面
    }


    @OnClick({R.id.tv_card_edit, R.id.iv_menu, R.id.checkbox_all, R.id.tv_settlement, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_card_edit:
                if (!flag) {
                    tvSettlement.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tv_card_edit.setText("完成");
                    tvMemu.setText("完成");
                    flag = true;
                    cartAdapter.isShow(flag);
                } else {
                    flag = false;
                    tvSettlement.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    tv_card_edit.setText("编辑");
                    tvMemu.setText("编辑");
                    cartAdapter.isShow(false);
                }
                break;
            case R.id.iv_menu://编辑
                if (!flag) {
                    tvSettlement.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                    tv_card_edit.setText("完成");
                    tvMemu.setText("完成");
                    flag = true;
                    cartAdapter.isShow(flag);
                } else {
                    flag = false;
                    tvSettlement.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                    tv_card_edit.setText("编辑");
                    tvMemu.setText("编辑");
                    cartAdapter.isShow(false);
                }
                break;
            case R.id.checkbox_all:
                if (cartShopBeanList.size() != 0) {
                    if (checkboxAll.isChecked()) {
                        for (int i = 0; i < cartShopBeanList.size(); i++) {
                            cartShopBeanList.get(i).setCheck(true);
                        }
                        cartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < cartShopBeanList.size(); i++) {
                            cartShopBeanList.get(i).setCheck(false);
                        }
                        cartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_settlement://结算
                settlementOrder();
                break;
            case R.id.tv_delete://删除
                prepareDelete();
//                statistics();
                break;
        }
    }

    /**
     * 记录选中的下标 与id
     */
    private void prepareDelete() {
        positionList.clear();
        cartIdList.clear();
        for (int i = 0; i < cartShopBeanList.size(); i++) {
            CartShopBean.CartListBean shoppingCartBean = cartShopBeanList.get(i);
            if (shoppingCartBean.isCheck()) {
                positionList.add(shoppingCartBean);
                cartIdList.add(cartShopBeanList.get(i).getId());
            }
        }

        if (cartIdList != null && cartIdList.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            Log.e(TAG, "onViewClicked:选中 ==" + cartIdList.size());
            for (int position = 0; position < cartIdList.size(); position++) {
                Log.e(TAG, "for position" + cartIdList.get(position));
                cartAdapter.notifyDataSetChanged();
                if (position == cartIdList.size() - 1) {
                    stringBuffer.append(cartIdList.get(position));
                } else {
                    stringBuffer.append(cartIdList.get(position) + ",");
                }
            }
            ids = stringBuffer.toString();
            Log.e(TAG, "stringBuffer: " + stringBuffer.toString());
            stringBuffer = new StringBuffer("");
            Log.e(TAG, "stringBuffer: " + stringBuffer.toString());
            Log.e(TAG, "onViewClicked: " + cartShopBeanList.size());
            showDeleteDialog();
        }
    }

    /**
     * 删除购物车
     */
    private void deleteCart(String deleteids) {
        Log.e(TAG, "deleteCart: " + deleteids);
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("ids", deleteids);
            params.put("userid", SPUtils.getInstance(getActivity()).get(Constant.USER_ID,""));
            RequestInterface.cartPrefix(getActivity(), params, TAG, ReqConstance.I_CART_REMOVE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0) {
                        if(checkboxAll.isChecked()){
                            checkboxAll.setChecked(false);
                        }
                        for (int i = 0; i < positionList.size(); i++) {
                            for (int j = 0; j < cartShopBeanList.size(); j++) {
                                if (positionList.get(i) == cartShopBeanList.get(j)) {
                                    cartShopBeanList.remove(j);
                                    cartAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                        if (cartShopBeanList.size() > 0) {
                            llEmpty.setVisibility(View.GONE);
                        } else {
                            llEmpty.setVisibility(View.VISIBLE);
                        }
                        statistics();
//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDeleteDialog() {
        new DeleteDialog(getActivity(), R.style.dialogphone, "您确定删除吗？", new DeleteDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    dialog.dismiss();
                    deleteCart(ids);
                }
            }
        }).show();
    }

    /**
     * 更新购物车数量
     */
    private void updateCartNumber(CartShopBean.CartListBean entity, final View view) {
        ModelLoading.getInstance(getActivity()).showLoading("", true);
        Log.e(TAG, "updateCartNumber:数量" + entity.getSkuNum());
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("id", entity.getId());
            params.put("userid", entity.getUserid());
            params.put("goodsId", entity.getGoodsId());
            params.put("goodsName", entity.getGoodsName());
            params.put("goodsImage", entity.getGoodsImage());
            params.put("skuId", entity.getSkuId());
            params.put("skuNum", entity.getSkuNum());
            params.put("skuName", entity.getSkuName());
            params.put("skuImage", entity.getSkuImage());
            params.put("unitPrice", entity.getUnitPrice());
            params.put("memo", entity.getMemo());
            RequestInterface.cartPrefix(getActivity(), params, TAG, ReqConstance.I_CART_UPDATE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.tokenTimeLimit(getActivity(), code);
                    if (code == 0) {
                        ((TextView) view).setText(currentCount + "");
                        cartAdapter.notifyDataSetChanged();
                        statistics();
//                            Log.e(TAG, "requestSuccess: " + jsonResult);
                    } else {
                        ToastUtils.getInstanc(getActivity()).showToast(msg);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(getActivity()).closeLoading();
                    ToastUtils.getInstanc(getActivity()).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
