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

package com.zilla.libraryzilla.test.api;

import pub.zilla.logzilla.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.common.CommonModel;
import com.zilla.libraryzilla.test.api.model.Org;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.SupportTip;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.DismissLoading;
import zilla.libcore.ui.InjectLayout;
import zilla.libcore.ui.SupportMethodLoading;

@InjectLayout(R.layout.activity_api)
public class APIActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
//        testLoading();
        getDataByAPI1();
//        testLoading("from apiactivity");
    }

    @SupportMethodLoading()
    private void testLoading(String message) {
        Log.d("testLoading:" + message);
    }

//    @SupportMethodLoading(autoDismiss = false)
    private void getDataByAPI1() {
        GitHubService service = ZillaApi.create(GitHubService.class);
        service.getRepos3("octokit", new Callback<CommonModel<List<Org>>>() {

//            @SupportTip
//            @DismissLoading
            @Override
            public void success(CommonModel<List<Org>> orgs, Response response) {
                if (orgs != null) {
                    for (Org org : orgs.getData()) {
                        Log.i(org.toString());
                    }
                }
            }

//            @SupportTip
//            @DismissLoading
            @Override
            public void failure(RetrofitError error) {
                Log.d(error.getMessage());
            }
        });
    }

//    private void getDataByAPI2() {
//        GitHubService service = ZillaApi2.create(GitHubService.class);
//        Call<List<Org>> call = service.getRepos("octokit");
//        call.enqueue(new retrofit2.Callback<List<Org>>() {
//            @Override
//            public void onResponse(Call<List<Org>> call, retrofit2.Response<List<Org>> response) {
//                if (response.isSuccessful()) {
//                    List<Org> orgList = response.body();
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Org>> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void getDataByRX() {
//        final GitHubService service = ZillaApi2.create(GitHubService.class);
//        service.getReposRx("octokit").subscribe(new Action1<List<Org>>() {
//            @Override
//            public void call(List<Org> orgs) {
//
//            }
//        });
//    }
}
