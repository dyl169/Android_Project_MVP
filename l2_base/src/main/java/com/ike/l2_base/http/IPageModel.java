package com.ike.l2_base.http;

import java.util.List;

/**
 * @auth ike
 * @date 2017/8/28
 * @desc 类描述：
 */

public interface IPageModel<T> {

    /**
     * @return 总页数
     */
    int getTotalPage();

    /**
     * @return 数据列表
     */
    List<T> getDataList();

    /**
     * @return 当前页码
     */
    int getCurrentPage();
}

