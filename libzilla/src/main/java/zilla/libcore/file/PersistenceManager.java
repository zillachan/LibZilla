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
package zilla.libcore.file;

import com.github.snowdream.android.util.Log;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * 对象持久化工具类<br>
 * 处理对象的持久化和读取
 *
 * @author ze.chen
 * @version 1.0
 */
public class PersistenceManager {

    private static HashMap<String, WeakReference<Serializable>> cache = new HashMap<>();

    /**
     * 读取对象
     *
     * @param name
     * @return
     */
    public static Serializable readObj(String name) {
//        if(cache.get(name) == null) return null;
        WeakReference<Serializable> o = cache.get(name);
        if (o == null || o.get() == null) {
            Object tempfileObj = FileHelper.readObj(FileHelper.PATH_FILES + name + ".obj");
            if (tempfileObj == null) return null;
            Serializable serializable = (Serializable) tempfileObj;
            cache.put(name, new WeakReference<Serializable>(serializable));
            return serializable;
        }
        return cache.get(name).get();
    }

    /**
     * 读取对象
     *
     * @param c
     * @return
     */
    public static Serializable readObj(Class c) {

        return readObj(c.getSimpleName());
    }

    /**
     * 保存对象
     *
     * @param obj
     * @return
     */
    public static boolean saveObj(Serializable obj) {
        return saveObj(obj.getClass().getSimpleName(), obj);
    }

    /**
     * save obj
     *
     * @param key
     * @param obj
     * @return
     */
    public static boolean saveObj(String key, Serializable obj) {
        cache.put(key, new WeakReference<Serializable>(obj));
        return FileHelper.saveObj(obj, FileHelper.PATH_FILES + key + ".obj");
    }

    /**
     * clear the key cache
     *
     * @param key
     */
    public static void clearKeyCache(String key) {
        cache.put(key, null);
    }

    /**
     * persistence a obj that in memory
     *
     * @param key
     * @return
     */
    public static boolean persistence(String key) {
        try {
            Serializable serializable = readObj(key);
            saveObj(serializable);
            return true;
        } catch (Exception e) {
            Log.e(e.getMessage());
            return false;
        }
    }

    /**
     * clear cache
     */
    public static void clearCache() {
        cache.clear();
    }
}
