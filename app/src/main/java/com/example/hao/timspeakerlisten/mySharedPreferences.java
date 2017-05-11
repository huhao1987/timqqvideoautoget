package com.example.hao.timspeakerlisten;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.Map;
import java.util.Set;

/**
 * Created by hal on 2017/5/11.
 */

public class mySharedPreferences  {
    static String seperator = ";";

    public static SharedPreferences getDefaultShareUsername() {
        String packageName = customApplication.getInstance()
                .getPackageName();
        return customApplication.getInstance().getSharedPreferences(
                packageName, Activity.MODE_PRIVATE);
    }

    public static void setAction(int a)
    {
        getDefaultShareUsername().edit().putInt("action",a).commit();
    }
    public static int getAction()
    {
        int a=getDefaultShareUsername().getInt("action",-1);
        return a;
    }
    public static void setOpenAuto(boolean a)
    {
        getDefaultShareUsername().edit().putBoolean("openauto",a).commit();

    }
    public static boolean getOpenAuto()
    {
        boolean a=getDefaultShareUsername().getBoolean("openauto",false);
        return a;
    }
}
