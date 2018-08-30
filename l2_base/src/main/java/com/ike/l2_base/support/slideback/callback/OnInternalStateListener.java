package com.ike.l2_base.support.slideback.callback;

import android.support.annotation.FloatRange;

import com.ike.l2_base.support.slideback.widget.SlideBackLayout;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */

public interface OnInternalStateListener {

    void onSlide(@FloatRange(from = 0.0,
            to = 1.0) float percent);

    void onOpen();

    void onClose(Boolean finishActivity);

    void onCheckPreActivity(SlideBackLayout slideBackLayout);

}