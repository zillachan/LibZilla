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

import com.ggx.libjerry.imagefileselector.ImageFileCropSelector;
import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.test.toolbar.CustomToolBarActivity;
import com.zilla.libraryzilla.test.validate.ValidateActivity;
import com.zilla.libraryzilla.test.zlistview.ZListViewActivity;
import zilla.libcore.ui.InjectLayout;
import com.zilla.libraryzilla.test.api.APIActivity;
import com.zilla.libraryzilla.test.binding.BindingActivity;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.db.DBTestActivity;

import butterknife.OnClick;

@InjectLayout(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    ImageFileCropSelector selector;
    @Override
    protected void initViews() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ImageFileCropSelector selector=new ImageFileCropSelector(this);
        selector.setQuality(80);//图片的压缩质量
        selector.setOutPutImageSize(100,100);//图片压缩后的输出大小
        selector.setOpenCrop(this);//开启裁减
        selector.setCropOutWH(100,100);//设置裁减的最大宽高
        selector.setAspectXY(1,1);//设置裁减框的比例

        selector.setCallback(new ImageFileCropSelector.Callback() {
            @Override
            public void onSuccess(String file) {
                Log.i("图片返回成功"+file);
            }

            @Override
            public void onError() {
                Log.i("图片返回失败");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selector.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        selector.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void initDatas() {

    }

    @OnClick({R.id.goadapter, R.id.godb, R.id.goapi, R.id.gobinding,R.id.gozlistview,R.id.govalidate,R.id.gotoolbar})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.goadapter:
                //startActivity(new Intent(this, ListViewTestActivity.class));
                selector.takePhoto(this);
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
