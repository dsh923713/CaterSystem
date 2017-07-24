package com.catersystem.zmy.catersystem.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.adapter.BehindBillsAdapter;
import com.catersystem.zmy.catersystem.adapter.BehindCookAdapter;
import com.catersystem.zmy.catersystem.adapter.BillsLatelyCompleteAdapter;
import com.catersystem.zmy.catersystem.adapter.LatelyCompleteAdapter;
import com.catersystem.zmy.catersystem.base.BaseFragment;
import com.catersystem.zmy.catersystem.bean.BehindBillsBean;
import com.catersystem.zmy.catersystem.bean.BehindCookBean;
import com.catersystem.zmy.catersystem.bean.LatelyCompleteBean;
import com.catersystem.zmy.catersystem.utils.DateUtil;
import com.catersystem.zmy.catersystem.utils.DialogUtil;
import com.catersystem.zmy.catersystem.utils.ScreenUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class BehindCookFragment extends BaseFragment implements TextToSpeech.OnInitListener {
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
    @BindView(R.id.rv_dishes)
    RecyclerView rvDishes; //菜品显示recyclerview
    @BindView(R.id.tv_lately_complete)
    TextView tvLatelyComplete; //最近完成
    @BindView(R.id.tv_history_dishes)
    TextView tvHistoryDishes;//历史菜品
    @BindView(R.id.tv_set)
    TextView tvSet;//设置

    private BehindCookAdapter adapter; //后厨适配器
    private int id;
    private BehindBillsAdapter billsAdapter;//后厨单据适配器
    private View dialogView; //最近完成--弹窗布局
    private RecyclerView rvDialog;//最近完成列表
    private TextView tvDishesType;
    private ImageView ivCancel;//关闭弹窗
    private TextView tvCompleteTime;//完成时间
    private Dialog dialog;//弹窗
    private TextView tvReform; //重做
    private TextView tvNoComplete; //还未完成
    private TextView tvCallOut; //呼叫
    private TextToSpeech tts;

    private List<BehindCookBean> cookingBeenList;//模拟后厨数据
    private List<BehindBillsBean> billsBeenList; //模拟后厨单据数据
    private List<BehindBillsBean.DishesListBean> dishesList;//模拟后厨单据菜品数据
    private List<BehindBillsBean.DishesListBean> dishesList1;//模拟后厨单据菜品数据
    private List<LatelyCompleteBean> latelyCompleteList; //最近完成-菜品模拟数据
    private LatelyCompleteAdapter latelyCompleteAdapter;
    private int mPosition = -1;
    private BillsLatelyCompleteAdapter billsLatelyCompleteAdapter;

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
        tts = new TextToSpeech(activity, this);
        rbAll.setChecked(true);
        setLatelyCompleteDialog();

        if (id == 1) {
            getBehindCookData();
            rvDishes.setLayoutManager(new GridLayoutManager(activity, 5));
            adapter = new BehindCookAdapter(cookingBeenList);
            rvDishes.setAdapter(adapter);
            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    switch (((BehindCookBean) adapter.getData().get(position)).getCookingStatus()) {
                        case 0://未烹饪
                            ((BehindCookBean) adapter.getData().get(position)).setCookingStatus(1);
                            adapter.notifyDataSetChanged();
                            showShortToast("开始烹饪...");
                            break;
                        case 1://正在烹饪
                            cookingBeenList.remove(((BehindCookBean) adapter.getData().get(position)));
                            adapter.notifyItemRemoved(position);
                            showShortToast("已完成烹饪！");
                            break;
                    }
                }
            });
        } else {
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

    /**
     * 最近完成弹窗
     */
    private void setLatelyCompleteDialog() {
        dialogView = View.inflate(activity, R.layout.dialog_lately_complete, null);
        rvDialog = (RecyclerView) dialogView.findViewById(R.id.rv_dishes_dialog);
        tvDishesType = (TextView) dialogView.findViewById(R.id.tv_dishes_type);
        ivCancel = (ImageView) dialogView.findViewById(R.id.iv_cancel);
        tvCompleteTime = (TextView) dialogView.findViewById(R.id.tv_complete_time);
        tvReform = (TextView) dialogView.findViewById(R.id.tv_reform);
        tvNoComplete = (TextView) dialogView.findViewById(R.id.tv_no_complete);
        tvCallOut = (TextView) dialogView.findViewById(R.id.tv_call_out);
        //注册点击事件
        ivCancel.setOnClickListener(listener);
        tvReform.setOnClickListener(listener);
        tvNoComplete.setOnClickListener(listener);
        tvCallOut.setOnClickListener(listener);
        tvCallOut.setClickable(false);
        //设置布局样式
        if (id == 1) { //后厨
            rvDialog.setLayoutManager(new GridLayoutManager(activity, 5));
            getLatelyCompleteData();
            latelyCompleteAdapter = new LatelyCompleteAdapter(latelyCompleteList);
            rvDialog.setAdapter(latelyCompleteAdapter);
            latelyCompleteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mPosition = position;
                    latelyCompleteAdapter.setPosition(position);
                    latelyCompleteAdapter.notifyDataSetChanged();
                    tvCompleteTime.setVisibility(View.VISIBLE);
                    tvCompleteTime.setText("完成时间：" + DateUtil.toDateHourFormat(((LatelyCompleteBean) adapter.getData().get(position)).getCompleteTime()));
                    tvCallOut.setClickable(true);
                }
            });
        }else { //单据
            getBehindBillData();
            rvDialog.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL));
            billsLatelyCompleteAdapter = new BillsLatelyCompleteAdapter(billsBeenList);
            rvDialog.setAdapter(billsLatelyCompleteAdapter);
            billsLatelyCompleteAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    billsLatelyCompleteAdapter.setPosition(position);
                    billsLatelyCompleteAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 最近完成--控件点击事件
     */
    View.OnClickListener listener = new View.OnClickListener() {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_cancel:
                    if (dialog != null) { //取消弹窗
                        dialog.dismiss();
                        //取消选中状态
                        if (id == 1) {
                            latelyCompleteAdapter.setPosition(-1);
                            latelyCompleteAdapter.notifyDataSetChanged();
                        }
                        tvCompleteTime.setVisibility(View.GONE);//隐藏完成时间
                        tvCallOut.setClickable(false);
                    }
                    break;
                case R.id.tv_reform: //重做
                    break;
                case R.id.tv_no_complete: //还未完成
                    break;
                case R.id.tv_call_out: //呼叫
                    if (tts != null && !tts.isSpeaking()) {
                        tts.setPitch(1.0f);//设置音色
                        tts.speak(((LatelyCompleteBean) latelyCompleteAdapter.getData().get(mPosition)).getTableNum()
                                + "号请道后厨取餐！", TextToSpeech.QUEUE_ADD, null, "speech");
                        showShortToast(((LatelyCompleteBean) latelyCompleteAdapter.getData().get(mPosition)).getTableNum()
                                + "号请道后厨取餐！");
                    }
                    break;
            }
        }
    };

    //后厨菜品模拟数据
    private void getBehindCookData() {
        cookingBeenList = new ArrayList<>();
        cookingBeenList.add(new BehindCookBean(0, "堂食", "A2", "鱼头汤", 2, 626, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A1", "干锅田鸡", 3, 555, "加辣"));
        cookingBeenList.add(new BehindCookBean(0, "堂食", "A1", "排骨汤", 1, 210, ""));
        cookingBeenList.add(new BehindCookBean(1, "堂食", "A3", "莆田卤面", 2, 626, ""));
        cookingBeenList.add(new BehindCookBean(0, "外卖", "A4", "炒时蔬", 6, 100, ""));
        cookingBeenList.add(new BehindCookBean(1, "堂食", "A2", "烤猪蹄", 2, 965, ""));
        cookingBeenList.add(new BehindCookBean(1, "堂食", "A3", "烤鸡翅", 8, 1200, ""));
        cookingBeenList.add(new BehindCookBean(0, "外卖", "A3", "红烧肉", 2, 652, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A4", "黑椒牛肉", 2, 213, ""));
        cookingBeenList.add(new BehindCookBean(1, "外卖", "A5", "茄子煲", 4, 442, ""));
        cookingBeenList.add(new BehindCookBean(0, "堂食", "A5", "炒三鲜", 1, 123, ""));
    }

    //单据菜品 模拟数据
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
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList1));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
        billsBeenList.add(new BehindBillsBean("外卖", "A1", 1020, dishesList));
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

    //最近完成 模拟数据
    private void getLatelyCompleteData() {
        latelyCompleteList = new ArrayList<>();
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "", 3, System.currentTimeMillis()));
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "加辣", 3, System.currentTimeMillis() + 100000l));
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "", 3, System.currentTimeMillis() + 251522l));
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "", 3, System.currentTimeMillis()));
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "", 3, System.currentTimeMillis()));
        latelyCompleteList.add(new LatelyCompleteBean("A12", "红烧肉", "", 3, System.currentTimeMillis()));
    }

    @OnClick({R.id.tv_lately_complete, R.id.tv_history_dishes, R.id.tv_set})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_lately_complete: //最近完成
                if (dialog == null) {
                    dialog = DialogUtil.getDialog1(activity, dialogView, ScreenUtil.getScreenWidthPix(activity) * 0.95,
                            ScreenUtil.getScreenHeightPix(activity) * 0.9);
                } else {
                    dialog.show();
                }
                break;
            case R.id.tv_history_dishes: //历史菜品
                showShortToast("历史菜品");
                break;
            case R.id.tv_set: //设置
                showShortToast("设置");
                break;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int language = tts.setLanguage(Locale.CHINA);
            if (language == TextToSpeech.LANG_MISSING_DATA
                    || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                showShortToast("数据丢失或不支持");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
