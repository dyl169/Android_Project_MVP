package com.ike.l2_base.http;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.ike.l2_base.error.BaseException;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：自定义Subscribe
 */

public abstract class RxSubscribe<T> extends ResourceSubscriber<T> {
    private Context mContext;
    private AlertDialog dialog;
    private String msg;

    protected boolean showDialog() {
        return true;
    }

    /**
     * @param context context
     * @param msg     dialog message
     */
    public RxSubscribe(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    /**
     * @param context context
     */
    public RxSubscribe(Context context) {
        this(context, "请稍后...");
    }

    @Override
    public void onComplete() {
        if (showDialog())
            dialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog()) {
            dialog = new AlertDialog.Builder(mContext).setTitle(msg).create();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            //点击取消的时候取消订阅
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (!isDisposed()) {
                        dispose();
                    }
                }
            });
            dialog.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) {
            //TODO 这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof BaseException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
        if (showDialog())
            dialog.dismiss();
    }
    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}

