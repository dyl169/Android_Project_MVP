package com.ike.l2_base.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ike.l2_base.BaseApplication;
import com.ike.l2_base.R;
import com.ike.l2_base.support.slideback.SlideBackHelper;
import com.ike.l2_base.support.slideback.SlideConfig;
import com.ike.l2_base.support.slideback.widget.SlideBackLayout;
import com.ike.l2_base.utils.ActivityUtil;
import com.ike.l2_base.utils.DialogUtil;
import com.ike.l2_base.utils.TUtil;
import com.ike.l2_base.widget.dialog.ProgressHUD;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：Activity基类
 */

public class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    public T mPresenter;
    public E mModel;

    private SlideBackLayout mSlideBackLayout;

    private ProgressHUD mProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;

            if (this instanceof BaseView) mPresenter.setVM(this, mModel);
        }

        ActivityUtil.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initSlideBackLayout() {
        mSlideBackLayout = SlideBackHelper.attach(
                // 当前Activity
                this,
                // Activity栈管理工具
                BaseApplication.activityHelper,
                // 参数的配置
                new SlideConfig.Builder()
                        // 屏幕是否旋转
                        .rotateScreen(true)
                        // 是否侧滑
                        .edgeOnly(true)
                        // 是否禁止侧滑
                        .lock(true)
                        // 侧滑的响应阈值，0~1，对应屏幕宽度*percent
                        .edgePercent(0.2f)
                        // 关闭页面的阈值，0~1，对应屏幕宽度*percent
                        .slideOutPercent(0.5f).create(),
                // 滑动的监听
                null);
    }

    /**
     * 开启侧滑关闭
     */
    public void enableSlideBack() {
        if (mSlideBackLayout == null) {
            initSlideBackLayout();
        }
        if (mSlideBackLayout != null) {
            mSlideBackLayout.lock(false);
        }
    }

    /**
     * 禁用侧滑关闭
     */
    public void disableSlideBack() {
        if (mSlideBackLayout != null) {
            mSlideBackLayout.lock(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtil.removeActivity(this);
        if (mPresenter != null)
            mPresenter.onDestroy();
    }

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show true显示 false隐藏
     */
    public void showProgress(boolean show) {
        showProgressWithText(show, getResources().getString(R.string.l2_base_loading_defaulttip));
    }

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show    true显示 false隐藏
     * @param message 显示内容
     */
    public void showProgress(boolean show, String message) {
        showProgressWithText(show, message);
    }

    /**
     * 显示一个等待dialog，内容交互或者提交的时候使用
     *
     * @param show    是否要显示
     * @param message 要显示的文字
     */
    public void showProgressWithText(boolean show, String message) {
        if (show) {
            if (mProgressHUD == null) {
                mProgressHUD = (ProgressHUD) DialogUtil.showProgressDialog(this, message);
            } else {
                if (mProgressHUD.isShowing()) {
                    mProgressHUD.setMessage(message);
                } else {
                    mProgressHUD = (ProgressHUD) DialogUtil.showProgressDialog(this, message);
                }
            }
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
        intent.setClass(this, cls);
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
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_base_activity_slide_in, R.anim.anim_base_activity_none);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_base_activity_none, R.anim.anim_base_activity_slide_out);
    }

}
