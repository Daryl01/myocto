package com.youxi912.yule912.model;

import java.util.List;

public class GetRoomNameModel extends BaseModel {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * cid : 02c19415bade43768f50bfcb7c4fab56
         * name : 12345上山打老虎
         * pushurl : rtmp://p7a03ce74.live.126.net/live/02c19415bade43768f50bfcb7c4fab56?wsSecret=8ace69e519b88c3cf8071f0926fa053e&wsTime=1535971814
         * http_pullurl : http://flv7a03ce74.live.126.net/live/02c19415bade43768f50bfcb7c4fab56.flv?netease=flv7a03ce74.live.126.net
         * hls_pullurl : http://pullhls7a03ce74.live.126.net/live/02c19415bade43768f50bfcb7c4fab56/playlist.m3u8
         * rtmp_pullurl : rtmp://v7a03ce74.live.126.net/live/02c19415bade43768f50bfcb7c4fab56
         * status : 0
         * cover :
         * creater : tt1
         * userid : 17985
         * roomid : 55960892
         * create_time : null
         * update_time : null
         */

        private String cid;
        private String name;
        private String pushurl;
        private String http_pullurl;
        private String hls_pullurl;
        private String rtmp_pullurl;
        private int status;
        private String cover;
        private String creater;
        private int userid;
        private int roomid;
        private Object create_time;
        private Object update_time;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPushurl() {
            return pushurl;
        }

        public void setPushurl(String pushurl) {
            this.pushurl = pushurl;
        }

        public String getHttp_pullurl() {
            return http_pullurl;
        }

        public void setHttp_pullurl(String http_pullurl) {
            this.http_pullurl = http_pullurl;
        }

        public String getHls_pullurl() {
            return hls_pullurl;
        }

        public void setHls_pullurl(String hls_pullurl) {
            this.hls_pullurl = hls_pullurl;
        }

        public String getRtmp_pullurl() {
            return rtmp_pullurl;
        }

        public void setRtmp_pullurl(String rtmp_pullurl) {
            this.rtmp_pullurl = rtmp_pullurl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getCreater() {
            return creater;
        }

        public void setCreater(String creater) {
            this.creater = creater;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public Object getCreate_time() {
            return create_time;
        }

        public void setCreate_time(Object create_time) {
            this.create_time = create_time;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }
    }
}
