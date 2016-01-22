package com.zilla.libraryzilla.test.net;

import android.content.Context;

import com.github.snowdream.android.util.Log;

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit.RetrofitError;
import zilla.libcore.api.IApiError;
import zilla.libcore.api.IApiErrorHandler;
import zilla.libcore.util.Util;

/**
 * Created by Zilla on 22/1/16.
 */
public class NetErrorHandler implements IApiErrorHandler {

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
                    Util.toastMsg(zilla.libcore.R.string.net_error_timeout);
                } else if (throwable instanceof UnknownHostException) {
                    Util.toastMsg(zilla.libcore.R.string.net_error_unknownhost);
                } else if (throwable instanceof InterruptedIOException) {
                    Util.toastMsg(zilla.libcore.R.string.net_error_timeout);
                } else {
                    Util.toastMsg(zilla.libcore.R.string.net_error_neterror);
                }
                break;
            case HTTP:
                int statusCode = error.getResponse().getStatus();
                switch (statusCode) {
                    case 401:
                        Util.toastMsg(zilla.libcore.R.string.net_http_401);
                        break;
                    case 403:
                        Util.toastMsg(zilla.libcore.R.string.net_http_403);
                        break;
                    case 404:
                        Util.toastMsg(zilla.libcore.R.string.net_http_404);
                        break;
                    case 500:
                        Util.toastMsg(zilla.libcore.R.string.net_http_500);
                        break;
                    case 502:
                        Util.toastMsg(zilla.libcore.R.string.net_http_502);
                        break;
                    default:
                        Util.toastMsg(zilla.libcore.R.string.net_http_other);
                        break;
                }
                break;
            case CONVERSION:
                Util.toastMsg(zilla.libcore.R.string.net_parse_error);
                Log.e("Parse error", error);
                break;
            case UNEXPECTED:
                Util.toastMsg(zilla.libcore.R.string.net_other);
                Log.e("UnKnuown error", error);
                break;
            default:
                break;
        }
    }
}
