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
package com.zilla.android.zillacore.libzilla.util;

import com.squareup.otto.Bus;

/**
 * 总线,用于应用消息的分发，类似于广播
 * 用法：用于Fragment、Activity，在onResume时调用register；onPause时调用unregister
 * BusProvider.getInstance().register(this);
 * BusProvider.getInstance().unregister(this);
 *
 * //@Produce消息生产者,方法的注释，返回事件对象
 * //@Subscribe消息消费者，方法注释，接受了该注释的方法在有事件产生时会被调用 Created by chenze on 13-12-1.
 */
public class BusProvider {

    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }
}
