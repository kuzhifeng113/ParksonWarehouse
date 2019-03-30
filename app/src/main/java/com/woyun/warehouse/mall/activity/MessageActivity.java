package com.woyun.warehouse.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.woyun.httptools.net.HSRequestCallBackInterface;
import com.woyun.warehouse.R;
import com.woyun.warehouse.api.Constant;
import com.woyun.warehouse.api.ReqConstance;
import com.woyun.warehouse.api.RequestInterface;
import com.woyun.warehouse.baseparson.BaseActivity;
import com.woyun.warehouse.bean.ShipAddressBean;
import com.woyun.warehouse.bean.UnReadNumBean;
import com.woyun.warehouse.my.activity.MyAddressActivity;
import com.woyun.warehouse.utils.ModelLoading;
import com.woyun.warehouse.utils.SPUtils;
import com.woyun.warehouse.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.udesk.PreferenceHelper;
import cn.udesk.UdeskSDKManager;
import cn.udesk.config.UdeskConfig;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;
import udesk.core.UdeskConst;


/**
 * 消息
 */
public class MessageActivity extends BaseActivity {
    private static final String TAG = "MessageActivity";
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.ll_order_message)
    RelativeLayout llOrderMessage;
    @BindView(R.id.ll_sys_message)
    RelativeLayout llSysMessage;

    @BindView(R.id.ll_kefu_message)
    RelativeLayout llKeFuMessage;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_sys)
    TextView tvSys;

    @BindView(R.id.tv_kefu)
    TextView tvKefu;
    Badge billBadge, sysBadge, kefuBadge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        billBadge = new QBadgeView(this);
        sysBadge = new QBadgeView(this);
        kefuBadge = new QBadgeView(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void setRedNum(int billNum, int sysNum) {
        if (billNum > 0) {
            billBadge.bindTarget(tvOrder).setBadgeGravity(Gravity.CENTER).setBadgeNumber(billNum).setExactMode(false);
            billBadge.setBadgePadding(6, true);
            billBadge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    if (dragState == STATE_SUCCEED) {
                        setReadRequest(loginUserId, 1);
                    }
                }
            });
        } else {
            billBadge.hide(false);
        }

        if (sysNum > 0) {
            sysBadge.bindTarget(tvSys).setBadgeGravity(Gravity.CENTER).setBadgeNumber(sysNum).setExactMode(false);
            sysBadge.setBadgePadding(6, true);
            sysBadge.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                @Override
                public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                    Log.e(TAG, "onDragStateChanged: " + dragState);
                    if (dragState == STATE_SUCCEED) {
                        setReadRequest(loginUserId, 0);
                    }
                }
            });
        } else {
            sysBadge.hide(false);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        initData(loginUserId);

        //获取udesk 未读消息
        int unreadMsg = UdeskSDKManager.getInstance().getCurrentConnectUnReadMsgCount(getApplicationContext(),
                loginUserId);
        Log.e(TAG, "onResume:未读客服消息" + unreadMsg);
        if (unreadMsg > 0) {
            kefuBadge.bindTarget(tvKefu).setBadgeGravity(Gravity.CENTER).setBadgeNumber(unreadMsg).setExactMode(false);
            kefuBadge.setBadgePadding(6, true);
        } else {
            kefuBadge.hide(false);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarDarkFont(true).init();
    }


    private void initData(String userId) {
        ModelLoading.getInstance(MessageActivity.this).showLoading("", true);
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            RequestInterface.sysPrefix(MessageActivity.this, params, TAG, ReqConstance.I_UNREAD_MESSAGE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    ModelLoading.getInstance(MessageActivity.this).closeLoading();
                    if (code == 0 && jsonArray.length() > 0) {
                        Gson gson = new Gson();
                        List<UnReadNumBean> unReadNumBeans = gson.fromJson(jsonArray.toString(), new TypeToken<List<UnReadNumBean>>() {
                        }.getType());
                        int billUnread = unReadNumBeans.get(0).getBillUnreadNum();
                        int sysUnread = unReadNumBeans.get(0).getSysUnreadNum();
                        setRedNum(billUnread, sysUnread);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ModelLoading.getInstance(MessageActivity.this).closeLoading();
                    ToastUtils.getInstanc(MessageActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置消息已读
     *
     * @param userId
     * @param type   type=1订单，type=0系统消息
     */
    private void setReadRequest(String userId, int type) {
        //获取数据
        try {
            JSONObject params = new JSONObject();
            params.put("userid", userId);
            params.put("type", type);
            RequestInterface.sysPrefix(MessageActivity.this, params, TAG, ReqConstance.I_SET_READ_MESSAGE, 1, new HSRequestCallBackInterface() {
                @Override
                public void requestSuccess(int funcID, int reqID, String reqToken, String msg, int code, JSONArray jsonArray) {
                    if (code == 0) {

                    } else {
                        initData(loginUserId);
                    }
                }

                @Override
                public void requestError(String s, int i) {
                    ToastUtils.getInstanc(MessageActivity.this).showToast(s);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.ll_order_message, R.id.ll_sys_message, R.id.ll_kefu_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_order_message:
                if (billBadge != null) {
                    billBadge.hide(false);
                }
                Intent message = new Intent(MessageActivity.this, MessageListActivity.class);
                message.putExtra("mess_type", 1);
                startActivity(message);
                break;
            case R.id.ll_sys_message:
                if (sysBadge != null) {
                    sysBadge.hide(false);
                }
                Intent messages = new Intent(MessageActivity.this, MessageListActivity.class);
                messages.putExtra("mess_type", 0);
                startActivity(messages);
                break;
            case R.id.ll_kefu_message://进入客服聊天
                String sdkToken = loginUserId;
                Map<String, String> info = new HashMap<String, String>();
                //以下信息是可选
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME, (String) SPUtils.getInstance(MessageActivity.this).get(Constant.USER_NICK_NAME, ""));//昵称
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE, (String) SPUtils.getInstance(MessageActivity.this).get(Constant.USER_MOBILE, ""));//手机
                UdeskConfig.Builder builder = new UdeskConfig.Builder();

                //toolBar 背景色
                builder.setUdeskTitlebarBgResId(R.color.white)//设置标题栏TitleBar的背景色
                        .setUdeskTitlebarTextLeftRightResId(R.color.text_black)//设置标题栏TitleBar，左右两侧文字的颜色
                        .setUdeskbackArrowIconResId(R.mipmap.back_black) // 设置返回箭头图标资源id
                        .setUdeskProductLinkColorResId(R.color.white) //设置商品信息 带链接时显示的颜色

                        .setDefualtUserInfo(info)
                        .setCustomerUrl((String) SPUtils.getInstance(MessageActivity.this).get(Constant.USER_AVATAR, ""));//用户头像
                UdeskSDKManager.getInstance().entryChat(getApplicationContext(), builder.build(), sdkToken);
                break;
        }
    }
}
