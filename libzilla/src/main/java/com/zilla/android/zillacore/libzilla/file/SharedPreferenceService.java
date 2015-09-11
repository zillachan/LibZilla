/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.zilla.android.zillacore.libzilla.file;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 配置文件管理器<br>
 * 配置文件管理器,简单封装SharedPreference,提供直接的读写方法,get,put
 *
 * @author ze.chen
 * @version 1.0
 */

@SuppressLint("CommitPrefEdits")
public class SharedPreferenceService {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    private Context mContext = null;

    private static SharedPreferenceService manager = null;

    private SharedPreferenceService(Context context) {
        mContext = context.getApplicationContext();
        sp = mContext.getSharedPreferences("sys_setting", 0);
        editor = sp.edit();
    }

    public static SharedPreferenceService getInstance(Context context) {
        if (manager == null) {
            manager = new SharedPreferenceService(context);
        }
        return manager;
    }

    public static SharedPreferenceService getInstance() {
        return manager;
    }

    public void put(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void put(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void put(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public void put(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String get(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public int get(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public long get(String key, long defValue) {
        return sp.getLong(key, defValue);
    }

    public boolean get(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }
}
