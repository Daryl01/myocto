package com.youxi912.yule912.model;

import java.util.List;

public class SignListModel extends BaseModel {

    /**
     * data : {"SingedScore":"12","Day":"2018/8/12 16:22:52","jr":"1"}
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * SingedScore : 12
         * Day : 2018/8/12 16:22:52
         * jr : 1
         */

        private String SingedScore;
        private String Day;
        private String jr;

        public String getSingedScore() {
            return SingedScore;
        }

        public void setSingedScore(String SingedScore) {
            this.SingedScore = SingedScore;
        }

        public String getDay() {
            return Day;
        }

        public void setDay(String Day) {
            this.Day = Day;
        }

        public String getJr() {
            return jr;
        }

        public void setJr(String jr) {
            this.jr = jr;
        }
    }
}
