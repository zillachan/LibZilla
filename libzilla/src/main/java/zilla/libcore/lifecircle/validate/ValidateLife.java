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
//
//package zilla.libcore.lifecircle.validate;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import com.mobsandgeeks.saripaar.Rule;
//import com.mobsandgeeks.saripaar.Validator;
//import zilla.libcore.lifecircle.ILifeCircle;
//import zilla.libcore.util.ValidatorControllor;
//
///**
// * Created by Zilla on 14/12/2015.
// */
//public class ValidateLife implements ILifeCircle {
//
//    private Validator.ValidationListener validationListener;
//
//    private Validator validator;
//
//    public ValidateLife(Context validationListener) {
//        if (validationListener instanceof Validator.ValidationListener) {
//            this.validationListener = (Validator.ValidationListener) validationListener;
//        } else {
//            throw new RuntimeException("your class must implements Validator.ValidationListener");
//        }
//    }
//
//    public ValidateLife(Object validationListener) {
//        if (validationListener instanceof Validator.ValidationListener) {
//            this.validationListener = (Validator.ValidationListener) validationListener;
//        } else {
//            throw new RuntimeException("your class must implements Validator.ValidationListener");
//        }
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        this.validator = ValidatorControllor.initValidator(this.validationListener);
//    }
//
//    @Override
//    public void onResume() {
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//    }
//
//    @Override
//    public void onPause() {
//
//    }
//
//    @Override
//    public void onDestroy() {
//
//    }
//
//    /**
//     * 验证
//     */
//    public void validate() {
//        this.validator.validate();
//    }
//
//    /**
//     * 验证失败默认处理
//     *
//     * @param failedView
//     * @param failedRule
//     */
//    public void onValidationFailed(View failedView, Rule<?> failedRule) {
//        ValidatorControllor.onValidationFailed(failedView, failedRule);
//    }
//}
