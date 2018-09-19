package com.youxi912.yule912.model;

import java.util.List;

public class RecordModel extends BaseModel {

    /**
     * data : {"trunin":[{"remark":"从虾虾转入1个章鱼币","datetime":"2018/6/4 23:46:49"}],"turnout":[{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/17 22:53:06"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/17 7:58:16"},{"remark":"向tt3转出5个章鱼币","datetime":"2018/8/17 4:03:49"},{"remark":"向tt3转出5个章鱼币","datetime":"2018/8/17 3:02:36"},{"remark":"向tt2转出6个章鱼币","datetime":"2018/8/16 21:19:32"},{"remark":"向tt2转出3个章鱼币","datetime":"2018/8/16 21:19:23"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 21:17:02"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 21:13:56"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 21:13:49"},{"remark":"向tt2转出2个章鱼币","datetime":"2018/8/16 21:10:37"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 21:05:51"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 20:59:23"},{"remark":"向tt2转出1个章鱼币","datetime":"2018/8/16 20:53:14"},{"remark":"向tt2转出10个章鱼币","datetime":"2018/8/16 20:51:18"},{"remark":"向tt2转出10个章鱼币","datetime":"2018/8/16 18:37:35"},{"remark":"向tt2转出10个章鱼币","datetime":"2018/8/16 18:36:40"},{"remark":"向大头哥转出100000个章鱼币","datetime":"2018/6/22 19:43:08"},{"remark":"向富婆来了转出400000个章鱼币","datetime":"2018/6/15 11:34:18"},{"remark":"向富婆来了转出200000个章鱼币","datetime":"2018/6/5 17:36:11"},{"remark":"向tt2转出11个章鱼币","datetime":"2018/6/4 23:15:20"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<Bean> trunin;
        private List<Bean> turnout;

        public List<Bean> getTrunin() {
            return trunin;
        }

        public void setTrunin(List<Bean> trunin) {
            this.trunin = trunin;
        }

        public List<Bean> getTurnout() {
            return turnout;
        }

        public void setTurnout(List<Bean> turnout) {
            this.turnout = turnout;
        }



        public static class Bean {
            /**
             * remark : 向tt2转出1个章鱼币
             * datetime : 2018/8/17 22:53:06
             */

            private String remark;
            private String datetime;

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getDatetime() {
                return datetime;
            }

            public void setDatetime(String datetime) {
                this.datetime = datetime;
            }
        }
    }
}
