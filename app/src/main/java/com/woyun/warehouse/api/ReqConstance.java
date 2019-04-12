package com.woyun.warehouse.api;

/**
 * 1.0 API
 */
public class ReqConstance {
        //当前API版本
        public static final String VER = "/v1";
        public static final String VER2 = "/v2";
        //本地地址
//        public static final String HOST_ADDR = "http://192.168.0.168:8080" + VER;
//        public static final String HOST_ADDR_TWO = "http://192.168.0.168:8080" + VER2;
        //服务器地址
        public static final String HOST_ADDR = "https://api.bscvip.com" + VER;
        public static final String HOST_ADDR_TWO = "https://api.bscvip.com" + VER2;

//        public static final String HOST_ADDR = "https://xcx.bscvip.com" + VER;
//        public static final String HOST_ADDR_TWO = "https://xcx.bscvip.com" + VER2;

        //////////////////用户登陆/////////////////////////////
        public static final String LOGIN_PREFIX = "/login";
        //登陆枚举 0手机，1微信，2QQ
        public static enum LOGIN_TYPE {
                MOB, WX, QQ
        }


        //请求手机登陆验证码
        public static final int I_GET_SMS_CODE = 100;
        //请求登陆
        public static final int I_USER_LOGIN = 101;

        //////////////////用户中心/////////////////////////////
        public static final String USER_PREFIX = "/users";
        //获取用户个人信息
        public static final int I_GET_USER_INFO = 100;
        //修改个人信息
        public static final int I_MODIFY_USER_INFO = 101;
        //获取阿里云OOS上传的授权和存储位置(bucket)
        public static final int I_GET_AVATAR_UPLOAD_AUTH = 102;
        //余额列表
        public static final int I_CB_BALANCE_LIST = 103;
        //用户设置修改二级密码
        public static final int I_USER_TWO_PWD = 104;
        //获取修改手机号验证码
        public static final int  I_GET_CHANAGE_MOB_VALIDATE_CODE = 105;
        //修改手机号
        public static final int I_CHANAGE_MOB = 106;
        //保存用户地址
        public static final int I_USERADDRESS_INSERT = 107;
        //删除用户地址
        public static final int I_USERADDRESS_DELETE = 108;
        //设置用户二级密码
        public static final int I_SET_USER_SECOND_PWD = 109;
        //用户地址列表
        public static final int I_USERADDRESS_LIST = 110;
        //默认地址+余额+仓币
        public static final int I_DEFALUT_ADDREDSS_MONEY_CB = 111;
        //用户中心
        public static final int I_USER_CENTER = 113;
        //分享下载
        public static final int I_USER_SHARE = 114;
        //新增发票
        public static final int I_USER_INVOICE_INSERT = 115;
        //修改发票
        public static final int I_USER_INVOICE_UPDATE = 116;
        //开通代理查询礼包信息
        public static final int I_USER_QUERY = 117;
        //赚钱-会员
        public static final int I_USER_MAKE_MOENY = 120;
        //提现
        public static final int I_USER_WITHDRAW = 119;
        //获取忘记二级密码验证码
        public static final int I_GET_USER_SECOND_PWD_CODE = 121;
        //重置二级密码
        public static final int I_RESET_USER_SECOND_PWD = 122;

        ///////////////////////VIP 订单////////////////////////////////
        public static final String PAY_PREFIX = "/pay";
        //http://dingyu.free.idcfengye.com
//        //vip购买，代理购买tradeType=1,2
//        public static final String WX_NOTIFY_URL = "https://api.bscvip.com" + VER + "/wxPayNotify";
//        public static final String ZFB_NOTIFY_URL = "https://api.bscvip.com" + VER + "/aliPayNotify";
//        //商城订单 tradeType=3
//        public static final String WX_SHOPPING_NOTIFY_URL = "https://api.bscvip.com" + VER + "/wxShopNotify";
//        public static final String ZFB_SHOPPING_NOTIFY_URL = "https://api.bscvip.com" + VER + "/aliShopNotify";
        //购买VIP-购买VIP会员, 代理购买VIP数量
        public static final int I_PAY_VIP = 100;
        //购买VIP-购买基础VIP会员订单确认
        public static final int I_PAY_VIP_CHECK = 101;
        //单个Vip详情
        public static final int I_PAY_VIP_RESOUCRE = 102;
        //商城下单
        public static final int I_PAY_SHOPPING = 103;
        //订单列表
        public static final int I_PAY_BILL_LIST = 104;
        //订单详情
        public static final int I_PAY_BILL_DETAIL = 105;

        //取消订单
        public static final int I_PAY_CANCLE_BILL = 106;
        //删除订单
        public static final int I_PAY_DELETE_BILL = 107;
        //订单重新发起支付
        public static final int I_PAY_BILL_AGAIN = 108;
        //确认收货
        public static final int I_PAY_BILL_RECEIPT = 109;

        //订单物流轨迹
         public static final int I_PAY_BILL_EXPRESS = 110;

        //VIP中心
        public static final int I_USER_VIP_CENTER = 112;
        //VIP领取礼包
        public static final int I_USER_VIP_GIFT = 113;


        ///////////////////////系统////////////////////////////////
        public static final String SYS_PREFIX = "/sys";
        //检查系统更新
        public static final int I_SYS_VERSION = 99;

        //常见问题
        public static final int I_SYS_QA = 101;
        //用户反馈建议 举报
        public static final int I_SYS_ADVICE = 102;
        //实名认证
        public static final int I_REAL_NAME_REQ = 103;
        //查询认证状态（进来这个页面先查一下，看是否已经提交，并回显认证状态）
        public static final int I_REAL_NAME_CHECK = 104;
        //常见问题详情
        public static final int I_SYS_QA_DETAIL = 105;
        //用户反馈建议举报类型列表
        public static final int I_SYS_ADVICE_TYPE = 106;
        //获取举报建议图片上传授权和存放路径
        public static final int I_GET_UPLOAD_ADVICE_IMG_AUTH = 107;
        //获取实名认证图片上传授权和存放路径
        public static final int I_GET_UPLOAD_REALNAME_IMG_AUTH = 108;
        //系统消息列表
        public static final int I_GET_MESSAGE_LIST = 109;

        //删除消息
        public static final int I_DELETE_MESSAGE = 111;
        //获取用户未读消息数量
        public static final int I_UNREAD_MESSAGE = 112;

        //设置消息已读
        public static final int I_SET_READ_MESSAGE = 113;
        //客服列表
        public static final int I_SYS_CUSTOMER = 100;

        ///////////////////////广告////////////////////////////////
        public static final String ADV_PREFIX = "/adv";
        //获取App启动时的闪屏或是引导页广告 需要做倒计时结束自动关闭
        public static final int I_GET_ADV_BOOT = 100;

        ///////////////////////商品////////////////////////////////
        public static final String GOODS_PREFIX = "/goods";
        //商城首页，
        public static final int I_GOODS_HOME= 100;
        //商品分类,以及第一个分类商品列表
        public static final int I_GOODS_CATEGORY = 101;
        //根据分类id 分页查询商品列表
        public static final int I_GOODS_PAGE_BY_CATEGORY = 102;

        //商品详情
        public static final int I_GOODS_DETAIL = 103;
        //添加商品收藏
        public static final int I_GOODS_FAVORITE_INSERT = 104;
        //删除商品收藏
        public static final int I_GOODS_FAVORITE_DELETE = 105;
        //分页收藏列表
        public static final int I_GOODS_FAVORITE_LIST = 106;
        //批量取消收藏
        public static final int I_BATCH_DELETE_FAVORITE = 107;
        //商品热门搜索列表
        public static final int I_GOODS_SEARCH_WORD = 108;
        //优选查看更多
        public static final int I_GOODS_PACK_DETAIL = 109;
        public static final int I_GOODS_SEARCH = 109;//搜索



        ///////////////////////投票////////////////////////////////
        public static final String VOTE_PREFIX = "/vote";
        //投票列表
        public static final int I_VOTE_GET_LIST = 100;
        //商品投票，规则：只有vip且每个商品每天一票
        public static final int I_VOTE_GOODS_INSERT = 101;
        //投票详情
        public static final int I_VOTE_DETAIL = 102;

        ///////////////////////购物车////////////////////////////////
        public static final String CART_PREFIX = "/cart";
        //购物车列表
        public static final int I_CART_GET_LIST = 100;
        //加入购物车
        public static final int I_CART_ADD = 101;
        //修改购物车
        public static final int I_CART_UPDATE= 102;
        //移除购物车
        public static final int I_CART_REMOVE = 103;
        //清空购物车
        public static final int I_CART_CLEAR = 104;


        ///////////////////////代理商////////////////////////////////
        public static final String AGENT_PREFIX = "/agent";
        //代理开通
        public static final int I_AGENT_QUERY = 100;
        //代理中心
        public static final int I_AGENT_INFO = 101;
       // 帮助注册
        public static final int I_AGENT_HELP = 102;
        //代理注册VIP会员
        public static final int I_AGENT_VIP = 103;
        //代理绑定微信支付宝
        public static final int I_AGENT_BINDING = 104;
        //代理购买会员
        public static final int I_AGENT_BUY_VIP = 105;
        //代理提现
        public static final int I_AGENT_WITHDRAW = 106;
        //申请退出代理
        public static final int I_AGENT_OUT = 107;
        //退出代理查询可退金额
        public static final int I_AGENT_OUT_QUERY = 108;
        //绑定
        public static final int I_USER_BINDING = 118;


        ///////////////////////限时抢购////////////////////////////////
        public static final String RUSH_PREFIX = "/rush";
        //限时抢购列表
        public static final int I_RUSH_GET_LIST = 100;
        public static final int I_RUSH_GOODS_GET_LIST = 101;
}
