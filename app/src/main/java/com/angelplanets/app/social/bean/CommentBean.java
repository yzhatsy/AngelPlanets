package com.angelplanets.app.social.bean;

import java.util.List;

/**
 * 评论列表的bean
 * Created by 123 on 2016/4/11.
 */
public class CommentBean {

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
        private int friendRelationId;
        private int status;
        /**
         * fromId : 86
         * socialCommentId : 124
         * fromName : 胖迪🙄
         * avatarUrl : /pic/customer/2016-01-19/e469e5fb-3e50-421b-a40b-89fd3e8d3256.jpg
         * fromContent : 是小飞机么
         * fromCreateTime : 1454485752000
         * toId : 0
         * toName :
         * toContent :
         * toStatus : 0
         */

        private List<SocialCommentListEntity> socialCommentList;

        public void setFriendRelationId(int friendRelationId) {
            this.friendRelationId = friendRelationId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setSocialCommentList(List<SocialCommentListEntity> socialCommentList) {
            this.socialCommentList = socialCommentList;
        }

        public int getFriendRelationId() {
            return friendRelationId;
        }

        public int getStatus() {
            return status;
        }

        public List<SocialCommentListEntity> getSocialCommentList() {
            return socialCommentList;
        }

        public static class SocialCommentListEntity {
            private int fromId;
            private int socialCommentId;
            private String fromName;
            private String avatarUrl;
            private String fromContent;
            private long fromCreateTime;
            private int toId;
            private String toName;
            private String toContent;
            private int toStatus;

            public void setFromId(int fromId) {
                this.fromId = fromId;
            }

            public void setSocialCommentId(int socialCommentId) {
                this.socialCommentId = socialCommentId;
            }

            public void setFromName(String fromName) {
                this.fromName = fromName;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public void setFromContent(String fromContent) {
                this.fromContent = fromContent;
            }

            public void setFromCreateTime(long fromCreateTime) {
                this.fromCreateTime = fromCreateTime;
            }

            public void setToId(int toId) {
                this.toId = toId;
            }

            public void setToName(String toName) {
                this.toName = toName;
            }

            public void setToContent(String toContent) {
                this.toContent = toContent;
            }

            public void setToStatus(int toStatus) {
                this.toStatus = toStatus;
            }

            public int getFromId() {
                return fromId;
            }

            public int getSocialCommentId() {
                return socialCommentId;
            }

            public String getFromName() {
                return fromName;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public String getFromContent() {
                return fromContent;
            }

            public long getFromCreateTime() {
                return fromCreateTime;
            }

            public int getToId() {
                return toId;
            }

            public String getToName() {
                return toName;
            }

            public String getToContent() {
                return toContent;
            }

            public int getToStatus() {
                return toStatus;
            }
        }
    }
}
