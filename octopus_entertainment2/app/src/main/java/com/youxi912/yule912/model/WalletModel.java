package com.youxi912.yule912.model;

public class WalletModel {
    private String name;
    private String money;
    private String num;
    private int rescourceId;

    public WalletModel(String name, String money, String num, int rescourceId) {
        this.name = name;
        this.money = money;
        this.num = num;
        this.rescourceId = rescourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getRescourceId() {
        return rescourceId;
    }

    public void setRescourceId(int rescourceId) {
        this.rescourceId = rescourceId;
    }
}
