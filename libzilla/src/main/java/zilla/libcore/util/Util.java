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
package zilla.libcore.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;
import com.github.snowdream.android.util.Log;
import zilla.libcore.Zilla;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 项目工具类<br>
 * 项目工具类,提供一些工具方法，例如网络连接是否开启，关闭输入输出流等
 *
 * @author ze.chen
 * @version 1.0
 */
public class Util {

    /**
     * Close outputStream
     *
     * @param stream stream
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                Log.e(e.getMessage());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        stream = null;
                        Log.e(e.getMessage());
                    }
                    stream = null;
                }
            }
        }
    }

    /**
     * 网络是否已经连接
     *
     * @param context context
     * @return if is connected
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }

    /**
     * Toast
     *
     * @param msg msg
     */
    public static void toastMsg(String msg) {
        Toast.makeText(Zilla.APP, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * Toast
     *
     * @param res res of mes
     */
    public static void toastMsg(int res) {
        Toast.makeText(Zilla.APP, res, Toast.LENGTH_LONG).show();
    }

    private static long lastClickTime;

    /**
     * 快速点击判断
     *
     * @return if is fast double click
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (timeD > 0 && timeD < 1000) {
            lastClickTime = time;
            return true;
        }
        return false;
    }

    /**
     * 编码URL
     *
     * @param url url
     * @return the encoded url
     */
    public static String encoderUrl(String url) {
        return URLEncoder.encode(url);
    }

    /**
     * Base64编码<br>
     * 将字符串编码成base64编码;编码标准为RFC 2045
     *
     * @param str string
     * @return base64 encoded url
     */
    public static String base64_encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String result = "";
        try {
            result = Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Base64解码<br>
     * 解码被编码的字符串
     *
     * @param base encoded string
     * @return decoded string
     */
    public static String base64_decode(String base) {
        if (TextUtils.isEmpty(base)) {
            return "";
        }
        String result = "";
        try {
            result = new String(Base64.decode(base, Base64.DEFAULT), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     *
     * @param context context
     * @return true 表示开启
     */
    public static final boolean isLocationOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }

        return false;
    }

    /**
     * 判断GPS是否开启
     *
     * @param context context
     * @return true 表示开启
     */
    public static final boolean isGPSOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 强制帮用户打开GPS
     *
     * @param context context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }
}
