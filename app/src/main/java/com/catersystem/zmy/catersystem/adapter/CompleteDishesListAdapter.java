package com.catersystem.zmy.catersystem.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 已完成或（单据）未烹饪菜品列表
 */

public class CompleteDishesListAdapter extends BaseQuickAdapter<BehindBillsBean.DishesListBean, BaseViewHolder> {

    public CompleteDishesListAdapter(@Nullable List<BehindBillsBean.DishesListBean> data) {
        super(R.layout.rv_child_item_behindbills, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BehindBillsBean.DishesListBean item) {
        holder.setVisible(R.id.tv_dishes_remark, !TextUtils.isEmpty(item.getDishesRemark()) ? true : false);
        holder.setText(R.id.tv_dishes_name, item.getDishesName()).setText(R.id.tv_dishes_num, item.getDishesNum() + "")
                .setText(R.id.tv_dishes_remark, item.getDishesRemark());
    }
}
