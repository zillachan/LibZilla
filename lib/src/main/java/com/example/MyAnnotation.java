package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jerry.Guan
 * @date 2016/9/24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MyAnnotation {
    String value();
}
