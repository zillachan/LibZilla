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

import com.zilla.libraryzilla.test.api.model.Org;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import zilla.libcore.api.annotation.Dialog;

/**
 * Created by zilla on 10/9/15.
 */
public interface GitHubService {

    /**
     * API2 call
     *
     * @param org
     * @return
     */

    @GET("orgs/{org}/repos")
    @Dialog("loading...")
    Call<List<Org>> getRepos(@Path("org") String org);

    /**
     * API2 RXjava
     *
     * @param org
     * @return
     */
    @GET("/orgs/{org}/repos")
    Observable<List<Org>> getReposRx(@Path("org") String org);

}
