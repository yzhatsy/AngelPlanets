package com.angelplanets.app.social;

import java.util.List;

/**
 * 社交页面的bean类
 * Created by 123 on 2016/3/11.
 */
public class SocialBean {


    /**
     * message : 社交列表返回成功
     * statusCode : 200
     * success : true
     * data : [{"socialId":274,"socialCollectId":324,"status":1,"pictures":["/pic/cut/social/2016-02-23/CUT-a162b2fb-c7e0-447d-a223-cf766f88b338@320_426.jpg","/pic/social/2016-02-23/a162b2fb-c7e0-447d-a223-cf766f88b338.jpg","/pic/social/2016-02-23/d9031e00-c8dd-4d7b-bcdc-046310a1d5a5.jpg","/pic/social/2016-02-23/17e68096-3034-4550-b766-2a0e146d38b1.jpg","/pic/social/2016-02-23/8ff47538-f221-4014-a2a0-9eb49736173d.jpg"],"detail":"豆汁儿和哈尼在牧云山居的团聚","updateTime":1456220780000,"customerId":132,"nickname":"豆汁儿妈","avatarUrl":"/pic/customer/2016-02-23/4cb98746-883c-4bda-9797-d89238dca229.jpg","collectCount":10,"signature":null},{"socialId":262,"socialCollectId":-1,"status":0,"pictures":["/pic/cut/social/2016-02-04/CUT-85c46be7-5a76-4d24-9b60-223129d47719@320_426.jpg","/pic/social/2016-02-04/85c46be7-5a76-4d24-9b60-223129d47719.jpg"],"detail":"慵懒的一天","updateTime":1454557204000,"customerId":87,"nickname":"蜜糖果","avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","collectCount":10,"signature":"我是奢宠计划"},{"socialId":282,"socialCollectId":332,"status":1,"pictures":["/pic/cut/social/2016-02-24/CUT-5babb35c-5087-4c48-965b-e7e6404e5e2a@250_444.jpg","/pic/social/2016-02-24/5babb35c-5087-4c48-965b-e7e6404e5e2a.jpg","/pic/social/2016-02-24/bc0c89a3-dfaf-4ecf-919c-636fd062f52a.jpg","/pic/social/2016-02-24/a19d2925-7c28-4e8a-8ccb-a656b85dae27.jpg","/pic/social/2016-02-24/7a62750f-798d-46c0-99f7-3d2684eb8d60.jpg"],"detail":"妞妞七个月大的时候，进山因为抓兔子玩，后脚跑劈了一个指甲流血了，还记得当时丢弃了所有东西把她装进大号登山包从山里背出来的情景，如果你见过了你的狗狗掉下眼泪，那么她不是你的宠物，而是，你的亲人。","updateTime":1456296881000,"customerId":142,"nickname":"大抓抓","avatarUrl":"/pic/customer/2016-02-24/b1138212-1c55-4702-a2b3-70b2472fef08.jpg","collectCount":6,"signature":null},{"socialId":239,"socialCollectId":272,"status":1,"pictures":["/pic/cut/social/2016-02-03/CUT-37192fe9-ed5e-4d14-a8fc-30a3b1e7328d@200_114.jpg","/pic/social/2016-02-03/37192fe9-ed5e-4d14-a8fc-30a3b1e7328d.jpg"],"detail":"我爱奢宠计划。","updateTime":1454492369000,"customerId":83,"nickname":"肉肉","avatarUrl":"/pic/customer/2016-01-18/d38cdb1f-2a4a-44af-9e94-7a08f9b16c19.jpg","collectCount":6,"signature":null},{"socialId":270,"socialCollectId":-1,"status":0,"pictures":["/pic/cut/social/2016-02-22/CUT-88bd3849-2d30-48ef-8db5-64ea77f69bbd@135_125.jpg","/pic/social/2016-02-22/88bd3849-2d30-48ef-8db5-64ea77f69bbd.jpg","/pic/social/2016-02-22/1abd0a90-2bbf-4d1e-bb93-8ffda0aadbb1.jpg","/pic/social/2016-02-22/4f772b5f-6bd5-4109-b450-5d9129b917ea.jpg"],"detail":"我的雪球，可爱不。😂😂","updateTime":1456112736000,"customerId":115,"nickname":"小鱼饼干越","avatarUrl":"/pic/customer/2016-02-22/2abfbd88-b31d-4f3a-96e5-6db18d31b76a.jpg","collectCount":6,"signature":null},{"socialId":249,"socialCollectId":273,"status":1,"pictures":["/pic/cut/social/2016-02-03/CUT-38b1c8cc-233e-47db-be26-548abfc1c2c7@213_472.jpg","/pic/social/2016-02-03/38b1c8cc-233e-47db-be26-548abfc1c2c7.jpg"],"detail":"text","updateTime":1454492786000,"customerId":87,"nickname":"蜜糖果","avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","collectCount":5,"signature":"我是奢宠计划"},{"socialId":277,"socialCollectId":314,"status":1,"pictures":["/pic/cut/social/2016-02-23/CUT-01263b8d-ffca-4e86-b319-ce57e9bb1102@375_562.jpg","/pic/social/2016-02-23/01263b8d-ffca-4e86-b319-ce57e9bb1102.jpg","/pic/social/2016-02-23/cb23a076-4885-42c8-a39f-1418054b9bf3.jpg"],"detail":"我爱奢宠计划。","updateTime":1456236304000,"customerId":137,"nickname":"你像是泡沫","avatarUrl":"/pic/customer/2016-02-23/8e587c9e-3c09-468b-88ab-93aa9bd39ed3.jpg","collectCount":4,"signature":null},{"socialId":272,"socialCollectId":-1,"status":0,"pictures":["/pic/cut/social/2016-02-22/CUT-a66daeb9-b68b-4f15-840a-411751ecf3ae@414_233.jpg","/pic/social/2016-02-22/a66daeb9-b68b-4f15-840a-411751ecf3ae.jpg","/pic/social/2016-02-22/75bdfb55-0039-4079-8d6d-f1c6859a7e6d.jpg","/pic/social/2016-02-22/2810b808-7aa3-4c7a-bd86-9acabff97f56.jpg","/pic/social/2016-02-22/60e2cbc0-6943-4b4e-8161-bbb3ed71a724.jpg"],"detail":"一直记得你第一天来家里撒娇的样子。那时候你才两个半月，现在都快半岁了。","updateTime":1456149577000,"customerId":128,"nickname":"熊奶奶","avatarUrl":"/pic/customer/2016-02-22/751f3f55-9a7f-4845-8610-ff3fa7ab6917.jpg","collectCount":4,"signature":null},{"socialId":236,"socialCollectId":275,"status":1,"pictures":["/pic/cut/social/2016-02-03/CUT-7ab4bfb6-1a46-4777-b466-d6374a1887c5@375_281.jpg","/pic/social/2016-02-03/7ab4bfb6-1a46-4777-b466-d6374a1887c5.jpg"],"detail":"真爱","updateTime":1454486503000,"customerId":87,"nickname":"蜜糖果","avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","collectCount":4,"signature":"我是奢宠计划"},{"socialId":268,"socialCollectId":-1,"status":0,"pictures":["/pic/cut/social/2016-02-04/CUT-98f80ae9-6f03-4e48-8cf6-fe742c310528@277_416.jpg","/pic/social/2016-02-04/98f80ae9-6f03-4e48-8cf6-fe742c310528.jpg"],"detail":"我爱奢宠计划。","updateTime":1454557308000,"customerId":87,"nickname":"蜜糖果","avatarUrl":"/pic/customer/2016-01-18/29263af8-9601-4108-bb8f-fbd777e07447.jpg","collectCount":3,"signature":"我是奢宠计划"},{"socialId":306,"socialCollectId":374,"status":1,"pictures":["/pic/cut/social/2016-03-03/CUT-3936f461-6d41-4fc1-96bc-86585e368cb6@319_213.jpg","/pic/social/2016-03-03/3936f461-6d41-4fc1-96bc-86585e368cb6.jpg","/pic/social/2016-03-03/4b509679-8a96-4750-bab0-93ffe2c12f11.jpg","/pic/social/2016-03-03/828e2699-05b0-493c-addd-c584e98b9177.jpg","/pic/social/2016-03-03/bf802f90-3f06-4f08-9b01-55c27a07adbb.jpg"],"detail":"这是妞子第一次参加奢宠计划的活动，希望能能认识更多的朋友","updateTime":1457016483000,"customerId":188,"nickname":"家有美妞","avatarUrl":"/pic/customer/2016-03-03/3339ac1c-6a67-4b5c-8cbd-6e63d1534eb6.jpg","collectCount":3,"signature":null},{"socialId":242,"socialCollectId":274,"status":1,"pictures":["/pic/cut/social/2016-02-03/CUT-166ac23f-f104-46f4-9c7a-2eb6742fdbaf@155_200.jpg","/pic/social/2016-02-03/166ac23f-f104-46f4-9c7a-2eb6742fdbaf.jpg"],"detail":"我爱奢宠计划。","updateTime":1454492400000,"customerId":83,"nickname":"肉肉","avatarUrl":"/pic/customer/2016-01-18/d38cdb1f-2a4a-44af-9e94-7a08f9b16c19.jpg","collectCount":3,"signature":null},{"socialId":241,"socialCollectId":331,"status":1,"pictures":["/pic/cut/social/2016-02-03/CUT-b0da47d1-ddfa-4b3b-8e4d-f3b2a2dbfc54@375_250.jpg","/pic/social/2016-02-03/b0da47d1-ddfa-4b3b-8e4d-f3b2a2dbfc54.jpg"],"detail":"我爱奢宠计划。","updateTime":1454492399000,"customerId":84,"nickname":"小飞侠","avatarUrl":"/pic/customer/2016-03-08/451a6759-9bd9-40b1-8b61-92764261b881.jpg","collectCount":3,"signature":null},{"socialId":285,"socialCollectId":323,"status":1,"pictures":["/pic/cut/social/2016-02-24/CUT-16de619b-f1bc-4126-bb15-dd0535214be8@213_284.jpg","/pic/social/2016-02-24/16de619b-f1bc-4126-bb15-dd0535214be8.jpg","/pic/social/2016-02-24/8585b16a-04ef-45c7-bc57-a9e8a549e193.jpg","/pic/social/2016-02-24/eecadc0c-1abd-4bac-93b0-4f84ce3b6215.jpg","/pic/social/2016-02-24/8f6154e7-f873-4c16-9309-7c9c82470519.jpg"],"detail":"实事求是的说，孙豆豆从小就不漂亮，顶多是萌，肥壮，嘴馋，脾气差，因为是独生子，所以性格独特，活脱脱一个活土匪。","updateTime":1456297578000,"customerId":142,"nickname":"大抓抓","avatarUrl":"/pic/customer/2016-02-24/b1138212-1c55-4702-a2b3-70b2472fef08.jpg","collectCount":2,"signature":null},{"socialId":273,"socialCollectId":303,"status":1,"pictures":["/pic/cut/social/2016-02-23/CUT-9bd1ca2b-4afd-4573-9162-5b1676ca4442@408_544.jpg","/pic/social/2016-02-23/9bd1ca2b-4afd-4573-9162-5b1676ca4442.jpg"],"detail":"在窗前干嘛？等漂酿小阿姨送的项链嘛？","updateTime":1456214764000,"customerId":128,"nickname":"熊奶奶","avatarUrl":"/pic/customer/2016-02-22/751f3f55-9a7f-4845-8610-ff3fa7ab6917.jpg","collectCount":2,"signature":null}]
     */

    private String message;
    private int statusCode;
    private boolean success;
    /**
     * socialId : 274
     * socialCollectId : 324
     * status : 1
     * pictures : ["/pic/cut/social/2016-02-23/CUT-a162b2fb-c7e0-447d-a223-cf766f88b338@320_426.jpg","/pic/social/2016-02-23/a162b2fb-c7e0-447d-a223-cf766f88b338.jpg","/pic/social/2016-02-23/d9031e00-c8dd-4d7b-bcdc-046310a1d5a5.jpg","/pic/social/2016-02-23/17e68096-3034-4550-b766-2a0e146d38b1.jpg","/pic/social/2016-02-23/8ff47538-f221-4014-a2a0-9eb49736173d.jpg"]
     * detail : 豆汁儿和哈尼在牧云山居的团聚
     * updateTime : 1456220780000
     * customerId : 132
     * nickname : 豆汁儿妈
     * avatarUrl : /pic/customer/2016-02-23/4cb98746-883c-4bda-9797-d89238dca229.jpg
     * collectCount : 10
     * signature : null
     */

    private List<SocialData> data;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(List<SocialData> data) {
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

    public List<SocialData> getData() {
        return data;
    }

    public static class SocialData {
        private int socialId;
        private int socialCollectId;
        private int status;
        private String detail;
        private long updateTime;
        private int customerId;
        private String nickname;
        private String avatarUrl;
        private int collectCount;
        private Object signature;
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

        public void setSignature(Object signature) {
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

        public Object getSignature() {
            return signature;
        }

        public List<String> getPictures() {
            return pictures;
        }
    }
}
