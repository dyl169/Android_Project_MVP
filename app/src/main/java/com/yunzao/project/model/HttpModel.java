package com.yunzao.project.model;


import com.ike.l2_base.http.IHttpModel;

import java.io.Serializable;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：基础实体类
 */

public class HttpModel<T> implements IHttpModel<T>, Serializable {

    public String code;
    public String message;
    public Boolean error;
    public T results;


    @Override
    public boolean success() {
        //2000请求成功
        return !error;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    public Boolean getError() {
        return error;
    }

    @Override
    public T getResults() {
        return results;
    }
}
