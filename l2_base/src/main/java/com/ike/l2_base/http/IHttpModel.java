package com.ike.l2_base.http;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：
 */

public interface IHttpModel<T> {

    /**
     * 接口是否正确
     */
    boolean success();



    /**
     * 错误码
     */
    String getErrorCode();

    /**
     * 接口提示信息
     */
    String getMsg();

    /**
     * 结果集
     */
    T getResults();

}