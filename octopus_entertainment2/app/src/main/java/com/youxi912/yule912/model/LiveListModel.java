package com.youxi912.yule912.model;

import java.util.List;

public class LiveListModel extends BaseModel {


    /**
     * data : {"pnum":2,"list":[{"needRecord":0,"uid":102308047,"duration":120,"status":0,"name":"许卓权的直播3","filename":"许卓权的直播3","format":1,"type":2,"ctime":1535958205598,"cid":"9e31fb19a48842838f72baa95f96fe24","recordStatus":0,"roomid":""},{"needRecord":0,"uid":102308047,"duration":120,"status":0,"name":"许","filename":"许","format":1,"type":2,"ctime":1535958260169,"cid":"ba6d47f3cdc741ab8bb811657565a610","recordStatus":0,"roomid":""}],"totalRecords":22,"totalPnum":2,"records":11}
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
         * pnum : 2
         * list : [{"needRecord":0,"uid":102308047,"duration":120,"status":0,"name":"许卓权的直播3","filename":"许卓权的直播3","format":1,"type":2,"ctime":1535958205598,"cid":"9e31fb19a48842838f72baa95f96fe24","recordStatus":0,"roomid":""},{"needRecord":0,"uid":102308047,"duration":120,"status":0,"name":"许","filename":"许","format":1,"type":2,"ctime":1535958260169,"cid":"ba6d47f3cdc741ab8bb811657565a610","recordStatus":0,"roomid":""}]
         * totalRecords : 22
         * totalPnum : 2
         * records : 11
         */

        private int pnum;
        private int totalRecords;
        private int totalPnum;
        private int records;
        private List<ListBean> list;

        public int getPnum() {
            return pnum;
        }

        public void setPnum(int pnum) {
            this.pnum = pnum;
        }

        public int getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(int totalRecords) {
            this.totalRecords = totalRecords;
        }

        public int getTotalPnum() {
            return totalPnum;
        }

        public void setTotalPnum(int totalPnum) {
            this.totalPnum = totalPnum;
        }

        public int getRecords() {
            return records;
        }

        public void setRecords(int records) {
            this.records = records;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * needRecord : 0
             * uid : 102308047
             * duration : 120
             * status : 0
             * name : 许卓权的直播3
             * filename : 许卓权的直播3
             * format : 1
             * type : 2
             * ctime : 1535958205598
             * cid : 9e31fb19a48842838f72baa95f96fe24
             * recordStatus : 0
             * roomid :
             */

            private int needRecord;
            private int uid;
            private int duration;
            private int status;
            private String name;
            private String filename;
            private int format;
            private int type;
            private long ctime;
            private String cid;
            private int recordStatus;
            private Integer roomid;
            private String rtmp;
            private String http_pullurl;
            private String hls_pullurl;
            private String rtmp_pullurl;
            private String cover;
            private int userid;
            private String creater;


            public String getRtmp() {
                return rtmp;
            }

            public void setRtmp(String rtmp) {
                this.rtmp = rtmp;
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

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public String getCreater() {
                return creater;
            }

            public void setCreater(String creater) {
                this.creater = creater;
            }

            public int getNeedRecord() {
                return needRecord;
            }

            public void setNeedRecord(int needRecord) {
                this.needRecord = needRecord;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFilename() {
                return filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public int getFormat() {
                return format;
            }

            public void setFormat(int format) {
                this.format = format;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
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

            public int getRecordStatus() {
                return recordStatus;
            }

            public void setRecordStatus(int recordStatus) {
                this.recordStatus = recordStatus;
            }

            public Integer getRoomid() {
                return roomid;
            }

            public void setRoomid(Integer roomid) {
                this.roomid = roomid;
            }
        }
    }
}
