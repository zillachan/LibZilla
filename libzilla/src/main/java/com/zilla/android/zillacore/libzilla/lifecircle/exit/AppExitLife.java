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
package com.zilla.android.zillacore.libzilla.lifecircle.exit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zilla.android.zillacore.libzilla.Zilla;
import com.zilla.android.zillacore.libzilla.lifecircle.ILifeCircle;

/**
 * Created by chenze on 13-12-2.
 */
public class AppExitLife implements ILifeCircle {

    private Context context;

    public AppExitLife(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity((Activity) context);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        AppManager.getAppManager().finishActivity((Activity) context);
    }
}
