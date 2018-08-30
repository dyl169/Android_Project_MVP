package com.yunzao.project.model.api.helper;

import com.ike.l2_base.error.BaseException;
import com.ike.l2_base.http.IHttpModel;

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
 * @desc 类描述： Rx 一些巧妙的处理
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
                        return createData(result);
                    }
                }).onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        BaseException exception = new BaseException();
                        if (throwable instanceof  BaseException){
                            exception = (BaseException) throwable;
                        }else {
                            exception.setOriError(throwable.getMessage());
                            exception.setError_code("-999");
                        }
                        return Observable.<T>error(exception);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据 升级到RxJava2无法发送NULL数据
     */
    private static <T> Observable<T> createData(final IHttpModel data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> observableEmitter) throws Exception {
                BaseException exception = new BaseException();
                try {
                    if (data != null) {
                        if (data.success()) {
                            observableEmitter.onNext((T) data.getResults());
                            observableEmitter.onComplete();
                        } else {
                            exception.setOriError(data.getMsg());
                            exception.setError_code(data.getErrorCode());
                            observableEmitter.onError(exception);
                        }
                    } else {
                        exception.setOriError("数据异常");
                        exception.setError_code("-1000");
                        observableEmitter.onError(exception);
                    }
                } catch (Exception e) {
                    exception.setOriError(e.getMessage());
                    exception.setError_code("-1001");
                    observableEmitter.onError(exception);
                }
            }
        });
    }
}