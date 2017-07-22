package com.catersystem.zmy.catersystem.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.catersystem.zmy.catersystem.R;
import com.catersystem.zmy.catersystem.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/5/15 0015.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity activity;
    public View view;
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (null != getArguments()) {
            getBundleExtras(getArguments());
        }

        if (null != view) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (null != parent) {
                parent.removeView(view);
            }
        } else {
            view = initContentView(inflater, container, savedInstanceState);
        }
        unbinder = ButterKnife.bind(this, view); //绑定注解
        initView(view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;//获取当前上下文联系

    }

    // 初始化UI setContentView
    protected abstract View initContentView(LayoutInflater inflater, @Nullable ViewGroup container,
                                            @Nullable Bundle savedInstanceState);

    // 初始化控件
    protected abstract void initView(View view);

    /**
     * 获取bundle信息
     *
     * @param bundle
     */
    protected void getBundleExtras(Bundle bundle) {
    }

    ;


    /**
     * Toast显示
     *
     * @param string
     */
    protected void showShortToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showShortToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    protected void showLongToast(String string) {
        ToastUtils.showShortToast(getActivity(), string);
    }

    protected void showLongToast(int stringId) {
        ToastUtils.showShortToast(getActivity(), stringId);
    }

    /**
     * 界面跳转
     *
     * @param cls 目标Activity
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 跳转界面，传参
     *
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param cls 目标Activity
     */
    protected void startActivityAndFinish(Class<?> cls) {
        startActivityAndFinish(cls, null);
    }

    /**
     * @param cls    目标Activity
     * @param bundle 数据
     */
    protected void startActivityAndFinish(Class<?> cls, Bundle bundle) {
        startActivity(cls, bundle);
        getActivity().finish();
    }

    /**
     * startActivityForResult
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     */
    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param cls         目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void startActivityForResult(Class<?> cls, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 切换fragment页面
     *
     * @param fragment
     */
    public void replaceFragment(int resId, Fragment fragment) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//        beginTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        beginTransaction.replace(resId, fragment);
//        beginTransaction.addToBackStack(null);
        beginTransaction.commitAllowingStateLoss();
    }

    /**
     * 切换fragment页面
     *
     * @param fragment
     */
    public void replaceFragment(int resId, Fragment fragment, boolean isAnimation, boolean isBackStack) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (isAnimation) {
            beginTransaction.setCustomAnimations(R.anim.right_in, R.anim.left_out, R.anim.left_in, R.anim.right_out);
        }
        beginTransaction.replace(resId, fragment);
        if (isBackStack) {
            beginTransaction.addToBackStack(null);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) { //解绑注解
            unbinder.unbind();
            unbinder = null;
        }
    }
}
