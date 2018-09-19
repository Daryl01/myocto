package com.youxi912.yule912.model;

public class CreateLiveModel extends BaseModel {


    /**
     * data : {"httpPullUrl":"http://flv7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5.flv?netease=flv7a03ce74.live.126.net","hlsPullUrl":"http://pullhls7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5/playlist.m3u8","rtmpPullUrl":"rtmp://v7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5","name":"测试直播8","pushUrl":"rtmp://p7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5?wsSecret=9359f36897a2c6fbc2f1f8abae8f767a&wsTime=1535956904","ctime":1535956904728,"cid":"2aecc0dc61584076bcbcdbeed21b8cc5","roomid":55890709}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * httpPullUrl : http://flv7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5.flv?netease=flv7a03ce74.live.126.net
         * hlsPullUrl : http://pullhls7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5/playlist.m3u8
         * rtmpPullUrl : rtmp://v7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5
         * name : 测试直播8
         * pushUrl : rtmp://p7a03ce74.live.126.net/live/2aecc0dc61584076bcbcdbeed21b8cc5?wsSecret=9359f36897a2c6fbc2f1f8abae8f767a&wsTime=1535956904
         * ctime : 1535956904728
         * cid : 2aecc0dc61584076bcbcdbeed21b8cc5
         * roomid : 55890709
         */

        private String httpPullUrl;
        private String hlsPullUrl;
        private String rtmpPullUrl;
        private String name;
        private String pushUrl;
        private long ctime;
        private String cid;
        private int roomid;

        public String getHttpPullUrl() {
            return httpPullUrl;
        }

        public void setHttpPullUrl(String httpPullUrl) {
            this.httpPullUrl = httpPullUrl;
        }

        public String getHlsPullUrl() {
            return hlsPullUrl;
        }

        public void setHlsPullUrl(String hlsPullUrl) {
            this.hlsPullUrl = hlsPullUrl;
        }

        public String getRtmpPullUrl() {
            return rtmpPullUrl;
        }

        public void setRtmpPullUrl(String rtmpPullUrl) {
            this.rtmpPullUrl = rtmpPullUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }
    }
}
