package com.catersystem.zmy.catersystem.bean;

import java.util.List;

/**
 * 单据
 * Created by Administrator on 2017/7/20 0020.
 */

public class BehindBillsBean {
    private String type; //堂食或外卖
    private String num; //A1-An
    private long waitTime;//等待时间
    private List<DishesListBean> dishesList;//菜品列表

    public BehindBillsBean(String type, String num, long waitTime, List<DishesListBean> dishesList) {
        this.type = type;
        this.num = num;
        this.waitTime = waitTime;
        this.dishesList = dishesList;
    }

    public String getType() {
        return type;
    }

    public String getNum() {
        return num;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public List<DishesListBean> getDishesList() {
        return dishesList;
    }

    public static class DishesListBean{
        private String dishesName;
        private int dishesNum;
        private String dishesRemark;

        public DishesListBean(String dishesName, int dishesNum, String dishesRemark) {
            this.dishesName = dishesName;
            this.dishesNum = dishesNum;
            this.dishesRemark = dishesRemark;
        }

        public String getDishesName() {
            return dishesName;
        }

        public int getDishesNum() {
            return dishesNum;
        }

        public String getDishesRemark() {
            return dishesRemark;
        }
    }
}
