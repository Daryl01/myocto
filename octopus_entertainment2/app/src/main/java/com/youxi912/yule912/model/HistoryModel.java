package com.youxi912.yule912.model;

import java.util.List;

public class HistoryModel extends BaseModel {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * SourceUserID : 17985
         * TargetUserID : 28847
         * SwapScore : 100000
         * TradeType : 3
         * CollectDate : 2018/6/22 19:43:08
         * SourceNickName : tt1
         * TargetNickName : 大头哥
         */

        private String SourceUserID;
        private String TargetUserID;
        private String SwapScore;
        private String TradeType;
        private String CollectDate;
        private String SourceNickName;
        private String TargetNickName;

        public String getSourceUserID() {
            return SourceUserID;
        }

        public void setSourceUserID(String SourceUserID) {
            this.SourceUserID = SourceUserID;
        }

        public String getTargetUserID() {
            return TargetUserID;
        }

        public void setTargetUserID(String TargetUserID) {
            this.TargetUserID = TargetUserID;
        }

        public String getSwapScore() {
            return SwapScore;
        }

        public void setSwapScore(String SwapScore) {
            this.SwapScore = SwapScore;
        }

        public String getTradeType() {
            return TradeType;
        }

        public void setTradeType(String TradeType) {
            this.TradeType = TradeType;
        }

        public String getCollectDate() {
            return CollectDate;
        }

        public void setCollectDate(String CollectDate) {
            this.CollectDate = CollectDate;
        }

        public String getSourceNickName() {
            return SourceNickName;
        }

        public void setSourceNickName(String SourceNickName) {
            this.SourceNickName = SourceNickName;
        }

        public String getTargetNickName() {
            return TargetNickName;
        }

        public void setTargetNickName(String TargetNickName) {
            this.TargetNickName = TargetNickName;
        }
    }
}
