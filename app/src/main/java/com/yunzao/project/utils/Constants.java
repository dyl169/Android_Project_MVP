package com.yunzao.project.utils;


import com.yunzao.project.Application;
import com.yunzao.project.BuildConfig;
import com.yunzao.project.R;

/**
 * 常量
 * Created by Beanu on 16/9/18.
 */
public class Constants {
    public static String API_SERVER = Application.getContext().getResources().getString(R.string.API_SERVER);

    public static final String TITLE_H5 = "title_h5";           //H5 标题
    public static final String URL_H5 = "url_h5";               //H5 URL

    ///////////////////////////////////////////////////////////////////
    //定义APP用到的一些唯一变量
    ///////////////////////////////////////////////////////////////////
    public static final String APPID = BuildConfig.APPLICATION_ID;          //包名唯一标识
    public static final String APP_PROVIDER = APPID + ".fileProvider";      //provider(必填)

}
