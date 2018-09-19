package com.youxi912.yule912.model;

import java.io.Serializable;

public class UserLoginModel extends BaseModel {

    /**
     * data : {"UserID":17988,"GameID":"616063","NickName":"tt3","MemberOrder":"5","Gender":0,"UnderWrite":"","Gold":8506,"DJ":"0","TJUser":"","TJUserGameID":1,"TxImg":"http:////www.912cc.com/JY/JYEIcon/TxIcon/17988_88_131791626376525592.png","StartCount":1,"EWMdress":"","yx_user":{"yx_accid":"9hcpl00g.8_-w27tbb_b@ifa9ejch3af","yx_token":"da9bd78f14665d25337e966ee7a2324a"},"token":"dxLd9GHKqbATsS7N4VFgGAwT6e1XJuavXhfNMXyCgfFHdewcsrUrWnZYhL5njgY","introducer_name":"未知"}
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
         * UserID : 17988
         * GameID : 616063
         * NickName : tt3
         * MemberOrder : 5
         * Gender : 0
         * UnderWrite :
         * Gold : 8506
         * DJ : 0
         * TJUser :
         * TJUserGameID : 1
         * TxImg : http:////www.912cc.com/JY/JYEIcon/TxIcon/17988_88_131791626376525592.png
         * StartCount : 1
         * EWMdress :
         * yx_user : {"yx_accid":"9hcpl00g.8_-w27tbb_b@ifa9ejch3af","yx_token":"da9bd78f14665d25337e966ee7a2324a"}
         * token : dxLd9GHKqbATsS7N4VFgGAwT6e1XJuavXhfNMXyCgfFHdewcsrUrWnZYhL5njgY
         * introducer_name : 未知
         */

        private int UserID;
        private String GameID;
        private String NickName;
        private String MemberOrder;
        private int Gender;
        private String UnderWrite;
        private int Gold;
        private String DJ;
        private String TJUser;
        private int TJUserGameID;
        private String TxImg;
        private String StartCount;
        private String EWMdress;
        private YxUserBean yx_user;
        private String token;
        private String isSigned;
        private String introducer_name;

        public String getIsSigned() {
            return isSigned;
        }

        public void setIsSigned(String isSigned) {
            this.isSigned = isSigned;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public String getGameID() {
            return GameID;
        }

        public void setGameID(String GameID) {
            this.GameID = GameID;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public String getMemberOrder() {
            return MemberOrder;
        }

        public void setMemberOrder(String MemberOrder) {
            this.MemberOrder = MemberOrder;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public String getUnderWrite() {
            return UnderWrite;
        }

        public void setUnderWrite(String UnderWrite) {
            this.UnderWrite = UnderWrite;
        }

        public int getGold() {
            return Gold;
        }

        public void setGold(int Gold) {
            this.Gold = Gold;
        }

        public String getDJ() {
            return DJ;
        }

        public void setDJ(String DJ) {
            this.DJ = DJ;
        }

        public String getTJUser() {
            return TJUser;
        }

        public void setTJUser(String TJUser) {
            this.TJUser = TJUser;
        }

        public int getTJUserGameID() {
            return TJUserGameID;
        }

        public void setTJUserGameID(int TJUserGameID) {
            this.TJUserGameID = TJUserGameID;
        }

        public String getTxImg() {
            return TxImg;
        }

        public void setTxImg(String TxImg) {
            this.TxImg = TxImg;
        }

        public String getStartCount() {
		 	if(StartCount == null)
            {
                return "0";
            }
            return StartCount;
        }

        public void setStartCount(String StartCount) {
            this.StartCount = StartCount;
        }

        public String getEWMdress() {
            return EWMdress;
        }

        public void setEWMdress(String EWMdress) {
            this.EWMdress = EWMdress;
        }

        public YxUserBean getYx_user() {
            return yx_user;
        }

        public void setYx_user(YxUserBean yx_user) {
            this.yx_user = yx_user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getIntroducer_name() {
            return introducer_name;
        }

        public void setIntroducer_name(String introducer_name) {
            this.introducer_name = introducer_name;
        }

        public static class YxUserBean  implements Serializable {
            /**
             * yx_accid : 9hcpl00g.8_-w27tbb_b@ifa9ejch3af
             * yx_token : da9bd78f14665d25337e966ee7a2324a
             */

            private String yx_accid;
            private String yx_token;

            public String getYx_accid() {
                return yx_accid;
            }

            public void setYx_accid(String yx_accid) {
                this.yx_accid = yx_accid;
            }

            public String getYx_token() {
                return yx_token;
            }

            public void setYx_token(String yx_token) {
                this.yx_token = yx_token;
            }
        }
    }
}
