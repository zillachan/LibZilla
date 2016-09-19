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
package com.zilla.libraryzilla;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.snowdream.android.util.Log;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import zilla.libcore.Zilla;
import zilla.libcore.api.RetrofitAPI;
import zilla.libcore.db.DBHelper;
import zilla.libcore.util.CrashHandler;

/**
 * Created by zilla on 9/8/15.
 */
public class ZillaApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {

    public static RefWatcher getRefWatcher(Context context) {
        ZillaApplication application = (ZillaApplication) context.getApplicationContext();
        return application.refWatcher;
    }
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        new Zilla().setCallBack(this).initSystem(this);
        CrashHandler.getInstance().init(this);
        refWatcher= LeakCanary.install(this);
    }

    /**
     * init
     *
     * @param context Context
     */
    @Override
    public void onInit(Context context) {
        initApi();
        DBHelper.getInstance().setDbUpgradeListener(this);
    }

    /**
     * Config API info
     */
    private void initApi() {
        RetrofitAPI.okHttpClient=new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        System.out.println(message);
                    }
                }))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original=chain.request();
                        //自定义请求体
                        Request.Builder builder=original.newBuilder();
                        //builder.addHeader("appid","176B381904E6E56E");
                        return chain.proceed(builder.build());
                    }
                }).build();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("onCreate(SQLiteDatabase db)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("onUpgrade(SQLiteDatabase db)");
    }
}
