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
package zilla.libcore.ui;

import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libzilla.dialog.IDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Description:
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-04-21
 */
public class SupportInjectUtil {

    public static void supportLoading(Object container) {

        boolean containLoading = false;
        Field[] fields = container.getClass().getFields();
        for (Field field : fields) {
            if (field.getAnnotation(LifeCircleInject.class) != null) {
                if (field.getType() == IDialog.class) {
                    containLoading = true;
                }
            }
        }
        Method[] methods = container.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getAnnotation(SupportLoading.class) != null) {
                method.setAccessible(true);
            }
        }
    }
}
