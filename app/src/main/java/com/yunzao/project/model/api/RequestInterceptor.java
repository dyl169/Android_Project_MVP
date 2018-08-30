package com.yunzao.project.model.api;

import com.yunzao.project.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @auth ike
 * @date 2018/4/4
 * @desc 类描述：添加固定参数 token和secret
 */

public class RequestInterceptor implements Interceptor {
    private static final String TAG = "RequestInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder();
        //post的形式
        if (original.body() instanceof FormBody) {
            HashMap<String, String> map = new HashMap<>();

            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            for (int i = 0; i < oidFormBody.size(); i++) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
                map.put(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
            }

            requestBuilder.method(original.method(), newFormBody.build());
        }
        Request request = requestBuilder.build();
        LogUtil.info(TAG, "post :" + request.url());
        return chain.proceed(request);
    }
}