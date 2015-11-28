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
package zilla.libcore.db.util;

import android.text.TextUtils;

import com.github.snowdream.android.util.Log;
import zilla.libcore.db.Id;
import zilla.libcore.db.Table;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * annotation util
 * <br>
 * 注释工作类
 *
 * @author ze.chen
 */
public class AnnotationUtil {

    /**
     * get @Id annotation from a model.
     * <br>
     * 获取模型中定义的主键
     *
     * @param model the pojo model
     * @return the key of the table
     */
    public static String getClassKey(Class<?> model) {
        String primaryKey = "";
        Field[] fields = model.getDeclaredFields();
        if (fields != null) {
            Id id = null;
            Field idField = null;
            for (Field field : fields) { //获取ID注解
                id = field.getAnnotation(Id.class);
                if (id != null) {
                    idField = field;
                    break;
                }
            }
            if (id != null) { //有ID注解
                primaryKey = idField.getName();
            } else {
                throw new RuntimeException("@Id annotation is not found, Please make sure the @Id annotation is added in Model!");
            }
        }
        return primaryKey;
    }

    /**
     * set keyvalue of an saved obj
     * @param obj obj
     * @param key key
     */
    public static void setKeyValue(Object obj,Object key) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null) {
            Id id = null;
            Field idField = null;
            for (Field field : fields) { //获取ID注解
                id = field.getAnnotation(Id.class);
                if (id != null) {
                    idField = field;
                    break;
                }
            }
            if (id != null) { //有ID注解
//                primaryKey = idField.getName();
                try {
                    idField.setAccessible(true);
                    idField.set(obj,key);
                } catch (IllegalAccessException e) {
                    Log.e("set key value failed:"+e.getMessage());
                }
            } else {
                throw new RuntimeException("@Id annotation is not found, Please make sure the @Id annotation is added in Model!");
            }
        }
    }

    /**
     * get tableName from a model.
     * <br>
     * 获取Class注释名称，如果没有注释名称则取类名的小写字符串
     *
     * @param model the pojo model
     * @return the table name of the model
     */
    public static String getClassName(Class<?> model) {
        Table table = model.getAnnotation(Table.class);
        if (table == null || TextUtils.isEmpty(table.value())) {
            //如果存在类名注释，使用注释名；否则使用类名小写的字符串作为表名
            String className = model.getName();
            return className.substring(className.lastIndexOf(".") + 1).toLowerCase(Locale.CHINA);
        }
        return table.value();
    }
}