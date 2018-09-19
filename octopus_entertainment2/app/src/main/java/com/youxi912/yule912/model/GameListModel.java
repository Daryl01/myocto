package com.youxi912.yule912.model;

public class GameListModel {
    private String name;
    private String description;
    private int number;
    private String size;
    private String imgPath;

    public GameListModel(String name, String description, int number, String size, String imgPath) {
        this.name = name;
        this.description = description;
        this.number = number;
        this.size = size;
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
