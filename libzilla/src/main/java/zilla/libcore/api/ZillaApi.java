/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package zilla.libcore.api;

import zilla.libcore.file.AddressManager;
import zilla.libcore.file.PropertiesManager;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Api util
 * Created by zilla on 14-7-17.
 */
public class ZillaApi {
    /**
     * the basic RestAdapter
     */
    public static RestAdapter SimpleRestAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(AddressManager.getHost())
            .build();

    public static RestAdapter NormalRestAdapter = new RestAdapter.Builder()
            .setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(AddressManager.getHost())
            .build();

    /**
     * return a custom RestAdpater
     *
     * @param url                the custom url
     * @param requestInterceptor requestInterceptor
     * @return restAdapter
     */
    public static RestAdapter getCustomRESTAdapter(String url, RequestInterceptor requestInterceptor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(url)
                .setRequestInterceptor(requestInterceptor)
                .build();
        return setLog(restAdapter);
    }

    /**
     * return a default RESTAdapter from address.properties file
     *
     * @param requestInterceptor requestInterceptor
     * @return a default restAdapter
     */
    public static RestAdapter getRESTAdapter(RequestInterceptor requestInterceptor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(AddressManager.getHost())
                .setRequestInterceptor(requestInterceptor)
                .build();
        return setLog(restAdapter);
    }

    private static RestAdapter setLog(RestAdapter restAdapter) {
        if (Boolean.parseBoolean(PropertiesManager.get("log"))) {
            restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            restAdapter.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        return restAdapter;
    }
}
