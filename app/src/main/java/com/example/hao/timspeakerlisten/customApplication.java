package com.example.hao.timspeakerlisten;

import android.app.Application;
import android.content.Context;

/**
 * Created by hal on 2017/5/11.
 */

public class customApplication extends Application{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getInstance() {
        return mContext;
    }
}
