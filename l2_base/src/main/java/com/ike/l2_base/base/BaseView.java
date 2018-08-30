package com.ike.l2_base.base;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */

public interface BaseView {
    //当前页面加载条
    void showProgress();

    //当前页面加载条
    void showProgress(String message);

    //当前页面隐藏加载条
    void hideProgress();

}
