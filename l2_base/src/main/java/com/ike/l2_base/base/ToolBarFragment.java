package com.ike.l2_base.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ike.l2_base.utils.MessageUtils;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */

public class ToolBarFragment<T extends BasePresenter, E extends BaseModel> extends BaseFragment<T, E> implements BaseView {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentActivity parent = getActivity();
        View view = parent.getWindow().getDecorView();
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void hideProgress() {
        showProgress(false);
    }

    @Override
    public void showTip(String msg) {
        MessageUtils.showShortToast(this.getActivity(), msg);
    }
}
