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
package com.zilla.android.zillacore.libzilla.util;

import android.widget.EditText;

/**
 * Created by zilla on 14-9-10.
 */
public class UIValueUtil {

    public static String getEditTextValue(EditText editText) {
        String result = "";
        if(editText == null) return result;
        return editText.getText().toString().trim();
    }
}
