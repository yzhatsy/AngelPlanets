package com.angelplanets.app.store.bean;

import java.util.List;

/**
 * 商品接口的bean类
 * Created by 123 on 2016/3/21.
 */
public class ShopBean {


    private DataEntity data;
    /**
     * data : {"cartCount":0,"commodityList":[{"commodityId":24,"name":"日本 Philocomb 斐洛宠物 贝壳梳 针梳 MK组合通用梳子"...}]}
     * message : 返回商品列表信息
     * statusCode : 200
     * success : true
     */

    private String message;
    private int statusCode;
    private boolean success;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public static class DataEntity {
        private int cartCount;
        /**
         * commodityId : 24
         * name : 日本 Philocomb 斐洛宠物 贝壳梳 针梳 MK组合通用梳子
         * photoUrl : /pic/commodity/2016-01-20/c04798bf-2900-4f1e-93ac-e5c0988710d5.jpg
         * price : 145.0
         * status : 0
         */

        private List<CommodityListEntity> commodityList;

        public void setCartCount(int cartCount) {
            this.cartCount = cartCount;
        }

        public void setCommodityList(List<CommodityListEntity> commodityList) {
            this.commodityList = commodityList;
        }

        public int getCartCount() {
            return cartCount;
        }

        public List<CommodityListEntity> getCommodityList() {
            return commodityList;
        }

        public static class CommodityListEntity {
            private int commodityId;
            private String name;
            private String photoUrl;
            private double price;
            private double oldPrice;
            private int status;

            public void setCommodityId(int commodityId) {
                this.commodityId = commodityId;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getCommodityId() {
                return commodityId;
            }

            public String getName() {
                return name;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public double getPrice() {
                return price;
            }

            public int getStatus() {
                return status;
            }

            public double getOldPrice() {
                return oldPrice;
            }

            public void setOldPrice(double oldPrice) {
                this.oldPrice = oldPrice;
            }

        }
    }
}
