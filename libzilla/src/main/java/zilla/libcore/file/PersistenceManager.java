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
        if(cache.get(name) == null) return null;
        Object obj = cache.get(name).get();
        if (obj == null) {
            obj = FileHelper.readObj(FileHelper.PATH_FILES + name + ".obj");
            if (obj == null) {
                return null;
            }
        }
        return (Serializable) obj;
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
        return saveObj(obj, obj.getClass().getSimpleName());
    }

    /**
     * save obj
     * @param obj
     * @param name
     * @return
     */
    public static boolean saveObj(Serializable obj, String name) {
        cache.put(name, new WeakReference<Serializable>(obj));
        return FileHelper.saveObj(obj, FileHelper.PATH_FILES + name + ".obj");
    }
}
