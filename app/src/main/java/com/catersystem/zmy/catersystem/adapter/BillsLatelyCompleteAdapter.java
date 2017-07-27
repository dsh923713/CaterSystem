package com.catersystem.zmy.catersystem.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.catersystem.zmy.catersystem.view.NewRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;
import java.util.zip.Inflater;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class BillsLatelyCompleteAdapter extends BaseQuickAdapter<BehindBillsBean, BaseViewHolder> {
    private int mPosition = -1;

    public BillsLatelyCompleteAdapter(IGetPositionListener listener, @Nullable List<BehindBillsBean> data) {
        super(R.layout.rv_item_dialog_lately_complete_bills, data);
        getListener(listener);
    }

    @SuppressLint("NewApi")
    @Override
    protected void convert(final BaseViewHolder holder, final BehindBillsBean item) {
        holder.setText(R.id.cb_table_num,item.getTableNum());
        holder.setChecked(R.id.cb_table_num, mPosition == holder.getLayoutPosition() ? true : false);

        final NewRecyclerView lvDishesLatelyComplete = holder.getView(R.id.lv_dishes_lately_complete);
        //懒汉模式--listview适配器
        CommonAdapter<BehindBillsBean.DishesListBean> commonAdapter = new CommonAdapter<BehindBillsBean.DishesListBean>
                (mContext, R.layout.rv_child_item_behindbills, item.getDishesList()) {
            @Override
            protected void convert(ViewHolder holder, BehindBillsBean.DishesListBean item, int position) {
                holder.setVisible(R.id.tv_dishes_remark, !TextUtils.isEmpty(item.getDishesRemark()) ? true : false);
                holder.setText(R.id.tv_dishes_name, item.getDishesName()).setText(R.id.tv_dishes_num, item.getDishesNum() + "")
                        .setText(R.id.tv_dishes_remark, item.getDishesRemark());
            }
        };
        lvDishesLatelyComplete.setAdapter(commonAdapter);
        //监听listview的滑动 记录滑动距离
        lvDishesLatelyComplete.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    // scrollPos记录当前可见的Listview顶端的一行的位置
                   int scrollPos = lvDishesLatelyComplete.getFirstVisiblePosition();
                    item.setItemPosition(scrollPos);
                }
                if (item.getDishesList() != null) {
                    //记录item的顶部到父控件的距离
                    View v = lvDishesLatelyComplete.getChildAt(0);
                    int scrollTop = (v == null) ? 0 : v.getTop();
                    item.setGoScrollY(scrollTop);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        //listview恢复到之前的状态
        lvDishesLatelyComplete.setSelectionFromTop(item.getItemPosition(),item.getGoScrollY());
        //点击listview的item来
        lvDishesLatelyComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = holder.getLayoutPosition();
                notifyDataSetChanged();
            }
        });
        //回调获取mPosition
        if (listener != null && mPosition != -1) {
            listener.getPosition(mPosition);
        }
    }

    public void setPosition(int position) {
        mPosition = position;
    }


    /**
     * 通过接口回调获取点击id
     */
    IGetPositionListener listener = null;

    private void getListener(IGetPositionListener listener) {
        this.listener = listener;
    }

    public interface IGetPositionListener {
        void getPosition(int position);
    }
}
