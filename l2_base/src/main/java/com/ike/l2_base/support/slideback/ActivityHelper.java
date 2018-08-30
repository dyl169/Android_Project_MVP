package com.ike.l2_base.support.slideback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.ike.l2_base.base.BaseActivity;

import java.util.Stack;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：Activity左滑退出
 */

public class ActivityHelper implements Application.ActivityLifecycleCallbacks {
    private static Stack<Activity> mActivityStack;

    public ActivityHelper() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
        Log.e("TAG", "长度" + mActivityStack.size());
        if (activity instanceof BaseActivity) {
            if (mActivityStack.size() < 2) {
                ((BaseActivity) activity).disableSlideBack();
            } else {
                ((BaseActivity) activity).enableSlideBack();
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

        // Log.e("TAG", "ActivityHelper-销毁: " + activity);
        mActivityStack.remove(activity);

        if (!mActivityStack.isEmpty()) {
            Activity a = mActivityStack.get(0);
            if (a instanceof BaseActivity) {
                ((BaseActivity) a).disableSlideBack();
            }
        }
    }

    public Activity getPreActivity() {
        if (mActivityStack == null) {
            return null;
        }
        int size = mActivityStack.size();
        if (size < 2) {
            return null;
        }
        return mActivityStack.elementAt(size - 2);
    }

    public static void finishAllActivity() {
        Log.e("TAG", "清空栈");
        if (mActivityStack == null) {
            return;
        }
        for (Activity activity : mActivityStack) {
            activity.finish();
        }
    }

    public void printAllActivity() {
        if (mActivityStack == null) {
            return;
        }
        for (int i = 0; i < mActivityStack.size(); i++) {
            Log.e("TAG", "位置" + i + ": " + mActivityStack.get(i));
        }
    }

    /**
     * 强制删掉activity，用于用户快速滑动页面的时候，因为页面还没来得及destroy导致的问题
     *
     * @param activity 删掉的activity
     */
    void postRemoveActivity(Activity activity) {
        if (mActivityStack != null) {
            mActivityStack.remove(activity);
        }
    }
}
