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

    public void setCookingStatus(int cookingStatus) {
        this.cookingStatus = cookingStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public int getDishesNum() {
        return dishesNum;
    }

    public void setDishesNum(int dishesNum) {
        this.dishesNum = dishesNum;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public String getDishesRemark() {
        return dishesRemark;
    }

    public void setDishesRemark(String dishesRemark) {
        this.dishesRemark = dishesRemark;
    }
}

