package com.ike.l2_base.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */
public interface ISetupToolBar {

    abstract String setupToolBarTitle();

    abstract boolean setupToolBarLeftButton(View leftButton);

    abstract boolean setupToolBarRightButton(View rightButton);

    abstract boolean setupToolBarRightTv(TextView rightTextView);

    abstract boolean setupToolBarTitleIv(ImageView titleIv);

    // 动态改变
    abstract TextView getmTitle();

    abstract View getmLeftButton();

    abstract View getmRightButton();

    abstract View getmRightTv();

    abstract View getmTitleIv();
}
