package com.angelplanets.app.store.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车列表信息的bean类
 * 设置为单例，以便于数据共享
 * Created by 123 on 2016/3/23.
 */
public class ShoppingCartBean {

    /**
     * data : [{"commodityId":24.....
     * message : 购物车列表信息
     * statusCode : 200
     * success : true
     */

    private String message;
    private int statusCode;
    private boolean success;
    /**
     * commodityId : 24
     * count : 1
     * name : 日本 Philocomb 斐洛宠物 贝壳梳 针梳 MK组合通用梳子
     * pictures : /pic/commodity/2016-01-20/c04798bf-2900-4f1e-93ac-e5c0988710d5.jpg
     * price : 145.0
     * specification : {"产品属性":"萌酷通用","品牌":"Philocomb斐洛"}
     */

    private List<DataEntity> data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
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

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity implements Comparable,Serializable {
        private int commodityId;
        private int count;
        private String name;
        private String pictures;
        private double price;
        private boolean isCheckable = true;

        public Map<String, String> getMap() {
            return map;
        }

        public void setMap(Map<String, String> map) {
            this.map = map;
        }

        private Map<String,String> map = new HashMap<>();

        /**
         * 产品属性 : 萌酷通用
         * 品牌 : Philocomb斐洛
         */

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPictures(String pictures) {
            this.pictures = pictures;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public boolean isCheckable() {
            return isCheckable;
        }

        public void setIsCheckable(boolean isCheckable) {
            this.isCheckable = isCheckable;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public int getCount() {
            return count;
        }

        public String getName() {
            return name;
        }

        public String getPictures() {
            return pictures;
        }

        public double getPrice() {
            return price;
        }


        @Override
        public int compareTo(Object o) {
            DataEntity dataEntity=(DataEntity)o;
            if(this.commodityId>dataEntity.commodityId)
                return 1;
            else if(this.commodityId==dataEntity.commodityId)
            {
                return 0;
            }
            return -1;
        }
    }
}

