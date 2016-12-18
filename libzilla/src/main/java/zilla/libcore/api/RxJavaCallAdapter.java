package zilla.libcore.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author jerry.Guan
 *         created by 2016/12/5
 */

public class RxJavaCallAdapter extends CallAdapter.Factory{

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType=getRawType(returnType);
        return null;
    }
}
