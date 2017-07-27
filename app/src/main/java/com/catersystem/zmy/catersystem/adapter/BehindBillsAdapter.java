package com.catersystem.zmy.catersystem.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.catersystem.zmy.catersystem.utils.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import static android.R.string.no;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class BehindBillsAdapter extends BaseQuickAdapter<BehindBillsBean, BaseViewHolder> {

    public BehindBillsAdapter(@Nullable List<BehindBillsBean> data) {
        super(R.layout.rv_item_behindbills, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final BehindBillsBean item) {
        holder.setText(R.id.tv_type, item.getType()).setText(R.id.tv_table_num, item.getTableNum())
                .setText(R.id.tv_complete_time, DateUtil.getTimeDown(item.getWaitTime()) + "分钟");

        final List<BehindBillsBean.DishesListBean> mCompleteDishes = new ArrayList<>();
        final List<BehindBillsBean.DishesListBean> notCompleteDishes = new ArrayList<>();
        notCompleteDishes.addAll(item.getDishesList());
        RecyclerView rvDishes = holder.getView(R.id.rv_dishes);
        rvDishes.setLayoutManager(new LinearLayoutManager(mContext));
        final CompleteDishesListAdapter dishesAdapter = new CompleteDishesListAdapter(notCompleteDishes);
        rvDishes.setAdapter(dishesAdapter);

        RecyclerView rvDishesComplete = holder.getView(R.id.rv_dishes_complete);
        rvDishesComplete.setLayoutManager(new LinearLayoutManager(mContext));
        final CompleteDishesListAdapter completeAdapter = new CompleteDishesListAdapter(mCompleteDishes);
        rvDishesComplete.setAdapter(completeAdapter);
        dishesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mCompleteDishes.add(notCompleteDishes.get(position));
                notCompleteDishes.remove(notCompleteDishes.get(position));
                dishesAdapter.notifyDataSetChanged();
                completeAdapter.notifyItemInserted(mCompleteDishes.size() - 1);
                holder.setVisible(R.id.v_line, notCompleteDishes.size() == 0 ? false : true);//完成则隐藏分割线
                holder.setVisible(R.id.iv_complete_tag, mCompleteDishes.size() > 0 ? true : false);//显示完成标签
            }
        });
        completeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                notCompleteDishes.add(mCompleteDishes.get(position));
                mCompleteDishes.remove(mCompleteDishes.get(position));
                completeAdapter.notifyDataSetChanged();
                dishesAdapter.notifyItemInserted(notCompleteDishes.size()-1);
                holder.setVisible(R.id.v_line, notCompleteDishes.size() == 0 ? false : true);//完成则隐藏分割线
                holder.setVisible(R.id.iv_complete_tag, mCompleteDishes.size() > 0 ? true : false);//显示完成标签
            }
        });
        //添加子item点击事件
        holder.addOnClickListener(R.id.tv_call_out);
        holder.addOnClickListener(R.id.tv_complete);
    }
}
