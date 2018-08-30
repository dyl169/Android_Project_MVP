package com.ike.l2_base.utils;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：设备信息
 */

public class DeviceInfo {
    private String osSystem;//手机操作系统
    private String osSystemVersion;// 手机系统版本
    private int versionCode;//版本号
    private String versionName;//版本名称
    private String deviceMode;// 设备型号

    private String countryCode;//国家code
    private String deviceID;//设备ID
    private String deviceImsi;// SIM卡的IMSI码
    private String phoneNumber;//手机号码

    private int screenHeight;//屏幕高度
    private int screenWidth;//屏幕宽度
    private int densityDpi;//DPI

    private Context context;

    public DeviceInfo(Context context) {
        this.context = context;
        try {
            osSystem = "Android";
            osSystemVersion = Build.VERSION.RELEASE;
            deviceMode = Build.MODEL;

            screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            screenHeight = context.getResources().getDisplayMetrics().heightPixels;
            densityDpi = context.getResources().getDisplayMetrics().densityDpi;

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            countryCode = tm == null ? "" : (tm.getSimCountryIso() == null ? "" : tm.getSimCountryIso().toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERROR", "Device Info Error");
        }
    }

    public String getDeviceImsi() {
        if (StringUtil.isNull(deviceImsi))
            deviceImsi = AndroidUtil.getDeviceImsi(context);
        return deviceImsi;
    }

    public String getOsSystem() {
        return osSystem;
    }

    public String getOsSystemVer() {
        return osSystemVersion;
    }

    public String getDeviceMode() {
        return deviceMode;
    }

    public int getVersionCode() {
        if (versionCode == 0)
            versionCode = AndroidUtil.getVerCode(context);
        return versionCode;
    }

    public String getVersionName() {
        if (StringUtil.isNull(versionName))
            versionName = AndroidUtil.getVerName(context);
        return versionName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDeviceID() {
        if (StringUtil.isNull(deviceID))
            deviceID = AndroidUtil.getDeviceId(context);
        return deviceID;
    }

    public String getPhoneNumber() {
        if (StringUtil.isNull(phoneNumber))
            phoneNumber = AndroidUtil.getPhoneNumber(context);
        return phoneNumber;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getDensityDpi() {
        return densityDpi;
    }
}
