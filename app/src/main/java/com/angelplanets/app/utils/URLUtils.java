package com.angelplanets.app.utils;

/**
 * 用于存放数据接口的
 * Created by 123 on 2016/3/9.
 */
public class URLUtils {

    private static int type = 1;
    private static int userId = 1;
    private static int pageCount = 1;
    private static int socialType = 3;

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        URLUtils.type = type;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        URLUtils.userId = userId;
    }

    public static int getPageCount() {
        return pageCount;
    }

    public static void setPageCount(int pageCount) {
        URLUtils.pageCount = pageCount;
    }

    public static int getSocialType() {
        return socialType;
    }

    public static void setSocialType(int socialType) {
        URLUtils.socialType = socialType;
    }

    public static final String USERID = "&userId=";
    public static final String PAGECOUNT = "&pageCount=";
    public static final String SOCIALTYPE = "&socialType=3";
    /**
     * 根接口
     */
    public static final String rootUrl = "http://123.57.55.74:8394";

    /**
     * 首页banner的url
     */
    public static final String bannerUrl = rootUrl+"/rest/api/banner/listAll?type="+type;

    /**
     * 社交页面list的url
     */
    public static final String socialUrl = rootUrl+"/rest/api/social/listAll?userId=";

    /**
     * 社交详情的url
     */
    public static final String SOCIAL_DETAIL_URL = rootUrl+"/rest/api/socials/";
    /**
     * 评论列表的url
     */
    public static final String COMMENT_URL =rootUrl+"/rest/api/socialComment/listAll?fromId=";
    public static final String SOCIAL_ID = "&socialId=";
    /**
     * 添加评论的url post请求
     */
    public static final String ADD_COMMENT_URL = rootUrl+"/rest/api/socialComment/add";
    /**
     * 用户点赞动作的url post请求 status: 0 点赞， 1 取消
     */
    public static final String LIKE_URL = rootUrl+"/rest/api/socialCollects/";
    /**
     * 是否点赞的状态url
     */
    public static final String LIKE_STATE_URL = rootUrl+"/rest/api/socialCollects/";

    /**
     * 商品列表的url
     */
    public static final String SHOPURL ="/rest/api/commodity/listAll2?type=";

    /**
     * 商品详情的url
     */
    public static final String SHOP_DETAIL_URL = rootUrl+"/rest/api/commodity/detail2?commodityId=";

    /**
     * 购物车的url
     */
    public static final String SHOPPING_CART = rootUrl+"/rest/api/shoppingCart/listAll?userId=";

    /**
     * 更新购物车的url
     */
    public static final String UPDATE_CART = rootUrl+"/rest/api/shoppingCart/update";

    /**
     * 支付url
     */
    public static final String PAY_URL = rootUrl+"/rest/api/order/getPayInfo";

    /**
     * 用户地址的url
     */
    public static final String ADDRESS_URL = rootUrl+"/rest/api/deliveryAddress/listAll?userId=";

    /**
     * 添加地址的url
     */
    public static final String ADD_ADDRESS_URL = rootUrl+"/rest/api/deliveryAddress/add";

    /**
     * 修改地址的url
     */
    public static final String ALTER_ADDRESS_URL = rootUrl+"/rest/api/deliveryAddress/update";
    /**
     * 登陆的url
     */
    public static final String LOGIN_URL = rootUrl+"/rest/api/login";
    /**
     * 获取注册验证码的url
     */
    public static final String OBTAIN_CODE_URL = rootUrl+"/rest/api/sms_code?phonenumber=";

    /**
     * 获取更改密码验证码的url
     * {"message":"手机号存在，可以更改密码","statusCode":200,"success":true,"data":{"identifyCode":250770,"identifyPhoneNumber":"18720932369","userId":167}}
     */
    public static final String OBTAIN_UPDATA_CODE_URL = rootUrl+"/rest/api/sms_changePassword_code?phonenumber=";
    /**
     * 更改密码的url
     */
    public static final String UPDATE_PWD_URL = rootUrl+"/rest/api/user_info_update";
    /**
     * 注册的url
     */
    public static final String REGISTER_URL = rootUrl+"/rest/api/register";


    public static final String USER_INFO_URL = rootUrl+"/rest/api/customers/";
    /**
     * 用户基本信息的url
     */
    public static final String BASE = "/base";
    /**
     * 用户宠物信息
     */
    public static final String PETS = "/pets";

    /**
     * 回忆的url
     */
    public static final String MEMORY_URL =rootUrl+"/rest/api/social/userRememberList?fromId=";
    public static final String CUSTOMER_ID = "&toId=";
    public static final String CODE = "&code=";
}
