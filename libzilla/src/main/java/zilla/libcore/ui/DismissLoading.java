package zilla.libcore.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:DismissLoading Loading dialog
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-04-01
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface DismissLoading {
}