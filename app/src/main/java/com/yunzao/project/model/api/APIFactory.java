package com.yunzao.project.model.api;

/**
 * @auth ike
 * @date 2018/4/4
 * @desc 类描述：API工厂类
 */

public class APIFactory {

    private static ApiService apiServer;
    protected static final Object monitor = new Object();

    public static ApiService getApiInstance() {
        synchronized (monitor) {
            if (apiServer == null) {
                apiServer = APIRetrofit.getDefault();
            }
            return apiServer;
        }
    }
}