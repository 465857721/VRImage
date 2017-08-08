package com.android11.vrimage.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
    private static final String HIGH = "high";
    private static final String NAME = "name";
    private static final String HEADURL = "headurl";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public SharePreferenceUtil(Context context, String file) {
        sp = context.getApplicationContext().getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setHighQulit(boolean high) {
        editor.putBoolean(HIGH, high);
        editor.commit();
    }

    public boolean getHighQulit() {
        return sp.getBoolean(HIGH, true);
    }

    public void setName(String name) {
        editor.putString(NAME, name);
        editor.commit();
    }

    public String getName() {
        return sp.getString(NAME, "");
    }

    public void setHeadurl(String url) {
        editor.putString(HEADURL, url);
        editor.commit();
    }

    public String getHeadurl() {
        return sp.getString(HEADURL, "");
    }
}
