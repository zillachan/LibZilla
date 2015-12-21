/*
 * Copyright (c) 2015. Zilla Chen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zilla.libcore.api.handler;

import android.content.Context;
import com.github.snowdream.android.util.Log;
import retrofit.RetrofitError;
import zilla.libcore.R;
import zilla.libcore.api.IApiError;
import zilla.libcore.api.IApiErrorHandler;
import zilla.libcore.util.Util;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Default error handler
 * Created by zilla on 14/12/17.
 */
public class DefaultApiErrorHandler implements IApiErrorHandler {

    @Override
    public boolean dealCustomError(Context context, IApiError object) {
        boolean isCustomError = true;
        try {
            int errorCode = object.getErrorCode();
            switch (errorCode) {
                case 1:
                    break;
                default:
                    break;
            }
            Util.toastMsg("" + object.getErrorMessage());
            Log.e(object.toString());
        } catch (Exception e) {
            Log.e(e.getMessage());
            Util.toastMsg("" + object.toString());
        }
        return false;
    }

    @Override
    public void dealNetError(RetrofitError error) {
        switch (error.getKind()) {
            case NETWORK:
                Throwable throwable = error.getCause();
                if (throwable instanceof SocketTimeoutException) {
                    Util.toastMsg(R.string.net_error_timeout);
                } else if (throwable instanceof UnknownHostException) {
                    Util.toastMsg(R.string.net_error_unknownhost);
                } else if (throwable instanceof InterruptedIOException) {
                    Util.toastMsg(R.string.net_error_timeout);
                } else {
                    Util.toastMsg(R.string.net_error_neterror);
                }
                break;
            case HTTP:
                int statusCode = error.getResponse().getStatus();
                switch (statusCode) {
                    case 401:
                        Util.toastMsg(R.string.net_http_401);
                        break;
                    case 403:
                        Util.toastMsg(R.string.net_http_403);
                        break;
                    case 404:
                        Util.toastMsg(R.string.net_http_404);
                        break;
                    case 500:
                        Util.toastMsg(R.string.net_http_500);
                        break;
                    case 502:
                        Util.toastMsg(R.string.net_http_502);
                        break;
                    default:
                        Util.toastMsg(R.string.net_http_other);
                        break;
                }
                break;
            case CONVERSION:
                Util.toastMsg(R.string.net_parse_error);
                Log.e("Parse error", error);
                break;
            case UNEXPECTED:
                Util.toastMsg(R.string.net_other);
                Log.e("UnKnuown error", error);
                break;
            default:
                break;
        }
    }
}
