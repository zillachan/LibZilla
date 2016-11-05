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

package com.zilla.libraryzilla.test.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.db.po.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import zilla.libcore.ui.InjectLayout;
import zilla.libjerry.adapter.EasyAdapter;
import zilla.libjerry.adapter.ViewHolder;

/**
 * Created by jerry on 11/4/16.
 */
@InjectLayout(R.layout.activity_listviewtest)
public class ListViewTestActivity2 extends BaseActivity {
    private EasyAdapter adapter;
    List<User> userList = new ArrayList<User>();

    @Bind(R.id.listview)
    ListView listView;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        for (int i = 0; i <20; i++) {
            User user = new User();
            user.setName("User" + i);
            user.setEmail("user" + i + "@example.com");
            user.setAddress("address" + i);
            userList.add(user);
        }
        adapter=new EasyAdapter<User>(this,userList,R.layout.user_item) {
            @Override
            public void convert(ViewHolder holder, final User data, int position) {
                TextView tv_name= holder.getView(R.id.name);
                tv_name.setText(data.getName());
                TextView tv_email= holder.getView(R.id.email);
                tv_email.setText(data.getEmail());
                TextView tv_address= holder.getView(R.id.address);
                tv_address.setText(data.getAddress());
                Button btn= holder.getView(R.id.button);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("user="+data);
                    }
                });
            }
        };
        listView.setAdapter(adapter);
    }
}
