package com.catersystem.zmy.catersystem.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.BehindCookBean;
import com.catersystem.zmy.catersystem.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class BehindCookAdapter extends BaseQuickAdapter<BehindCookBean, BaseViewHolder> {
    public BehindCookAdapter(@Nullable List<BehindCookBean> data) {
        super(R.layout.rv_item_behindcook, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BehindCookBean item) {
        holder.setVisible(R.id.tv_dishes_remark, !TextUtils.isEmpty(item.getDishesRemark()) ? true : false);
        holder.setText(R.id.tv_type, item.getType()).setText(R.id.tv_number, item.getNum())
                .setText(R.id.tv_dishes_name, item.getDishesName()).setText(R.id.tv_dishes_num, item.getDishesNum() + "道")
                .setText(R.id.tv_wait_time, "已等待"+DateUtil.getTimeDown(item.getWaitTime())+"分钟")
                .setText(R.id.tv_dishes_remark, "(" + item.getDishesRemark() + ")");
        switch (item.getCookingStatus()) {
            case 0:
                holder.setText(R.id.tv_cooking_status, R.string.no_cooking);
                holder.setBackgroundRes(R.id.ll_bg, R.drawable.blue_bg_5_shape);
                break;
            case 1:
                holder.setText(R.id.tv_cooking_status, R.string.cooking);
                holder.setBackgroundRes(R.id.ll_bg, R.drawable.orange_bg_5_shape);
                break;
            case 2:
                holder.setText(R.id.tv_cooking_status, R.string.already_cooking);
                holder.setBackgroundRes(R.id.ll_bg, R.drawable.green_bg_5_shape);
                break;
            case 3:
                holder.setText(R.id.tv_cooking_status, R.string.cannot_cooking);
                holder.setBackgroundRes(R.id.ll_bg, R.drawable.gray_bg_5_shape);
                break;
        }
    }
}
