package com.yunzao.project.mvp.presenter;

import com.ike.l2_base.utils.MessageUtils;
import com.yunzao.project.model.bean.DataItem;
import com.yunzao.project.mvp.contract.MainContract;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * @auth ike
 * @date 2018/4/19
 * @desc 类描述：
 */

public class MainPresenterImpl extends MainContract.Presenter {

    @Override
    public void presenterGetData(String dataType, int pageSize, int page) {
        mView.showProgress("加载中...");
        mRxManage.add(mModel.httpGetData(dataType, pageSize, page)
                .subscribe(new Consumer<ArrayList<DataItem>>() {
                    @Override
                    public void accept(@NonNull ArrayList<DataItem> datas) throws Exception {
                        mView.showDatas(datas);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //处理请求失败
                        mView.hideProgress();
                        MessageUtils.showShortToast(mContext, throwable.getMessage());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //处理请求完成
                        mView.hideProgress();
                    }
                }));
    }
}