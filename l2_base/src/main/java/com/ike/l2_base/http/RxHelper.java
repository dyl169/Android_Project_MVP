package com.ike.l2_base.http;

import android.text.TextUtils;

import com.ike.l2_base.error.BaseException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：Rx 一些巧妙的处理
 */

public class RxHelper {

    /**
     * 对结果进行预处理
     */
    public static <T> ObservableTransformer<IHttpModel<T>, T> handleResult() {

        return new ObservableTransformer<IHttpModel<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<IHttpModel<T>> tObservable) {
                return (ObservableSource<T>) tObservable.flatMap(new Function<IHttpModel<T>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull IHttpModel<T> result) throws Exception {
                        if (result.success()) {
                            return createData(result.getResults());
                        } else {
                            BaseException exception = new BaseException();
                            if (!TextUtils.equals(result.getErrorCode(), "1015")) {
                                exception.setError_code(result.getErrorCode());
                                exception.setOriError(result.getMsg());
                            }
                            return Observable.error(exception);
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(data);
                    e.onComplete();
                } catch (Exception exception) {
                    e.onError(exception);
                }
            }
        });

    }
}
