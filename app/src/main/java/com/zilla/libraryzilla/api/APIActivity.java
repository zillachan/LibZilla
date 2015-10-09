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

package com.zilla.libraryzilla.api;

import com.github.snowdream.android.util.Log;
import com.zilla.android.zillacore.libzilla.api.ZillaApi;
import com.zilla.android.zillacore.libzilla.lifecircle.annotation.LifeCircleInject;
import com.zilla.android.zillacore.libzilla.ui.annotatioin.InjectLayout;
import com.zilla.android.zillacore.libzilla.util.Util;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.api.model.Org;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.dialog.LoadingDialog;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@InjectLayout(R.layout.activity_api)
public class APIActivity extends BaseActivity {

    @LifeCircleInject
    LoadingDialog loadingDialog;

    @Override
    protected void initViews() {
        loadingDialog.show();
    }

    @Override
    protected void initDatas() {
        GitHubService service = ZillaApi.NormalRestAdapter.create(GitHubService.class);
        service.getRepos("octokit", new Callback<List<Org>>() {
            @Override
            public void success(List<Org> orgs, Response response) {
                if (orgs != null) {
                    for (Org org : orgs) {
                        Log.i(org.toString());
                    }
                }
                Util.toastMsg("success");
                loadingDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(error.toString());
                Util.toastMsg("failed"+error.getMessage());
                loadingDialog.dismiss();
            }
        });
    }
}
