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

import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;
import zilla.libcore.R;
import zilla.libcore.api.IApiErrorHandler;
import zilla.libcore.util.Util;

/**
 * Default error handler
 * Created by zilla on 14/12/17.
 */
    public class DefaultApiErrorHandler extends IApiErrorHandler {


    @Override
    protected void dealCustomError(Response error) {

    }

    @Override
    protected void dealNetError(Throwable error) {
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
    }
}
