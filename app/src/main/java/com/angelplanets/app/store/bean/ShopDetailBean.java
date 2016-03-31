package com.angelplanets.app.store.bean;

import java.util.List;

/**
 * 商品详情页面数据的bean类
 * Created by 123 on 2016/3/22.
 */
public class ShopDetailBean {


    /**
     * message : 商品详情
     * statusCode : 200
     * success : true
     * detail:.............
     */

    private String message;
    private int statusCode;
    private boolean success;

    private DataEntity data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(DataEntity data) {
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

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private int commodityId;
        private String name;
        private double price;
        private double oldPrice;
        private int sales;
        private boolean donate;
        private String detail;
        private int status;
        private int stars;
        private List<String> picures;

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setOldPrice(double oldPrice) {
            this.oldPrice = oldPrice;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public void setDonate(boolean donate) {
            this.donate = donate;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }

        public void setPicures(List<String> picures) {
            this.picures = picures;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public double getOldPrice() {
            return oldPrice;
        }

        public int getSales() {
            return sales;
        }

        public boolean isDonate() {
            return donate;
        }

        public String getDetail() {
            return detail;
        }


        public int getStatus() {
            return status;
        }

        public int getStars() {
            return stars;
        }

        public List<String> getPicures() {
            return picures;
        }


    }
}
