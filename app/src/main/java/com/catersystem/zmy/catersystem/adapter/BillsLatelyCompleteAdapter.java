package com.catersystem.zmy.catersystem.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class BillsLatelyCompleteAdapter extends BaseQuickAdapter<BehindBillsBean, BaseViewHolder> {
    private int mPosition = -1;

    public BillsLatelyCompleteAdapter(@Nullable List<BehindBillsBean> data) {
        super(R.layout.rv_item_dialog_lately_complete_bills, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BehindBillsBean item) {
        RecyclerView rvDishesLatelyComplete = holder.getView(R.id.rv_dishes_lately_complete);
        rvDishesLatelyComplete.setLayoutManager(new LinearLayoutManager(mContext));
        DishesAdapter completeAdapter = new DishesAdapter(item.getDishesList());
        rvDishesLatelyComplete.setAdapter(completeAdapter);
        holder.setChecked(R.id.cb_table_num, mPosition == holder.getAdapterPosition() ? true : false);
    }

    /**
     * 烹饪菜品列表
     */
    public class DishesAdapter extends BaseQuickAdapter<BehindBillsBean.DishesListBean, BaseViewHolder> {

        public DishesAdapter(@Nullable List<BehindBillsBean.DishesListBean> data) {
            super(R.layout.rv_child_item_behindbills, data);
        }

        @Override
        protected void convert(BaseViewHolder holder, BehindBillsBean.DishesListBean item) {
            holder.setVisible(R.id.tv_dishes_remark, !TextUtils.isEmpty(item.getDishesRemark()) ? true : false);
            holder.setText(R.id.tv_dishes_name, item.getDishesName()).setText(R.id.tv_dishes_num, item.getDishesNum() + "")
                    .setText(R.id.tv_dishes_remark, item.getDishesRemark());
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }
}
