package com.youxi912.yule912.model;

//二维码
public class QRImgModel {

    /**
     * code : 200
     * msg : request success .
     * data : data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAu4AAAU2CAIAAABFtaRRAAAABnRSTlMA/wD/AP83WBt9AAAgAElEQVR4nOy9eZhcR3kv/Du99...
     */

    private int code;
    private String msg;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        public String image;
    }
}
