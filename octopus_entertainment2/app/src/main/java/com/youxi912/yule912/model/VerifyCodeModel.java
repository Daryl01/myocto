package com.youxi912.yule912.model;

public class VerifyCodeModel extends BaseModel {

    /**
     * data : {"verifyid":"5767"}
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
         * verifyid : 5767
         */

        private String verifyid;

        public String getVerifyid() {
            return verifyid;
        }

        public void setVerifyid(String verifyid) {
            this.verifyid = verifyid;
        }
    }
}
