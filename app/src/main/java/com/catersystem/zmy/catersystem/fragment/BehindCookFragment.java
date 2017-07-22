package com.catersystem.zmy.catersystem.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.adapter.BehindBillsAdapter;
import com.catersystem.zmy.catersystem.adapter.BehindCookAdapter;
import com.catersystem.zmy.catersystem.base.BaseFragment;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.catersystem.zmy.catersystem.bean.BehindCookBean;
import com.catersystem.zmy.catersystem.utils.DialogUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class BehindCookFragment extends BaseFragment {
    @BindView(R.id.rb_all)
    RadioButton rbAll;//全部
    @BindView(R.id.rb_eat_in)
    RadioButton rbEatIn;//堂食
    @BindView(R.id.rb_eat_out)
    RadioButton rbEatOut;//外卖
    @BindView(R.id.rg_style)
    RadioGroup rgStyle;//食用方式
    @BindView(R.id.tv_no_cooking)
    TextView tvNoCooking;//未烹饪
    @BindView(R.id.tv_cooking)
    TextView tvCooking;//正在烹饪
    @BindView(R.id.tv_already_cooking)
    TextView tvAlreadyCooking;//已经烹饪
    @BindView(R.id.tv_cannot_cooking)
    TextView tvCannotCooking;//无法烹饪
    @BindView(R.id.iv_voice)
    ImageView ivVoice;//声音
    @BindView(R.id.iv_set)
    ImageView ivSet;//设置
    @BindView(R.id.rv_dishes)
    RecyclerView rvDishes; //菜品显示recyclerview
    private List<BehindCookBean> cookingBeenList;//模拟后厨数据
    private List<BehindBillsBean> billsBeenList; //模拟后厨单据数据
    private List<BehindBillsBean.DishesListBean> dishesList;//模拟后厨单据菜品数据
    private List<BehindBillsBean.DishesListBean> dishesList1;//模拟后厨单据菜品数据
    private BehindCookAdapter adapter;
    private int id;
    private BehindBillsAdapter billsAdapter;
    private View dialogView;
    private Dialog dialog;
    private ImageView ivCancel;
    private TextView tvLeft;
    private TextView tvCenter;
    private TextView tvRight;
    private TextView tvCookingStatus;

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_behindcook, container, false);
        return view;
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
        id = bundle.getInt("id");
    }

    @Override
    protected void initView(View view) {
        rbAll.setChecked(true);
        if (id == 1) {
            ivVoice.setVisibility(View.VISIBLE);
            getBehindCookData();
            rvDishes.setLayoutManager(new GridLayoutManager(activity, 5));
            adapter = new BehindCookAdapter(cookingBeenList);
            rvDishes.setAdapter(adapter);
            //弹窗控件实例化
            dialogView = View.inflate(activity, R.layout.dialog_behindcook_rv, null);
            ivCancel = (ImageView) dialogView.findViewById(R.id.iv_cancel);
            tvLeft = (TextView) dialogView.findViewById(R.id.tv_left);
            tvCenter = (TextView) dialogView.findViewById(R.id.tv_center);
            tvRight = (TextView) dialogView.findViewById(R.id.tv_right);
            tvCookingStatus = (TextView) dialogView.findViewById(R.id.tv_cooking_status);
            //注册点击事件
            ivCancel.setOnClickListener(listener);
            tvLeft.setOnClickListener(listener);
            tvCenter.setOnClickListener(listener);
            tvRight.setOnClickListener(listener);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (dialog == null) {
                        dialog = DialogUtil.getDialog(activity, dialogView);
                    } else {
                        dialog.show();
                    }
                    switch (((BehindCookBean) adapter.getData().get(position)).getCookingStatus()) {
                        case 0://未烹饪
                            tvCookingStatus.setText("烹饪设置"+"（"+getString(R.string.no_cooking)+"）");
                            tvLeft.setVisibility(View.VISIBLE);
                            tvCenter.setVisibility(View.VISIBLE);
                            tvCenter.setText(getString(R.string.unable_to_make));
                            tvRight.setVisibility(View.VISIBLE);
                            break;
                        case 1://正在烹饪
                            tvCookingStatus.setText("烹饪设置"+"（"+getString(R.string.cooking)+"）");
                            tvLeft.setVisibility(View.GONE);
                            tvCenter.setVisibility(View.VISIBLE);
                            tvCenter.setText(getString(R.string.complete));
                            tvRight.setVisibility(View.VISIBLE);
                            break;
                        case 2://已烹饪
                            tvCookingStatus.setText("烹饪设置"+"（"+getString(R.string.already_cooking)+"）");
                            tvLeft.setVisibility(View.GONE);
                            tvCenter.setVisibility(View.VISIBLE);
                            tvCenter.setText(getString(R.string.call_out));
                            tvRight.setVisibility(View.GONE);
                            break;
                        default://无法烹饪
                            tvCookingStatus.setText("烹饪设置"+"（"+getString(R.string.cannot_cooking)+"）");
                            tvLeft.setVisibility(View.GONE);
                            tvCenter.setVisibility(View.VISIBLE);
                            tvCenter.setText(getString(R.string.cancel));
                            tvRight.setVisibility(View.GONE);
                            break;
                    }
                }
            });
        } else {
            ivVoice.setVisibility(View.GONE);
            getBehindBillData();
            rvDishes.setLayoutManager(new GridLayoutManager(activity, 4));
            billsAdapter = new BehindBillsAdapter(billsBeenList);
            rvDishes.setAdapter(billsAdapter);
            billsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (view.getId()) {
                        case R.id.tv_call_out:
                            showLongToast("呼叫" + position);
                            break;
                        case R.id.tv_complete:
                            billsBeenList.remove(position);
                            billsAdapter.notifyItemRemoved(position);
                            showLongToast("完成" + position);
                            break;
                    }
                }
            });

        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_cancel:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
                case R.id.tv_left:
                    showLongToast("烹饪");
                    break;
                case R.id.tv_center:
                    showLongToast(tvCenter.getText().toString());
                    break;
                case R.id.tv_right:
                    showLongToast("打印");
                    break;
            }
        }
    };

    private void getBehindCookData() {
        cookingBeenList = new ArrayList<>();
        cookingBeenList.add(new BehindCookBean(0, "堂食", "A2", "鱼头汤", 2, 626, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A1", "干锅田鸡", 3, 555, "加辣"));
        cookingBeenList.add(new BehindCookBean(2, "堂食", "A1", "排骨汤", 1, 210, ""));
        cookingBeenList.add(new BehindCookBean(1, "堂食", "A3", "莆田卤面", 2, 626, ""));
        cookingBeenList.add(new BehindCookBean(2, "外卖", "A4", "炒时蔬", 6, 100, ""));
        cookingBeenList.add(new BehindCookBean(1, "堂食", "A2", "烤猪蹄", 2, 965, ""));
        cookingBeenList.add(new BehindCookBean(3, "堂食", "A3", "烤鸡翅", 8, 1200, ""));
        cookingBeenList.add(new BehindCookBean(2, "外卖", "A3", "红烧肉", 2, 652, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A4", "黑椒牛肉", 2, 213, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A5", "茄子煲", 4, 442, ""));
        cookingBeenList.add(new BehindCookBean(3, "堂食", "A5", "炒三鲜", 1, 123, ""));
    }

    private void getBehindBillData() {
        billsBeenList = new ArrayList<>();
        dishesList = new ArrayList<>();
        dishesList1 = new ArrayList<>();
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("堂食", "A1", 1020, dishesList1));
        billsBeenList.add(new BehindBillsBean("堂食", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList1));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList1));
        dishesList.add(new BehindBillsBean.DishesListBean("红烧茄子", 1, "加辣"));
        dishesList.add(new BehindBillsBean.DishesListBean("回锅肉", 1, ""));
        dishesList.add(new BehindBillsBean.DishesListBean("排骨汤", 1, ""));
        dishesList.add(new BehindBillsBean.DishesListBean("荔枝肉", 1, ""));
        dishesList.add(new BehindBillsBean.DishesListBean("炒荷兰豆", 1, ""));
        dishesList.add(new BehindBillsBean.DishesListBean("宫保鸡丁", 1, ""));
        dishesList.add(new BehindBillsBean.DishesListBean("鱼头汤", 1, ""));
        dishesList1.add(new BehindBillsBean.DishesListBean("炒时蔬", 1, ""));
        dishesList1.add(new BehindBillsBean.DishesListBean("花蛤蛋汤", 1, ""));
        dishesList1.add(new BehindBillsBean.DishesListBean("干锅田鸡", 1, ""));
        dishesList1.add(new BehindBillsBean.DishesListBean("清蒸鲤鱼", 1, ""));
    }

}
