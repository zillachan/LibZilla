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

package com.zilla.libraryzilla.binding;

import android.widget.TextView;
import butterknife.InjectView;
import zilla.libcore.ui.InjectBinding;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.ZillaBinding;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.db.po.User;

@InjectLayout(R.layout.activity_binding)
public class BindingActivity extends BaseActivity {

    @InjectView(R.id.user_id)
    @InjectBinding("id")
    TextView userId;

    @InjectView(R.id.user_name)
    @InjectBinding("name")
    TextView userName;

    @InjectView(R.id.user_email)
    @InjectBinding("email")
    TextView userEmail;

    @InjectView(R.id.user_address)
    @InjectBinding("address")
    TextView userAddress;

    private User user;

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        user = new User();
        user.setId(123);
        user.setName("zilla");
        user.setEmail("zillachan@gamil.com");
        user.setAddress("beijing china");

        ZillaBinding.binding(this, user);
    }
}
