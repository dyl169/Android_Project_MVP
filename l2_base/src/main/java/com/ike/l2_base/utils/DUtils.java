package com.ike.l2_base.utils;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Window;

import java.lang.reflect.Field;

/**
 * 手机尺寸转化工具
 */
public class DUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int toPx(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int toDip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得手机屏幕宽度得到像素px
     */
    public static int getWith(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * 获得手机屏幕高度得到像素px
     */
    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getTitleHeight(Activity context) {
        int contentTop = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        //statusBarHeight是上面状态栏的高度
        int titleBarHeight = contentTop;
        return titleBarHeight;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            Log.d("", "get status bar height fail");
            e1.printStackTrace();
            return 75;
        }
    }
}
