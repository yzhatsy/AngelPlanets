package com.angelplanets.app.mine.bean;

/**
 * 用户基本信息
 * Created by 123 on 2016/4/7.
 */
public class BaseInfoBean {


    /**
     * message : null
     * statusCode : 200
     * success : true
     * data : {"id":167,"username":.....:null}
     */

    private Object message;
    private int statusCode;
    private boolean success;
    /**
     * id : 167
     * username : null
     * nickname : 葱宠
     * phonenumber : 18720932369
     * sex : 1
     * age : null
     * avatarUrl : /pic/customer/2016-04-07/4d1e0576-88a9-4e6c-ac76-f3746159179b.jpg
     * petType : 2
     * signature : null
     * followerCount : 0
     * followningCount : 1
     * score : 0
     * scoreTime : null
     */

    private DataEntity data;

    public void setMessage(Object message) {
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

    public Object getMessage() {
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
        private int id;
        private String username;
        private String nickname;
        private String phonenumber;
        private int sex;
        private int age;
        private String avatarUrl;
        private int petType;
        private String signature;
        private int followerCount;
        private int followningCount;
        private int score;
        private long scoreTime;

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

        public void setFollowerCount(int followerCount) {
            this.followerCount = followerCount;
        }

        public void setFollowningCount(int followningCount) {
            this.followningCount = followningCount;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setScoreTime(long scoreTime) {
            this.scoreTime = scoreTime;
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

        public int getFollowerCount() {
            return followerCount;
        }

        public int getFollowningCount() {
            return followningCount;
        }

        public int getScore() {
            return score;
        }

        public long getScoreTime() {
            return scoreTime;
        }
    }
}
