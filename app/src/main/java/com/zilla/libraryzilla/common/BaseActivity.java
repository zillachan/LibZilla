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
package com.zilla.libraryzilla.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zilla.libraryzilla.R;

import butterknife.ButterKnife;
import zilla.libcore.Zilla;
import zilla.libcore.lifecircle.LifeCircle;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.lifecircle.exit.AppExitLife;
import zilla.libcore.ui.LayoutInjectUtil;
import zilla.libzilla.dialog.LoadingDialog;

/**
 * Created by zilla on 14/12/1.
 */
public abstract class BaseActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    /**
     * 生命周期管理
     */
    @LifeCircleInject
    public AppExitLife lifeCicleExit;

    @LifeCircleInject
    public LoadingDialog loadingDialog;
    /**
     * Toobar
     */
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInjectUtil.getInjectLayoutId(this));
        LifeCircle.onCreate(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if(mToolbar!=null){
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setOnMenuItemClickListener(this);
        }
        ButterKnife.bind(this);
        initViews();
        initDatas();
    }

    public void onResume() {
        super.onResume();
        Zilla.ACTIVITY = this;
        LifeCircle.onResume(this);
    }

    public void onPause() {
        super.onPause();
        LifeCircle.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LifeCircle.onDestory(this);
        ButterKnife.unbind(this);
    }

    protected abstract void initViews();

    protected abstract void initDatas();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onMenuClick(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        onMenuClick(menuItem);
        return false;
    }

    /**
     * 如果使用菜单栏,重写该方法
     *
     * @param item
     */
    public void onMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = LayoutInjectUtil.getInjectMenuId(this);
        if (menuId != 0) {
            getMenuInflater().inflate(menuId, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
}
