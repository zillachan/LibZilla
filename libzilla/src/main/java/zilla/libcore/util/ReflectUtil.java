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
package zilla.libcore.util;

import com.github.snowdream.android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类<br>
 * 处理方法、字段等的反射
 *
 * @author ze.chen
 * @version 1.0
 */
public class ReflectUtil {

    public static Object invokeMethod(String className, String methodName, Class[] properties, Object... values) {
        try {
            Class<?> c = Class.forName(className);
            Method method = c.getMethod(methodName, properties);
            return method.invoke(c, values);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射属性
     *
     * @param obj       obj
     * @param fieldName field
     * @return the value of the fieldName
     */
    public static Object getObj(Object obj, String fieldName) {
        Object result = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            result = field.get(obj);
        } catch (NoSuchFieldException e) {
            Log.e(e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(e.getMessage());
        }
        return result;
    }

    /**
     * 一个参数的构造函数
     *@param c  class
     * @param obj obj
     * @return the instance of c with param obj
     */
    public static Object getConstructorInstance(Class c, Object obj) {
        try {
            Constructor ctor = c.getDeclaredConstructor(obj.getClass());
            return ctor.newInstance(obj);
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取ben的所有私有属性值
     *
     * @param obj obj
     * @return field list
     */
    public static String[] getFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String[] results = new String[fields.length];
        for (int i = 0, l = fields.length; i < l; i++) {
            results[i] = fields[i].getName();
        }
        return results;
    }

    /**
     * 设置私有字段的值
     * @param obj boject
     * @param key fieldName
     * @param value the value to be set
     */
    public static void setFieldValue(Object obj, String key, Object value) {
        Field field = null;
        try {
            field = obj.getClass().getDeclaredField(key);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (NoSuchFieldException e) {
            Log.e(e.getMessage());
        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
            if (field != null)
                Log.e("IllegalArgumentException--" + "field:" + field.getName() + "-value:" + field.getType());
        } catch (IllegalAccessException e) {
            Log.e(e.getMessage());
        }
    }

    /**
     * 返回对象属性的值
     * @param  obj obj
     * @param  key field
     * @return the value of field
     */
    public static Object getFieldValue(Object obj, String key) {
        Field field = null;
        Object result = null;
        try {
            field = obj.getClass().getDeclaredField(key);
            field.setAccessible(true);
            result = field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
