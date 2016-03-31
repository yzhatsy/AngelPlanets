package com.angelplanets.app.home;

import java.util.List;

/**
 * Banner数据接口的实体类
 * Created by 123 on 2016/3/9.
 */
public class BannerBean {

    /**
     * message : 商品详情
     * statusCode : 200
     * success : true
     * data : [{"bannerId":25,"title":"奢宠承诺","photoUrl":"/pic/banner/2016-02-02/4ea28369-d215-46dd-a657-450294510d35.png","type":5,"typeId":0,"typeUrl":"/pic/common/commitment.png"},{"bannerId":2,"title":"摄影","photoUrl":"/pic/banner/2016-01-27/213ff529-35ab-47f4-a049-af059f087c63.png","type":2,"typeId":33,"typeUrl":""},{"bannerId":1,"title":"手机壳定制","photoUrl":"/pic/banner/2016-01-27/2d75af8e-7156-4641-9d6a-2233c8bb2d5a.png","type":2,"typeId":32,"typeUrl":""}]
     */

    private String message;
    private int statusCode;
    private boolean success;
    /**
     * bannerId : 25
     * title : 奢宠承诺
     * photoUrl : /pic/banner/2016-02-02/4ea28369-d215-46dd-a657-450294510d35.png
     * type : 5
     * typeId : 0
     * typeUrl : /pic/common/commitment.png
     */

    private List<BannerEntity> data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<BannerEntity> data) {
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

    public List<BannerEntity> getData() {
        return data;
    }

    public static class BannerEntity {
        private int bannerId;
        private String title;
        private String photoUrl;
        private int type;
        private int typeId;
        private String typeUrl;

        public void setBannerId(int bannerId) {
            this.bannerId = bannerId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public void setTypeUrl(String typeUrl) {
            this.typeUrl = typeUrl;
        }

        public int getBannerId() {
            return bannerId;
        }

        public String getTitle() {
            return title;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public int getType() {
            return type;
        }

        public int getTypeId() {
            return typeId;
        }

        public String getTypeUrl() {
            return typeUrl;
        }
    }
}
