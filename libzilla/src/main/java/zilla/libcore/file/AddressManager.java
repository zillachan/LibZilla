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
package zilla.libcore.file;

import android.content.Context;
import zilla.libcore.Zilla;
import zilla.libcore.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 地址配置参数管理类<br>
 * 直接调用get方法，获取raw/address.properties中对应的value
 *
 * @author ze.chen
 * @version 1.0
 */
public class AddressManager {

    private static Properties properties;

    private static AddressManager instance = null;

    // host
    private static String host = "";

    private AddressManager(Context context) {
        InputStream is = null;
        try {
            is = Zilla.APP.getAssets().open("config/address.properties");
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Util.closeStream(is);
        }
    }

    /**
     * 实例化<br>
     * 该方法在系统启动的时候调用
     *
     * @param context context
     * @return instance
     */
    public static AddressManager getInstance(Context context) {
        if (instance == null) {
            instance = new AddressManager(context);
        }
        return instance;
    }

    /**
     * 取值<br>
     * 输入的参数为raw/address.properties中的key
     *
     * @param key the key
     * @param defaultValue defaultValue
     * @return the value of the key
     */
    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 取值<br>
     * 输入的参数为raw/address.properties中的key
     *
     * @param key the key
     * @return the value of the key
     */
    public static String get(String key) {
        return properties.getProperty(key, "");
    }

    /**
     * 获取完整的URL地址<br>
     * 输入的参数为raw/address.properties中的key
     *
     * @param key the key
     * @param defaultValue defaultValue
     * @return the value of the key
     */
    public static String getUrl(String key, String defaultValue) {
        return getHost() + get(key, defaultValue);
    }

    /**
     * 获取完整的URL地址<br>
     * 输入的参数为raw/address.properties中的key
     *
     * @param key the key
     * @return the value of the key
     */
    public static String getUrl(String key) {
        return getHost() + properties.getProperty(key, "");
    }

    /**
     * 获取主机<br>
     * 获取主机地址
     *
     * @return the host of api address
     */
    public static String getHost() {
//        if (TextUtils.isEmpty(host)) {
            host = properties.getProperty("host", "");
//        }
        return host;
    }

}
