package com.ike.l2_base.support.slideback.callback;

import android.support.annotation.FloatRange;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：
 */

public interface OnSlideListener {

    void onSlide(@FloatRange(from = 0.0,
            to = 1.0) float percent);

    void onOpen();

    void onClose();

}
