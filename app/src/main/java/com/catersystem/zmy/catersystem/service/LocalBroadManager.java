package com.catersystem.zmy.catersystem.service;

import android.support.v4.content.LocalBroadcastManager;

import com.catersystem.zmy.catersystem.MyApplication;


/**
 * Created by Administrator on 2017/5/27.
 */

public class LocalBroadManager {
    private static LocalBroadcastManager broadcastManager = null;

    public static LocalBroadcastManager getInstance(){
        if (broadcastManager == null){
            broadcastManager = LocalBroadcastManager.getInstance(MyApplication.getInstance());
        }
        return broadcastManager;
    }
}
