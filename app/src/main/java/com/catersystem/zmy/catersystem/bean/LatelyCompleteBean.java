package com.catersystem.zmy.catersystem.bean;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class LatelyCompleteBean {
    private String tableNum;
    private String dishesName;
    private String dishesRemark;
    private int dishesNum;
    private long completeTime;

    public LatelyCompleteBean(String tableNum, String dishesName, String dishesRemark, int dishesNum, long completeTime) {
        this.tableNum = tableNum;
        this.dishesName = dishesName;
        this.dishesRemark = dishesRemark;
        this.dishesNum = dishesNum;
        this.completeTime = completeTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    public String getTableNum() {
        return tableNum;
    }

    public void setTableNum(String tableNum) {
        this.tableNum = tableNum;
    }

    public String getDishesName() {
        return dishesName;
    }

    public void setDishesName(String dishesName) {
        this.dishesName = dishesName;
    }

    public String getDishesRemark() {
        return dishesRemark;
    }

    public void setDishesRemark(String dishesRemark) {
        this.dishesRemark = dishesRemark;
    }

    public int getDishesNum() {
        return dishesNum;
    }

    public void setDishesNum(int dishesNum) {
        this.dishesNum = dishesNum;
    }
}
