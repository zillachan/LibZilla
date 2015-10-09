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
package com.zilla.libraryzilla.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.zilla.libraryzilla.R;

/**
 * Loading Dialog Entity
 *
 * @author zilla
 * @version 1.0
 * @date 2013-10-10 下午11:28:20
 */
public class LoadingDialogEntity extends Dialog {


    private TextView titleView;
    /**
     * @param context
     */
    public LoadingDialogEntity(Context context) {
        super(context);
        init(context);
    }

    public LoadingDialogEntity(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    public LoadingDialogEntity(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    private void init(Context context) {
        setContentView(R.layout.dialog_loading);
        titleView = (TextView) findViewById(R.id.uilib_dialog_title);
    }

    /**
     * setTitle
     * @param title
     */
    public void setTitle(String title){
        titleView.setText(title);
    }
}
