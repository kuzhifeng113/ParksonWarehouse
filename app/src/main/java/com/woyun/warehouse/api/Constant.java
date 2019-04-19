package com.woyun.warehouse.api;

/**
 *
 */
public class Constant {

    public static final String HW_APP_ID = "100167819";
    //第三方登录信息
    public static final String QQ_APP_ID = "1106481861";
    //    public static final String WX_APP_ID="wxafaf430d07ed6e57";
    public static final String WX_APP_ID = "wx76f362b1df43cb52";
    //    public static final String WX_APP_SECRET="dd0f5e052cf1699de4a17a5bd5a3841e";
    public static final String WX_APP_SECRET = "fb6e385a0fbf62582d17bbaf8ac81b96";

    //微博key
    public static final String WBAPP_KEY      = "2865772874";
    //Udesk
    public static final String UDESK_DOMAN="woyun.udesk.cn";
    public static final String UDESK_APPID="81007105c805f4f6";
    public static final String UDESK_KEY="7c38f3599e6e67188d1057380029183a";
    /**
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";
    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    //隐私声明地址
    //分享下载
    public static final String SHARE_KEY = "share_key";//分享链接附加参数
    public static final String SHARE_GOODS_ID = "share_goodsid";//分享商品id
    public static final String SHARE_HX_KEY = "share_hx_key";//唤醒分享链接附加参数--没用到
    public static final String SHARE_URL = "share_url";
    public static final String SHARE_TITLE = "share_title";
    public static final String SHARE_CONTENT = "share_content";
    public static final String SHARE_ICON = "share_icon";


    // 保存的 key
    public static String USER_INFO_BEAN = "user_info_bean";//对象
    public static String IS_LOGIN = "is_login";

    public static String IS_FIRST_START = "is_first";
    public static String IS_FIRST_OPENINSTALL = "is_first_open";//避免 重复调用getinstall
    public static String IS_FIRST_YD = "is_first_yd";
    public static String TOKEN = "user_token";
    public static String USER_IS_REAL = "user_is_real";
    public static String USER_IS_VIP = "user_is_vip";
    public static String USER_IS_AGENT = "user_is_agent";//是否代理
    public static String USER_SEX = "sex";
    public static String USER_MOBILE = "user_mobile";
    public static String USER_TWO_PWD = "user_two_pwd";
    public static String USER_WX = "user_wx";//微信openid
    public static String USER_WX_NAME = "user_wx_name";//绑定微信名字
    public static String USER_ZFB = "user_zfb";//支付宝账号
    public static String USER_ZFB_NAME = "user_zfb_name";//支付宝姓名
    public static String USER_SEARCH_HISTORY = "user_search";
    public static String USER_INVITE_CODE = "user_code";//代理邀请码
    public static String PAY_TYPE = "pay_type";//支付购买类型 1vip  2代理
    public static String USER_DEFAULT_ADDRESS = "default_address";//是否设置默认地址
    public static String USER_WX_TYPE = "user_wx_type";//0 登录授权   1 是授权绑定
    public static String UNREAD_NUM="unread_num";//未读消息数

    public static String USER_ID = "user_id";
    public static String USER_DEVICE_ID = "user_device_id";
    public static String USER_NICK_NAME = "user_nick_name";
    public static String USER_AVATAR = "user_avatar";


    public static String UPDATE_TIME = "update_time";//上次更新弹窗时间
    public static String ADV_TIME = "adv_time";//上次弹广告时间


    public static String UPDATE_FLAG = "update_flag";//版本更新标识
    public static String UPDATE_FLAG_STATUS="update_flag_status";//版本更新标识  为1时上线
    public static String UPDATE_DOWN_URL = "update_down_url";//下载地址
    public static String UPDATE_CONTENT = "update_content";//下载内容提示


    //商城首页 3个图地址
    public static final String WEB_MALL_ONE = "https://comm.bscvip.com/sincerity.jpg";
    public static final String WEB_MALL_TWO = "https://comm.bscvip.com/green.jpg";
    public static final String WEB_MALL_THREE = "https://comm.bscvip.com/fine.jpg";
    //   商城首页优质推荐图片地址
    public static final String WEB_MALL_LEFT="https://comm.bscvip.com/yztj.png";

    //常见问题地址
    public static final String WEB_PROBLEM = "https://comm.bscvip.com/problem.html";
    // 投票规则地址
    public static final String WEB_VOTE_RULE = "https://comm.bscvip.com/vote.html";
    //注册隐私协议地址
    public static final String WEB_PRIVATE= "https://comm.bscvip.com/service.html";
    //代理规则地址
    public static final String WEB_AGENT= "https://comm.bscvip.com/agent.html";
    //会员规则地址
    public static final String WEB_VIP= "https://comm.bscvip.com/vip.html";
    //会员须知
    public static final String WEB_VIP_NOTICE= "https://comm.bscvip.com/vipNotice.html";
    //代理须知
    public static final String WEB_AGENT_NOTICE= "https://comm.bscvip.com/agentNotice.html";
    //商品详情页地址 https://comm.bscvip.com/detail.html?id=1&vip=1
    public static final String WEB_GOODS_DETAIL= "https://api.bscvip.com/details/detail.html";
    //客服
    public static final String WEB_KE_FU="http://w1.ttkefu.com/k/linkurl/?t=5J9GDJ5";
//    public static final String WEB_KE_FU="http://w1.ttkefu.com/k/linkurl/?t=5J9GDJ5";
    //关于我们
    public static final String WEB_ABOUT_ME="https://comm.bscvip.com/about.html";

    //商品分享地址
    public static final String WEB_SHARE_GOODS="https://api.bscvip.com/details/detailshare.html";
    public static final String WEB_SHARE_GOODS2="https://api.bscvip.com/detailv2/detailshare.html";
    public static final String WEB_SHARE_GOODS_KF="https://api.bscvip.com/kf/detailshare.html";
    public static final String WEB_GOODS_URL="https://api.bscvip.com/detailv2/detail.html";
    //赚钱 规则
    public static final String WEB_MAKE_MONEY="  https://comm.bscvip.com/makemoney.html";




    //    0代理开通，1VIP开通，2代理购买VIP，3商城订单，4代理提现，5礼包领取
    public static final int PAY_TYPE_AGENT = 0;
    public static final int PAY_TYPE_VIP = 1;
    public static final int PAY_TYPE_AGENT_BUY_VIP = 2;
    public static final int PAY_TYPE_SHOP = 3;
    public static final int PAY_TYPE_LIMITED_TIME = 4;
    public static final int PAY_TYPE_RED_PACK = 5;//红包购物订单

    //    public static  String  USER_HEAD_URL = "user_head_url";
//ERROR
    public final static String NET_ERROR = "手机网络不在状态噢，差点跑偏了~哟";

    public static final String end_day="2019-4-16 08:00:00";
}
