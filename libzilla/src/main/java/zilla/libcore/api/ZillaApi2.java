///*
//Copyright 2015 Zilla Chen
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
// */
//package zilla.libcore.api;
//
//import android.content.Context;
//import pub.zilla.logzilla.Log;
//
//import okhttp3.Interceptor;
//import okhttp3.OkHttpClient;
//import okhttp3.logging.HttpLoggingInterceptor;
//import retrofit.RetrofitError;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//import zilla.libcore.api.handler.DefaultApiErrorHandler;
//import zilla.libcore.file.AddressManager;
//import zilla.libcore.file.PropertiesManager;
//
///**
// * Api util
// * Created by zilla on 14-7-17.
// */
//public class ZillaApi2 {
//    /**
//     * the basic Retrofit
//     */
//    public static Retrofit simpleRetrofit = new Retrofit.Builder()
//            .baseUrl(AddressManager.getHost())
//            .build();
//
//    public static Retrofit retrofit;
//
//    public static <T> T create(Class<T> service) {
//        if (retrofit == null) {
//            boolean isLog = Boolean.parseBoolean(PropertiesManager.get("log"));
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(getLogger())
//                    .build();
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(AddressManager.getHost())
//                    .client(client)
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit.create(service);
//    }
//
//    /**
//     * return a custom RestAdpater
//     * <p>
//     * new Interceptor() {
//     * *@Override
//     * public Response intercept(Chain chain) throws IOException {
//     * Request newRequest = chain.request().newBuilder()
//     * .addHeader("platform", "android")
//     * .addHeader("appVersion", BuildConfig.VERSION_NAME)
//     * .build();
//     * return chain.proceed(newRequest);
//     * }
//     *
//     * @param url         the custom url
//     * @param interceptor interceptor
//     * @return retrofit
//     */
//    public static Retrofit getCustomRetrofit(String url, Interceptor interceptor) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(getLogger())
//                .addInterceptor(interceptor)
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .client(client)
//                .baseUrl(url)
//                .client(client)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        return retrofit;
//    }
//
//    /**
//     * return a default RESTAdapter from address.properties file
//     *
//     * @param interceptor interceptor
//     * @return a default retrofit
//     */
//    public static Retrofit getRESTRetrofit(Interceptor interceptor) {
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(getLogger())
//                .addInterceptor(interceptor)
//                .build();
//
//        retrofit = new Retrofit.Builder()
//                .client(new OkHttpClient.Builder().addInterceptor(getLogger()).build())
//                .baseUrl(AddressManager.getHost())
//                .client(client)
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        return retrofit;
//    }
//
//    private static IApiErrorHandler mIApiErrorHandler = null;
//
//    public static void setmIApiErrorHandler(IApiErrorHandler mIApiErrorHandler) {
//        ZillaApi2.mIApiErrorHandler = mIApiErrorHandler;
//    }
//
//    private static HttpLoggingInterceptor getLogger() {
//        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//                Log.d(message);
//            }
//        });
//    }
//
//    /**
//     * deal custom error
//     *
//     * @param context
//     * @param object
//     * @return
//     */
//    public static boolean dealCustomError(Context context, IApiModel object) {
//        if (mIApiErrorHandler == null) {
//            mIApiErrorHandler = new DefaultApiErrorHandler();
//        }
//        return mIApiErrorHandler.dealCustomError(context, object);
//    }
//
//    /**
//     * deal net error
//     *
//     * @param error
//     */
//    public static void dealNetError(RetrofitError error) {
//        if (mIApiErrorHandler == null) {
//            mIApiErrorHandler = new DefaultApiErrorHandler();
//        }
//        mIApiErrorHandler.dealNetError(error);
//    }
//
//}
