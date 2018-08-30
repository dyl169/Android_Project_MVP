package com.ike.l2_base;

import android.content.SharedPreferences;

import com.ike.l2_base.base.IPreferences;

import java.util.Map;
import java.util.Set;

/**
 * @auth ike
 * @date 2017/8/25
 * @desc 类描述：preference 轻量级存储
 */

public class BasePreferences implements IPreferences {

    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    public BasePreferences(SharedPreferences preferences) {
        this.sharedPrefs = preferences;
    }

    @Override
    public IPreferences putBoolean(String key, boolean val) {
        edit();
        editor.putBoolean(key, val);
        return this;
    }

    @Override
    public IPreferences putInteger(String key, int val) {
        edit();
        editor.putInt(key, val);
        return this;
    }

    @Override
    public IPreferences putLong(String key, long val) {
        edit();
        editor.putLong(key, val);
        return this;
    }

    @Override
    public IPreferences putFloat(String key, float val) {
        edit();
        editor.putFloat(key, val);
        return this;
    }

    @Override
    public IPreferences putString(String key, String val) {
        edit();
        editor.putString(key, val);
        return this;
    }

    @Override
    public IPreferences putStringSet(String key, Set<String> set) {
        edit();
        editor.putStringSet(key, set);
        return this;
    }

    @Override
    public IPreferences put(Map<String, ?> vals) {
        edit();
        for (Map.Entry<String, ?> val : vals.entrySet()) {
            if (val.getValue() instanceof Boolean)
                putBoolean(val.getKey(), (Boolean) val.getValue());
            if (val.getValue() instanceof Integer)
                putInteger(val.getKey(), (Integer) val.getValue());
            if (val.getValue() instanceof Long)
                putLong(val.getKey(), (Long) val.getValue());
            if (val.getValue() instanceof String)
                putString(val.getKey(), (String) val.getValue());
            if (val.getValue() instanceof Float)
                putFloat(val.getKey(), (Float) val.getValue());
            if (val.getValue() instanceof Set) {
                putStringSet(val.getKey(), (Set<String>) val.getValue());
            }
        }
        return this;
    }

    @Override
    public boolean getBoolean(String key) {
        return sharedPrefs.getBoolean(key, false);
    }

    @Override
    public int getInteger(String key) {
        return sharedPrefs.getInt(key, 0);
    }

    @Override
    public long getLong(String key) {
        return sharedPrefs.getLong(key, 0);
    }

    @Override
    public float getFloat(String key) {
        return sharedPrefs.getFloat(key, 0);
    }

    @Override
    public String getString(String key) {
        return sharedPrefs.getString(key, "");
    }

    @Override
    public Set<String> getStringSet(String key) {
        return sharedPrefs.getStringSet(key, null);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return sharedPrefs.getBoolean(key, defValue);
    }

    @Override
    public int getInteger(String key, int defValue) {
        return sharedPrefs.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return sharedPrefs.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return sharedPrefs.getFloat(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return sharedPrefs.getString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValue) {
        return sharedPrefs.getStringSet(key, defValue);
    }

    @Override
    public Map<String, ?> get() {
        return sharedPrefs.getAll();
    }

    @Override
    public boolean contains(String key) {
        return sharedPrefs.contains(key);
    }

    @Override
    public void clear() {
        edit();
        editor.clear();
    }

    @Override
    public void flush() {
        if (editor != null) {
            editor.commit();
            editor = null;
        }
    }

    @Override
    public void remove(String key) {
        edit();
        editor.remove(key);
    }

    private void edit() {
        if (editor == null) {
            editor = sharedPrefs.edit();
        }
    }
}
