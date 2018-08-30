package com.yunzao.project.model.api;


import com.yunzao.project.model.HttpModel;
import com.yunzao.project.model.bean.DataItem;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @auth ike
 * @date 2018/4/4
 * @desc 类描述：
 */

public interface ApiService {
    ///////////////////////////////////////////////////////////////////////////
    // 基础信息
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param dataType 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
     * @param pageSize 个数
     * @param page     页码
     * @return
     */
    @GET("data/{dataType}/{pageSize}/{page}")
    Observable<HttpModel<ArrayList<DataItem>>> getDataInfo(@Path("dataType") String dataType,
                                                           @Path("pageSize") int pageSize,
                                                           @Path("page") int page);

}





