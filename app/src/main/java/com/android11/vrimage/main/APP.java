package com.android11.vrimage.main;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

/**
 * Created by zhoukang on 2017/8/7.
 */

public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);
//        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setQQZone("1105034952", "IXtK5xIFr2wKZXvp");
    }
}
