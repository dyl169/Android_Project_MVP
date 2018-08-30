package com.ike.l2_base.error;

import android.content.res.Resources;
import android.text.TextUtils;

import com.ike.l2_base.BaseAPP;

import java.io.Serializable;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：base异常类
 */

public class BaseException extends Exception implements Serializable {

    private static final long serialVersionUID = -7543828327339565532L;
    private String error;
    private String oriError;
    private String error_code;

    public BaseException() {

    }

    public BaseException(String detailMessage) {
        error = detailMessage;
    }

    public BaseException(String detailMessage, Throwable throwable) {
        error = detailMessage;
    }

    private String getError() {

        String result;

        if (!TextUtils.isEmpty(error)) {
            result = error;
        } else {

            String name = "code" + error_code;
            int i = BaseAPP.app.getResources().getIdentifier(name, "string", BaseAPP.app.getPackageName());

            try {
                result = BaseAPP.app.getString(i);

            } catch (Resources.NotFoundException e) {

                if (!TextUtils.isEmpty(oriError)) {
                    result = oriError;
                } else {
//                    result = BaseAPP.app.getString(R.string.base_unknown_error_code) + error_code;
                    result = error_code;
                }
            }
        }

        return result;
    }

    @Override
    public String getMessage() {
        return getError();
    }

    /**
     * 设置错误代码
     *
     * @param error_code
     */
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_code() {
        return error_code;
    }

    /**
     * 如果没有错误代码，可以抛出原声的错误信息
     *
     * @param oriError
     */
    public void setOriError(String oriError) {
        this.oriError = oriError;
    }
}
