package com.ike.l2_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ike.l2_base.R;
import com.ike.l2_base.utils.TUtil;
import com.ike.l2_base.widget.dialog.ProgressHUD;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述： 基础fragment  1.加入了progress dialog 2.mvp的泛形实现 3.start activity的封装
 */

public class BaseFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    public T mPresenter;
    public E mModel;

    private ProgressHUD mProgressHUD;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
            if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    public void showProgress(boolean show) {
        showProgressWithText(show, getResources().getString(R.string.l2_base_loading_defaulttip));
    }

    public void showProgress(boolean show, String message) {
        showProgressWithText(show, message);
    }

    public void showProgressWithText(boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(getActivity(), message, true, true, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

}
