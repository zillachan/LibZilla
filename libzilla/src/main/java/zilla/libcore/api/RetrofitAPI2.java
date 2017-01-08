package zilla.libcore.api;

import java.lang.reflect.Proxy;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import zilla.libcore.api.handler.DefaultApiErrorHandler;
import zilla.libcore.file.AddressManager;

/**
 * @author jerry.Guan
 *         created by 2017/1/8
 */

public enum RetrofitAPI2 {

    NormalBuild{
        @Override
        public <S> S createService(Class<S> serviceClass) {
            if (okHttpClient!=null)
                builder.client(okHttpClient);
            return builder.baseUrl(getUrl()).build().create(serviceClass);
        }
    },
    ProxyBuild{
        @Override
        public <S> S createService(Class<S> serviceClass) {
            if (okHttpClient!=null)
                builder.client(okHttpClient);
            S realS=builder.baseUrl(getUrl()).build().create(serviceClass);//真实角色
            ServiceProxy proxy=new ServiceProxy(realS);
            S s= (S) Proxy.newProxyInstance(proxy.getClass().getClassLoader(),realS.getClass().getInterfaces(),proxy);
            return  s;
        }
    },
    RxBuild{
        @Override
        public <S> S createService(Class<S> serviceClass) {
            if (okHttpClient!=null)
                builder.baseUrl(getUrl()).client(okHttpClient);
            return  rxbuilder.build().create(serviceClass);
        }
    },
    CustomBuild{
        @Override
        public <S> S createService(Class<S> serviceClass) {
            return null;
        }
    };

    private String url;

    RetrofitAPI2(String url){
        this.url=url;
    }
    RetrofitAPI2(){
        this(AddressManager.getHost());
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public static OkHttpClient okHttpClient;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit.Builder rxbuilder=
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

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

    public abstract <S>S createService(Class<S> serviceClass);
}
