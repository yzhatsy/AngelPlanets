package com.angelplanets.app.social.bean;

import java.util.List;

/**
 * 回忆列表的bean
 * Created by 123 on 2016/4/8.
 */
public class MemoryBean {

    private DataEntity data;
    /**
     * message : 好友状态返回成功
     * statusCode : 200
     * success : true
     */


    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }


    public static class DataEntity {
        private int friendRelationId;
        private int status;
        /**
         * followerCount : 2
         * followingCount : 0
         * userAvatarUrl : /pic/customer/2016-02-23/4cb98746-883c-4bda-9797-d89238dca229.jpg
         * userId : 132
         * userNickname : 豆汁儿妈
         */

        private UserInfoEntity userInfo;
        /**
         * accessType : 0
         * socialCollectCount : 13
         * socialCollectId : 372
         * socialCommentCount : 2
         * socialCreateTime : 1456220780000
         * socialDetial : 豆汁儿和哈尼在牧云山居的团聚
         * socialId : 274
         * status : 1
         */

        private List<SocialsEntity> socials;

        public void setFriendRelationId(int friendRelationId) {
            this.friendRelationId = friendRelationId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setUserInfo(UserInfoEntity userInfo) {
            this.userInfo = userInfo;
        }

        public void setSocials(List<SocialsEntity> socials) {
            this.socials = socials;
        }

        public int getFriendRelationId() {
            return friendRelationId;
        }

        public int getStatus() {
            return status;
        }

        public UserInfoEntity getUserInfo() {
            return userInfo;
        }

        public List<SocialsEntity> getSocials() {
            return socials;
        }

        public static class UserInfoEntity {
            private int followerCount;
            private int followingCount;
            private String userAvatarUrl;
            private int userId;
            private String userNickname;

            public void setFollowerCount(int followerCount) {
                this.followerCount = followerCount;
            }

            public void setFollowingCount(int followingCount) {
                this.followingCount = followingCount;
            }

            public void setUserAvatarUrl(String userAvatarUrl) {
                this.userAvatarUrl = userAvatarUrl;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public void setUserNickname(String userNickname) {
                this.userNickname = userNickname;
            }

            public int getFollowerCount() {
                return followerCount;
            }

            public int getFollowingCount() {
                return followingCount;
            }

            public String getUserAvatarUrl() {
                return userAvatarUrl;
            }

            public int getUserId() {
                return userId;
            }

            public String getUserNickname() {
                return userNickname;
            }
        }

        public static class SocialsEntity {
            private int accessType;
            private int socialCollectCount;
            private int socialCollectId;
            private int socialCommentCount;
            private long socialCreateTime;
            private String socialDetial;
            private int socialId;
            private int status;
            private List<String> socialPhoto;

            public void setAccessType(int accessType) {
                this.accessType = accessType;
            }

            public void setSocialCollectCount(int socialCollectCount) {
                this.socialCollectCount = socialCollectCount;
            }

            public void setSocialCollectId(int socialCollectId) {
                this.socialCollectId = socialCollectId;
            }

            public void setSocialCommentCount(int socialCommentCount) {
                this.socialCommentCount = socialCommentCount;
            }

            public void setSocialCreateTime(long socialCreateTime) {
                this.socialCreateTime = socialCreateTime;
            }

            public void setSocialDetial(String socialDetial) {
                this.socialDetial = socialDetial;
            }

            public void setSocialId(int socialId) {
                this.socialId = socialId;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public void setSocialPhoto(List<String> socialPhoto) {
                this.socialPhoto = socialPhoto;
            }

            public int getAccessType() {
                return accessType;
            }

            public int getSocialCollectCount() {
                return socialCollectCount;
            }

            public int getSocialCollectId() {
                return socialCollectId;
            }

            public int getSocialCommentCount() {
                return socialCommentCount;
            }

            public long getSocialCreateTime() {
                return socialCreateTime;
            }

            public String getSocialDetial() {
                return socialDetial;
            }

            public int getSocialId() {
                return socialId;
            }

            public int getStatus() {
                return status;
            }

            public List<String> getSocialPhoto() {
                return socialPhoto;
            }
        }
    }
}
