package zilla.libcore.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:Error Tip Annotation
 *
 * @author Zilla
 * @version 1.0
 * @date 2016-04-01
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface SupportTip {
    /**
     * default loading message
     *
     * @return
     */
    String value() default "";
}
