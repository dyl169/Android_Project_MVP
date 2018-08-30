package com.ike.l2_base.utils;

import android.app.Activity;

import com.ike.l2_base.R;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：activity动画
 */

public class AnimUtil {

    /** slip in */
    public static void intentSlidIn(Activity activity) {
        activity.overridePendingTransition(R.anim.anim_base_activity_slide_in_right, R.anim.anim_base_activity_slide_out_left);
    }

    /** Slip off */
    public static void intentSlidOut(Activity activity) {
        activity.overridePendingTransition(R.anim.anim_base_activity_slide_in_left, R.anim.anim_base_activity_slide_out_right);
    }

    /** push up */
    public static void intentPushUp(Activity activity) {
        activity.overridePendingTransition(R.anim.anim_base_activity_push_up, R.anim.anim_base_activity_push_out);
    }

    /** push down */
    public static void intentPushDown(Activity activity) {
        activity.overridePendingTransition(R.anim.anim_base_activity_push_out, R.anim.anim_base_activity_push_down);
    }
}
