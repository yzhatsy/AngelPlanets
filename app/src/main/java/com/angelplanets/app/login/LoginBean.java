package com.angelplanets.app.login;

import java.util.List;

/**
 * Created by 123 on 2016/4/5.
 * 登录成功返回数据的bean类
 */
public class LoginBean {


    /**
     * friendList : [{"avatarUrl":"/pic/customer/2016-02-22/16425c00-9c05-49f0-a691-a6c9b3ca5401.jpg","nickname":"Mark","userId":117},{"avatarUrl":"/pic/customer/2016-03-04/b6e45a13-23f2-4f98-9b49-14994d988e73.jpg","nickname":"纠结的小飞机","userId":85},{"avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","nickname":"蜜糖果","userId":87},{"avatarUrl":"/pic/customer/2016-03-08/451a6759-9bd9-40b1-8b61-92764261b881.jpg","nickname":"小飞侠","userId":84}]
     * userInfo : {"avatarUrl":"/pic/common/customer_default.jpg","birthday":"","city":"","nickname":"葱宠","petType":2,"province":"","sex":0,"signature":"","userId":167,"username":""}
     */

    private PersonalData data;
    /**
     * data : {"friendList":[{"avatarUrl":"/pic/customer/2016-02-22/16425c00-9c05-49f0-a691-a6c9b3ca5401.jpg","nickname":"Mark","userId":117},{"avatarUrl":"/pic/customer/2016-03-04/b6e45a13-23f2-4f98-9b49-14994d988e73.jpg","nickname":"纠结的小飞机","userId":85},{"avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","nickname":"蜜糖果","userId":87},{"avatarUrl":"/pic/customer/2016-03-08/451a6759-9bd9-40b1-8b61-92764261b881.jpg","nickname":"小飞侠","userId":84}],"userInfo":{"avatarUrl":"/pic/common/customer_default.jpg","birthday":"","city":"","nickname":"葱宠","petType":2,"province":"","sex":0,"signature":"","userId":167,"username":""}}
     * message : 登陆成功
     * statusCode : 200
     * success : true
     */

    private String message;
    private int statusCode;
    private boolean success;

    public void setData(PersonalData data) {
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

    public PersonalData getData() {
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

    public static class PersonalData {
        /**
         * avatarUrl : /pic/common/customer_default.jpg
         * birthday :
         * city :
         * nickname : 葱宠
         * petType : 2
         * province :
         * sex : 0
         * signature :
         * userId : 167
         * username :
         */

        private UserInfoEntity userInfo;
        /**
         * avatarUrl : /pic/customer/2016-02-22/16425c00-9c05-49f0-a691-a6c9b3ca5401.jpg
         * nickname : Mark
         * userId : 117
         */

        private List<FriendListEntity> friendList;

        public void setUserInfo(UserInfoEntity userInfo) {
            this.userInfo = userInfo;
        }

        public void setFriendList(List<FriendListEntity> friendList) {
            this.friendList = friendList;
        }

        public UserInfoEntity getUserInfo() {
            return userInfo;
        }

        public List<FriendListEntity> getFriendList() {
            return friendList;
        }

        public static class UserInfoEntity {
            private String avatarUrl;
            private String birthday;
            private String city;
            private String nickname;
            private int petType;
            private String province;
            private int sex;
            private String signature;
            private int userId;
            private String username;

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setPetType(int petType) {
                this.petType = petType;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public void setSex(int sex) {
                this.sex = sex;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public String getBirthday() {
                return birthday;
            }

            public String getCity() {
                return city;
            }

            public String getNickname() {
                return nickname;
            }

            public int getPetType() {
                return petType;
            }

            public String getProvince() {
                return province;
            }

            public int getSex() {
                return sex;
            }

            public String getSignature() {
                return signature;
            }

            public int getUserId() {
                return userId;
            }

            public String getUsername() {
                return username;
            }
        }

        public static class FriendListEntity {
            private String avatarUrl;
            private String nickname;
            private int userId;

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public String getNickname() {
                return nickname;
            }

            public int getUserId() {
                return userId;
            }
        }
    }
}
