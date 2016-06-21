package com.zilla.libraryzilla.test.toolbar;

import android.view.MenuItem;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;

import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;

@InjectLayout(value = R.layout.activity_custom_tool_bar, menu = R.menu.custom_toolbar_menu)
public class CustomToolBarActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onMenuClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Util.toastMsg(R.string.action_settings);
                break;
            default:
                break;
        }
        super.onMenuClick(item);
    }
}
