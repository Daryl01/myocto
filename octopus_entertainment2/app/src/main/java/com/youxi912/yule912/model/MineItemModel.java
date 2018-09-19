package com.youxi912.yule912.model;

public class MineItemModel {
    private int resourceId;
    private String text;

    public MineItemModel(int resourceId, String text) {
        this.resourceId = resourceId;
        this.text = text;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getText() {
        return text;
    }
}
