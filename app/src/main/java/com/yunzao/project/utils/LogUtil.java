package com.yunzao.project.utils;

import android.util.Log;

import com.yunzao.project.BuildConfig;


/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：logUtil
 */
public class LogUtil {

    public static void warn(String TAG , String MESSAGE){
        if (BuildConfig.DEBUG)
            Log.w(TAG,MESSAGE);
    }

    public static void error(String TAG , String MESSAGE){
        if (BuildConfig.DEBUG)
            Log.e(TAG,MESSAGE);
    }

    public static void debug(String TAG , String MESSAGE){
        if (BuildConfig.DEBUG)
            Log.d(TAG,MESSAGE);
    }

    public static void info(String TAG , String MESSAGE){
        if (BuildConfig.DEBUG)
            Log.i(TAG,MESSAGE);
    }

}
