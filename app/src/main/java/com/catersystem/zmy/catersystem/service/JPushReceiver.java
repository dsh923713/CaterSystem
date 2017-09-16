package com.catersystem.zmy.catersystem.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


import com.catersystem.zmy.catersystem.utils.ToastUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义的JPush广播接收器
 * Created by Administrator on 2017/5/23.
 */

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "DSH -> JPushReceiver";
    private String message;
    private Bundle bundle;

    @Override
    public void onReceive(Context context, Intent intent) {
        bundle = intent.getExtras();

        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) { //接收自定义消息
            message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        }
        if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) { //接收普通消息
            message = (String) bundle.get(JPushInterface.EXTRA_ALERT);
        }
        Log.d(TAG, "onReceive: "+message);
        if (!TextUtils.isEmpty(message)){
            ToastUtils.showLongToast(context, message);
            Intent receiver = new Intent("com.zmq.lottery.JPUSH_RECEIVER");
            receiver.putExtra("msg",message);
            LocalBroadManager.getInstance().sendBroadcast(receiver);//发送本地广播
        }
    }
}
