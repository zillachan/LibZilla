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

package com.zilla.libraryzilla.test.db;

import android.os.Handler;
import android.os.Message;

import com.github.snowdream.android.util.Log;

import zilla.libcore.db.ZillaDB;
import zilla.libcore.lifecircle.LifeCircleInject;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.util.Util;
import zilla.libzilla.dialog.LoadingDialog;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.db.po.User;

import java.util.List;

@InjectLayout(R.layout.activity_dbtest)
public class DBTestActivity extends BaseActivity {

    @LifeCircleInject
    public LoadingDialog loadingDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    loadingDialog.dismiss();
                    Util.toastMsg("success");
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        testDB();
    }


    private void testDB() {
        loadingDialog.show("testDB...");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //Delete all rows
//                ZillaDB.getInstance().deleteAll(User.class);
//
//                //save
//                User user = new User();
//                user.setName("user1");
//                user.setEmail("user1@example.com");
//                user.setAddress("user1 address");
//                ZillaDB.getInstance().save(user);
//
//                //save list
//                List<User> userList = new ArrayList<User>();
//                for (int i = 0; i < 10; i++) {
//                    User u = new User();
//                    u.setName("name" + i);
//                    u.setEmail("name" + i + "@example.com");
//                    u.setAddress("address" + i);
//                    userList.add(u);
//                }
//
//                ZillaDB.getInstance().saveList(userList);
//
//
//                //Query
//                User user1 = ZillaDB.getInstance().query(User.class, "address = ?", new String[]{"address1"});
//                Log.i("user1:" + user1.toString());
//
//                ZillaDB.getInstance().update(user1);
//
//                //query all rows
//                List<User> users = ZillaDB.getInstance().queryAll(User.class);
//                for (User u : users) {
//                    Log.i(u.toString());
//                }
//                mHandler.sendEmptyMessage(1);
//            }
//        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                //query all rows
                Log.d("===================================READ=======================================");
                List<User> users = ZillaDB.getInstance().queryAll(User.class);
                Log.d("===================================READ_RESULT================================");
                for (User u : users) {
                    Log.i("****" + u.toString());
                }
            }
        }).start();
    }
}
