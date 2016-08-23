///*
// * Copyright (c) 2015. Zilla Chen
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package zilla.libcore.api;
//
//import java.lang.reflect.Method;
//
//import retrofit.Callback;
//import retrofit.RetrofitError;
//import retrofit.client.Response;
//import zilla.libcore.Zilla;
//import zilla.libzilla.dialog.IDialog;
//
///**
// * Callback Proxy to deal some other things
// * Created by Zilla on 20/4/16.
// */
//public class CallbackProxy<T> implements Callback<IApiModel> {
//
//    private Callback<IApiModel> callback;
//    private IDialog loadingDialog;
//
//    public CallbackProxy(Callback<IApiModel> callback) {
//        this.callback = callback;
//    }
//
//    public CallbackProxy(Callback<IApiModel> callback, IDialog loadingDialog) {
//        this.callback = callback;
//        this.loadingDialog = loadingDialog;
//        loadingDialog.show();
//    }
//
//    @Override
//    public void success(IApiModel t, Response response) {
//        if (loadingDialog != null) {
//            loadingDialog.dismiss();
//        }
//        if (NetErrorAnnotationUtil.isCustomTipSupport(this.callback)) {
//            ZillaApi.dealCustomError(Zilla.ACTIVITY, t);
//        }
//        this.callback.success(t, response);
//    }
//
//    @Override
//    public void failure(RetrofitError error) {
//        if (loadingDialog != null) {
//            loadingDialog.dismiss();
//        }
//        if (NetErrorAnnotationUtil.isFailureTipSupport(this.callback)) {
//            ZillaApi.dealNetError(error);
//        }
//        this.callback.failure(error);
//    }
//
//    /**
//     * NetErrorAnnotation tools
//     */
//    static class NetErrorAnnotationUtil {
//        static boolean isFailureTipSupport(Callback<IApiModel> callback) {
//            try {
//                Method method = callback.getClass().getMethod("success", IApiModel.class, Response.class);
//                SupportTip supportTip = method.getAnnotation(SupportTip.class);
//                if (supportTip != null) {
//                    return true;
//                }
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//
//        static boolean isCustomTipSupport(Callback<IApiModel> callback) {
//            try {
//                Method method = callback.getClass().getMethod("failure", RetrofitError.class);
//                SupportTip supportTip = method.getAnnotation(SupportTip.class);
//                if (supportTip != null) {
//                    return true;
//                }
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            return false;
//        }
//    }
//}
