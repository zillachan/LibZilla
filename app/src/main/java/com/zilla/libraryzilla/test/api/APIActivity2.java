package com.zilla.libraryzilla.test.api;

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.api.model.Org;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zilla.libcore.api.RetrofitAPI;
import zilla.libcore.api.annotation.Dismiss;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_api2)
public class APIActivity2 extends BaseActivity{

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        GitHubService service= RetrofitAPI.createService(GitHubService.class);
        Call<List<Org>> call=service.getRepos("octokit");
        call.enqueue(new Callback<List<Org>>() {
            @Override
            @Dismiss
            public void onResponse(Call<List<Org>> call, Response<List<Org>> response) {
                List<Org> orgs=response.body();
                Log.i(orgs.toString());
            }

            @Override
            @Dismiss
            public void onFailure(Call<List<Org>> call, Throwable t) {
                Log.i("请求被取消了");
                t.printStackTrace();
            }
        });
    }



}
