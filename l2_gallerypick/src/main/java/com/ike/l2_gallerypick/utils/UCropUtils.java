package com.ike.l2_gallerypick.utils;

import android.app.Activity;
import android.net.Uri;

import com.ike.l2_gallerypick.R;
import com.yalantis.ucrop.UCrop;

import java.io.File;

/**
 * UCropUtils
 * Created by Yancy on 2016/11/2.
 */
public class UCropUtils {

    public static void start(Activity mActivity, File sourceFile, File destinationFile, float aspectRatioX, float aspectRatioY, int maxWidth, int maxHeight) {
        UCrop uCrop = UCrop.of(Uri.fromFile(sourceFile), Uri.fromFile(destinationFile))
                .withAspectRatio(aspectRatioX, aspectRatioY)
                .withMaxResultSize(maxWidth, maxHeight);

        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(mActivity.getResources().getColor(R.color.gallery_474747));
        options.setStatusBarColor(mActivity.getResources().getColor(R.color.gallery_colorPrimaryDark));
        options.setCircleDimmedLayer(true);
        options.setHideBottomControls(true);//裁剪页面底部操作功能
        uCrop.withOptions(options);


        uCrop.start(mActivity);
    }


}
/*
 *   ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 *     ┃　　　┃
 *     ┃　　　┃
 *     ┃　　　┗━━━┓
 *     ┃　　　　　　　┣┓
 *     ┃　　　　　　　┏┛
 *     ┗┓┓┏━┳┓┏┛
 *       ┃┫┫　┃┫┫
 *       ┗┻┛　┗┻┛
 *        神兽保佑
 *        代码无BUG!
 */