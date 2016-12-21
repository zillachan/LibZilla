package zilla.libcore.api;

import java.lang.reflect.Method;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zilla.libcore.api.annotation.Dismiss;
import zilla.libjerry.ui.CustomProgress;

/**
 * @author jerry.Guan
 * @date 2016/8/30
 */
public class CallbackProxy<T> implements Callback<T>{

    private Callback callback;
    private CustomProgress progress;

    public CallbackProxy(Callback callback,CustomProgress progress) {
        this.callback = callback;
        this.progress=progress;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response)  {

        try {
            Method method=this.callback.getClass().getMethod("onResponse",new Class[]{Call.class,Response.class});
            if(method.isAnnotationPresent(Dismiss.class)){
                if(progress!=null&&progress.isShowing()){
                    progress.dismiss();
                }
            }
            //method.invoke(callback,call,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!response.isSuccessful()){
            RetrofitAPI.dealCustomError(response);
        }else {
            this.callback.onResponse(call,response);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        try {
            Method method=this.callback.getClass().getMethod("onFailure",new Class[]{Call.class,Throwable.class});
            if(method.isAnnotationPresent(Dismiss.class)){
                if(progress!=null&&progress.isShowing()){
                    progress.dismiss();
                }
            }
            //method.invoke(callback,call,t);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        RetrofitAPI.dealNetError(t);
        this.callback.onFailure(call,t);
    }
}
