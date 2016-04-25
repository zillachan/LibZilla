package zilla.libcore.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:Loading support
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-04-01
 */
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD })
public @interface SupportLoading {
    /**
     * dialog auto dismiss when method finish
     *
     * @return
     */

    boolean autoDismiss() default false;
}