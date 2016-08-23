package zilla.libcore.db.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;

import zilla.libcore.db.exception.NoDBOperationAnnotationException;
import zilla.libcore.db.operate.Delete;
import zilla.libcore.db.operate.Save;
import zilla.libcore.db.operate.Select;
import zilla.libcore.db.operate.Update;

/**
 * Created by Zilla on 22/8/2016.
 */
public class DBHandler<T> implements InvocationHandler {

    /**
     * 方法缓存
     */
    static LinkedHashMap<String, Annotation[]> sMethodAnnotations = new LinkedHashMap<>();

    private Class dao;

    public DBHandler() {

    }

    public DBHandler(Class<?> c) {
        this.dao = c;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //TODO 参数解析
        Annotation[] annotations = sMethodAnnotations.get(method.getName());

        if (annotations == null) {
            annotations = method.getAnnotations();
            sMethodAnnotations.put(method.getName(), annotations);
        }
        // if has no operation annotated
        if (annotations == null) {
            throw new NoDBOperationAnnotationException();
        }
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> temp = annotation.annotationType();
            if (Save.class == temp) {
//                ZillaDB.getInstance().save()

            } else if (Select.class == temp) {

            } else if (Update.class == temp) {

            } else if (Delete.class == temp) {

            }
        }

        return method.invoke(proxy, args);
    }
}
