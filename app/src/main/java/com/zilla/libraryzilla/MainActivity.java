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
package com.zilla.libraryzilla;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.activitytransition.TransitionActivity;
import com.zilla.libraryzilla.test.adapter.ListViewTestActivity;
import com.zilla.libraryzilla.test.adapter.ListViewTestActivity2;
import com.zilla.libraryzilla.test.api.APIActivity;
import com.zilla.libraryzilla.test.api.APIActivity2;
import com.zilla.libraryzilla.test.binding.BindingActivity;
import com.zilla.libraryzilla.test.db.DBTestActivity;
import com.zilla.libraryzilla.test.gallery.GalleryActivity;
import com.zilla.libraryzilla.test.permission.PermissionActivity;
import com.zilla.libraryzilla.test.selectorphoto.SelectorPhotoActivity;
import com.zilla.libraryzilla.test.toolbar.CustomToolBarActivity;
import com.zilla.libraryzilla.test.validate.ValidateActivity;
import com.zilla.libraryzilla.test.zlistview.ZListViewActivity;

import butterknife.BindView;
import butterknife.OnClick;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }


    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.permission,R.id.shareele,R.id.gophoto,R.id.goadapter, R.id.godb, R.id.goapi, R.id.gobinding,R.id.gozlistview,R.id.govalidate,R.id.gotoolbar})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.goadapter:
//                startActivity(new Intent(this, ListViewTestActivity2.class));
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.godb:
                startActivity(new Intent(this, DBTestActivity.class));
                break;
            case R.id.goapi:
                startActivity(new Intent(this, APIActivity2.class));
                break;
            case R.id.gobinding:
                startActivity(new Intent(this, BindingActivity.class));
                break;
            case R.id.gozlistview:
                startActivity(new Intent(this, ZListViewActivity.class));
                break;
            case R.id.govalidate:
                startActivity(new Intent(this, ValidateActivity.class));
                break;
            case R.id.gotoolbar:
                startActivity(new Intent(this, CustomToolBarActivity.class));
                break;
            case R.id.gophoto:
                startActivity(new Intent(this, SelectorPhotoActivity.class));
                break;
            case R.id.shareele:
                startActivity(new Intent(this, TransitionActivity.class));
                break;
            case R.id.permission:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
            default:
                break;
        }
    }
}
