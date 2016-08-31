package zilla.libcore.api;

import com.github.snowdream.android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zilla.libcore.api.annotation.Dismiss;
import zilla.libcore.api.eventModel.EventModel;

/**
 * @author jerry.Guan
 * @date 2016/8/30
 */
public class CallbackProxy<T> implements Callback<T>{

    private Callback callback;

    public CallbackProxy(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response)  {
        try {
            Method method=this.callback.getClass().getMethod("onResponse",new Class[]{Call.class,Response.class});
            if(method.isAnnotationPresent(Dismiss.class)){
                EventBus.getDefault().post(new EventModel());
            }
            //method.invoke(callback,call,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.callback.onResponse(call,response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        try {
            Method method=this.callback.getClass().getMethod("onFailure",new Class[]{Call.class,Throwable.class});
            if(method.isAnnotationPresent(Dismiss.class)){
                EventBus.getDefault().post(new EventModel());
            }
            //method.invoke(callback,call,t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        this.callback.onFailure(call,t);
    }
}
