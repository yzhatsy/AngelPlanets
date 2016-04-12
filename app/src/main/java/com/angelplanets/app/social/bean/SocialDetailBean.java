package com.angelplanets.app.social.bean;

import java.util.List;

/**
 * 社交详情的bean
 * Created by 123 on 2016/4/10.
 */
public class SocialDetailBean {

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
        private int socialId;
        private int customerId;
        private String detail;
        private int accessType;
        private String photos;
        private long createTime;
        private long updateTime;
        private int collectCount;
        private int commentCount;
        private int shareCount;
        /**
         * id : 132
         * username : null
         * nickname : 豆汁儿妈
         * phonenumber : null
         * sex : null
         * age : null
         * avatarUrl : /pic/customer/2016-02-23/4cb98746-883c-4bda-9797-d89238dca229.jpg
         * petType : null
         * signature : null
         */

        private CustomerEntity customer;
        private String tag1;
        private String tag2;
        private String tag3;
        private List<String> photoList;

        public void setSocialId(int socialId) {
            this.socialId = socialId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setAccessType(int accessType) {
            this.accessType = accessType;
        }

        public void setPhotos(String photos) {
            this.photos = photos;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public void setShareCount(int shareCount) {
            this.shareCount = shareCount;
        }

        public void setCustomer(CustomerEntity customer) {
            this.customer = customer;
        }

        public void setTag1(String tag1) {
            this.tag1 = tag1;
        }

        public void setTag2(String tag2) {
            this.tag2 = tag2;
        }

        public void setTag3(String tag3) {
            this.tag3 = tag3;
        }

        public void setPhotoList(List<String> photoList) {
            this.photoList = photoList;
        }

        public int getSocialId() {
            return socialId;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getDetail() {
            return detail;
        }

        public int getAccessType() {
            return accessType;
        }

        public String getPhotos() {
            return photos;
        }

        public long getCreateTime() {
            return createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public int getShareCount() {
            return shareCount;
        }

        public CustomerEntity getCustomer() {
            return customer;
        }

        public String getTag1() {
            return tag1;
        }

        public String getTag2() {
            return tag2;
        }

        public String getTag3() {
            return tag3;
        }

        public List<String> getPhotoList() {
            return photoList;
        }

        public static class CustomerEntity {
            private int id;
            private String username;
            private String nickname;
            private String phonenumber;
            private int sex;
            private int age;
            private String avatarUrl;
            private int petType;
            private String signature;

            public void setId(int id) {
                this.id = id;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setPhonenumber(String phonenumber) {
                this.phonenumber = phonenumber;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public void setPetType(int petType) {
                this.petType = petType;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public int getId() {
                return id;
            }

            public String getUsername() {
                return username;
            }

            public String getNickname() {
                return nickname;
            }

            public String getPhonenumber() {
                return phonenumber;
            }

            public int getSex() {
                return sex;
            }

            public int getAge() {
                return age;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public int getPetType() {
                return petType;
            }

            public String getSignature() {
                return signature;
            }
        }
    }
}

