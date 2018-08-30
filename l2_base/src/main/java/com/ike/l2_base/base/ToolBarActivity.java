package com.ike.l2_base.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ike.l2_base.R;
import com.ike.l2_base.utils.AnimUtil;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：加入toolbar的操作
 */

public class ToolBarActivity<T extends BasePresenter, E extends BaseModel> extends BaseActivity<T, E> implements ISetupToolBar, BaseView {

    private TextView mTitle;
    private View mLeftButton;
    private View mRightButton;
    private TextView mRightTv;
    private ImageView mTitleIv;

    private ActionBar mActionBar;
    private Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.toolbar_title);
        mLeftButton = findViewById(R.id.toolbar_leftBtn);
        mRightButton = findViewById(R.id.toolbar_rightBtn);
        mRightTv = (TextView) findViewById(R.id.toolbar_rightTv);
        mTitleIv = (ImageView) findViewById(R.id.toolbar_title_iv);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            hideHomeAsUp();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mTitle != null && setupToolBarTitle() != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(setupToolBarTitle());
            if (mActionBar != null) {
                mActionBar.setDisplayShowTitleEnabled(false);
            }
        } else {
            if (mTitle != null)
                mTitle.setVisibility(View.GONE);
        }

        if (mLeftButton != null) {
            if (setupToolBarLeftButton(mLeftButton)) {
                mLeftButton.setVisibility(View.VISIBLE);
                hideHomeAsUp();
            } else {
                mLeftButton.setVisibility(View.GONE);
            }
        }

        if (mRightButton != null) {
            if (setupToolBarRightButton(mRightButton)) {
                mRightButton.setVisibility(View.VISIBLE);
            } else {
                mRightButton.setVisibility(View.GONE);
            }
        }

        if (mRightTv != null) {
            if (setupToolBarRightTv(mRightTv)) {
                mRightTv.setVisibility(View.VISIBLE);
            } else {
                mRightTv.setVisibility(View.GONE);
            }
        }

        if (mTitleIv != null) {
            if (setupToolBarTitleIv(mTitleIv)) {
                mTitleIv.setVisibility(View.VISIBLE);
                mActionBar.setDisplayShowTitleEnabled(false);
            } else {
                mTitleIv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        AnimUtil.intentSlidOut(this);
    }

    protected void displayHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void hideHomeAsUp() {
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    ////////////////////////////////////////////////
    //动态设置
    ////////////////////////////////////////////////
    @Override
    public String setupToolBarTitle() {
        return null;
    }

    @Override
    public boolean setupToolBarLeftButton(View leftButton) {
        return false;
    }

    @Override
    public boolean setupToolBarRightButton(View rightButton) {
        return false;
    }

    @Override
    public boolean setupToolBarRightTv(TextView rightTextView) {
        return false;
    }

    @Override
    public boolean setupToolBarTitleIv(ImageView titleIv) {
        return false;
    }

    @Override
    public TextView getmTitle() {
        return mTitle;
    }

    @Override
    public View getmLeftButton() {
        return mLeftButton;
    }

    @Override
    public View getmRightButton() {
        return mRightButton;
    }

    @Override
    public View getmRightTv() {
        return mRightTv;
    }

    @Override
    public View getmTitleIv() {
        return mTitleIv;
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
}
