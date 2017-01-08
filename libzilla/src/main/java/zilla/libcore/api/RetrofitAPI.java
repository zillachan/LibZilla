package zilla.libcore.api;

import com.github.snowdream.android.util.Log;

import java.lang.reflect.Proxy;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zilla.libcore.api.handler.DefaultApiErrorHandler;
import zilla.libcore.file.AddressManager;

/**
 * Created by Jerry.Guan on 2016/8/23.
 *
 */
public class RetrofitAPI {

    public static OkHttpClient okHttpClient;
    private static Retrofit.Builder builder =
                        new Retrofit.Builder()
                                .baseUrl(AddressManager.getHost())
                                .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit.Builder rxbuilder=
                        new Retrofit.Builder()
                                .baseUrl(AddressManager.getHost())
                                .addConverterFactory(GsonConverterFactory.create())
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    public enum Build{
        NormalService{
            @Override
            public <S> S create(Class<S> serviceClass) {
                if (okHttpClient!=null)
                    builder.client(okHttpClient);
                return builder.build().create(serviceClass);
            }

        },
        ProxyService{
            @Override
            public <S> S create(Class<S> serviceClass) {
                if (okHttpClient!=null)
                    builder.client(okHttpClient);
                S realS=builder.build().create(serviceClass);//真实角色
                ServiceProxy proxy=new ServiceProxy(realS);
                S s= (S) Proxy.newProxyInstance(proxy.getClass().getClassLoader(),realS.getClass().getInterfaces(),proxy);
                return  s;
            }
        },
        RxService{
            @Override
            public <S> S create(Class<S> serviceClass) {
                if (okHttpClient!=null)
                    builder.client(okHttpClient);
                return  rxbuilder.build().create(serviceClass);
            }
        };
        public abstract <S>S create(Class<S> serviceClass);
    }


    /**
     * 创建指定拦截器的Retrofit
     * @param url
     * @param interceptor 如果拦截器为null则创建无拦截器的Retrofit
     * @return
     */
    public static Retrofit.Builder createCustomRetroft(String url,Interceptor interceptor){
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(url);
        if(interceptor!=null){
            OkHttpClient okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(getLogger())
                    .addInterceptor(interceptor).build();
            builder.client(okHttpClient);
        }
        return builder;
    }

    private static HttpLoggingInterceptor getLogger() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(message);
            }
        });
    }

    private static IApiErrorHandler apiErrorHandler;
    public static void dealNetError(Throwable error){
        if(apiErrorHandler==null){
            apiErrorHandler=new DefaultApiErrorHandler();
        }
        apiErrorHandler.dealNetError(error);

    }
    public static void dealCustomError(Response response){
        if(apiErrorHandler==null){
            apiErrorHandler=new DefaultApiErrorHandler();
        }
        apiErrorHandler.dealCustomError(response);
    }
}
