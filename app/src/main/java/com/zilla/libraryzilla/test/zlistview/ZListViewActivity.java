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

package com.zilla.libraryzilla.test.zlistview;

import android.view.View;
import android.widget.TextView;

import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.api.GitHubService;
import com.zilla.libraryzilla.test.api.model.Org;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zilla.libcore.api.RetrofitAPI;
import zilla.libcore.ui.InjectLayout;
import zilla.libzilla.listview.ZListViewWraper;

//import zilla.libcore.api.ZillaApi;

@InjectLayout(R.layout.activity_zlistviewtest)
public class ZListViewActivity extends BaseActivity {

    private ZListViewWraper<Org> xListViewWraper;


    @Override
    protected void initViews() {
        final GitHubService service = RetrofitAPI.createService(GitHubService.class);
        final Call<List<Org>> call=service.getRepos("octokit");
        xListViewWraper = new ZListViewWraper<Org>(getWindow().getDecorView(), R.layout.item_zlistview, ViewHolder.class) {
            @Override
            public void loadData() {

                call.enqueue(new Callback<List<Org>>() {
                    @Override
                    public void onResponse(Call<List<Org>> call, Response<List<Org>> response) {
                        if(response.isSuccessful()){
                            xListViewWraper.setModelList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Org>> call, Throwable t) {
                        xListViewWraper.refreshFail();
                    }
                });
            }

            @Override
            public void loadMore() {
                call.enqueue(new Callback<List<Org>>() {
                    @Override
                    public void onResponse(Call<List<Org>> call, Response<List<Org>> response) {
                        if(response.isSuccessful()){
                            xListViewWraper.setModelList(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Org>> call, Throwable t) {
                        xListViewWraper.refreshFail();
                    }
                });
            }
        };
    }

    @Override
    protected void initDatas() {

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'item_zlistview.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.item_org_name)
        TextView name;
        @Bind(R.id.item_org_full_name)
        TextView full_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
