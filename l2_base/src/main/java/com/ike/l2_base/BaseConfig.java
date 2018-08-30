package com.ike.l2_base;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：定义配置信息
 */

public class BaseConfig {
    /**
     * SharedPreference轻量级存储 路径在/data/data/PACKAGE_NAME/shared_prefs
     */
    public String PREFERENCES_NAME = "base_preference";


    /**
     * 如果SD卡存在，则存在SD/Android/PACKAGE_NAME/cache/aradCache
     * 如果没有SD卡，则存在/data/data/PACKAGE_NAME/cache/aradCache
     */
    public String IMAGE_CACHEFOLDER = "base_ImageCache";

    /**
     * 如果SD卡存在，则存在SD/Android/PACKAGE_NAME/files/aradLog
     * 如果没有SD卡，则存在/data/data/PACKAGE_NAME/files/aradLog
     * 如果将此项设为 null 或 空字符串，则不在本机上存储日志
     */
    public String LOG_FOLDER = "base_Log";

    /**
     * 如果开启了CrashHandler并设置了此项，就会将错误信息以字符流的形式传到此服务器上
     */
    public String DEBUG_SERVER = "";


    /**
     * 定义lite-orm的数据库名称
     */
    public static String DB_NAME = "base.db";
}
