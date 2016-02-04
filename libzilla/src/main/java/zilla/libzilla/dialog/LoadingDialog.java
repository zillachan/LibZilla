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
package zilla.libzilla.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.github.snowdream.android.util.Log;

import zilla.libcore.R;
import zilla.libcore.lifecircle.ILifeCircle;

/**
 * LoadingDialog
 */
public class LoadingDialog implements ILifeCircle, IDialog {

    private Context context;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    private ProgressDialog dialogEntity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        dialogEntity = new ProgressDialog(context);
//        dialogEntity = new LoadingDialogEntity(context);
        dialogEntity.setCanceledOnTouchOutside(false);
        dialogEntity.setTitle(context.getResources().getString(R.string.dialog_loading));
    }

    @Override
    public void onResume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onDestroy() {
        dismiss();
    }

    /**
     * show dialog
     */
    @Override
    public void show() {
        try {
            if (dialogEntity != null) {
                dialogEntity.show();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    @Override
    public void show(String title) {
        try {
            if (dialogEntity != null) {
                dialogEntity.setTitle(title);
                dialogEntity.show();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    /**
     * dismiss dialog
     */
    @Override
    public void dismiss() {
        try {
            if (dialogEntity != null) {
                dialogEntity.dismiss();
            }
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

}
