package com.catersystem.zmy.catersystem.bean;

import java.util.List;

/**
 * 后厨菜品烹饪状态显示数量
 */

public class HomeBean {
    private int noCookingNum;
    private int cookingNum;
    private int alreadyCookingNum;
    private int cannotCookingNum;

    public HomeBean(int noCookingNum, int cookingNum, int alreadyCookingNum, int cannotCookingNum) {
        this.noCookingNum = noCookingNum;
        this.cookingNum = cookingNum;
        this.alreadyCookingNum = alreadyCookingNum;
        this.cannotCookingNum = cannotCookingNum;
    }

    public int getNoCookingNum() {
        return noCookingNum;
    }

    public int getCookingNum() {
        return cookingNum;
    }

    public int getAlreadyCookingNum() {
        return alreadyCookingNum;
    }

    public int getCannotCookingNum() {
        return cannotCookingNum;
    }



}
