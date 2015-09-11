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
package com.zilla.android.zillacore.libzilla.module;

import android.content.Intent;
import android.text.TextUtils;

import com.github.snowdream.android.util.Log;
import com.zilla.android.zillacore.libzilla.Zilla;
import com.zilla.android.zillacore.libzilla.module.exception.MalformedZillaURLException;
import com.zilla.android.zillacore.libzilla.module.exception.ModuleTypeException;

/**
 * 模块过滤器<br>
 * 定义模块跳转协议，统一管理模块跳转问题
 *
 * @author ze.chen
 * @version 1.0
 */
public class ModuleFilter {

    private IQueryContent mContext;

    public ModuleFilter(IQueryContent context) {
        this.mContext = context;
    }

    public void goModule(String zillaURL) {
        try {
            ZillaURL url = new ZillaURL(zillaURL);
            String module = getmoduleclassName(url.getModule());
            String path = getPathClassName(url.getModule() + url.getPath());
            String query = url.getQuery();
            if (TextUtils.isEmpty(path)) {
                goClass(module, query);
            } else {
                goClass(path, query);
            }
        } catch (MalformedZillaURLException e) {
            e.printStackTrace();
        } catch (ModuleTypeException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Fragment ,如果为空返回null
     *
     * @param zillaURL The ZillaURL formate url
     * @return 实现了IQueryContent接口的Fragment
     */
    public IQueryContent getFragment(String zillaURL) {
        try {
            ZillaURL url = new ZillaURL(zillaURL);
            String module = getmoduleclassName(url.getModule());
            Class c = Class.forName(module);
            IQueryContent fragment = (IQueryContent) c.newInstance();
            //fix ze.chen      处理URL跳转时，信息无法传到fragment的问题
            fragment.setQuery(url.getQuery());
            return fragment;
        } catch (MalformedZillaURLException e) {
            Log.e(e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e(e.getMessage());
        } catch (InstantiationException e) {
            Log.e(e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(e.getMessage());
        }
        return null;
    }

    /**
     * 跳转的实现代码<br>
     * 通过给定的类名和参数，跳转并传入参数
     *
     * @param className
     * @param query
     * @throws ModuleTypeException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private void goClass(String className, String query) throws ModuleTypeException, ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        Class c = Class.forName(className);
        if (c.getName().endsWith("Activity")) {
            Intent intent = new Intent(Zilla.ACTIVITY, c);
            intent.putExtra("query", query);
            mContext.startActivity(intent);
        } else if (c.getName().endsWith("Fragment")) {
            IQueryContent fragment = (IQueryContent) c.newInstance();
            fragment.setQuery(query);
            mContext.changeContent(fragment);
        } else {
            throw new ModuleTypeException("the module is not Fragment or Activity!");
        }
    }

    private String getmoduleclassName(String module) {
        return ModulePropertiesManager.get(module, "");
    }

    private String getPathClassName(String path) {
        return ModulePropertiesManager.get(path, "");
    }
}
