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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import zilla.libcore.api.ZillaApi;
import zilla.libcore.ui.InjectLayout;
import zilla.libzilla.listview.ZListViewWraper;

@InjectLayout(R.layout.activity_zlistviewtest)
public class ZListViewActivity extends BaseActivity {

    private ZListViewWraper<Org> xListViewWraper;
    GitHubService service;

    @Override
    protected void initViews() {
        service = ZillaApi.normalRestAdapter.create(GitHubService.class);
        xListViewWraper = new ZListViewWraper<Org>(getWindow().getDecorView(), R.layout.item_zlistview, ViewHolder.class) {
            @Override
            public void loadData() {
                service.getRepos("octokit", new Callback<List<Org>>() {
                    @Override
                    public void success(List<Org> orgs, Response response) {
                        xListViewWraper.setModelList(orgs);
//                        xListViewWraper.setModelList(new ArrayList<Org>());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        xListViewWraper.refreshFail();
                    }
                });
            }

            @Override
            public void loadMore() {
                service.getRepos("octokit", new Callback<List<Org>>() {
                    @Override
                    public void success(List<Org> orgs, Response response) {
                        xListViewWraper.addModelList(orgs);
                    }

                    @Override
                    public void failure(RetrofitError error) {
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
        @BindView(R.id.item_org_name)
        TextView name;
        @BindView(R.id.item_org_full_name)
        TextView full_name;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
