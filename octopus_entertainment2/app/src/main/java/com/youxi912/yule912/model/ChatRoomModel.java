package com.youxi912.yule912.model;

import java.util.List;

public class ChatRoomModel extends BaseModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * roomid : 55628478
         * name : 区块链知识普及
         * userid : 11111
         * creator : tt1
         * broadcasturl :
         * cover :
         * roomtype : 1
         * create_time :
         * update_time :
         */

        private int roomid;
        private String name;
        private int userid;
        private String creator;
        private String broadcasturl;
        private String cover;
        private int roomtype;
        private String create_time;
        private String update_time;

        public int getRoomid() {
            return roomid;
        }

        public void setRoomid(int roomid) {
            this.roomid = roomid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getBroadcasturl() {
            return broadcasturl;
        }

        public void setBroadcasturl(String broadcasturl) {
            this.broadcasturl = broadcasturl;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getRoomtype() {
            return roomtype;
        }

        public void setRoomtype(int roomtype) {
            this.roomtype = roomtype;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
