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
package com.zilla.android.zillacore.libzilla.lifecircle;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * 生命周期管理器，其自身也实现了生命周期接口
 * Created by chenze on 13-12-2.
 */
public class LifeCircleManager implements ILifeCircle{

    /**
     * 父容器(Activity/Fragment)
     */
    private ILifeCircle container;

    public LifeCircleManager(ILifeCircle container){
        this.container = container;
    }

    private List<ILifeCircle> callbacks = new ArrayList<ILifeCircle>();

    @Override
    public void onCreate(Bundle savedInstanceState){
        for (ILifeCircle callback : callbacks) {
            callback.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onResume(){
        for (ILifeCircle callback : callbacks) {
            callback.onResume();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (ILifeCircle callback : callbacks) {
            callback.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onPause(){
        for (ILifeCircle callback : callbacks) {
            callback.onPause();
        }
    }

    @Override
    public void onDestroy() {
        for (ILifeCircle callback : callbacks) {
            callback.onDestroy();
        }
    }

    /**
     * 添加回调
     *
     * @param callback callback
     */
    public void addCallback(ILifeCircle callback) {
        callbacks.add(callback);
    }
}
