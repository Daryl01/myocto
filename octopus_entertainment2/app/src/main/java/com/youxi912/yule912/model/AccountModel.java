package com.youxi912.yule912.model;

import java.util.List;

public class AccountModel extends BaseModel {

    /**
     * data : {"vip_level":"章鱼捕手","receipts_month":"0","receipts_today":"0","receipts_whole":"119675","amount":"57589","history":[{"date":"2018/7/27 23:30:36","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:21","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:16","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:12","detail":"BTC兑换20359.000000章鱼丸"},{"date":"2018/7/27 23:28:20","detail":"打中小丑鱼获得0.000017BTC"},{"date":"2018/7/27 23:28:19","detail":"打中章鱼获得0.000030BTC"},{"date":"2018/7/27 23:28:18","detail":"打中海龟获得0.000020BTC"},{"date":"2018/7/27 23:28:13","detail":"打中小丑鱼获得0.000017BTC"},{"date":"2018/7/27 23:28:12","detail":"打中飞鱼获得0.000040BTC"}],"wallet":[{"id":0,"currency":"PS","currency_name":"章鱼币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ps.png","rate":"1","amount":"57589"},{"id":1,"currency":"BTC","currency_name":"比特币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_btc.png","rate":"1366575","amount":"0.000001"},{"id":2,"currency":"ETH","currency_name":"ETH","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_eth.png","rate":"78454","amount":"0.000000"},{"id":3,"currency":"XRP","currency_name":"瑞波币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_xrp.png","rate":"88","amount":"0.000000"},{"id":4,"currency":"BCH","currency_name":"比特币现金","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_bch.png","rate":"153636","amount":"0.000000"},{"id":5,"currency":"EOS","currency_name":"EOS","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_eos.png","rate":"1470","amount":"0.000000"},{"id":6,"currency":"LTC","currency_name":"莱特币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ltc.png","rate":"16000","amount":"0.000000"},{"id":7,"currency":"ADA","currency_name":"ADA","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ada.png","rate":"28","amount":"0.000000"},{"id":8,"currency":"XLM","currency_name":"恒星币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_xlm.png","rate":"55","amount":"0.000000"},{"id":9,"currency":"MIOTA","currency_name":"IOTA","currency_img":"","rate":"189","amount":"0.000000"},{"id":10,"currency":"TRX","currency_name":"波场","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_trx.png","rate":"6","amount":"0.000000"}]}
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
         * vip_level : 章鱼捕手
         * receipts_month : 0
         * receipts_today : 0
         * receipts_whole : 119675
         * amount : 57589
         * history : [{"date":"2018/7/27 23:30:36","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:21","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:16","detail":"BTC兑换0.000000章鱼丸"},{"date":"2018/7/27 23:30:12","detail":"BTC兑换20359.000000章鱼丸"},{"date":"2018/7/27 23:28:20","detail":"打中小丑鱼获得0.000017BTC"},{"date":"2018/7/27 23:28:19","detail":"打中章鱼获得0.000030BTC"},{"date":"2018/7/27 23:28:18","detail":"打中海龟获得0.000020BTC"},{"date":"2018/7/27 23:28:13","detail":"打中小丑鱼获得0.000017BTC"},{"date":"2018/7/27 23:28:12","detail":"打中飞鱼获得0.000040BTC"}]
         * wallet : [{"id":0,"currency":"PS","currency_name":"章鱼币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ps.png","rate":"1","amount":"57589"},{"id":1,"currency":"BTC","currency_name":"比特币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_btc.png","rate":"1366575","amount":"0.000001"},{"id":2,"currency":"ETH","currency_name":"ETH","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_eth.png","rate":"78454","amount":"0.000000"},{"id":3,"currency":"XRP","currency_name":"瑞波币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_xrp.png","rate":"88","amount":"0.000000"},{"id":4,"currency":"BCH","currency_name":"比特币现金","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_bch.png","rate":"153636","amount":"0.000000"},{"id":5,"currency":"EOS","currency_name":"EOS","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_eos.png","rate":"1470","amount":"0.000000"},{"id":6,"currency":"LTC","currency_name":"莱特币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ltc.png","rate":"16000","amount":"0.000000"},{"id":7,"currency":"ADA","currency_name":"ADA","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_ada.png","rate":"28","amount":"0.000000"},{"id":8,"currency":"XLM","currency_name":"恒星币","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_xlm.png","rate":"55","amount":"0.000000"},{"id":9,"currency":"MIOTA","currency_name":"IOTA","currency_img":"","rate":"189","amount":"0.000000"},{"id":10,"currency":"TRX","currency_name":"波场","currency_img":"http://octopus.anybind.com/uploads/testdata/icon_currency_trx.png","rate":"6","amount":"0.000000"}]
         */

        private String vip_level;
        private String receipts_month;
        private String receipts_today;
        private String receipts_whole;
        private String amount;
        private List<HistoryBean> history;
        private List<WalletBean> wallet;

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

        public List<HistoryBean> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryBean> history) {
            this.history = history;
        }

        public List<WalletBean> getWallet() {
            return wallet;
        }

        public void setWallet(List<WalletBean> wallet) {
            this.wallet = wallet;
        }

        public static class HistoryBean {
            /**
             * date : 2018/7/27 23:30:36
             * detail : BTC兑换0.000000章鱼丸
             */

            private String date;
            private String detail;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }

        public static class WalletBean {
            /**
             * id : 0
             * currency : PS
             * currency_name : 章鱼币
             * currency_img : http://octopus.anybind.com/uploads/testdata/icon_currency_ps.png
             * rate : 1
             * amount : 57589
             */

            private int id;
            private String currency;
            private String currency_name;
            private String currency_img;
            private String rate;
            private String amount;
            private int ps_amount;

            public int getPs_amount() {
                return ps_amount;
            }

            public void setPs_amount(int ps_amount) {
                this.ps_amount = ps_amount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

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

            public String getRate() {
                return rate;
            }

            public void setRate(String rate) {
                this.rate = rate;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }
    }
}
