package com.yunzao.project.model.api;


import android.content.Context;

import com.alibaba.fastjson.support.retrofit.Retrofit2ConverterFactory;
import com.yunzao.project.Application;
import com.yunzao.project.model.api.ssl.TrustAllCerts;
import com.yunzao.project.utils.Constants;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @auth ike
 * @date 2018/4/4
 * @desc 类描述：
 */

public class APIRetrofit {

    private static ApiService SERVICE;

    public static ApiService getDefault() {
        if (SERVICE == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(1 * 30, TimeUnit.SECONDS)
                    .sslSocketFactory(createSSLSocketFactory(Application.getContext()), new TrustAllCerts())
                    .hostnameVerifier(new UnSafeHostnameVerifier())//添加hostName验证器
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(logging)
                    .build();

            SERVICE = new Retrofit.Builder()
                    .baseUrl(Constants.API_SERVER)
                    .addConverterFactory(new Retrofit2ConverterFactory())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build().create(ApiService.class);

        }
        return SERVICE;
    }

    /**
     * SSL相关
     *
     * @param context
     * @return
     */
    private static SSLSocketFactory createSSLSocketFactory(Context context) {

        //默认信任所有https证书
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            try {
                sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sc.getSocketFactory();
    }

    static class UnSafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//自行添加判断逻辑，true->Safe，false->unsafe
        }
    }
}
