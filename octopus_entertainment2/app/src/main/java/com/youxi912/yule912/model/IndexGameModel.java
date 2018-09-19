package com.youxi912.yule912.model;

import java.io.Serializable;
import java.util.List;

public class IndexGameModel extends BaseModel implements Serializable {

    /**
     * data : {"total":2,"per_page":"100","current_page":"1","last_page":1,"data":[{"id":1,"name":"王者荣耀","subject":null,"cate_id":10,"size":null,"support_android":0,"support_ios":0,"price":"0.000000","price_currency":null,"download_times":0,"content":null,"rank":null,"status":0,"create_time":null,"update_time":null,"search_times":18,"logo":"//www.baidu.com/img/baidu_jgylogo3.gif","typename":"单机游戏","pic":[{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null}]},{"id":2,"name":"绝地求生","subject":null,"cate_id":11,"size":null,"support_android":0,"support_ios":0,"price":"0.000000","price_currency":null,"download_times":0,"content":null,"rank":null,"status":0,"create_time":null,"update_time":null,"search_times":8,"logo":"//www.baidu.com/img/baidu_jgylogo3.gif","typename":"网络游戏","pic":[{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null}]}]}
     */

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Serializable {
        /**
         * total : 2
         * per_page : 100
         * current_page : 1
         * last_page : 1
         * data : [{"id":1,"name":"王者荣耀","subject":null,"cate_id":10,"size":null,"support_android":0,"support_ios":0,"price":"0.000000","price_currency":null,"download_times":0,"content":null,"rank":null,"status":0,"create_time":null,"update_time":null,"search_times":18,"logo":"//www.baidu.com/img/baidu_jgylogo3.gif","typename":"单机游戏","pic":[{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null}]},{"id":2,"name":"绝地求生","subject":null,"cate_id":11,"size":null,"support_android":0,"support_ios":0,"price":"0.000000","price_currency":null,"download_times":0,"content":null,"rank":null,"status":0,"create_time":null,"update_time":null,"search_times":8,"logo":"//www.baidu.com/img/baidu_jgylogo3.gif","typename":"网络游戏","pic":[{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null},{"url":"//www.baidu.com/img/baidu_jgylogo3.gif","title":null}]}]
         */

        private int total;
        private int per_page;
        private int current_page;
        private int last_page;
        private List<DataBean> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

    }
}
