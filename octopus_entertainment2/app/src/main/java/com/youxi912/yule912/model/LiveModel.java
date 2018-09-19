package com.youxi912.yule912.model;

import java.util.List;

public class LiveModel extends BaseModel {


    /**
     * "data":{
     "needRecord":0,
     "uid":102308047,
     "duration":120,
     "status":0,
     "name":"12345上山打老虎",
     "filename":"12345上山打老虎",
     "format":1,
     "type":2,
     "ctime":1535971813998,
     "cid":"02c19415bade43768f50bfcb7c4fab56",
     "recordStatus":0
     }
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
    }
}
