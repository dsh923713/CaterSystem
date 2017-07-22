package com.catersystem.zmy.catersystem.bean;

/**
 * 后厨制作过程菜品信息
 */


public class BehindCookBean {
    private int cookingStatus;
    private String type;
    private String num;
    private String dishesName;
    private int dishesNum;
    private long waitTime;
    private String dishesRemark;

    public BehindCookBean(int cookingStatus, String type, String num, String dishesName, int dishesNum, long waitTime,
                          String dishesRemark) {
        this.cookingStatus = cookingStatus;
        this.type = type;
        this.num = num;
        this.dishesName = dishesName;
        this.dishesNum = dishesNum;
        this.waitTime = waitTime;
        this.dishesRemark = dishesRemark;
    }

    public int getCookingStatus() {
        return cookingStatus;
    }

    public String getType() {
        return type;
    }

    public String getNum() {
        return num;
    }

    public String getDishesName() {
        return dishesName;
    }

    public int getDishesNum() {
        return dishesNum;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public String getDishesRemark() {
        return dishesRemark;
    }
}

