package com.youxi912.yule912.model;

public class EveryDayModel extends BaseModel {
    private int resource;
    private String name;
    private String price;
    private String num;
    private boolean increase;

    public EveryDayModel(int resource, String name, String price, String num, boolean increase) {
        this.resource = resource;
        this.name = name;
        this.price = price;
        this.num = num;
        this.increase = increase;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }
}
