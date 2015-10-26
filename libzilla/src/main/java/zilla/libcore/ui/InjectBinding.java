/*
 * Copyright (c)  2015. Softtek
 */

package zilla.libcore.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * binding a model value to a view
 * Created by zilla on 10/22/15.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectBinding {
    String value();
}
