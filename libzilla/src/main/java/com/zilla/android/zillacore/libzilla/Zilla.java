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
package com.zilla.android.zillacore.libzilla;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import com.github.snowdream.android.util.Log;
import com.zilla.android.zillacore.libzilla.db.DBHelper;
import com.zilla.android.zillacore.libzilla.file.AddressManager;
import com.zilla.android.zillacore.libzilla.file.FileHelper;
import com.zilla.android.zillacore.libzilla.file.PropertiesManager;
import com.zilla.android.zillacore.libzilla.file.SharedPreferenceService;
import com.zilla.android.zillacore.libzilla.module.ModulePropertiesManager;
import com.zilla.android.zillacore.libzilla.util.CrashHandler;

/**
 * Created by chenze on 14/6/19.
 */
public class Zilla {

    private InitCallback callback;

    public static Application APP;

    public static Activity ACTIVITY;

    public static DisplayMetrics DM;

    public Zilla() {

    }

    /**
     * 系统核心库初始化 将应用Application传递到核心库中做初始化动作
     *
     * @param app
     *          the application
     *          @return Zilla Object
     */
    public Zilla initSystem(Application app) {
        APP = app;
        //系统配置
        PropertiesManager.getInstance(app);
        //日志开关
        initLog();
        //api地址初始化
        AddressManager.getInstance(app);
        //程序内部SharedPreference初始化
        SharedPreferenceService.getInstance(app);
        //模块配置初始化
        ModulePropertiesManager.getInstance(app);
        //路径初始化
        FileHelper.initPath(PropertiesManager.get("cache"));
        //屏幕密度参数
        DM = app.getResources().getDisplayMetrics();
        //数据库配置初始化
        DBHelper.init(app, PropertiesManager.get("dbname"), Integer.parseInt(PropertiesManager.get("dbversion", "1")));

        if("true".equals(PropertiesManager.get("exception", "false"))){
            CrashHandler.getInstance().init(app);
        }
        //其它初始化回调
        if (this.callback != null) {
            this.callback.onInit(APP);
        }
        return this;
    }

    private void initLog() {
        Log.setEnabled(Boolean.parseBoolean(PropertiesManager.get("log")));
        Log.setTag(PropertiesManager.get("appname"));
    }

    /**
     * 设置初始化回掉监听器
     *
     * @param initCallback the initCallback for application
     *                     @return Zilla object
     */
    public Zilla setCallBack(InitCallback initCallback) {
        this.callback = initCallback;
        return this;
    }

    /**
     * 初始化对外回掉 初始化对外回掉
     *
     * @author zilla
     * @version 1.0
     */
    public interface InitCallback {
        void onInit(Context context);
    }
}
