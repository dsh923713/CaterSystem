package com.catersystem.zmy.catersystem.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.base.BaseActivity;
import com.catersystem.zmy.catersystem.fragment.BehindCookFragment;
import com.catersystem.zmy.catersystem.utils.DateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.viewFlipper1)
    ViewFlipper viewFlipper1;
    @BindView(R.id.tv_now_time)
    TextView tvNowTime;
    private Bundle bundle;
    private BehindCookFragment behindCookFragment;

    public HomeActivity() {
        super(R.layout.activity_home);
    }

    protected void initView() {

        bundle = new Bundle();
        initViewFlipper();
//        replaceFragment(R.id.fl_content, new BehindCookFragment());
        //设置时间
        StringBuilder sb = new StringBuilder(DateUtil.toTime11(System.currentTimeMillis()));
        sb.insert(11, "　" + DateUtil.getWeek(System.currentTimeMillis()) + "　");
        tvNowTime.setText(sb);
    }

    private void initViewFlipper() {
        for (int i = 0; i < 5; i++) {
            TextView tv = new TextView(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER_VERTICAL);
            tv.setTextSize(13f);
            tv.setTextColor(ContextCompat.getColor(this, R.color.white));
            Drawable d = ContextCompat.getDrawable(this, R.mipmap.ic_news_voice);
            d.setBounds(0, 0, 40, 40);
            tv.setCompoundDrawables(d, null, null, null);
            tv.setCompoundDrawablePadding(20);
            tv.setText(i + "你有新的外卖订单，请注意查收！");
            viewFlipper1.addView(tv);
        }
        viewFlipper1.startFlipping();
    }

    @OnClick({R.id.tv1, R.id.tv2})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                behindCookFragment = new BehindCookFragment();
                bundle.putInt("id",1);
                behindCookFragment.setArguments(bundle);
                replaceFragment(R.id.fl_content, behindCookFragment);
                break;
            case R.id.tv2:
                behindCookFragment = new BehindCookFragment();
                bundle.putInt("id",2);
                behindCookFragment.setArguments(bundle);
                replaceFragment(R.id.fl_content, behindCookFragment);
                break;
        }
    }
}
