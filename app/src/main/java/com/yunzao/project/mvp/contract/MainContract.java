package com.yunzao.project.mvp.contract;


import com.ike.l2_base.base.BaseModel;
import com.ike.l2_base.base.BasePresenter;
import com.ike.l2_base.base.BaseView;
import com.yunzao.project.model.bean.DataItem;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * @auth ike
 * @date 2018/4/19
 * @desc 类描述：车辆解除绑定
 */


public interface MainContract {

    public interface View extends BaseView {
        void showDatas(ArrayList<DataItem> datas);
    }

    public abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void presenterGetData(String dataType, int pageSize, int page);
    }

    public interface Model extends BaseModel {
        Observable<ArrayList<DataItem>> httpGetData(String dataType, int pageSize, int page);
    }


}