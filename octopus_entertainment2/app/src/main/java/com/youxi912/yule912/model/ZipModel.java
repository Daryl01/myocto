package com.youxi912.yule912.model;

public class ZipModel {
    private IndexGameModel recently;
    private IndexGameModel recommend;
    private IndexGameModel nonFree;

    public ZipModel(IndexGameModel recently, IndexGameModel recommend, IndexGameModel nonFree) {
        this.recently = recently;
        this.recommend = recommend;
        this.nonFree = nonFree;
    }

    public IndexGameModel getRecently() {
        return recently;
    }

    public IndexGameModel getRecommend() {
        return recommend;
    }

    public IndexGameModel getNonFree() {
        return nonFree;
    }
}
