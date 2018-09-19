package com.youxi912.yule912.model;

public class Zip2Model {
    private IndexGameModel recommends;
    private IndexGameModel pay;

    public Zip2Model(IndexGameModel recommends, IndexGameModel pay) {
        this.recommends = recommends;
        this.pay = pay;
    }

    public IndexGameModel getRecommends() {
        return recommends;
    }

    public void setRecommends(IndexGameModel recommends) {
        this.recommends = recommends;
    }

    public IndexGameModel getPay() {
        return pay;
    }

    public void setPay(IndexGameModel pay) {
        this.pay = pay;
    }
}
