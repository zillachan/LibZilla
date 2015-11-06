/*
 * Copyright (c) 2015. Zilla Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zilla.libcore.module.util;

import zilla.libcore.module.ZillaLink;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zilla on 10/26/15.
 */
public class LinkUtil {

    private static List<String> cache = new ArrayList<String>();
    private static Map<String, String> map = new HashMap<String, String>();

    public static void link(Object obj) {
        String className = obj.getClass().getName();
        if (cache.contains(className)) {
            return;
        }
        cache.add(className);
        ZillaLink zillaLink = obj.getClass().getAnnotation(ZillaLink.class);
        if (zillaLink == null) return;
        String value = zillaLink.value();
        map.put(value, className);
    }

}
