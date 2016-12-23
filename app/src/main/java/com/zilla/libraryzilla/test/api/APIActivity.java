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

import com.github.snowdream.android.util.Log;
import com.zilla.libraryzilla.R;
import com.zilla.libraryzilla.common.BaseActivity;
import com.zilla.libraryzilla.test.api.model.Org;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zilla.libcore.api.RetrofitAPI;
import zilla.libcore.api.annotation.Dismiss;
import zilla.libcore.ui.InjectLayout;

@InjectLayout(R.layout.activity_api)
public class APIActivity extends BaseActivity {


    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        //getDataByAPI();

        getDataByAPI2();
        /*String data="I am a string to be prepared";
        *//*Observable.just(data)
                .subscribe(str->Log.i(str));

        String[] strs={"I","am","a","person"};
        Observable.fromArray(strs).subscribe(str->Log.i(str));*//*
        List<String> list=new ArrayList<>();
        list.add("I");
        list.add("am");
        list.add("a");
        list.add("person");
        Observable.fromIterable(list)
                .map(str->str.hashCode())
                .subscribe(num->Log.i(num+""));
        Observable.fromArray(1,2,3,4,5)
                .filter(integer -> integer>3)
                .subscribe(integer -> Log.i(integer+""));

        *//*Observable.interval(0,1, TimeUnit.SECONDS)
        .subscribe(num->Log.i(num+""));*//*
//        Observable.timer(3,TimeUnit.SECONDS).subscribe(num->Log.i(num+""));
        Observable.range(2,5).subscribe(num->{Log.i(num+"");});
        *//*Flowable.just("Hello,I am China!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("accept="+s);
                    }
                });*//*
        *//*Flowable.fromArray("Hello,I am China!")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("accept="+s);
                    }
                });*//*
        Flowable.just("Hello,I am China!")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s+"add_char";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("accept="+s);
                    }
                });

        //ç®?ä¾¿æ–¹æ³?
*//*        Flowable.fromIterable(list)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("accept="+s);
                    }
                });*//*
        //ä¼ ç»Ÿä½¿ç”¨

        Flowable.just(list)
                .flatMap(stringList->Flowable.fromIterable(stringList))
                .filter(str->!str.startsWith("a"))
                .take(2)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/

    }

    private void getDataByAPI2() {
        GitHubService service = RetrofitAPI.createService(GitHubService.class);
        Call<List<Org>> call = service.getRepos("octokit");
        call.enqueue(new Callback<List<Org>>() {
            @Dismiss
            public void onResponse(Call<List<Org>> call, Response<List<Org>> response) {
                if(response.isSuccessful()){
                    Log.i(response.body().toString());
                }
            }
            @Dismiss
            public void onFailure(Call<List<Org>> call, Throwable t) {
                Log.i("dialog ±»È¡ÏûÁËÑ½?t===="+(t==null));
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

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
