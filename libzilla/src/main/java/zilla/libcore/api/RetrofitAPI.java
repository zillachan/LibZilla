package zilla.libcore.api;

import android.content.Context;

import java.lang.reflect.Proxy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zilla.libcore.api.handler.DefaultApiErrorHandler;
import zilla.libcore.file.AddressManager;

/**
 * Created by John on 2016/8/23.
 */
public class RetrofitAPI {

    public static OkHttpClient okHttpClient;
    private static Retrofit.Builder builder =
                        new Retrofit.Builder()
                                .baseUrl(AddressManager.getHost())
                                .addConverterFactory(GsonConverterFactory.create());

    /**
     * 创建请求对象
     * @param serviceClass
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass) {
            if (okHttpClient!=null)
                builder.client(okHttpClient);
        S realS=builder.build().create(serviceClass);//真实角色
        ServiceProxy proxy=new ServiceProxy(realS);
        S s= (S) Proxy.newProxyInstance(proxy.getClass().getClassLoader(),realS.getClass().getInterfaces(),proxy);
        return  s;
    }

    /**
     * 创建指定拦截器的Service
     * @param serviceClass
     * @param interceptor 如果拦截器为null则创建普通的Service
     * @param <S>
     * @return
     */
    public static <S>S createCustomService(Class<S> serviceClass,Interceptor interceptor){
        if(interceptor!=null){
            okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(getLogger())
                    .addInterceptor(interceptor).build();
        }else{
            okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(getLogger()).build();
        }
        builder.client(okHttpClient);
        return builder.build().create(serviceClass);
    }

    public static Retrofit retrofit() {
        return builder.build();
    }

    private static HttpLoggingInterceptor getLogger() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //Log.d(message);
            }
        });
    }

    private static IApiErrorHandler apiErrorHandler;
    private static void dealNetError(Throwable error){
        if(apiErrorHandler==null){
            apiErrorHandler=new DefaultApiErrorHandler();
        }
        apiErrorHandler.dealNetError(error);

    }
}
