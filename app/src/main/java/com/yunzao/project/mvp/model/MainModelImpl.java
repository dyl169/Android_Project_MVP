package com.yunzao.project.mvp.model;


import com.yunzao.project.model.api.APIFactory;
import com.yunzao.project.model.api.helper.RxHelper;
import com.yunzao.project.model.bean.DataItem;
import com.yunzao.project.mvp.contract.MainContract;

import java.util.ArrayList;

import io.reactivex.Observable;


/**
 * @auth ike
 * @date 2018/4/19
 * @desc 类描述：
 */


public class MainModelImpl implements MainContract.Model {


    @Override
    public Observable<ArrayList<DataItem>> httpGetData(String dataType, int pageSize, int page) {
        return APIFactory.getApiInstance()
                .getDataInfo(dataType, pageSize, page)
                .compose(RxHelper.<ArrayList<DataItem>>handleResult());
    }
}