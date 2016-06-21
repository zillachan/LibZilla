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
import android.view.View;

import com.zilla.libraryzilla.test.toolbar.CustomToolBarActivity;
import com.zilla.libraryzilla.test.validate.ValidateActivity;
import com.zilla.libraryzilla.test.zlistview.ZListViewActivity;
import zilla.libcore.ui.InjectLayout;
import com.zilla.libraryzilla.test.api.APIActivity;
import com.zilla.libraryzilla.test.binding.BindingActivity;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.db.DBTestActivity;
import com.zilla.libraryzilla.test.adapter.ListViewTestActivity;

import butterknife.OnClick;

@InjectLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.goadapter, R.id.godb, R.id.goapi, R.id.gobinding,R.id.gozlistview,R.id.govalidate,R.id.gotoolbar})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.goadapter:
                startActivity(new Intent(this, ListViewTestActivity.class));
                break;
            case R.id.godb:
                startActivity(new Intent(this, DBTestActivity.class));
                break;
            case R.id.goapi:
                startActivity(new Intent(this, APIActivity.class));
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
            default:
                break;
        }
    }
}
