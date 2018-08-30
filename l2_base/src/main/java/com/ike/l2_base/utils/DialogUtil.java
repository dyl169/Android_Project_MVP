package com.ike.l2_base.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.ike.l2_base.BaseAPP;
import com.ike.l2_base.widget.dialog.AlertDialogFragment;
import com.ike.l2_base.widget.dialog.ProgressHUD;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：通用dialog
 */

public class DialogUtil {
    /**
     * 获取上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseAPP.app;
    }

    /**
     * 获取布局
     *
     * @param resId
     * @return
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    public static int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * show ProgressDialog
     */
    public static Dialog showProgressDialog(Context context, String message) {
        ProgressHUD progressHUD = ProgressHUD.show(context, message, true, true, null);
        progressHUD.setMessage(message);
        return progressHUD;
    }

    /**
     * hide ProgressDialog
     *
     * @param dialog
     */
    public static void hideProgressDialog(Dialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    /**
     * AlertDialog
     */
    public static void showAlertDialog(FragmentManager fm, String title,
                                       String message, String positiveButtonText,
                                       String negativeButtonText, DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        AlertDialogFragment dialog = AlertDialogFragment.newInstance(title,
                message, positiveButtonText, negativeButtonText);
        dialog.setNegativeListener(negativeListener);
        dialog.setPositiveListener(positiveListener);
        dialog.show(fm, "dialog");
    }

    /**
     * 隐藏一个等待dialog fm=getSupportFragmentManager()
     */
    public static void hideDialogFragment(FragmentManager fm) {
        DialogFragment prev = (DialogFragment) fm.findFragmentByTag("dialog");
        if (prev != null) {
            prev.dismiss();
        }
    }


    /**
     * 打电话拨号
     */
    public static void dial(Context context, String telephone) {
        if (telephone != null && !telephone.equals("")) {
            Uri uri = Uri.parse("tel:" + telephone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        }
    }

}
