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

import android.content.Context;
import android.content.Intent;

import com.github.snowdream.android.util.Log;
import com.zilla.android.zillacore.libzilla.lifecircle.annotation.LifeCircleInject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * 解析injectedSource中的注入字段
 * User: chenze
 * Date: 13-10-11
 * Time: 上午11:29
 */
public class LifeCircle {

    static WeakHashMap<Object, List> map = new WeakHashMap<>();

    /**
     * 载体对象
     *
     * @param injectedSource 父容器(Activity/Fragment)
     */
    public static void onCreate(Object injectedSource) {
        List<ILifeCircle> callbacks = Collections.synchronizedList(new ArrayList<ILifeCircle>());
        Class containerClass = injectedSource.getClass();

        //Field补全
        Field[] fields1 = containerClass.getFields();
        Field[] fields2 = containerClass.getDeclaredFields();

        Set<Field> fieldSet = new HashSet<>();

        for (Field field : fields1) {
            fieldSet.add(field);
        }
        for (Field field : fields2) {
            fieldSet.add(field);
        }
        //Field补全}

        if (fieldSet != null && fieldSet.size() > 0) {
            for (Field field : fieldSet) {
                LifeCircleInject callbackInject = field.getAnnotation(LifeCircleInject.class);
                if (callbackInject != null) {
                    Class type = field.getType();
                    field.setAccessible(true);
                    try {
                        Constructor constructor;
                        if (injectedSource instanceof Context) {
                            constructor = type.getConstructor(Context.class);
                            field.set(injectedSource, constructor.newInstance(injectedSource));
                            callbacks.add((ILifeCircle) field.get(injectedSource));
                        } else {
                            Class V4Fragment = Class.forName("android.support.v4.app.Fragment");
                            if (V4Fragment.isInstance(injectedSource)) {
                                try {
                                    constructor = type.getConstructor(V4Fragment);
                                    field.set(injectedSource, constructor.newInstance(injectedSource));
                                    callbacks.add((ILifeCircle) field.get(injectedSource));
                                } catch (Exception e) {//转成Context
                                    constructor = type.getConstructor(Context.class);
                                    Method getActivity = injectedSource.getClass().getDeclaredMethod("getActivity");
                                    Context context = (Context) getActivity.invoke(injectedSource);
                                    field.set(injectedSource, constructor.newInstance(context));
                                    callbacks.add((ILifeCircle) field.get(injectedSource));
                                }
                            } else {
                                constructor = type.getConstructor(containerClass);
                                field.set(injectedSource, constructor.newInstance(injectedSource));
                                callbacks.add((ILifeCircle) field.get(injectedSource));
                            }
                        }

                    } catch (Exception e) {
                        Log.e("inject", e);
                    }
                }
            }
        }
        map.put(injectedSource, callbacks);
        for (ILifeCircle callback : callbacks) {
            callback.onCreate(null);
        }
    }

    /**
     * clear the inject objects.
     *
     * @param injectedSource
     */
    public static void onDestory(Object injectedSource) {
        try {
            List<ILifeCircle> callbacks = map.get(injectedSource);
            for (ILifeCircle callback : callbacks) {
                callback.onDestroy();
            }
            callbacks.clear();
            map.remove(injectedSource);
        } catch (Exception e) {
            Log.e(e.getMessage() + "");
        }
    }

    public static void onResume(Object injectedSource) {
        try {
            List<ILifeCircle> callbacks = map.get(injectedSource);
            for (ILifeCircle callback : callbacks) {
                callback.onResume();
            }
        } catch (Exception e) {
            Log.e(e.getMessage() + "");
        }
    }

    public static void onActivityResult(Object injectedSource, int requestCode, int resultCode, Intent data) {
        try {
            List<ILifeCircle> callbacks = map.get(injectedSource);
            for (ILifeCircle callback : callbacks) {
                callback.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
            Log.e(e.getMessage() + "");
        }
    }

    public static void onPause(Object injectedSource) {
        try {
            List<ILifeCircle> callbacks = map.get(injectedSource);
            for (ILifeCircle callback : callbacks) {
                callback.onPause();
            }
        } catch (Exception e) {
            Log.e(e.getMessage() + "");
        }
    }

}
