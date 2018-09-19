package com.youxi912.yule912.model;

import java.util.List;

public class CheckInModel extends BaseModel {

    /**
     * data : {"NowCount":"12","SumCount":"24"}
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
         * NowCount : 12
         * SumCount : 24
         */

        private String NowCount;
        private String SumCount;

        public String getNowCount() {
            return NowCount;
        }

        public void setNowCount(String NowCount) {
            this.NowCount = NowCount;
        }

        public String getSumCount() {
            return SumCount;
        }

        public void setSumCount(String SumCount) {
            this.SumCount = SumCount;
        }
    }
}
