package com.youxi912.yule912.model;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {

    /**
     * id : 2
     * name : 绝地求生
     * subject : 正版吃鸡，激情一夏
     * cate_id : 13
     * size : 12
     * support_android : 1
     * support_ios : 1
     * price : 0.000000
     * price_currency : 12
     * download_times : 0
     * content : 游戏内容
     * rank : 95.00
     * status : 1
     * create_time : 1532529088
     * update_time : 1532529088
     * version : {"ios":{"id":4,"game_id":2,"resource_url":"","resource_size":"30.25","resource_type":2,"platform":1,"package_id":11,"package_version":"1.90.6","package_content":"全面开放新的玉林地图，玩家可以在地图上随机匹配","package_publish":1532529088,"downloads":10,"create_time":1532529088,"update_time":1532529088},"android":{"id":5,"game_id":2,"resource_url":"","resource_size":"33.67","resource_type":2,"platform":2,"package_id":null,"package_version":"1.90.6","package_content":"全面开放新的玉林地图，玩家可以在地图上随机匹配","package_publish":1532534400,"downloads":5,"create_time":1532529088,"update_time":1532529088},"pc":null}
     * rec_content : 操作实时，动画效果好
     * logo : http://soft.abrain.cn/octopus/testdata/icon_game2.png
     * typename : 棋牌
     * pic : [{"url":"http://soft.abrain.cn/octopus/testdata/game_detail1.png","title":""},{"url":"http://soft.abrain.cn/octopus/testdata/game_detail1.png","title":""},{"url":"http://soft.abrain.cn/octopus/testdata/game_detail1.png","title":""}]
     * pay_status : 0
     */

    private int id;
    private String name;
    private String subject;
    private int cate_id;
    private String game_level;
    private String size;
    private int support_android;
    private int support_ios;
    private String price;
    private String price_currency;
    private int download_times;
    private String content;
    private String rank;
    private int status;
    private long create_time;
    private long update_time;
    private VersionBean version;
    private String rec_content;
    private String logo;
    private String typename;
    private int pay_status;
    private List<PicBean> pic;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getGame_level() {
        return game_level;
    }

    public void setGame_level(String game_level) {
        this.game_level = game_level;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSupport_android() {
        return support_android;
    }

    public void setSupport_android(int support_android) {
        this.support_android = support_android;
    }

    public int getSupport_ios() {
        return support_ios;
    }

    public void setSupport_ios(int support_ios) {
        this.support_ios = support_ios;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice_currency() {
        return price_currency;
    }

    public void setPrice_currency(String price_currency) {
        this.price_currency = price_currency;
    }

    public int getDownload_times() {
        return download_times;
    }

    public void setDownload_times(int download_times) {
        this.download_times = download_times;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public String getRec_content() {
        return rec_content;
    }

    public void setRec_content(String rec_content) {
        this.rec_content = rec_content;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class VersionBean implements Serializable {
        /**
         * ios : {"id":4,"game_id":2,"resource_url":"","resource_size":"30.25","resource_type":2,"platform":1,"package_id":11,"package_version":"1.90.6","package_content":"全面开放新的玉林地图，玩家可以在地图上随机匹配","package_publish":1532529088,"downloads":10,"create_time":1532529088,"update_time":1532529088}
         * android : {"id":5,"game_id":2,"resource_url":"","resource_size":"33.67","resource_type":2,"platform":2,"package_id":null,"package_version":"1.90.6","package_content":"全面开放新的玉林地图，玩家可以在地图上随机匹配","package_publish":1532534400,"downloads":5,"create_time":1532529088,"update_time":1532529088}
         * pc : null
         */

        private AndroidBean ios;
        private AndroidBean android;
        private AndroidBean pc;

        public AndroidBean getIos() {
            return ios;
        }

        public void setIos(AndroidBean ios) {
            this.ios = ios;
        }

        public AndroidBean getAndroid() {
            return android;
        }

        public void setAndroid(AndroidBean android) {
            this.android = android;
        }

        public AndroidBean getPc() {
            return pc;
        }

        public void setPc(AndroidBean pc) {
            this.pc = pc;
        }


        public static class AndroidBean  implements Serializable  {
            /**
             * id : 5
             * game_id : 2
             * resource_url :
             * resource_size : 33.67
             * resource_type : 2
             * platform : 2
             * package_id : null
             * package_version : 1.90.6
             * package_content : 全面开放新的玉林地图，玩家可以在地图上随机匹配
             * package_publish : 1532534400
             * downloads : 5
             * create_time : 1532529088
             * update_time : 1532529088
             */

            private int id;
            private int game_id;
            private String resource_url;
            private String resource_size;
            private int resource_type;
            private int platform;
            private String package_id;
            private String package_version;
            private String package_content;
            private long package_publish;
            private int downloads;
            private long create_time;
            private long update_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getGame_id() {
                return game_id;
            }

            public void setGame_id(int game_id) {
                this.game_id = game_id;
            }

            public String getResource_url() {
                return resource_url;
            }

            public void setResource_url(String resource_url) {
                this.resource_url = resource_url;
            }

            public String getResource_size() {
                return resource_size;
            }

            public void setResource_size(String resource_size) {
                this.resource_size = resource_size;
            }

            public int getResource_type() {
                return resource_type;
            }

            public void setResource_type(int resource_type) {
                this.resource_type = resource_type;
            }

            public int getPlatform() {
                return platform;
            }

            public void setPlatform(int platform) {
                this.platform = platform;
            }

            public String getPackage_id() {
                return package_id;
            }

            public void setPackage_id(String package_id) {
                this.package_id = package_id;
            }

            public String getPackage_version() {
                return package_version;
            }

            public void setPackage_version(String package_version) {
                this.package_version = package_version;
            }

            public String getPackage_content() {
                return package_content;
            }

            public void setPackage_content(String package_content) {
                this.package_content = package_content;
            }

            public long getPackage_publish() {
                return package_publish;
            }

            public void setPackage_publish(long package_publish) {
                this.package_publish = package_publish;
            }

            public int getDownloads() {
                return downloads;
            }

            public void setDownloads(int downloads) {
                this.downloads = downloads;
            }

            public long getCreate_time() {
                return create_time;
            }

            public void setCreate_time(long create_time) {
                this.create_time = create_time;
            }

            public long getUpdate_time() {
                return update_time;
            }

            public void setUpdate_time(long update_time) {
                this.update_time = update_time;
            }
        }
    }

    public static class PicBean  implements Serializable  {
        /**
         * url : http://soft.abrain.cn/octopus/testdata/game_detail1.png
         * title :
         */

        private String url;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}


