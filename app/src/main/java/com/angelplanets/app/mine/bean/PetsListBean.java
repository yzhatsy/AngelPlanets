package com.angelplanets.app.mine.bean;

import java.util.List;

/**
 * Created by 123 on 2016/4/7.
 * 宠物列表的bean
 */
public class PetsListBean {

    /**
     * message : null
     * statusCode : 200
     * success : true
     * data : [{"id":116,"name":"呵呵","avatarUrl":....."}]
     */

    private Object message;
    private int statusCode;
    private boolean success;
    /**
     * id : 116
     * name : 呵呵
     * avatarUrl : null
     */

    private List<DataEntity> data;

    public void setMessage(Object message) {
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

    public Object getMessage() {
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

    public static class DataEntity {
        private int id;
        private String name;
        private Object avatarUrl;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAvatarUrl(Object avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Object getAvatarUrl() {
            return avatarUrl;
        }
    }
}
