package com.android11.vrimage.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by zhoukang on 2017/6/29.
 */

public interface Const {

    String HOME_PATH = Environment.getExternalStorageDirectory() + "/android11/VRImage";
    String VRIMAGE_PATH = HOME_PATH + "/download";
    String FIND = "https://kuula.co/api/?action=explore&tag=featured&limit=18&time=0&app=vr";
    String HOME = "https://kuula.co/api/?action=explore&tag=tinyplanet&limit=18&time=0&cid=1";


}
