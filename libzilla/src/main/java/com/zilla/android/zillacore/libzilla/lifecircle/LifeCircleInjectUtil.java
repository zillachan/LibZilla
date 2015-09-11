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
//package com.zilla.android.zillacore.libzilla.lifecircle;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import com.github.snowdream.android.util.Log;
//import com.zilla.android.zillacore.libzilla.lifecircle.annotation.LifeCircleInject;
//import javassist.*;
//
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
///**
// * 解析injectedSource中的注入字段
// * User: chenze
// * Date: 13-10-11
// * Time: 上午11:29
// */
//public class LifeCircleInjectUtil {
//
//    static ClassPool pool = ClassPool.getDefault();
//    /**
//     * ILifeCircle列表
//     */
//    static List<ILifeCircle> callbacks = Collections.synchronizedList(new ArrayList<ILifeCircle>());
//
//    Class cls;
//
//    /**
//     * 载体对象
//     *
//     * @param injectedSource 父容器(Activity/Fragment)
//     */
//    public static void inject(Object injectedSource) {
//        Field[] fields = injectedSource.getClass().getDeclaredFields();
//        Class containerClass = injectedSource.getClass();
//        if (fields != null && fields.length > 0) {
//            for (Field field : fields) {
//                LifeCircleInject callbackInject = field.getAnnotation(LifeCircleInject.class);
//                if (callbackInject != null) {
//                    Class type = field.getType();
//                    try {
//                        Constructor constructor;
//                        if (injectedSource instanceof Context) {
//                            constructor = type.getConstructor(Context.class);
//                        } else if (injectedSource instanceof Fragment) {
//                            constructor = type.getConstructor(Fragment.class);
//                        } else {
//                            constructor = type.getConstructor(containerClass);
//                        }
//                        field.setAccessible(true);
//                        field.set(injectedSource, constructor.newInstance(injectedSource));//构造统一采用当前Activity
//                        callbacks.add((ILifeCircle) field.get(injectedSource));
//                    } catch (Exception e) {
//                        Log.e(e.getMessage());
//                    }
//                }
//            }
//        }
////        if (!callbacks.isEmpty()) {
////            //如果是activity
////            CtClass cc = null;
////            try {
////                cc = pool.get(containerClass.getName());
////            } catch (NotFoundException e) {
////                e.printStackTrace();
////            }
////            if (cc != null) {
////                if (injectedSource instanceof Context) {
////                    try {
//////                        Method pauseMethod = containerClass.getDeclaredMethod("onPause");
////                        CtMethod cm = cc.getDeclaredMethod("onPause");
////                        cc.writeFile();
////                        cm.insertAfter("for (ILifeCircle callback : callbacks) {\n" +
////                                "                callback.onCreate(savedInstanceState);\n" +
////                                "            }");
////                    } catch (NotFoundException e) {
////                        e.printStackTrace();
////                    } catch (CannotCompileException e) {
////                        e.printStackTrace();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                } else if (injectedSource instanceof Fragment) {
////
////                }
////            }
////
////        }
//        callbacks.clear();
//    }
//
//    /**
//     * Actiivty生命周期注解
//     */
//    class ProxyHandler extends Activity {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            for (ILifeCircle callback : callbacks) {
//                callback.onCreate(savedInstanceState);
//            }
//        }
//
//        @Override
//        public void onResume() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onResume();
//            }
//        }
//
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            for (ILifeCircle callback : callbacks) {
//                callback.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//
//        @Override
//        public void onPause() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onPause();
//            }
//        }
//
//        @Override
//        public void onDestroy() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onDestroy();
//            }
//        }
//    }
//
//    /**
//     * Framgment生命周期注解
//     */
//    class ProxyFragmentHandler extends Fragment {
//        @Override
//        public void onCreate(Bundle savedInstanceState) {
//            for (ILifeCircle callback : callbacks) {
//                callback.onCreate(savedInstanceState);
//            }
//        }
//
//        @Override
//        public void onResume() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onResume();
//            }
//        }
//
//        @Override
//        public void onActivityResult(int requestCode, int resultCode, Intent data) {
//            for (ILifeCircle callback : callbacks) {
//                callback.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//
//        @Override
//        public void onPause() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onPause();
//            }
//        }
//
//        @Override
//        public void onDestroy() {
//            for (ILifeCircle callback : callbacks) {
//                callback.onDestroy();
//            }
//        }
//    }
//
//}
