package com.android11.vrimage.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
    private static final String HIGH = "high";
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


}
