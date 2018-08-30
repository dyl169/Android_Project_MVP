package com.ike.l2_base.http;

import android.content.Context;

import java.io.Serializable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：RxJava + Retrofit 的缓存机制
 */

public class RxRetrofitCache {

    /**
     * @param <T>
     * @param context
     * @param cacheKey     缓存key
     * @param expireTime   过期时间 0 表示有缓存就读，没有就从网络获取
     * @param fromNetwork  从网络获取的Observable
     * @param forceRefresh 是否强制刷新
     * @return
     */
    public static <T> Observable<T> load(final Context context, final String cacheKey, final long expireTime, Observable<T> fromNetwork, boolean forceRefresh) {
        Observable<T> fromCache = Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<T> observableEmitter) throws Exception {
                T cache = (T) CacheManager.readObject(context, cacheKey, expireTime);
                if (cache != null) {
                    observableEmitter.onNext(cache);
                } else {
                    observableEmitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


        /**
         * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
         */
        fromNetwork = fromNetwork.map(new Function<T, T>() {
            @Override
            public T apply(@NonNull T result) throws Exception {
                CacheManager.saveObject(context, (Serializable) result, cacheKey);
                return result;
            }
        });
        if (forceRefresh) {
            return fromNetwork;
        } else {
            return Observable.concat(fromCache, fromNetwork).firstElement().toObservable();
        }

    }


}