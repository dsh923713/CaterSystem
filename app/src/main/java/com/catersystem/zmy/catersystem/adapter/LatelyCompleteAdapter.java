package com.catersystem.zmy.catersystem.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.LatelyCompleteBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class LatelyCompleteAdapter extends BaseQuickAdapter<LatelyCompleteBean, BaseViewHolder> {
    private int mPosition = -1;

    public LatelyCompleteAdapter(@Nullable List<LatelyCompleteBean> data) {
        super(R.layout.rv_item_dialog_lately_complete, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, LatelyCompleteBean item) {
        holder.setVisible(R.id.tv_dishes_remark, !TextUtils.isEmpty(item.getDishesRemark()) ? true : false);
        holder.setText(R.id.cb_table_num, item.getTableNum()).setText(R.id.tv_dishes_name, item.getDishesName())
                .setText(R.id.tv_dishes_remark, "(" + item.getDishesRemark() + ")").setText(R.id.tv_dishes_num, item.getDishesNum() + "道");
        //判断是否选中
        holder.setChecked(R.id.cb_table_num, mPosition == holder.getAdapterPosition() ? true : false);
    }

    /**
     * 获取点击的position
     * @param position
     */
    public void setPosition(int position) {
        mPosition = position;
    }
}
