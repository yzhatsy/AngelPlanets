package com.angelplanets.app.utils;

/**
 * 用于存放数据接口的
 * Created by 123 on 2016/3/9.
 */
public class URLUtils {

    private static int type = 1;
    private static int userId = 1;
    private static int pageCount = 1;
    private static int socialType = 1;

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
    public static final String SOCIALTYPE = "&socialType=";

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
    public static final String socialUrl = rootUrl+"/rest/api/social/listAll?userId="+userId+"&pageCount="+pageCount+"&socialType="+socialType;

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
}
