package com.youxi912.yule912.model;

public class PsModel extends BaseModel {

    /**
     * data : {"MessageID":-1,"vip_level":"","receipts_month":"","receipts_today":"","receipts_whole":"","amount":""}
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
         * MessageID : -1
         * vip_level :
         * receipts_month :
         * receipts_today :
         * receipts_whole :
         * amount :
         */

        private int MessageID;
        private String vip_level;
        private String receipts_month;
        private String receipts_today;
        private String receipts_whole;
        private String amount;

        public int getMessageID() {
            return MessageID;
        }

        public void setMessageID(int MessageID) {
            this.MessageID = MessageID;
        }

        public String getVip_level() {
            return vip_level;
        }

        public void setVip_level(String vip_level) {
            this.vip_level = vip_level;
        }

        public String getReceipts_month() {
            return receipts_month;
        }

        public void setReceipts_month(String receipts_month) {
            this.receipts_month = receipts_month;
        }

        public String getReceipts_today() {
            return receipts_today;
        }

        public void setReceipts_today(String receipts_today) {
            this.receipts_today = receipts_today;
        }

        public String getReceipts_whole() {
            return receipts_whole;
        }

        public void setReceipts_whole(String receipts_whole) {
            this.receipts_whole = receipts_whole;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }
    }
}
