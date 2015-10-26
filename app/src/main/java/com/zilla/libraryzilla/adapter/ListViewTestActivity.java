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

package com.zilla.libraryzilla.adapter;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import zilla.libcore.ui.ZillaAdapter;
import zilla.libcore.ui.InjectLayout;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.db.po.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zilla on 10/9/15.
 */
@InjectLayout(R.layout.activity_listviewtest)
public class ListViewTestActivity extends BaseActivity {
    ZillaAdapter<User> adapter;
    List<User> userList = new ArrayList<User>();

    @InjectView(R.id.listview)
    ListView listView;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName("User" + i);
            user.setEmail("user" + i + "@example.com");
            user.setAddress("address" + i);
            userList.add(user);
        }
        adapter = new ZillaAdapter<User>(this, userList, R.layout.user_item, ViewHolder.class);
        listView.setAdapter(adapter);
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'user_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.name)
        TextView name;
        @InjectView(R.id.email)
        TextView email;
        @InjectView(R.id.address)
        TextView address;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
