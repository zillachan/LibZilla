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
import com.zilla.android.zillacore.libzilla.Zilla;
import com.zilla.android.zillacore.libzilla.api.ZillaApi;
import com.zilla.android.zillacore.libzilla.db.DBHelper;

import retrofit.RequestInterceptor;

/**
 * Created by zilla on 9/8/15.
 */
public class ZillaApplication extends Application implements Zilla.InitCallback, DBHelper.DBUpgradeListener {
    @Override
    public void onCreate() {
        super.onCreate();
        new Zilla().setCallBack(this).initSystem(this);
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
        ZillaApi.NormalRestAdapter = ZillaApi.getRESTAdapter(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade requestFacade) {
//                requestFacade.addEncodedPathParam();
            }
        });
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
