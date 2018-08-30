package com.ike.l2_base;

import android.app.Application;
import android.content.Context;

import com.ike.l2_base.crash.CrashHandler;
import com.ike.l2_base.support.slideback.ActivityHelper;
import com.ike.l2_base.utils.DeviceInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：全局入口baseApplication
 */

public abstract class BaseApplication extends Application {
    public DeviceInfo deviceInfo;
    public BaseConfig config;

    public static ActivityHelper activityHelper;
    private static BaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        config = appConfig();
        context = this;

        BaseAPP.app = this;
        BaseAPP.db = BaseDB.getInstance(getApplicationContext());
        BaseAPP.preferences = new BasePreferences(getSharedPreferences(config.PREFERENCES_NAME, Context.MODE_PRIVATE));
        deviceInfo = new DeviceInfo(getApplicationContext());
        BaseAPP.bus = EventBus.getDefault();

        //开启CrashHandler
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        crashHandler.setLogFolder(config.LOG_FOLDER);
        crashHandler.setDebugServer(config.DEBUG_SERVER);

        //开启侧滑支持需要此Helper类支持
        activityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(activityHelper);

    }

    public static BaseApplication getAppContext() {
        return context;
    }

    protected abstract BaseConfig appConfig();

    public void disableCrashHandler() {
        CrashHandler.getInstance().disable();
    }

    public void enableCrashHandler() {
        CrashHandler.getInstance().enable();
    }
}
