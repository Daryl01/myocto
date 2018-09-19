package com.youxi912.yule912.model;

import java.util.List;

public class AwardModel extends BaseModel {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * ID : 64007
         * UserID : 17985
         * JLCount : 81
         * Ms : 恭喜您获得游戏返利奖励
         * AddTime : 2018/7/29 0:01:00
         */

        private String ID;
        private String UserID;
        private String JLCount;
        private String Ms;
        private String AddTime;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public String getJLCount() {
            return JLCount;
        }

        public void setJLCount(String JLCount) {
            this.JLCount = JLCount;
        }

        public String getMs() {
            return Ms;
        }

        public void setMs(String Ms) {
            this.Ms = Ms;
        }

        public String getAddTime() {
            return AddTime;
        }

        public void setAddTime(String AddTime) {
            this.AddTime = AddTime;
        }
    }
}
