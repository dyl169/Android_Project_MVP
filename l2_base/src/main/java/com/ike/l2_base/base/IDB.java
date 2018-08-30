package com.ike.l2_base.base;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：基于 lite-orm 定义一些DB的常用操作
 */

public interface IDB {
    public void save(Object entity);

    public <T> void save(List<T> list);

    public <T> T findById(Class<T> clazz, Object id);

    public <T> int countByWhere(Class<T> clazz, String strWhere);

    public <T> ArrayList<T> findAll(Class<T> clazz);

    public <T> ArrayList<T> findAll(Class<T> clazz, String orderBy);

    public <T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere);

    public void update(Object entity);

    public void update(Object entity, String strWhere);

    public void delete(Object entity);

    public <T> void deleteById(Class<T> clazz, String id);

    public <T> void deleteByWhere(Class<?> clazz, String strWhere);
}
