package com.youxi912.yule912.model;

import java.io.Serializable;

public class CoinDetailModel extends BaseModel implements Serializable{

    /**
     * data : {"currency_name":"TRX","currency_img":"","available_balance":"0.000000","freezing_amount":"0.000000","locking_amount":"0.000000","ps_amount":"0.000000"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * currency_name : TRX
         * currency_img :
         * available_balance : 0.000000
         * freezing_amount : 0.000000
         * locking_amount : 0.000000
         * ps_amount : 0.000000
         */

        private String currency_name;
        private String currency_img;
        private String available_balance;
        private String freezing_amount;
        private String locking_amount;
        private String ps_amount;

        public String getCurrency_name() {
            return currency_name;
        }

        public void setCurrency_name(String currency_name) {
            this.currency_name = currency_name;
        }

        public String getCurrency_img() {
            return currency_img;
        }

        public void setCurrency_img(String currency_img) {
            this.currency_img = currency_img;
        }

        public String getAvailable_balance() {
            return available_balance;
        }

        public void setAvailable_balance(String available_balance) {
            this.available_balance = available_balance;
        }

        public String getFreezing_amount() {
            return freezing_amount;
        }

        public void setFreezing_amount(String freezing_amount) {
            this.freezing_amount = freezing_amount;
        }

        public String getLocking_amount() {
            return locking_amount;
        }

        public void setLocking_amount(String locking_amount) {
            this.locking_amount = locking_amount;
        }

        public String getPs_amount() {
            return ps_amount;
        }

        public void setPs_amount(String ps_amount) {
            this.ps_amount = ps_amount;
        }
    }
}
