package com.angelplanets.app.social.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 123 on 2016/4/10.
 */
public class SocialBean {


    private List<SocialData> data;


    public void setData(List<SocialData> data) {
        this.data = data;
    }

    public List<SocialData> getData() {
        return data;
    }

    public static class SocialData implements Serializable {
        private int socialId;
        private int socialCollectId;
        private int status;
        private String detail;
        private long updateTime;
        private int customerId;
        private String nickname;
        private String avatarUrl;
        private int collectCount;
        private String signature;
        private List<String> pictures;


        public void setSocialId(int socialId) {
            this.socialId = socialId;
        }

        public void setSocialCollectId(int socialCollectId) {
            this.socialCollectId = socialCollectId;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
        }

        public void setCollectCount(int collectCount) {
            this.collectCount = collectCount;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setPictures(List<String> pictures) {
            this.pictures = pictures;
        }

        public int getSocialId() {
            return socialId;
        }

        public int getSocialCollectId() {
            return socialCollectId;
        }

        public int getStatus() {
            return status;
        }

        public String getDetail() {
            return detail;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getNickname() {
            return nickname;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public int getCollectCount() {
            return collectCount;
        }

        public String getSignature() {
            return signature;
        }

        public List<String> getPictures() {
            return pictures;
        }
    }
}
