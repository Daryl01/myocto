package com.youxi912.yule912.model;

import java.io.Serializable;
import java.util.List;

public class BannerModel extends BaseModel implements Serializable{

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * mid : 3
         * title : app图片1
         * url :
         * litpic : /uploads/image/20180725/a422e82910557859fc79f96e1c5bb792.png
         * info : app图片1
         * sorts : 50
         * status : 1
         * create_time : 1532529088
         * update_time : 1532529088
         */

        private int id;
        private int gameid;
        private int mid;
        private String title;
        private String url;
        private String litpic;
        private String info;
        private int sorts;
        private int status;
        private int create_time;
        private int update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGameId() {
            return gameid;
        }

        public void setGameId(int gameid) {
            this.gameid = gameid;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getLitpic() {
            return litpic;
        }

        public void setLitpic(String litpic) {
            this.litpic = litpic;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getSorts() {
            return sorts;
        }

        public void setSorts(int sorts) {
            this.sorts = sorts;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }
    }
}
