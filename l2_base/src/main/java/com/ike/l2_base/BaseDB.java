package com.ike.l2_base;

import android.content.Context;

import com.ike.l2_base.base.IDB;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：lite-orm数据库建议操作
 */

public class BaseDB implements IDB {
    private static BaseDB instance;
    private LiteOrm mLiteOrm;

    public BaseDB(Context context) {
        mLiteOrm = LiteOrm.newCascadeInstance(context, BaseConfig.DB_NAME);
        mLiteOrm.setDebugged(BuildConfig.DEBUG);
    }

    public static BaseDB getInstance(Context context) {
        if (instance == null)
            instance = new BaseDB(context);
        return instance;
    }

    public LiteOrm getLiteOrm() {
        return mLiteOrm;
    }

    @Override
    public void save(Object entity) {
        mLiteOrm.save(entity);
    }

    @Override
    public <T> void save(List<T> list) {
        mLiteOrm.save(list);
    }

    @Override
    public <T> T findById(Class<T> clazz, Object id) {
        if (id instanceof Long) {
            return mLiteOrm.queryById((Long) id, clazz);
        } else if (id instanceof String) {
            return mLiteOrm.queryById((String) id, clazz);
        }

        return mLiteOrm.queryById(String.valueOf(id), clazz);
    }

    @Override
    public <T> int countByWhere(Class<T> clazz, String strWhere) {
        return (int) mLiteOrm.queryCount(new QueryBuilder(clazz).where(strWhere));
    }

    @Override
    public <T> ArrayList<T> findAll(Class<T> clazz) {
        return mLiteOrm.query(clazz);
    }

    @Override
    public <T> ArrayList<T> findAll(Class<T> clazz, String orderBy) {
        return mLiteOrm.query(new QueryBuilder<T>(clazz).orderBy(orderBy));
    }

    @Override
    public <T> ArrayList<T> findAllByWhere(Class<T> clazz, String strWhere) {
        return mLiteOrm.query(new QueryBuilder<T>(clazz).where(strWhere));
    }

    @Override
    public void update(Object entity) {
        mLiteOrm.update(entity);
    }

    @Override
    public void update(Object entity, String strWhere) {

    }

    @Override
    public void delete(Object entity) {
        mLiteOrm.delete(entity);
    }

    @Override
    public <T> void deleteById(Class<T> clazz, String id) {
    }

    @Override
    public <T> void deleteByWhere(Class<?> clazz, String strWhere) {

    }
}
