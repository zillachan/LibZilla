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
//package zilla.libcore.util;
//
//import android.text.Html;
//import android.view.View;
//import android.widget.EditText;
//import com.mobsandgeeks.saripaar.Rule;
//import com.mobsandgeeks.saripaar.Validator;
//
///**
// * Created by chenze on 13-12-26.
// */
//public class ValidatorControllor {
//
//    public static Validator initValidator(Validator.ValidationListener listener) {
//        Validator validator = new Validator(listener);
//        validator.setValidationListener(listener);
//        return validator;
//    }
//
//    public static void onValidationFailed(View failedView, Rule<?> failedRule) {
//        String message = failedRule.getFailureMessage();
//        if (failedView instanceof EditText) {
//            failedView.requestFocus();
////            ((EditText) failedView).setError(Html.fromHtml("<font color=#808183>" + message + "</font>"));
//            ((EditText) failedView).setError(Html.fromHtml("<font color=#FFFFFF>" + message + "</font>"));
//        }
//    }
//}
