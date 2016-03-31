package com.angelplanets.app.store.bean;

import java.util.List;

/**
 * 地址信息的bean类
 * Created by 123 on 2016/3/30.
 */
public class AddressBean {

    /**
     * message : 收获地址列表
     * statusCode : 200
     * success : true
     * data : [{"deliveryAddressId":....}]
     */

    private String message;
    private int statusCode;
    private boolean success;


    private List<AddressMessage> data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<AddressMessage> data) {
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

    public List<AddressMessage> getData() {
        return data;
    }

    public static class AddressMessage {
        private int deliveryAddressId;
        private String name;
        private String detailAddress;
        private String postcode;
        private String phonenumber;

        public void setDeliveryAddressId(int deliveryAddressId) {
            this.deliveryAddressId = deliveryAddressId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public int getDeliveryAddressId() {
            return deliveryAddressId;
        }

        public String getName() {
            return name;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public String getPostcode() {
            return postcode;
        }

        public String getPhonenumber() {
            return phonenumber;
        }
    }
}
