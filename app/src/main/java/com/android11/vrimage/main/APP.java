package com.android11.vrimage.main;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by zhoukang on 2017/8/7.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
    }
}
