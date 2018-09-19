package com.youxi912.yule912.model;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InviteRecordModel extends BaseModel {
    private DataBean data;
    /**
     * data : {"avatar_svr":"http://www.912cc.com/JYEIcon/","all":[{"NickName":"小项小项","Accounts":"17681850424","Dj":"0","RegisterDate":"2018/5/23 1:32:50","ZT":null,"Tx":""},{"NickName":"业哥","Accounts":"13299111215","Dj":"2","RegisterDate":"2018/5/23 2:04:32","ZT":null,"Tx":""}],"zt":[{"Accounts":"13299111215","NickName":"业哥","RegisterDate":"2018/5/23 2:03:43","Dj":"2","ZT":null,"Tx":"/TxIcon/morentouxiang.png"},{"Accounts":"17681850424","NickName":"小项小项","RegisterDate":"2018/5/23 1:28:58","Dj":"0","ZT":null,"Tx":"/TxIcon/morentouxiang.png"}]}
     */

    @SerializedName("data")
    private DataBean dataX;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public DataBean getDataX() {
        return dataX;
    }

    public void setDataX(DataBean dataX) {
        this.dataX = dataX;
    }

    public static class DataBean {
        /**
         * avatar_svr : http://www.912cc.com/JYEIcon/
         * all : [{"NickName":"小项小项","Accounts":"17681850424","Dj":"0","RegisterDate":"2018/5/23 1:32:50","ZT":null,"Tx":""},{"NickName":"业哥","Accounts":"13299111215","Dj":"2","RegisterDate":"2018/5/23 2:04:32","ZT":null,"Tx":""}]
         * zt : [{"Accounts":"13299111215","NickName":"业哥","RegisterDate":"2018/5/23 2:03:43","Dj":"2","ZT":null,"Tx":"/TxIcon/morentouxiang.png"},{"Accounts":"17681850424","NickName":"小项小项","RegisterDate":"2018/5/23 1:28:58","Dj":"0","ZT":null,"Tx":"/TxIcon/morentouxiang.png"}]
         */

        private String avatar_svr;
        private List<AllBean> all;
        private List<AllBean> zt;

        public String getAvatar_svr() {
            return avatar_svr;
        }

        public void setAvatar_svr(String avatar_svr) {
            this.avatar_svr = avatar_svr;
        }

        public List<AllBean> getAll() {
            return all;
        }

        public void setAll(List<AllBean> all) {
            this.all = all;
        }

        public List<AllBean> getZt() {
            return zt;
        }

        public void setZt(List<AllBean> zt) {
            this.zt = zt;
        }

        public static class AllBean implements Parcelable {
            /**
             * NickName : 小项小项
             * Accounts : 17681850424
             * Dj : 0
             * RegisterDate : 2018/5/23 1:32:50
             * ZT : null
             * Tx :
             */

            private String NickName;
            private String Accounts;
            private String Dj;
            private String RegisterDate;
            private Object ZT;
            private String Tx;

            public AllBean(){}


            private AllBean(Parcel in) {
                NickName = in.readString();
                Accounts = in.readString();
                Dj = in.readString();
                RegisterDate = in.readString();
                ZT = in.readValue(getClass().getClassLoader());
                Tx = in.readString();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(NickName);
                parcel.writeString(Accounts);
                parcel.writeString(Dj);
                parcel.writeString(RegisterDate);
                parcel.writeValue(ZT);
                parcel.writeString(Tx);
            }

            public static final Creator<AllBean> CREATOR = new Creator<AllBean>() {
                @Override
                public AllBean createFromParcel(Parcel parcel) {
                    return new AllBean(parcel);
                }

                @Override
                public AllBean[] newArray(int i) {
                    return new AllBean[i];
                }
            };

            public String getNickName() {
                return NickName;
            }

            public void setNickName(String NickName) {
                this.NickName = NickName;
            }

            public String getAccounts() {
                return Accounts;
            }

            public void setAccounts(String Accounts) {
                this.Accounts = Accounts;
            }

            public String getDj() {
                return Dj;
            }

            public void setDj(String Dj) {
                this.Dj = Dj;
            }

            public String getRegisterDate() {
                return RegisterDate;
            }

            public void setRegisterDate(String RegisterDate) {
                this.RegisterDate = RegisterDate;
            }

            public Object getZT() {
                return ZT;
            }

            public void setZT(Object ZT) {
                this.ZT = ZT;
            }

            public String getTx() {
                return Tx;
            }

            public void setTx(String Tx) {
                this.Tx = Tx;
            }
        }
    }
}
