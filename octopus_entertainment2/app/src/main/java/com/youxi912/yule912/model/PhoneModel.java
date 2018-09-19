package com.youxi912.yule912.model;


public class PhoneModel extends BaseModel {

    /**
     * data : {"phone_no":"tt1"}
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
         * phone_no : tt1
         */

        private String phone_no;

        public String getPhone_no() {
            return phone_no;
        }

        public void setPhone_no(String phone_no) {
            this.phone_no = phone_no;
        }
    }
}
