/*
Copyright 2015 Zilla Chen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.zilla.android.zillacore.libzilla.util;

import android.text.TextUtils;

import com.zilla.android.zillacore.libzilla.file.SharedPreferenceService;

/**
 * HTTP Basic Authentication <br>
 * http://en.wikipedia.org/wiki/Basic_authentication_scheme
 * 用户账号管理
 * Created by chenze on 13-12-4.
 */
public class UserManager {

    private static UserManager userManager = null;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    public String getUserName() {
        return SharedPreferenceService.getInstance().get("userName", "");
    }

    public void saveUserName(String userName) {
        SharedPreferenceService.getInstance().put("userName", userName);
    }

    public String getPassword() {
        return SharedPreferenceService.getInstance().get("password", "");
    }

    public void savePassword(String password) {
        SharedPreferenceService.getInstance().put("password", password);
    }

    /**
     * 是否登录
     *
     * @return isLogin
     */
    public boolean isLogin() {
        String userName = getUserName();
        String psw = getPassword();
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(psw)) {
            return false;
        }
        return true;
    }

    public void clearCache() {
        SharedPreferenceService.getInstance().put("userName", "");
        SharedPreferenceService.getInstance().put("password", "");
    }

    /**
     * 获取Author
     *
     * @return author
     */
    public String getAuthor() {
        return "Basic " + Util.base64_encode(getUserName() + ":" + getPassword()).trim();
    }
}
