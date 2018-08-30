package com.ike.l2_base.base;

import android.content.Context;

import com.ike.l2_base.support.rxjava.RxManager;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */

public class BasePresenter<T, E> {
    public Context mContext;
    public E mModel;
    public T mView;
    public RxManager mRxManage = new RxManager();

    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {
    }


    public void onDestroy() {
        mRxManage.clear();
    }
}
