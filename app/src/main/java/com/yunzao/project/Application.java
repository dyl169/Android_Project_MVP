package com.yunzao.project;

import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.ike.l2_base.BaseApplication;
import com.ike.l2_base.BaseConfig;

/**
 * @auth ike
 * @date 2018/8/30
 * @desc 类描述：
 */

public class Application extends BaseApplication {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        disableCrashHandler();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            MultiDex.install(this);
    }

    @Override
    protected BaseConfig appConfig() {
        BaseConfig baseConfig = new BaseConfig();
        baseConfig.PREFERENCES_NAME = "mvp_project_sp";
        baseConfig.DEBUG_SERVER = "";
        baseConfig.LOG_FOLDER = "mvp_project_log";
        baseConfig.DB_NAME = "mvp_project_db.db";
        return new BaseConfig();
    }
}
